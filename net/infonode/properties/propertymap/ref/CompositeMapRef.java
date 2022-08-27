package net.infonode.properties.propertymap.ref;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import net.infonode.properties.propertymap.PropertyMapImpl;

public class CompositeMapRef implements PropertyMapRef {
   private PropertyMapRef ref1;
   private PropertyMapRef ref2;

   public CompositeMapRef(PropertyMapRef var1, PropertyMapRef var2) {
      super();
      this.ref1 = var1;
      this.ref2 = var2;
   }

   public PropertyMapImpl getMap(PropertyMapImpl var1) {
      return this.ref2.getMap(this.ref1.getMap(var1));
   }

   public String toString() {
      return this.ref1 + "." + this.ref2;
   }

   public void write(ObjectOutputStream var1) throws IOException {
      var1.writeInt(3);
      this.ref1.write(var1);
      this.ref2.write(var1);
   }

   public static CompositeMapRef decode(ObjectInputStream var0) throws IOException {
      return new CompositeMapRef(PropertyMapRefDecoder.decode(var0), PropertyMapRefDecoder.decode(var0));
   }
}
