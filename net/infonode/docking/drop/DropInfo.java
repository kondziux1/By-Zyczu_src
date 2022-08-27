package net.infonode.docking.drop;

import java.awt.Point;
import net.infonode.docking.DockingWindow;

public class DropInfo {
   private DockingWindow window;
   private DockingWindow dropWindow;
   private Point point;

   DropInfo(DockingWindow var1, DockingWindow var2, Point var3) {
      super();
      this.window = var1;
      this.dropWindow = var2;
      this.point = var3;
   }

   public DockingWindow getWindow() {
      return this.window;
   }

   public DockingWindow getDropWindow() {
      return this.dropWindow;
   }

   public Point getPoint() {
      return this.point;
   }
}
