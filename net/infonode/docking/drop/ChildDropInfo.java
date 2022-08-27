package net.infonode.docking.drop;

import java.awt.Point;
import net.infonode.docking.DockingWindow;

public class ChildDropInfo extends DropInfo {
   private DockingWindow childWindow;

   public ChildDropInfo(DockingWindow var1, DockingWindow var2, Point var3, DockingWindow var4) {
      super(var1, var2, var3);
      this.childWindow = var4;
   }

   public DockingWindow getChildWindow() {
      return this.childWindow;
   }
}
