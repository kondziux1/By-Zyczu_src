package net.infonode.properties.propertymap.ref;

import java.io.IOException;
import java.io.ObjectOutputStream;
import net.infonode.properties.propertymap.PropertyMapImpl;

public class ThisPropertyMapRef implements PropertyMapRef {
   public static final ThisPropertyMapRef INSTANCE = new ThisPropertyMapRef();

   private ThisPropertyMapRef() {
      super();
   }

   public PropertyMapImpl getMap(PropertyMapImpl var1) {
      return var1;
   }

   public String toString() {
      return "this";
   }

   public void write(ObjectOutputStream var1) throws IOException {
      var1.writeInt(1);
   }
}
