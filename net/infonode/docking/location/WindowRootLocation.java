package net.infonode.docking.location;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.RootWindow;
import net.infonode.docking.internalutil.InternalDockingUtil;

public class WindowRootLocation extends AbstractWindowLocation {
   public WindowRootLocation(RootWindow var1) {
      super(var1, null);
   }

   private WindowRootLocation() {
      super();
   }

   protected boolean set(DockingWindow var1, DockingWindow var2) {
      RootWindow var3 = (RootWindow)var1;
      InternalDockingUtil.addToRootWindow(var2, var3);
      return true;
   }

   public void write(ObjectOutputStream var1) throws IOException {
      var1.writeInt(1);
      super.write(var1);
   }

   public static WindowRootLocation decode(ObjectInputStream var0, RootWindow var1) throws IOException {
      WindowRootLocation var2 = new WindowRootLocation();
      var2.read(var0, var1);
      return var2;
   }
}
