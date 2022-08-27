package net.infonode.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashMap;

public class Enum implements Serializable, Writable {
   private static final long serialVersionUID = 1L;
   private static final HashMap VALUE_MAP = new HashMap();
   private int value;
   private transient String name;

   protected Enum(int var1, String var2) {
      super();
      this.value = var1;
      this.name = var2;
      HashMap var3 = (HashMap)VALUE_MAP.get(this.getClass());
      if (var3 == null) {
         var3 = new HashMap();
         VALUE_MAP.put(this.getClass(), var3);
      }

      var3.put(new Integer(var1), this);
   }

   public int getValue() {
      return this.value;
   }

   public String getName() {
      return this.name;
   }

   public String toString() {
      return this.name;
   }

   public void write(ObjectOutputStream var1) throws IOException {
      this.writeObject(var1);
   }

   protected static Object getObject(Class var0, int var1) throws IOException {
      HashMap var2 = (HashMap)VALUE_MAP.get(var0);
      if (var2 == null) {
         throw new IOException("Invalid enum class '" + var0 + "'!");
      } else {
         Object var3 = var2.get(new Integer(var1));
         if (var3 == null) {
            throw new IOException("Invalid enum value '" + var1 + "'!");
         } else {
            return var3;
         }
      }
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.writeShort(this.value);
   }

   private void readObject(ObjectInputStream var1) throws IOException {
      this.value = var1.readShort();
   }

   protected static Object decode(Class var0, ObjectInputStream var1) throws IOException {
      return getObject(var0, var1.readShort());
   }

   protected Object readResolve() throws ObjectStreamException {
      try {
         return getObject(this.getClass(), this.getValue());
      } catch (IOException var2) {
         return this;
      }
   }
}
