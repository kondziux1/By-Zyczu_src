package net.infonode.docking;

import java.awt.event.MouseEvent;
import net.infonode.docking.internalutil.DropAction;
import net.infonode.util.Direction;

class DockingWindow$11 extends DropAction {
   DockingWindow$11(DockingWindow var1, Direction var2) {
      super();
      this.this$0 = var1;
      this.val$splitDir = var2;
   }

   public void execute(DockingWindow var1, MouseEvent var2) {
      try {
         var1.beforeDrop(this.this$0);
         this.this$0.split(var1, this.val$splitDir, this.val$splitDir != Direction.UP && this.val$splitDir != Direction.LEFT ? 0.66F : 0.33F);
         var1.restoreFocus();
      } catch (OperationAbortedException var4) {
      }

   }
}
