package net.infonode.properties.propertymap.ref;

import java.io.IOException;
import java.io.ObjectInputStream;

public class PropertyMapRefDecoder {
   public static final int PARENT = 0;
   public static final int THIS = 1;
   public static final int PROPERTY_OBJECT_PROPERTY = 2;
   public static final int COMPOSITE = 3;

   private PropertyMapRefDecoder() {
      super();
   }

   public static PropertyMapRef decode(ObjectInputStream var0) throws IOException {
      int var1 = var0.readInt();
      switch(var1) {
         case 0:
            return ParentMapRef.INSTANCE;
         case 1:
            return ThisPropertyMapRef.INSTANCE;
         case 2:
            return PropertyMapPropertyRef.decode(var0);
         case 3:
            return CompositeMapRef.decode(var0);
         default:
            throw new IOException("Invalid property object ref type!");
      }
   }
}
