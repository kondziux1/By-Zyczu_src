package net.infonode.docking.location;

import java.io.IOException;
import java.io.ObjectInputStream;
import net.infonode.docking.RootWindow;

public class LocationDecoder {
   public static final int NULL = 0;
   public static final int ROOT = 1;
   public static final int SPLIT = 2;
   public static final int TAB = 3;

   private LocationDecoder() {
      super();
   }

   public static WindowLocation decode(ObjectInputStream var0, RootWindow var1) throws IOException {
      int var2 = var0.readInt();
      switch(var2) {
         case 0:
            return NullLocation.INSTANCE;
         case 1:
            return WindowRootLocation.decode(var0, var1);
         case 2:
            return WindowSplitLocation.decode(var0, var1);
         case 3:
            return WindowTabLocation.decode(var0, var1);
         default:
            throw new IOException("Invalid location type!");
      }
   }
}
