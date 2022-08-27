package net.infonode.docking;

import java.awt.event.MouseEvent;
import net.infonode.docking.internalutil.DropAction;

class FloatingWindow$6 extends DropAction {
   FloatingWindow$6(FloatingWindow var1) {
      super();
      this.this$0 = var1;
   }

   public void execute(DockingWindow var1, MouseEvent var2) {
      this.this$0.setWindow(var1);
   }
}
