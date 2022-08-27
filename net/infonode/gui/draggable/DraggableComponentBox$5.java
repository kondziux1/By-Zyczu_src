package net.infonode.gui.draggable;

import net.infonode.gui.ScrollableBox;

class DraggableComponentBox$5 implements Runnable {
   DraggableComponentBox$5(DraggableComponentBox var1, DraggableComponent var2) {
      super();
      this.this$0 = var1;
      this.val$c = var2;
   }

   public void run() {
      if (DraggableComponentBox.access$1100(this.this$0)) {
         ((ScrollableBox)DraggableComponentBox.access$1300(this.this$0))
            .ensureVisible(DraggableComponentBox.access$1200(this.this$0).indexOf(this.val$c.getComponent()));
      }

   }
}
