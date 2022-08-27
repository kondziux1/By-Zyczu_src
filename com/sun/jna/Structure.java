package com.sun.jna;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.zip.Adler32;

public abstract class Structure {
   static final boolean isPPC;
   static final boolean isSPARC;
   static final boolean isARM;
   public static final int ALIGN_DEFAULT = 0;
   public static final int ALIGN_NONE = 1;
   public static final int ALIGN_GNUC = 2;
   public static final int ALIGN_MSVC = 3;
   static final int MAX_GNUC_ALIGNMENT = !isSPARC && (!isPPC && !isARM || !Platform.isLinux() && !Platform.isAndroid()) && !Platform.isAix()
      ? Native.LONG_SIZE
      : 8;
   protected static final int CALCULATE_SIZE = -1;
   static final Map layoutInfo = new WeakHashMap();
   static final Map fieldOrder = new WeakHashMap();
   private Pointer memory;
   private int size = -1;
   private int alignType;
   private int actualAlignType;
   private int structAlignment;
   private Map structFields;
   private final Map nativeStrings = new HashMap();
   private TypeMapper typeMapper;
   private long typeInfo;
   private boolean autoRead = true;
   private boolean autoWrite = true;
   private Structure[] array;
   private static final ThreadLocal reads = new Structure$1();
   private static final ThreadLocal busy = new Structure$2();

   protected Structure() {
      this(0);
   }

   protected Structure(TypeMapper mapper) {
      this(null, 0, mapper);
   }

   protected Structure(int alignType) {
      this(null, alignType);
   }

   protected Structure(int alignType, TypeMapper mapper) {
      this(null, alignType, mapper);
   }

   protected Structure(Pointer p) {
      this(p, 0);
   }

   protected Structure(Pointer p, int alignType) {
      this(p, alignType, null);
   }

   protected Structure(Pointer p, int alignType, TypeMapper mapper) {
      super();
      this.setAlignType(alignType);
      this.initializeTypeMapper(mapper);
      this.validateFields();
      if (p != null) {
         this.useMemory(p);
      } else {
         this.allocateMemory(-1);
      }

      this.initializeFields();
   }

   Map fields() {
      return this.structFields;
   }

   TypeMapper getTypeMapper() {
      return this.typeMapper;
   }

   /** @deprecated */
   protected void setTypeMapper(TypeMapper mapper) {
      this.initializeTypeMapper(mapper);
   }

   private void initializeTypeMapper(TypeMapper mapper) {
      if (mapper == null) {
         Class declaring = this.getClass().getDeclaringClass();
         if (declaring != null) {
            mapper = Native.getTypeMapper(declaring);
         }
      }

      this.typeMapper = mapper;
      this.layoutChanged();
   }

   private void layoutChanged() {
      if (this.size != -1) {
         this.size = -1;
         if (this.memory instanceof Structure.AutoAllocated) {
            this.memory = null;
         }

         this.ensureAllocated();
      }

   }

   protected void setAlignType(int alignType) {
      this.alignType = alignType;
      if (alignType == 0) {
         Class declaring = this.getClass().getDeclaringClass();
         if (declaring != null) {
            alignType = Native.getStructureAlignment(declaring);
         }

         if (alignType == 0) {
            if (Platform.isWindows()) {
               alignType = 3;
            } else {
               alignType = 2;
            }
         }
      }

      this.actualAlignType = alignType;
      this.layoutChanged();
   }

   protected Memory autoAllocate(int size) {
      return new Structure.AutoAllocated(size);
   }

   protected void useMemory(Pointer m) {
      this.useMemory(m, 0);
   }

   protected void useMemory(Pointer m, int offset) {
      try {
         this.memory = m.share((long)offset);
         if (this.size == -1) {
            this.size = this.calculateSize(false);
         }

         if (this.size != -1) {
            this.memory = m.share((long)offset, (long)this.size);
         }

         this.array = null;
      } catch (IndexOutOfBoundsException var4) {
         throw new IllegalArgumentException("Structure exceeds provided memory bounds");
      }
   }

   protected void ensureAllocated() {
      this.ensureAllocated(false);
   }

   private void ensureAllocated(boolean avoidFFIType) {
      if (this.memory == null) {
         this.allocateMemory(avoidFFIType);
      } else if (this.size == -1) {
         this.size = this.calculateSize(true, avoidFFIType);
         if (!(this.memory instanceof Structure.AutoAllocated)) {
            try {
               this.memory = this.memory.share(0L, (long)this.size);
            } catch (IndexOutOfBoundsException var3) {
               throw new IllegalArgumentException("Structure exceeds provided memory bounds");
            }
         }
      }

   }

   protected void allocateMemory() {
      this.allocateMemory(false);
   }

   private void allocateMemory(boolean avoidFFIType) {
      this.allocateMemory(this.calculateSize(true, avoidFFIType));
   }

   protected void allocateMemory(int size) {
      if (size == -1) {
         size = this.calculateSize(false);
      } else if (size <= 0) {
         throw new IllegalArgumentException("Structure size must be greater than zero: " + size);
      }

      if (size != -1) {
         if (this.memory == null || this.memory instanceof Structure.AutoAllocated) {
            this.memory = this.autoAllocate(size);
         }

         this.size = size;
      }

   }

