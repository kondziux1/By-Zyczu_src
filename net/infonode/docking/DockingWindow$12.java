package net.infonode.docking;

import java.awt.event.MouseEvent;
import net.infonode.docking.internalutil.DropAction;

class DockingWindow$12 extends DropAction {
   DockingWindow$12(DockingWindow var1) {
      super();
      this.this$0 = var1;
   }

   public void execute(DockingWindow var1, MouseEvent var2) {
      DockingWindow.optimizeAfter(var1.getWindowParent(), new DockingWindow$13(this, var1));
   }
}
