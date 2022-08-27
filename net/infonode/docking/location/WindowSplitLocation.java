package net.infonode.docking.location;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.RootWindow;
import net.infonode.util.Direction;

public class WindowSplitLocation extends AbstractWindowLocation {
   private Direction direction;
   private float dividerLocation;

   public WindowSplitLocation(DockingWindow var1, WindowLocation var2, Direction var3, float var4) {
      super(var1, var2);
      this.direction = var3;
      this.dividerLocation = var4;
   }

   private WindowSplitLocation(Direction var1, float var2) {
      super();
      this.direction = var1;
      this.dividerLocation = var2;
   }

   public boolean set(DockingWindow var1, DockingWindow var2) {
      var1.split(var2, this.direction, this.dividerLocation);
      return true;
   }

   public void write(ObjectOutputStream var1) throws IOException {
      var1.writeInt(2);
      this.direction.write(var1);
      var1.writeFloat(this.dividerLocation);
      super.write(var1);
   }

   public static WindowSplitLocation decode(ObjectInputStream var0, RootWindow var1) throws IOException {
      WindowSplitLocation var2 = new WindowSplitLocation(Direction.decode(var0), var0.readFloat());
      var2.read(var0, var1);
      return var2;
   }
}
