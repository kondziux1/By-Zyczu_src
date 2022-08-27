package net.infonode.properties.propertymap;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.infonode.properties.base.Property;
import net.infonode.properties.base.exception.InvalidPropertyException;
import net.infonode.properties.propertymap.ref.CompositeMapRef;
import net.infonode.properties.propertymap.ref.ParentMapRef;
import net.infonode.properties.propertymap.ref.PropertyMapPropertyRef;
import net.infonode.properties.propertymap.ref.PropertyMapRef;
import net.infonode.properties.propertymap.ref.ThisPropertyMapRef;
import net.infonode.properties.propertymap.value.PropertyRefValue;
import net.infonode.properties.propertymap.value.PropertyValue;
import net.infonode.properties.propertymap.value.ValueDecoder;
import net.infonode.properties.util.PropertyChangeListener;
import net.infonode.properties.util.PropertyPath;
import net.infonode.util.Printer;
import net.infonode.util.Utils;
import net.infonode.util.ValueChange;
import net.infonode.util.collection.map.ConstVectorMap;
import net.infonode.util.collection.map.MapAdapter;
import net.infonode.util.collection.map.SingleValueMap;
import net.infonode.util.collection.map.base.ConstMap;
import net.infonode.util.collection.map.base.ConstMapIterator;
import net.infonode.util.collection.map.base.MapIterator;
import net.infonode.util.collection.notifymap.AbstractConstChangeNotifyMap;
import net.infonode.util.collection.notifymap.ChangeNotifyMapWrapper;
import net.infonode.util.collection.notifymap.ConstChangeNotifyMap;
import net.infonode.util.collection.notifymap.ConstChangeNotifyVectorMap;
import net.infonode.util.signal.Signal;
import net.infonode.util.signal.SignalListener;

public class PropertyMapImpl implements PropertyMap {
   private static final int SERIALIZE_VERSION = 1;
   private PropertyMapGroup propertyGroup;
   private PropertyMapImpl parent;
   private PropertyMapProperty property;
   private ChangeNotifyMapWrapper values = new ChangeNotifyMapWrapper(new MapAdapter());
   private ConstChangeNotifyVectorMap superMap = new ConstChangeNotifyVectorMap();
   private ConstVectorMap vectorMap = new ConstVectorMap();
   private PropertyMapImpl.PropertyObjectMap map = new PropertyMapImpl.PropertyObjectMap();
   private ArrayList superMaps = new ArrayList(1);
   private MapAdapter childMaps = new MapAdapter();
   private HashMap propertyChangeListeners;
   private ArrayList listeners;
   private ArrayList treeListeners;
   private SignalListener mapListener;

   public PropertyMapImpl(PropertyMapGroup var1) {
      this(var1, null);
   }

   public PropertyMapImpl(PropertyMapImpl var1) {
      this(var1.getPropertyGroup(), var1);
   }

   public PropertyMapImpl(PropertyMapGroup var1, PropertyMapImpl var2) {
      this(var1, null, null);
      if (var2 != null) {
         this.addSuperMap(var2);
      }

   }

   public PropertyMapImpl(PropertyMapImpl var1, PropertyMapProperty var2) {
      this(var2.getPropertyMapGroup(), var1, var2);
   }

   public PropertyMapImpl(PropertyMapGroup var1, PropertyMapImpl var2, PropertyMapProperty var3) {
      super();
      this.parent = var2;
      this.property = var3;
      this.propertyGroup = var1;
      Property[] var4 = this.propertyGroup.getProperties();

      for(int var5 = 0; var5 < var4.length; ++var5) {
         if (var4[var5] instanceof PropertyMapProperty) {
            PropertyMapProperty var6 = (PropertyMapProperty)var4[var5];
            PropertyMapImpl var7 = new PropertyMapImpl(this, var6);
            this.childMaps.put(var6, var7);
         }
      }

      this.vectorMap.addMap(this.values);
      this.vectorMap.addMap(this.superMap);
   }

   private boolean hasTreeListener() {
      return this.treeListeners != null && this.treeListeners.size() > 0 || this.parent != null && this.parent.hasTreeListener();
   }

