package net.infonode.properties.propertymap.value;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import net.infonode.properties.propertymap.PropertyMapImpl;
import net.infonode.util.Printer;
import net.infonode.util.Utils;

public class SimplePropertyValue implements PropertyValue {
   private final Object value;

   public SimplePropertyValue(Object var1) {
      super();
      this.value = var1;
   }

   public void updateListener(boolean var1) {
   }

   public PropertyValue getParent() {
      return null;
   }

   public Object get(PropertyMapImpl var1) {
      return this.value;
   }

   public Object getWithDefault(PropertyMapImpl var1) {
      return this.value;
   }

   public PropertyValue getSubValue(PropertyMapImpl var1) {
      return null;
   }

   public void unset() {
   }

   public String toString() {
      return String.valueOf(this.value);
   }

   public void dump(Printer var1) {
      var1.println(this.toString());
   }

   public boolean equals(Object var1) {
      return var1 != null && var1 instanceof SimplePropertyValue && Utils.equals(((SimplePropertyValue)var1).value, this.value);
   }

   public int hashCode() {
      return this.value.hashCode();
   }

   public void write(ObjectOutputStream var1) throws IOException {
      var1.writeInt(0);
      var1.writeObject(this.value);
   }

   public boolean isSerializable() {
      return this.value instanceof Serializable;
   }

   public static PropertyValue decode(ObjectInputStream var0) throws IOException {
      try {
         return new SimplePropertyValue(var0.readObject());
      } catch (ClassNotFoundException var2) {
         throw new IOException(var2.getMessage());
      }
   }

   public static void skip(ObjectInputStream var0) throws IOException {
      try {
         var0.readObject();
      } catch (ClassNotFoundException var2) {
         throw new IOException(var2.getMessage());
      }
   }

   public PropertyValue copyTo(PropertyMapImpl var1) {
      return this;
   }
}
