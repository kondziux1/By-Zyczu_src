package net.infonode.gui.draggable;

import net.infonode.gui.ScrollableBox;

class DraggableComponentBox$9 implements Runnable {
   DraggableComponentBox$9(DraggableComponentBox var1) {
      super();
      this.this$0 = var1;
   }

   public void run() {
      if (DraggableComponentBox.access$1100(this.this$0)
         && DraggableComponentBox.access$1500(this.this$0)
         && DraggableComponentBox.access$1600(this.this$0) != null) {
         ((ScrollableBox)DraggableComponentBox.access$1300(this.this$0))
            .ensureVisible(DraggableComponentBox.access$1200(this.this$0).indexOf(DraggableComponentBox.access$1600(this.this$0).getComponent()));
      }

   }
}
