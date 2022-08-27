package net.infonode.gui.draggable;

import net.infonode.gui.ScrollButtonBoxListener;
import net.infonode.gui.ScrollableBox;

class DraggableComponentBox$6 implements ScrollButtonBoxListener {
   DraggableComponentBox$6(DraggableComponentBox var1, ScrollableBox var2) {
      super();
      this.this$0 = var1;
      this.val$scrollableBox = var2;
   }

   public void scrollButton1() {
      this.val$scrollableBox.scrollLeft(1);
   }

   public void scrollButton2() {
      this.val$scrollableBox.scrollRight(1);
   }
}
