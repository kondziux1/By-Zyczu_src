package net.infonode.docking.location;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import net.infonode.docking.AbstractTabWindow;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.RootWindow;

public class WindowTabLocation extends AbstractWindowLocation {
   private int index;

   public WindowTabLocation(AbstractTabWindow var1, WindowLocation var2, int var3) {
      super(var1, var2);
      this.index = var3;
   }

   private WindowTabLocation(int var1) {
      super();
      this.index = var1;
   }

   public boolean set(DockingWindow var1, DockingWindow var2) {
      ((AbstractTabWindow)var1).addTab(var2, this.index);
      return true;
   }

   public void write(ObjectOutputStream var1) throws IOException {
      var1.writeInt(3);
      var1.writeInt(this.index);
      super.write(var1);
   }

   public static WindowTabLocation decode(ObjectInputStream var0, RootWindow var1) throws IOException {
      WindowTabLocation var2 = new WindowTabLocation(var0.readInt());
      var2.read(var0, var1);
      return var2;
   }
}