   public int size() {
      this.ensureAllocated();
      return this.size;
   }

   public void clear() {
      this.ensureAllocated();
      this.memory.clear((long)this.size());
   }

   public Pointer getPointer() {
      this.ensureAllocated();
      return this.memory;
   }

   static Set busy() {
      return (Set)busy.get();
   }

   static Map reading() {
      return (Map)reads.get();
   }

   public void read() {
      this.ensureAllocated();
      if (!busy().contains(this)) {
         busy().add(this);
         if (this instanceof Structure.ByReference) {
            reading().put(this.getPointer(), this);
         }

         try {
            Iterator i = this.fields().values().iterator();

            while(i.hasNext()) {
               this.readField((Structure.StructField)i.next());
            }
         } finally {
            busy().remove(this);
            if (reading().get(this.getPointer()) == this) {
               reading().remove(this.getPointer());
            }

         }

      }
   }

   protected int fieldOffset(String name) {
      this.ensureAllocated();
      Structure.StructField f = (Structure.StructField)this.fields().get(name);
      if (f == null) {
         throw new IllegalArgumentException("No such field: " + name);
      } else {
         return f.offset;
      }
   }

   public Object readField(String name) {
      this.ensureAllocated();
      Structure.StructField f = (Structure.StructField)this.fields().get(name);
      if (f == null) {
         throw new IllegalArgumentException("No such field: " + name);
      } else {
         return this.readField(f);
      }
   }

   Object getFieldValue(Field field) {
      try {
         return field.get(this);
      } catch (Exception var3) {
         throw new Error("Exception reading field '" + field.getName() + "' in " + this.getClass() + ": " + var3);
      }
   }

   void setFieldValue(Field field, Object value) {
      this.setFieldValue(field, value, false);
   }

   private void setFieldValue(Field field, Object value, boolean overrideFinal) {
      try {
         field.set(this, value);
      } catch (IllegalAccessException var6) {
         int modifiers = field.getModifiers();
         if (Modifier.isFinal(modifiers)) {
            if (overrideFinal) {
               throw new UnsupportedOperationException(
                  "This VM does not support Structures with final fields (field '" + field.getName() + "' within " + this.getClass() + ")"
               );
            } else {
               throw new UnsupportedOperationException("Attempt to write to read-only field '" + field.getName() + "' within " + this.getClass());
            }
         } else {
            throw new Error("Unexpectedly unable to write to field '" + field.getName() + "' within " + this.getClass() + ": " + var6);
         }
      }
   }

   static Structure updateStructureByReference(Class type, Structure s, Pointer address) {
      if (address == null) {
         s = null;
      } else {
         if (s == null || !address.equals(s.getPointer())) {
            Structure s1 = (Structure)reading().get(address);
            if (s1 != null && type.equals(s1.getClass())) {
               s = s1;
            } else {
               s = newInstance(type);
               s.useMemory(address);
            }
         }

         s.autoRead();
      }

      return s;
   }

   Object readField(Structure.StructField structField) {
      int offset = structField.offset;
      Class fieldType = structField.type;
      FromNativeConverter readConverter = structField.readConverter;
      if (readConverter != null) {
         fieldType = readConverter.nativeType();
      }

      Object currentValue = !Structure.class.isAssignableFrom(fieldType)
            && !Callback.class.isAssignableFrom(fieldType)
            && (!Platform.HAS_BUFFERS || !Buffer.class.isAssignableFrom(fieldType))
            && !Pointer.class.isAssignableFrom(fieldType)
            && !NativeMapped.class.isAssignableFrom(fieldType)
            && !fieldType.isArray()
         ? null
         : this.getFieldValue(structField.field);
      Object result = this.memory.getValue((long)offset, fieldType, currentValue);
      if (readConverter != null) {
         result = readConverter.fromNative(result, structField.context);
         if (currentValue != null && currentValue.equals(result)) {
            result = currentValue;
         }
      }

      if (fieldType.equals(String.class) || fieldType.equals(WString.class)) {
         this.nativeStrings.put(structField.name + ".ptr", this.memory.getPointer((long)offset));
         this.nativeStrings.put(structField.name + ".val", result);
      }

      this.setFieldValue(structField.field, result, true);
      return result;
   }

   public void write() {
      this.ensureAllocated();
      if (this instanceof Structure.ByValue) {
         this.getTypeInfo();
      }

      if (!busy().contains(this)) {
         busy().add(this);

         try {
            for(Structure.StructField sf : this.fields().values()) {
               if (!sf.isVolatile) {
                  this.writeField(sf);
               }
            }
         } finally {
            busy().remove(this);
         }

      }
   }

   public void writeField(String name) {
      this.ensureAllocated();
      Structure.StructField f = (Structure.StructField)this.fields().get(name);
      if (f == null) {
         throw new IllegalArgumentException("No such field: " + name);
      } else {
         this.writeField(f);
      }
   }

