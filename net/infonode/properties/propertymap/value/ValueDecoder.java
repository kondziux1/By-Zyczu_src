package net.infonode.properties.propertymap.value;

import java.io.IOException;
import java.io.ObjectInputStream;
import net.infonode.properties.base.Property;
import net.infonode.properties.propertymap.PropertyMapImpl;

public class ValueDecoder {
   public static final int SIMPLE = 0;
   public static final int REF = 1;

   private ValueDecoder() {
      super();
   }

   public static PropertyValue decode(ObjectInputStream var0, PropertyMapImpl var1, Property var2) throws IOException {
      int var3 = var0.readInt();
      switch(var3) {
         case 0:
            return SimplePropertyValue.decode(var0);
         case 1:
            return PropertyRefValue.decode(var0, var1, var2);
         default:
            throw new IOException("Invalid value type!");
      }
   }

   public static void skip(ObjectInputStream var0) throws IOException {
      int var1 = var0.readInt();
      switch(var1) {
         case 0:
            SimplePropertyValue.skip(var0);
            break;
         case 1:
            PropertyRefValue.skip(var0);
            break;
         default:
            throw new IOException("Invalid value type!");
      }

   }
}