   private boolean hasListener() {
      return this.hasTreeListener()
         || this.listeners != null && this.listeners.size() > 0
         || this.propertyChangeListeners != null && this.propertyChangeListeners.size() > 0;
   }

   private void updateListenerRecursive() {
      this.updateListener();
      ConstMapIterator var1 = this.childMaps.constIterator();

      while(var1.atEntry()) {
         ((PropertyMapImpl)var1.getValue()).updateListenerRecursive();
         var1.next();
      }

   }

   private void updateListener() {
      if (this.hasListener()) {
         if (this.mapListener == null) {
            this.mapListener = new PropertyMapImpl$1(this);
            this.map.getChangeSignal().add(this.mapListener);
         }
      } else if (this.mapListener != null) {
         this.map.getChangeSignal().remove(this.mapListener);
         this.mapListener = null;
         this.map.updateListeners();
      }

   }

   private boolean checkListeners(Set var1) {
      if (var1.contains(this)) {
         return false;
      } else {
         var1.add(this);
         return this.hasListener() || this.map.checkListeners(var1);
      }
   }

   public ConstChangeNotifyMap getMap() {
      return this.map;
   }

   public PropertyMap getSuperMap() {
      return this.superMaps.size() == 0 ? null : (PropertyMap)this.superMaps.get(0);
   }

   public Object removeValue(Property var1) throws InvalidPropertyException {
      this.checkProperty(var1);
      PropertyValue var2 = (PropertyValue)this.values.get(var1);
      if (var2 != null && var2.getParent() == null) {
         this.values.remove(var1);
         PropertyMapManager.getInstance().beginBatch();

         try {
            this.firePropertyValueChanged(var1, new ValueChange(var2, this.getValue(var1)));
         } finally {
            PropertyMapManager.getInstance().endBatch();
         }

         return var2.get(this);
      } else {
         return null;
      }
   }

   private PropertyMapRef getPathFrom(PropertyMapImpl var1) {
      if (this.parent == null) {
         return null;
      } else if (this.parent == var1) {
         return new PropertyMapPropertyRef(this.property);
      } else {
         PropertyMapRef var2 = this.parent.getPathFrom(var1);
         return var2 == null ? null : new CompositeMapRef(var2, new PropertyMapPropertyRef(this.property));
      }
   }

   private PropertyMapRef getRelativePathTo(PropertyMapImpl var1) {
      Object var2 = var1 == this ? ThisPropertyMapRef.INSTANCE : var1.getPathFrom(this);
      return (PropertyMapRef)(var2 == null
         ? (this.parent == null ? null : new CompositeMapRef(ParentMapRef.INSTANCE, this.parent.getRelativePathTo(var1)))
         : var2);
   }

   public Object createRelativeRef(Property var1, PropertyMap var2, Property var3) {
      PropertyValue var4 = this.setValue(var1, new PropertyRefValue(this, var1, this.getRelativePathTo((PropertyMapImpl)var2), var3, null));
      return var4 == null ? null : var4.getWithDefault(this);
   }

   public int getSuperMapCount() {
      return this.superMaps.size();
   }

   public void addSuperMap(PropertyMap var1) {
      PropertyMapImpl var2 = (PropertyMapImpl)var1;
      PropertyMapManager.getInstance().beginBatch();

      try {
         this.addSuperMap(0, var2);
      } finally {
         PropertyMapManager.getInstance().endBatch();
      }

   }

   public PropertyMap removeSuperMap() {
      if (this.superMaps.size() > (this.parent == null ? 0 : this.parent.superMaps.size())) {
         PropertyMapImpl var1 = (PropertyMapImpl)this.superMaps.get(0);
         this.removeSuperMap(0);
         return var1;
      } else {
         return null;
      }
   }