   public void writeField(String name, Object value) {
      this.ensureAllocated();
      Structure.StructField structField = (Structure.StructField)this.fields().get(name);
      if (structField == null) {
         throw new IllegalArgumentException("No such field: " + name);
      } else {
         this.setFieldValue(structField.field, value);
         this.writeField(structField);
      }
   }

   void writeField(Structure.StructField structField) {
      if (!structField.isReadOnly) {
         int offset = structField.offset;
         Object value = this.getFieldValue(structField.field);
         Class fieldType = structField.type;
         ToNativeConverter converter = structField.writeConverter;
         if (converter != null) {
            value = converter.toNative(value, new StructureWriteContext(this, structField.field));
            fieldType = converter.nativeType();
         }

         if (String.class == fieldType || WString.class == fieldType) {
            boolean wide = fieldType == WString.class;
            if (value != null) {
               if (this.nativeStrings.containsKey(structField.name + ".ptr") && value.equals(this.nativeStrings.get(structField.name + ".val"))) {
                  return;
               }

               NativeString nativeString = new NativeString(value.toString(), wide);
               this.nativeStrings.put(structField.name, nativeString);
               value = nativeString.getPointer();
            } else {
               this.nativeStrings.remove(structField.name);
            }

            this.nativeStrings.remove(structField.name + ".ptr");
            this.nativeStrings.remove(structField.name + ".val");
         }

         try {
            this.memory.setValue((long)offset, value, fieldType);
         } catch (IllegalArgumentException var8) {
            String msg = "Structure field \""
               + structField.name
               + "\" was declared as "
               + structField.type
               + (structField.type == fieldType ? "" : " (native type " + fieldType + ")")
               + ", which is not supported within a Structure";
            throw new IllegalArgumentException(msg);
         }
      }
   }

   protected abstract List getFieldOrder();

   /** @deprecated */
   protected final void setFieldOrder(String[] fields) {
      throw new Error("This method is obsolete, use getFieldOrder() instead");
   }

   protected void sortFields(List fields, List names) {
      for(int i = 0; i < names.size(); ++i) {
         String name = (String)names.get(i);

         for(int f = 0; f < fields.size(); ++f) {
            Field field = (Field)fields.get(f);
            if (name.equals(field.getName())) {
               Collections.swap(fields, i, f);
               break;
            }
         }
      }

   }

   protected List getFieldList() {
      List flist = new ArrayList();

      for(Class cls = this.getClass(); !cls.equals(Structure.class); cls = cls.getSuperclass()) {
         List classFields = new ArrayList();
         Field[] fields = cls.getDeclaredFields();

         for(int i = 0; i < fields.length; ++i) {
            int modifiers = fields[i].getModifiers();
            if (!Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers)) {
               classFields.add(fields[i]);
            }
         }

         flist.addAll(0, classFields);
      }

