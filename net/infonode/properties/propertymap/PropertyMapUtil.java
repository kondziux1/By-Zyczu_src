package net.infonode.properties.propertymap;

import java.io.IOException;
import java.io.ObjectInputStream;

public final class PropertyMapUtil {
   private PropertyMapUtil() {
      super();
   }

   public static void skipMap(ObjectInputStream var0) throws IOException {
      PropertyMapImpl.skip(var0);
   }
}
