package net.infonode.gui.draggable;

import net.infonode.gui.ScrollableBox;
import net.infonode.gui.ScrollableBoxListener;

class DraggableComponentBox$8 implements ScrollableBoxListener {
   DraggableComponentBox$8(DraggableComponentBox var1) {
      super();
      this.this$0 = var1;
   }

   public void scrolledLeft(ScrollableBox var1) {
      DraggableComponentBox.access$1400(this.this$0).setButton1Enabled(!var1.isLeftEnd());
      DraggableComponentBox.access$1400(this.this$0).setButton2Enabled(true);
   }

   public void scrolledRight(ScrollableBox var1) {
      DraggableComponentBox.access$1400(this.this$0).setButton1Enabled(true);
      DraggableComponentBox.access$1400(this.this$0).setButton2Enabled(!var1.isRightEnd());
   }

   public void changed(ScrollableBox var1) {
      DraggableComponentBox.access$1000(this.this$0);
   }
}