      return flist;
   }

   private List fieldOrder() {
      synchronized(fieldOrder) {
         List list = (List)fieldOrder.get(this.getClass());
         if (list == null) {
            list = this.getFieldOrder();
            fieldOrder.put(this.getClass(), list);
         }

         return list;
      }
   }

   private List sort(Collection c) {
      List list = new ArrayList(c);
      Collections.sort(list);
      return list;
   }

   protected List getFields(boolean force) {
      List flist = this.getFieldList();
      Set names = new HashSet();
      Iterator i = flist.iterator();

      while(i.hasNext()) {
         names.add(((Field)i.next()).getName());
      }

      List fieldOrder = this.fieldOrder();
      if (fieldOrder.size() == flist.size() || flist.size() <= 1) {
         Set orderedNames = new HashSet(fieldOrder);
         if (!orderedNames.equals(names)) {
            throw new Error(
               "Structure.getFieldOrder() on "
                  + this.getClass()
                  + " returns names ("
                  + this.sort(fieldOrder)
                  + ") which do not match declared field names ("
                  + this.sort(names)
                  + ")"
            );
         } else {
            this.sortFields(flist, fieldOrder);
            return flist;
         }
      } else if (force) {
         throw new Error(
            "Structure.getFieldOrder() on "
               + this.getClass()
               + " does not provide enough names ("
               + this.sort(fieldOrder)
               + ") to match declared fields ("
               + this.sort(names)
               + ")"
         );
      } else {
         return null;
      }
   }

   private int calculateSize(boolean force) {
      return this.calculateSize(force, false);
   }

   int calculateSize(boolean force, boolean avoidFFIType) {
      int size = -1;
      Structure.LayoutInfo info;
      synchronized(layoutInfo) {
         info = (Structure.LayoutInfo)layoutInfo.get(this.getClass());
      }

      if (info == null || this.alignType != info.alignType || this.typeMapper != info.typeMapper) {
         info = this.deriveLayout(force, avoidFFIType);
      }

      if (info != null) {
         this.structAlignment = info.alignment;
         this.structFields = info.fields;
         if (!info.variable) {
            synchronized(layoutInfo) {
               if (!layoutInfo.containsKey(this.getClass()) || this.alignType != 0 || this.typeMapper != null) {
                  layoutInfo.put(this.getClass(), info);
               }
            }
         }

         size = info.size;
      }

      return size;
   }

   private void validateField(String name, Class type) {
      if (this.typeMapper != null) {
         ToNativeConverter toNative = this.typeMapper.getToNativeConverter(type);
         if (toNative != null) {
            this.validateField(name, toNative.nativeType());
            return;
         }
      }

      if (type.isArray()) {
         this.validateField(name, type.getComponentType());
      } else {
         try {
            this.getNativeSize(type);
         } catch (IllegalArgumentException var5) {
            String msg = "Invalid Structure field in " + this.getClass() + ", field name '" + name + "' (" + type + "): " + var5.getMessage();
            throw new IllegalArgumentException(msg);
         }
      }

   }

   private void validateFields() {
      for(Field f : this.getFieldList()) {
         this.validateField(f.getName(), f.getType());
      }

   }

   private Structure.LayoutInfo deriveLayout(boolean force, boolean avoidFFIType) {
      int calculatedSize = 0;
      List fields = this.getFields(force);
      if (fields == null) {
         return null;
      } else {
         Structure.LayoutInfo info = new Structure.LayoutInfo(null);
         info.alignType = this.alignType;
         info.typeMapper = this.typeMapper;
         boolean firstField = true;

         for(Field field : fields) {
            int modifiers = field.getModifiers();
            Class type = field.getType();
            if (type.isArray()) {
               info.variable = true;
            }

            Structure.StructField structField = new Structure.StructField();
            structField.isVolatile = Modifier.isVolatile(modifiers);
            structField.isReadOnly = Modifier.isFinal(modifiers);
            if (structField.isReadOnly) {
               if (!Platform.RO_FIELDS) {
                  throw new IllegalArgumentException(
                     "This VM does not support read-only fields (field '" + field.getName() + "' within " + this.getClass() + ")"
                  );
               }

               field.setAccessible(true);
            }

            structField.field = field;
            structField.name = field.getName();
            structField.type = type;
            if (Callback.class.isAssignableFrom(type) && !type.isInterface()) {
               throw new IllegalArgumentException("Structure Callback field '" + field.getName() + "' must be an interface");
            }

            if (type.isArray() && Structure.class.equals(type.getComponentType())) {
               String msg = "Nested Structure arrays must use a derived Structure type so that the size of the elements can be determined";
               throw new IllegalArgumentException(msg);
            }

            int fieldAlignment = 1;
            if (Modifier.isPublic(field.getModifiers())) {
               Object value = this.getFieldValue(structField.field);
               if (value == null && type.isArray()) {
                  if (force) {
                     throw new IllegalStateException("Array fields must be initialized");
                  }

                  return null;
               }

               Class nativeType = type;
               if (NativeMapped.class.isAssignableFrom(type)) {
                  NativeMappedConverter tc = NativeMappedConverter.getInstance(type);
                  nativeType = tc.nativeType();
                  structField.writeConverter = tc;
                  structField.readConverter = tc;
                  structField.context = new StructureReadContext(this, field);
               } else if (this.typeMapper != null) {
                  ToNativeConverter writeConverter = this.typeMapper.getToNativeConverter(type);
                  FromNativeConverter readConverter = this.typeMapper.getFromNativeConverter(type);
                  if (writeConverter != null && readConverter != null) {
                     value = writeConverter.toNative(value, new StructureWriteContext(this, structField.field));
                     nativeType = value != null ? value.getClass() : Pointer.class;
                     structField.writeConverter = writeConverter;
                     structField.readConverter = readConverter;
                     structField.context = new StructureReadContext(this, field);
                  } else if (writeConverter != null || readConverter != null) {
                     String msg = "Structures require bidirectional type conversion for " + type;
                     throw new IllegalArgumentException(msg);
                  }
               }

               if (value == null) {
                  value = this.initializeField(structField.field, type);
               }

               try {
                  structField.size = this.getNativeSize(nativeType, value);
                  fieldAlignment = this.getNativeAlignment(nativeType, value, firstField);
               } catch (IllegalArgumentException var18) {
                  if (!force && this.typeMapper == null) {
                     return null;
                  }

                  String msg = "Invalid Structure field in "
                     + this.getClass()
                     + ", field name '"
                     + structField.name
                     + "' ("
                     + structField.type
                     + "): "
                     + var18.getMessage();
                  throw new IllegalArgumentException(msg);
               }

               info.alignment = Math.max(info.alignment, fieldAlignment);
               if (calculatedSize % fieldAlignment != 0) {
                  calculatedSize += fieldAlignment - calculatedSize % fieldAlignment;
               }

               structField.offset = calculatedSize;
               calculatedSize += structField.size;
               info.fields.put(structField.name, structField);
            }

            firstField = false;
         }

         if (calculatedSize > 0) {
            int size = this.calculateAlignedSize(calculatedSize, info.alignment);
            if (this instanceof Structure.ByValue && !avoidFFIType) {
               this.getTypeInfo();
            }

            info.size = size;
            return info;
         } else {
            throw new IllegalArgumentException("Structure " + this.getClass() + " has unknown size (ensure " + "all fields are public)");
         }
      }
   }

   private void initializeFields() {
      for(Field f : this.getFieldList()) {
         try {
            Object o = f.get(this);
            if (o == null) {
               this.initializeField(f, f.getType());
            }
         } catch (Exception var5) {
            throw new Error("Exception reading field '" + f.getName() + "' in " + this.getClass() + ": " + var5);
         }
      }

   }

   private Object initializeField(Field field, Class type) {
      Object value = null;
      if (Structure.class.isAssignableFrom(type) && !Structure.ByReference.class.isAssignableFrom(type)) {
         try {
            value = newInstance(type);
            this.setFieldValue(field, value);
         } catch (IllegalArgumentException var6) {
            String msg = "Can't determine size of nested structure: " + var6.getMessage();
            throw new IllegalArgumentException(msg);
         }
      } else if (NativeMapped.class.isAssignableFrom(type)) {
         NativeMappedConverter tc = NativeMappedConverter.getInstance(type);
         value = tc.defaultValue();
         this.setFieldValue(field, value);
      }

      return value;
   }

   int calculateAlignedSize(int calculatedSize) {
      return this.calculateAlignedSize(calculatedSize, this.structAlignment);
   }

   private int calculateAlignedSize(int calculatedSize, int alignment) {
      if (this.actualAlignType != 1 && calculatedSize % alignment != 0) {
         calculatedSize += alignment - calculatedSize % alignment;
      }

      return calculatedSize;
   }

   protected int getStructAlignment() {
      if (this.size == -1) {
         this.calculateSize(true);
      }

      return this.structAlignment;
   }

   protected int getNativeAlignment(Class type, Object value, boolean isFirstElement) {
      int alignment = 1;
      if (NativeMapped.class.isAssignableFrom(type)) {
         NativeMappedConverter tc = NativeMappedConverter.getInstance(type);
         type = tc.nativeType();
         value = tc.toNative(value, new ToNativeContext());
      }

      int size = Native.getNativeSize(type, value);
      if (!type.isPrimitive()
         && Long.class != type
         && Integer.class != type
         && Short.class != type
         && Character.class != type
         && Byte.class != type
         && Boolean.class != type
         && Float.class != type
         && Double.class != type) {
         if ((!Pointer.class.isAssignableFrom(type) || Function.class.isAssignableFrom(type))
            && (!Platform.HAS_BUFFERS || !Buffer.class.isAssignableFrom(type))
            && !Callback.class.isAssignableFrom(type)
            && WString.class != type
            && String.class != type) {
            if (Structure.class.isAssignableFrom(type)) {
               if (Structure.ByReference.class.isAssignableFrom(type)) {
                  alignment = Pointer.SIZE;
               } else {
                  if (value == null) {
                     value = newInstance(type);
                  }

                  alignment = ((Structure)value).getStructAlignment();
               }
            } else {
               if (!type.isArray()) {
                  throw new IllegalArgumentException("Type " + type + " has unknown " + "native alignment");
               }

               alignment = this.getNativeAlignment(type.getComponentType(), null, isFirstElement);
            }
         } else {
            alignment = Pointer.SIZE;
         }
      } else {
         alignment = size;
      }

      if (this.actualAlignType == 1) {
         alignment = 1;
      } else if (this.actualAlignType == 3) {
         alignment = Math.min(8, alignment);
      } else if (this.actualAlignType == 2) {
         if (!isFirstElement || !Platform.isMac() || !isPPC) {
            alignment = Math.min(MAX_GNUC_ALIGNMENT, alignment);
         }

         if (!isFirstElement && Platform.isAix() && type.getName().equals("double")) {
            alignment = 4;
         }
      }

      return alignment;
   }

   public String toString() {
      return this.toString(Boolean.getBoolean("jna.dump_memory"));
   }

   public String toString(boolean debug) {
      return this.toString(0, true, true);
   }

   private String format(Class type) {
      String s = type.getName();
      int dot = s.lastIndexOf(".");
      return s.substring(dot + 1);
   }

   private String toString(int indent, boolean showContents, boolean dumpMemory) {
      this.ensureAllocated();
      String LS = System.getProperty("line.separator");
      String name = this.format(this.getClass()) + "(" + this.getPointer() + ")";
      if (!(this.getPointer() instanceof Memory)) {
         name = name + " (" + this.size() + " bytes)";
      }

      String prefix = "";

      for(int idx = 0; idx < indent; ++idx) {
         prefix = prefix + "  ";
      }

      String contents = LS;
      if (!showContents) {
         contents = "...}";
      } else {
         Iterator i = this.fields().values().iterator();

         while(i.hasNext()) {
            Structure.StructField sf = (Structure.StructField)i.next();
            Object value = this.getFieldValue(sf.field);
            String type = this.format(sf.type);
            String index = "";
            contents = contents + prefix;
            if (sf.type.isArray() && value != null) {
               type = this.format(sf.type.getComponentType());
               index = "[" + Array.getLength(value) + "]";
            }

            contents = contents + "  " + type + " " + sf.name + index + "@" + Integer.toHexString(sf.offset);
            if (value instanceof Structure) {
               value = ((Structure)value).toString(indent + 1, !(value instanceof Structure.ByReference), dumpMemory);
            }

            contents = contents + "=";
            if (value instanceof Long) {
               contents = contents + Long.toHexString((Long)value);
            } else if (value instanceof Integer) {
               contents = contents + Integer.toHexString((Integer)value);
            } else if (value instanceof Short) {
               contents = contents + Integer.toHexString((Short)value);
            } else if (value instanceof Byte) {
               contents = contents + Integer.toHexString((Byte)value);
            } else {
               contents = contents + String.valueOf(value).trim();
            }

            contents = contents + LS;
            if (!i.hasNext()) {
               contents = contents + prefix + "}";
            }
         }
      }

      if (indent == 0 && dumpMemory) {
         int BYTES_PER_ROW = 4;
         contents = contents + LS + "memory dump" + LS;
         byte[] buf = this.getPointer().getByteArray(0L, this.size());

         for(int i = 0; i < buf.length; ++i) {
            if (i % 4 == 0) {
               contents = contents + "[";
            }

            if (buf[i] >= 0 && buf[i] < 16) {
               contents = contents + "0";
            }

            contents = contents + Integer.toHexString(buf[i] & 255);
            if (i % 4 == 3 && i < buf.length - 1) {
               contents = contents + "]" + LS;
            }
         }

         contents = contents + "]";
      }

      return name + " {" + contents;
   }

   public Structure[] toArray(Structure[] array) {
      this.ensureAllocated();
      if (this.memory instanceof Structure.AutoAllocated) {
         Memory m = (Memory)this.memory;
         int requiredSize = array.length * this.size();
         if (m.size() < (long)requiredSize) {
            this.useMemory(this.autoAllocate(requiredSize));
         }
      }

      array[0] = this;
      int size = this.size();

      for(int i = 1; i < array.length; ++i) {
         array[i] = newInstance(this.getClass());
         array[i].useMemory(this.memory.share((long)(i * size), (long)size));
         array[i].read();
      }

      if (!(this instanceof Structure.ByValue)) {
         this.array = array;
      }

      return array;
   }

   public Structure[] toArray(int size) {
      return this.toArray((Structure[])Array.newInstance(this.getClass(), size));
   }

   private Class baseClass() {
      return (this instanceof Structure.ByReference || this instanceof Structure.ByValue) && Structure.class.isAssignableFrom(this.getClass().getSuperclass())
         ? this.getClass().getSuperclass()
         : this.getClass();
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof Structure)) {
         return false;
      } else if (o.getClass() != this.getClass() && ((Structure)o).baseClass() != this.baseClass()) {
         return false;
      } else {
         Structure s = (Structure)o;
         if (s.getPointer().equals(this.getPointer())) {
            return true;
         } else if (s.size() == this.size()) {
            this.clear();
            this.write();
            byte[] buf = this.getPointer().getByteArray(0L, this.size());
            s.clear();
            s.write();
            byte[] sbuf = s.getPointer().getByteArray(0L, s.size());
            return Arrays.equals(buf, sbuf);
         } else {
            return false;
         }
      }
   }

   public int hashCode() {
      this.clear();
      this.write();
      Adler32 code = new Adler32();
      code.update(this.getPointer().getByteArray(0L, this.size()));
      return (int)code.getValue();
   }

   protected void cacheTypeInfo(Pointer p) {
      this.typeInfo = p.peer;
   }

   Pointer getFieldTypeInfo(Structure.StructField f) {
      Class type = f.type;
      Object value = this.getFieldValue(f.field);
      if (this.typeMapper != null) {
         ToNativeConverter nc = this.typeMapper.getToNativeConverter(type);
         if (nc != null) {
            type = nc.nativeType();
            value = nc.toNative(value, new ToNativeContext());
         }
      }

      return Structure.FFIType.get(value, type);
   }

   Pointer getTypeInfo() {
      Pointer p = getTypeInfo(this);
      this.cacheTypeInfo(p);
      return p;
   }

   public void setAutoSynch(boolean auto) {
      this.setAutoRead(auto);
      this.setAutoWrite(auto);
   }

   public void setAutoRead(boolean auto) {
      this.autoRead = auto;
   }

   public boolean getAutoRead() {
      return this.autoRead;
   }

   public void setAutoWrite(boolean auto) {
      this.autoWrite = auto;
   }

   public boolean getAutoWrite() {
      return this.autoWrite;
   }

   static Pointer getTypeInfo(Object obj) {
      return Structure.FFIType.get(obj);
   }

   public static Structure newInstance(Class type) throws IllegalArgumentException {
      try {
         Structure s = (Structure)type.newInstance();
         if (s instanceof Structure.ByValue) {
            s.allocateMemory();
         }

         return s;
      } catch (InstantiationException var3) {
         String msg = "Can't instantiate " + type + " (" + var3 + ")";
         throw new IllegalArgumentException(msg);
      } catch (IllegalAccessException var4) {
         String msgx = "Instantiation of " + type + " not allowed, is it public? (" + var4 + ")";
         throw new IllegalArgumentException(msgx);
      }
   }

   private static void structureArrayCheck(Structure[] ss) {
      if (!Structure.ByReference[].class.isAssignableFrom(ss.getClass())) {
         Pointer base = ss[0].getPointer();
         int size = ss[0].size();

         for(int si = 1; si < ss.length; ++si) {
            if (ss[si].getPointer().peer != base.peer + (long)(size * si)) {
               String msg = "Structure array elements must use contiguous memory (bad backing address at Structure array index " + si + ")";
               throw new IllegalArgumentException(msg);
            }
         }

      }
   }

   public static void autoRead(Structure[] ss) {
      structureArrayCheck(ss);
      if (ss[0].array == ss) {
         ss[0].autoRead();
      } else {
         for(int si = 0; si < ss.length; ++si) {
            if (ss[si] != null) {
               ss[si].autoRead();
            }
         }
      }

   }

   public void autoRead() {
      if (this.getAutoRead()) {
         this.read();
         if (this.array != null) {
            for(int i = 1; i < this.array.length; ++i) {
               this.array[i].autoRead();
            }
         }
      }

   }

   public static void autoWrite(Structure[] ss) {
      structureArrayCheck(ss);
      if (ss[0].array == ss) {
         ss[0].autoWrite();
      } else {
         for(int si = 0; si < ss.length; ++si) {
            if (ss[si] != null) {
               ss[si].autoWrite();
            }
         }
      }

   }

   public void autoWrite() {
      if (this.getAutoWrite()) {
         this.write();
         if (this.array != null) {
            for(int i = 1; i < this.array.length; ++i) {
               this.array[i].autoWrite();
            }
         }
      }

   }

   protected int getNativeSize(Class nativeType) {
      return this.getNativeSize(nativeType, null);
   }

   protected int getNativeSize(Class nativeType, Object value) {
      return Native.getNativeSize(nativeType, value);
   }

   static {
      String arch = System.getProperty("os.arch").toLowerCase();
      isPPC = "ppc".equals(arch) || "powerpc".equals(arch);
      isSPARC = "sparc".equals(arch);
      isARM = arch.startsWith("arm");
   }

   private static class AutoAllocated extends Memory {
      public AutoAllocated(int size) {
         super((long)size);
         super.clear();
      }

      public String toString() {
         return "auto-" + super.toString();
      }
   }

   public interface ByReference {
   }

   public interface ByValue {
   }

   static class FFIType extends Structure {
      private static Map typeInfoMap = new WeakHashMap();
      private static final int FFI_TYPE_STRUCT = 13;
      public Structure.FFIType.size_t size;
      public short alignment;
      public short type = 13;
      public Pointer elements;

      private FFIType(Structure ref) {
         super();
         ref.ensureAllocated(true);
         Pointer[] els;
         if (ref instanceof Union) {
            Structure.StructField sf = ((Union)ref).biggestField;
            els = new Pointer[]{get(ref.getFieldValue(sf.field), sf.type), null};
         } else {
            els = new Pointer[ref.fields().size() + 1];
            int idx = 0;

            for(Structure.StructField sf : ref.fields().values()) {
               els[idx++] = ref.getFieldTypeInfo(sf);
            }
         }

         this.init(els);
      }

      private FFIType(Object array, Class type) {
         super();
         int length = Array.getLength(array);
         Pointer[] els = new Pointer[length + 1];
         Pointer p = get(null, type.getComponentType());

         for(int i = 0; i < length; ++i) {
            els[i] = p;
         }

         this.init(els);
      }

      protected List getFieldOrder() {
         return Arrays.asList("size", "alignment", "type", "elements");
      }

      private void init(Pointer[] els) {
         this.elements = new Memory((long)(Pointer.SIZE * els.length));
         this.elements.write(0L, els, 0, els.length);
         this.write();
      }

      static Pointer get(Object obj) {
         if (obj == null) {
            return Structure.FFIType.FFITypes.ffi_type_pointer;
         } else {
            return obj instanceof Class ? get(null, (Class)obj) : get(obj, obj.getClass());
         }
      }

      private static Pointer get(Object obj, Class cls) {
         TypeMapper mapper = Native.getTypeMapper(cls);
         if (mapper != null) {
            ToNativeConverter nc = mapper.getToNativeConverter(cls);
            if (nc != null) {
               cls = nc.nativeType();
            }
         }

         synchronized(typeInfoMap) {
            Object o = typeInfoMap.get(cls);
            if (o instanceof Pointer) {
               return (Pointer)o;
            } else if (o instanceof Structure.FFIType) {
               return ((Structure.FFIType)o).getPointer();
            } else if ((!Platform.HAS_BUFFERS || !Buffer.class.isAssignableFrom(cls)) && !Callback.class.isAssignableFrom(cls)) {
               if (Structure.class.isAssignableFrom(cls)) {
                  if (obj == null) {
                     obj = newInstance(cls);
                  }

                  if (Structure.ByReference.class.isAssignableFrom(cls)) {
                     typeInfoMap.put(cls, Structure.FFIType.FFITypes.ffi_type_pointer);
                     return Structure.FFIType.FFITypes.ffi_type_pointer;
                  } else {
                     Structure.FFIType type = new Structure.FFIType((Structure)obj);
                     typeInfoMap.put(cls, type);
                     return type.getPointer();
                  }
               } else if (NativeMapped.class.isAssignableFrom(cls)) {
                  NativeMappedConverter c = NativeMappedConverter.getInstance(cls);
                  return get(c.toNative(obj, new ToNativeContext()), c.nativeType());
               } else if (cls.isArray()) {
                  Structure.FFIType type = new Structure.FFIType(obj, cls);
                  typeInfoMap.put(obj, type);
                  return type.getPointer();
               } else {
                  throw new IllegalArgumentException("Unsupported Structure field type " + cls);
               }
            } else {
               typeInfoMap.put(cls, Structure.FFIType.FFITypes.ffi_type_pointer);
               return Structure.FFIType.FFITypes.ffi_type_pointer;
            }
         }
      }

      static {
         if (Native.POINTER_SIZE == 0) {
            throw new Error("Native library not initialized");
         } else if (Structure.FFIType.FFITypes.ffi_type_void == null) {
            throw new Error("FFI types not initialized");
         } else {
            typeInfoMap.put(Void.TYPE, Structure.FFIType.FFITypes.ffi_type_void);
            typeInfoMap.put(Void.class, Structure.FFIType.FFITypes.ffi_type_void);
            typeInfoMap.put(Float.TYPE, Structure.FFIType.FFITypes.ffi_type_float);
            typeInfoMap.put(Float.class, Structure.FFIType.FFITypes.ffi_type_float);
            typeInfoMap.put(Double.TYPE, Structure.FFIType.FFITypes.ffi_type_double);
            typeInfoMap.put(Double.class, Structure.FFIType.FFITypes.ffi_type_double);
            typeInfoMap.put(Long.TYPE, Structure.FFIType.FFITypes.ffi_type_sint64);
            typeInfoMap.put(Long.class, Structure.FFIType.FFITypes.ffi_type_sint64);
            typeInfoMap.put(Integer.TYPE, Structure.FFIType.FFITypes.ffi_type_sint32);
            typeInfoMap.put(Integer.class, Structure.FFIType.FFITypes.ffi_type_sint32);
            typeInfoMap.put(Short.TYPE, Structure.FFIType.FFITypes.ffi_type_sint16);
            typeInfoMap.put(Short.class, Structure.FFIType.FFITypes.ffi_type_sint16);
            Pointer ctype = Native.WCHAR_SIZE == 2 ? Structure.FFIType.FFITypes.ffi_type_uint16 : Structure.FFIType.FFITypes.ffi_type_uint32;
            typeInfoMap.put(Character.TYPE, ctype);
            typeInfoMap.put(Character.class, ctype);
            typeInfoMap.put(Byte.TYPE, Structure.FFIType.FFITypes.ffi_type_sint8);
            typeInfoMap.put(Byte.class, Structure.FFIType.FFITypes.ffi_type_sint8);
            typeInfoMap.put(Pointer.class, Structure.FFIType.FFITypes.ffi_type_pointer);
            typeInfoMap.put(String.class, Structure.FFIType.FFITypes.ffi_type_pointer);
            typeInfoMap.put(WString.class, Structure.FFIType.FFITypes.ffi_type_pointer);
            typeInfoMap.put(Boolean.TYPE, Structure.FFIType.FFITypes.ffi_type_uint32);
            typeInfoMap.put(Boolean.class, Structure.FFIType.FFITypes.ffi_type_uint32);
         }
      }

      private static class FFITypes {
         private static Pointer ffi_type_void;
         private static Pointer ffi_type_float;
         private static Pointer ffi_type_double;
         private static Pointer ffi_type_longdouble;
         private static Pointer ffi_type_uint8;
         private static Pointer ffi_type_sint8;
         private static Pointer ffi_type_uint16;
         private static Pointer ffi_type_sint16;
         private static Pointer ffi_type_uint32;
         private static Pointer ffi_type_sint32;
         private static Pointer ffi_type_uint64;
         private static Pointer ffi_type_sint64;
         private static Pointer ffi_type_pointer;

         private FFITypes() {
            super();
         }
      }

      public static class size_t extends IntegerType {
         public size_t() {
            this(0L);
         }

         public size_t(long value) {
            super(Native.POINTER_SIZE, value);
         }
      }
   }

   private static class LayoutInfo {
      private int size = -1;
      private int alignment = 1;
      private final Map fields = Collections.synchronizedMap(new LinkedHashMap());
      private int alignType = 0;
      private TypeMapper typeMapper;
      private boolean variable;

      private LayoutInfo() {
         super();
      }
   }

   static class StructField {
      public String name;
      public Class type;
      public Field field;
      public int size = -1;
      public int offset = -1;
      public boolean isVolatile;
      public boolean isReadOnly;
      public FromNativeConverter readConverter;
      public ToNativeConverter writeConverter;
      public FromNativeContext context;

      StructField() {
         super();
      }

      public String toString() {
         return this.name + "@" + this.offset + "[" + this.size + "] (" + this.type + ")";
      }
   }
}
