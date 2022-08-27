package net.infonode.docking.internal;

import net.infonode.docking.DockingWindow;

public class WindowAncestors {
   private DockingWindow[] ancestors;
   private boolean minimized;
   private boolean undocked;

   public WindowAncestors(DockingWindow[] var1, boolean var2, boolean var3) {
      super();
      this.ancestors = var1;
      this.minimized = var2;
      this.undocked = var3;
   }

   public DockingWindow[] getAncestors() {
      return this.ancestors;
   }

   public boolean isMinimized() {
      return this.minimized;
   }

   public boolean isUndocked() {
      return this.undocked;
   }
}
