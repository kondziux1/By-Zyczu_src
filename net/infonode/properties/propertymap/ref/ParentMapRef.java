package net.infonode.properties.propertymap.ref;

import java.io.IOException;
import java.io.ObjectOutputStream;
import net.infonode.properties.propertymap.PropertyMapImpl;

public class ParentMapRef implements PropertyMapRef {
   public static final ParentMapRef INSTANCE = new ParentMapRef();

   private ParentMapRef() {
      super();
   }

   public PropertyMapImpl getMap(PropertyMapImpl var1) {
      return var1 == null ? null : var1.getParent();
   }

   public String toString() {
      return "parent";
   }

   public void write(ObjectOutputStream var1) throws IOException {
      var1.writeInt(0);
   }
}
