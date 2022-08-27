package net.infonode.properties.propertymap.ref;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import net.infonode.properties.propertymap.PropertyMapImpl;
import net.infonode.properties.propertymap.PropertyMapProperty;

public class PropertyMapPropertyRef implements PropertyMapRef {
   private String propertyName;

   private PropertyMapPropertyRef(String var1) {
      super();
      this.propertyName = var1;
   }

   public PropertyMapPropertyRef(PropertyMapProperty var1) {
      super();
      this.propertyName = var1.getName();
   }

   public PropertyMapImpl getMap(PropertyMapImpl var1) {
      return var1 == null ? null : var1.getChildMapImpl((PropertyMapProperty)var1.getPropertyGroup().getProperty(this.propertyName));
   }

   public String toString() {
      return "(property '" + this.propertyName + "')";
   }

   public void write(ObjectOutputStream var1) throws IOException {
      var1.writeInt(2);
      var1.writeUTF(this.propertyName);
   }

   public static PropertyMapRef decode(ObjectInputStream var0) throws IOException {
      return new PropertyMapPropertyRef(var0.readUTF());
   }
}
