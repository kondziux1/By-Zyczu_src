package net.infonode.docking.location;

import java.io.IOException;
import java.io.ObjectOutputStream;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.RootWindow;
import net.infonode.docking.internalutil.InternalDockingUtil;

public class NullLocation implements WindowLocation {
   public static final NullLocation INSTANCE = new NullLocation();

   private NullLocation() {
      super();
   }

   public boolean set(DockingWindow var1) {
      RootWindow var2 = var1.getRootWindow();
      if (var2 == null) {
         return false;
      } else {
         InternalDockingUtil.addToRootWindow(var1, var2);
         return true;
      }
   }

   public void write(ObjectOutputStream var1) throws IOException {
      var1.writeInt(0);
   }
}
