package net.infonode.docking;

import java.awt.event.MouseEvent;
import net.infonode.docking.internalutil.DropAction;

class RootWindow$8 extends DropAction {
   RootWindow$8(RootWindow var1) {
      super();
      this.this$0 = var1;
   }

   public void execute(DockingWindow var1, MouseEvent var2) {
      this.this$0.setWindow(var1);
   }
}