   public boolean removeSuperMap(PropertyMap var1) {
      if (this.superMaps.size() > (this.parent == null ? 0 : this.parent.superMaps.size())) {
         int var2 = this.superMaps.indexOf(var1);
         if (var2 == -1) {
            return false;
         } else {
            this.removeSuperMap(var2);
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean replaceSuperMap(PropertyMap var1, PropertyMap var2) {
      if (var1 != var2 && this.superMaps.size() > (this.parent == null ? 0 : this.parent.superMaps.size())) {
         int var3 = this.superMaps.indexOf(var1);
         if (var3 == -1) {
            return false;
         } else {
            PropertyMapManager.getInstance().beginBatch();

            try {
               this.removeSuperMap(var3);
               this.addSuperMap(var3, (PropertyMapImpl)var2);
            } finally {
               PropertyMapManager.getInstance().endBatch();
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private void removeParentSuperMap(int var1) {
      this.removeSuperMap(this.superMaps.size() - this.parent.superMaps.size() - 1 + var1);
   }

   private void removeSuperMap(int var1) {
      PropertyMapManager.getInstance().beginBatch();

      try {
         this.superMap.removeMap(var1);
         this.superMaps.remove(var1);
         ConstMapIterator var2 = this.childMaps.constIterator();

         while(var2.atEntry()) {
            ((PropertyMapImpl)var2.getValue()).removeParentSuperMap(var1);
            var2.next();
         }
      } finally {
         PropertyMapManager.getInstance().endBatch();
      }

   }

   private void addSuperMap(PropertyMapImpl var1) {
      this.addSuperMap(0, var1);
   }

   private void addParentSuperMap(PropertyMapImpl var1, int var2) {
      this.addSuperMap(this.superMaps.size() - this.parent.superMaps.size() + 1 + var2, var1);
   }

   private void addSuperMap(int var1, PropertyMapImpl var2) {
      PropertyMapManager.getInstance().beginBatch();

      try {
         this.superMap.addMap(var1, var2.map);
         this.superMaps.add(var1, var2);
         ConstMapIterator var3 = this.childMaps.constIterator();

         while(var3.atEntry()) {
            ((PropertyMapImpl)var3.getValue()).addParentSuperMap(var2.getChildMapImpl((PropertyMapProperty)var3.getKey()), var1);
            var3.next();
         }
      } finally {
         PropertyMapManager.getInstance().endBatch();
      }

   }

   public void addTreeListener(PropertyMapTreeListener var1) {
      if (this.treeListeners == null) {
         this.treeListeners = new ArrayList(2);
      }

      this.treeListeners.add(var1);
      this.updateListenerRecursive();
   }

   public void removeTreeListener(PropertyMapTreeListener var1) {
      if (this.treeListeners != null) {
         this.treeListeners.remove(var1);
         if (this.treeListeners.size() == 0) {
            this.treeListeners = null;
         }

         this.updateListenerRecursive();
      }

   }

   public void addListener(PropertyMapListener var1) {
      if (this.listeners == null) {
         this.listeners = new ArrayList(2);
      }

      this.listeners.add(var1);
      this.updateListener();
   }

   public void removeListener(PropertyMapListener var1) {
      if (this.listeners != null) {
         this.listeners.remove(var1);
         if (this.listeners.size() == 0) {
            this.listeners = null;
         }
      }

      this.updateListener();
   }

   public PropertyMapGroup getPropertyGroup() {
      return this.propertyGroup;
   }

   public void addPropertyChangeListener(Property var1, PropertyChangeListener var2) {
      if (this.propertyChangeListeners == null) {
         this.propertyChangeListeners = new HashMap(4);
      }

      ArrayList var3 = (ArrayList)this.propertyChangeListeners.get(var1);
      if (var3 == null) {
         var3 = new ArrayList(2);
         this.propertyChangeListeners.put(var1, var3);
      }

      var3.add(var2);
      this.updateListener();
   }

   public void removePropertyChangeListener(Property var1, PropertyChangeListener var2) {
      if (this.propertyChangeListeners != null) {
         ArrayList var3 = (ArrayList)this.propertyChangeListeners.get(var1);
         if (var3 == null) {
            return;
         }

         var3.remove(var2);
         if (var3.isEmpty()) {
            this.propertyChangeListeners.remove(var1);
            if (this.propertyChangeListeners.isEmpty()) {
               this.propertyChangeListeners = null;
            }
         }

         this.updateListener();
      }

   }

   public PropertyMapImpl getParent() {
      return this.parent;
   }

   public PropertyMapProperty getProperty() {
      return this.property;
   }

   private void checkProperty(Property var1) {
      if (!this.propertyGroup.hasProperty(var1)) {
         throw new InvalidPropertyException(var1, "Property '" + var1 + "' not found in object '" + this.propertyGroup + "'!");
      }
   }

   public PropertyMap getChildMap(PropertyMapProperty var1) {
      return this.getChildMapImpl(var1);
   }

   public PropertyMapImpl getChildMapImpl(PropertyMapProperty var1) {
      this.checkProperty(var1);
      return (PropertyMapImpl)this.childMaps.get(var1);
   }

   private PropertyValue getParentDefaultValue(PropertyPath var1) {
      PropertyValue var2 = this.parent == null ? null : this.parent.getParentDefaultValue(new PropertyPath(this.property, var1));
      return var2 == null ? ((PropertyMapImpl)this.propertyGroup.getDefaultMap()).getValue(var1) : var2;
   }

   public PropertyValue getValueWithDefault(Property var1) {
      PropertyValue var2 = this.getValue(var1);
      return var2 == null ? this.getParentDefaultValue(new PropertyPath(var1)) : var2;
   }

   private PropertyValue getValue(PropertyPath var1) {
      return var1.getTail() == null
         ? this.getValue(var1.getProperty())
         : this.getChildMapImpl((PropertyMapProperty)var1.getProperty()).getValue(var1.getTail());
   }

   public PropertyValue getValue(Property var1) {
      this.checkProperty(var1);
      return (PropertyValue)this.map.get(var1);
   }

   private PropertyValue internalSetValue(Property var1, PropertyValue var2) {
      PropertyValue var3 = (PropertyValue)(var2 == null ? this.values.remove(var1) : this.values.put(var1, var2));
      if (var2 != null) {
         var2.updateListener(this.hasListener());
      }

      if (var3 != null) {
         var3.unset();
      }

      return var3;
   }

   public PropertyValue setValue(Property var1, PropertyValue var2) {
      this.checkProperty(var1);
      PropertyValue var3 = this.getValue(var1);
      this.internalSetValue(var1, var2);
      if (!Utils.equals(var2, var3)) {
         PropertyMapManager.getInstance().beginBatch();

         try {
            this.firePropertyValueChanged(var1, new ValueChange(var3, var2));
         } finally {
            PropertyMapManager.getInstance().endBatch();
         }
      }

      return var3;
   }

   public boolean valueIsSet(Property var1) {
      PropertyValue var2 = (PropertyValue)this.values.get(var1);
      return var2 != null && var2.getParent() == null;
   }

   public void firePropertyValueChanged(Property var1, ValueChange var2) {
      this.map.fireEntriesChanged(new SingleValueMap(var1, var2));
   }

   protected void firePropertyTreeValuesChanged(Map var1) {
      if (this.treeListeners != null) {
         PropertyMapTreeListener[] var2 = (PropertyMapTreeListener[])this.treeListeners.toArray(new PropertyMapTreeListener[this.treeListeners.size()]);

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var2[var3].propertyValuesChanged(var1);
         }
      }

   }

   void firePropertyValuesChanged(Map var1) {
      if (this.listeners != null) {
         PropertyMapListener[] var2 = (PropertyMapListener[])this.listeners.toArray(new PropertyMapListener[this.listeners.size()]);

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var2[var3].propertyValuesChanged(this, var1);
         }
      }

      if (this.propertyChangeListeners != null) {
         for(Entry var9 : var1.entrySet()) {
            ArrayList var4 = (ArrayList)this.propertyChangeListeners.get(var9.getKey());
            if (var4 != null) {
               ValueChange var5 = (ValueChange)var9.getValue();
               PropertyChangeListener[] var6 = (PropertyChangeListener[])var4.toArray(new PropertyChangeListener[var4.size()]);

               for(int var7 = 0; var7 < var6.length; ++var7) {
                  var6[var7].propertyChanged((Property)var9.getKey(), this, var5.getOldValue(), var5.getNewValue());
               }
            }
         }
      }

   }

   public void dump() {
      this.dump(new Printer(), new HashSet(4));
   }

   public void dump(Printer var1, Set var2) {
      var2.add(this);
      ConstMapIterator var3 = this.values.constIterator();

      while(var3.atEntry()) {
         var1.println(var3.getKey() + " = " + var3.getValue());
         var3.next();
      }

      if (!this.values.isEmpty()) {
         var1.println();
      }

      for(int var4 = 0; var4 < this.superMaps.size(); ++var4) {
         var1.println("Super Object " + (var4 + 1) + ':');
         var1.beginSection();
         ((PropertyMapImpl)this.superMaps.get(var4)).dump(var1, var2);
         var1.endSection();
         var1.println();
      }

      var3 = this.childMaps.constIterator();

      while(var3.atEntry()) {
         var1.println(var3.getKey() + ":");
         var1.beginSection();
         ((PropertyMapImpl)var3.getValue()).dump(var1, var2);
         var1.endSection();
         var1.println();
         var3.next();
      }

   }

   public void dumpSuperMaps(Printer var1) {
      var1.println(System.identityHashCode(this) + ":" + this);

      for(int var2 = 0; var2 < this.superMaps.size(); ++var2) {
         var1.beginSection();
         ((PropertyMapImpl)this.superMaps.get(var2)).dumpSuperMaps(var1);
         var1.endSection();
      }

   }

   public void clear(boolean var1) {
      PropertyMapManager.getInstance().beginBatch();

      try {
         this.doClear(var1);
      } finally {
         PropertyMapManager.getInstance().endBatch();
      }

   }

   private void doClear(boolean var1) {
      ArrayList var2 = new ArrayList(10);

      for(MapIterator var3 = this.values.iterator(); var3.atEntry(); var3.next()) {
         PropertyValue var4 = (PropertyValue)var3.getValue();
         if (var4.getParent() == null) {
            var2.add(var3.getKey());
         }
      }

      for(int var5 = 0; var5 < var2.size(); ++var5) {
         this.removeValue((Property)var2.get(var5));
      }

      if (var1) {
         ConstMapIterator var6 = this.childMaps.constIterator();

         while(var6.atEntry()) {
            ((PropertyMapImpl)var6.getValue()).doClear(var1);
            var6.next();
         }
      }

   }

   public boolean isEmpty(boolean var1) {
      ConstMapIterator var2 = this.values.constIterator();

      while(var2.atEntry()) {
         PropertyValue var3 = (PropertyValue)var2.getValue();
         if (var3.getParent() == null) {
            return false;
         }

         var2.next();
      }

      if (var1) {
         var2 = this.childMaps.constIterator();

         while(var2.atEntry()) {
            if (!((PropertyMapImpl)var2.getValue()).isEmpty(var1)) {
               return false;
            }

            var2.next();
         }
      }

      return true;
   }

   private void doRead(ObjectInputStream var1) throws IOException {
      while(var1.readBoolean()) {
         String var2 = var1.readUTF();
         Property var3 = this.getPropertyGroup().getProperty(var2);
         PropertyValue var4 = ValueDecoder.decode(var1, this, var3);
         if (var3 != null && var4 != null) {
            this.setValue(var3, var4);
         }
      }

      while(var1.readBoolean()) {
         PropertyMapProperty var5 = (PropertyMapProperty)this.getPropertyGroup().getProperty(var1.readUTF());
         this.getChildMapImpl(var5).doRead(var1);
      }

   }

   public void write(ObjectOutputStream var1, boolean var2) throws IOException {
      var1.writeInt(1);
      this.doWrite(var1, var2);
   }

   public void write(ObjectOutputStream var1) throws IOException {
      this.write(var1, true);
   }

   private void doWrite(ObjectOutputStream var1, boolean var2) throws IOException {
      for(ConstMapIterator var3 = this.values.constIterator(); var3.atEntry(); var3.next()) {
         PropertyValue var4 = (PropertyValue)var3.getValue();
         if (var4.getParent() == null && var4.isSerializable()) {
            var1.writeBoolean(true);
            var1.writeUTF(((Property)var3.getKey()).getName());
            var4.write(var1);
         }
      }

      var1.writeBoolean(false);
      if (var2) {
         for(ConstMapIterator var5 = this.childMaps.constIterator(); var5.atEntry(); var5.next()) {
            if (!((PropertyMapImpl)var5.getValue()).isEmpty(true)) {
               var1.writeBoolean(true);
               var1.writeUTF(((Property)var5.getKey()).getName());
               ((PropertyMapImpl)var5.getValue()).doWrite(var1, var2);
            }
         }
      }

      var1.writeBoolean(false);
   }

   public void read(ObjectInputStream var1) throws IOException {
      PropertyMapManager.getInstance().beginBatch();

      try {
         int var2 = var1.readInt();
         if (var2 > 1) {
            throw new IOException("Can't read object because serialized version is newer than current version!");
         }

         this.doRead(var1);
      } finally {
         PropertyMapManager.getInstance().endBatch();
      }

   }

   public static void skip(ObjectInputStream var0) throws IOException {
      int var1 = var0.readInt();
      if (var1 > 1) {
         throw new IOException("Can't read object because serialized version is newer than current version!");
      } else {
         doSkip(var0);
      }
   }

   private static void doSkip(ObjectInputStream var0) throws IOException {
      while(var0.readBoolean()) {
         var0.readUTF();
         ValueDecoder.skip(var0);
      }

      while(var0.readBoolean()) {
         var0.readUTF();
         doSkip(var0);
      }

   }

   private boolean doValuesEqual(PropertyMapImpl var1, boolean var2) {
      ConstMapIterator var3 = this.map.constIterator();

      while(var3.atEntry()) {
         Property var4 = (Property)var3.getKey();
         if (!Utils.equals(((PropertyValue)var3.getValue()).get(this), var1.getValue(var4).get(this))) {
            return false;
         }

         var3.next();
      }

      if (var2) {
         var3 = this.childMaps.constIterator();

         while(var3.atEntry()) {
            PropertyMapProperty var6 = (PropertyMapProperty)var3.getKey();
            if (!((PropertyMapImpl)var3.getValue()).doValuesEqual(var1.getChildMapImpl(var6), var2)) {
               return false;
            }

            var3.next();
         }
      }

      return true;
   }

   public boolean valuesEqualTo(PropertyMap var1, boolean var2) {
      return this.doValuesEqual((PropertyMapImpl)var1, var2);
   }

   public PropertyMap copy(boolean var1, boolean var2) {
      PropertyMapImpl var3 = new PropertyMapImpl(this.propertyGroup);
      this.doCopy(var3, var1, var2, true);
      return var3;
   }

   private void doCopy(PropertyMapImpl var1, boolean var2, boolean var3, boolean var4) {
      for(ConstMapIterator var5 = this.values.constIterator(); var5.atEntry(); var5.next()) {
         PropertyValue var6 = (PropertyValue)var5.getValue();
         if (var6.getParent() == null) {
            var1.values.put(var5.getKey(), var6.copyTo(var1));
         }
      }

      if (var2) {
         for(int var7 = 0; var7 < (var4 ? this.superMaps.size() : this.superMaps.size() - this.parent.superMaps.size()); ++var7) {
            var1.addSuperMap((PropertyMapImpl)this.superMaps.get(var7));
         }
      }

      if (var3) {
         ConstMapIterator var8 = this.childMaps.constIterator();

         while(var8.atEntry()) {
            ((PropertyMapImpl)var8.getValue()).doCopy((PropertyMapImpl)var1.getChildMap((PropertyMapProperty)var8.getKey()), var2, var3, false);
            var8.next();
         }
      }

   }

   private class PropertyObjectMap extends AbstractConstChangeNotifyMap implements SignalListener {
      private boolean listenerActive;

      PropertyObjectMap() {
         super();
      }

      protected void listenerAdded() {
         if (!this.listenerActive) {
            this.listenerActive = true;
            this.addInheritedReferences();
            PropertyMapImpl.this.superMap.getChangeSignal().add(this);
         }

      }

      public void signalEmitted(Signal var1, Object var2) {
         ConstMap var3 = (ConstMap)var2;
         MapAdapter var4 = new MapAdapter();

         for(ConstMapIterator var5 = var3.constIterator(); var5.atEntry(); var5.next()) {
            Property var6 = (Property)var5.getKey();
            if (PropertyMapImpl.this.propertyGroup.hasProperty(var6)) {
               PropertyValue var7 = (PropertyValue)PropertyMapImpl.this.values.get(var6);
               if (var7 == null || var7.getParent() != null) {
                  ValueChange var8 = (ValueChange)var5.getValue();
                  PropertyValue var9 = (PropertyValue)var8.getNewValue();
                  PropertyValue var10 = var9 == null ? null : var9.getSubValue(PropertyMapImpl.this);
                  PropertyMapImpl.this.internalSetValue(var6, var10);
                  var4.put(var6, new ValueChange(var7 != null ? var7 : var8.getOldValue(), var10 != null ? var10 : var8.getNewValue()));
               }
            }
         }

         if (!var4.isEmpty()) {
            this.fireEntriesChanged(var4);
         }

      }

      protected void lastListenerRemoved() {
         if (this.listenerActive) {
            this.listenerActive = false;
            PropertyMapImpl.this.superMap.getChangeSignal().remove(this);
            this.removeInheritedReferences();
         }

      }

      public boolean checkListeners(Set var1) {
         for(Object var3 : this.getChangeSignalInternal()) {
            if (var3 instanceof PropertyRefValue) {
               PropertyRefValue var4 = (PropertyRefValue)var3;
               if (var4.getMap().checkListeners(var1)) {
                  return true;
               }
            }
         }

         return false;
      }

      public void updateListeners() {
         Iterator var1 = this.getChangeSignalInternal().iterator();

         while(var1.hasNext()) {
            if (!(var1.next() instanceof PropertyRefValue)) {
               return;
            }
         }

         for(Object var2 : this.getChangeSignalInternal()) {
            if (var2 instanceof PropertyRefValue) {
               PropertyRefValue var3 = (PropertyRefValue)var2;
               if (var3.getMap().checkListeners(new HashSet())) {
                  return;
               }
            }
         }

         this.lastListenerRemoved();
      }

      private void addInheritedReferences() {
         ConstMapIterator var1 = PropertyMapImpl.this.values.constIterator();

         while(var1.atEntry()) {
            Property var2 = (Property)var1.getKey();
            PropertyValue var3 = (PropertyValue)PropertyMapImpl.this.values.get(var2);
            var3.updateListener(true);
            var1.next();
         }

         for(ConstMapIterator var6 = PropertyMapImpl.this.superMap.constIterator(); var6.atEntry(); var6.next()) {
            Property var7 = (Property)var6.getKey();
            if (PropertyMapImpl.this.propertyGroup.hasProperty(var7)) {
               PropertyValue var8 = (PropertyValue)PropertyMapImpl.this.values.get(var7);
               if (var8 == null || var8.getParent() != null) {
                  PropertyValue var4 = (PropertyValue)var6.getValue();
                  PropertyValue var5 = var4 == null ? null : var4.getSubValue(PropertyMapImpl.this);
                  PropertyMapImpl.this.internalSetValue(var7, var5);
               }
            }
         }

      }

      private void removeInheritedReferences() {
         ArrayList var1 = new ArrayList();

         for(ConstMapIterator var2 = PropertyMapImpl.this.values.constIterator(); var2.atEntry(); var2.next()) {
            Property var3 = (Property)var2.getKey();
            PropertyValue var4 = (PropertyValue)PropertyMapImpl.this.values.get(var3);
            if (var4.getParent() != null) {
               var4.unset();
               var1.add(var3);
            } else {
               var4.updateListener(false);
            }
         }

         for(int var5 = 0; var5 < var1.size(); ++var5) {
            PropertyMapImpl.this.values.remove(var1.get(var5));
         }

      }

      public Object get(Object var1) {
         return PropertyMapImpl.this.vectorMap.get(var1);
      }

      public boolean containsKey(Object var1) {
         return PropertyMapImpl.this.vectorMap.containsKey(var1);
      }

      public boolean containsValue(Object var1) {
         return PropertyMapImpl.this.vectorMap.containsValue(var1);
      }

      public boolean isEmpty() {
         return PropertyMapImpl.this.vectorMap.isEmpty();
      }

      public ConstMapIterator constIterator() {
         return PropertyMapImpl.this.vectorMap.constIterator();
      }

      protected void fireEntriesChanged(ConstMap var1) {
         super.fireEntriesChanged(var1);
      }
   }
}
