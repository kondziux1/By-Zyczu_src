package net.infonode.docking.drop;

import java.awt.Point;
import net.infonode.docking.DockingWindow;
import net.infonode.util.Direction;

public class SplitDropInfo extends DropInfo {
   private Direction splitDirection;

   public SplitDropInfo(DockingWindow var1, DockingWindow var2, Point var3, Direction var4) {
      super(var1, var2, var3);
      this.splitDirection = var4;
   }

   public Direction getSplitDirection() {
      return this.splitDirection;
   }
}
