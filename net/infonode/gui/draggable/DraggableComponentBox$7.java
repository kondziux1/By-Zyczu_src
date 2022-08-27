package net.infonode.gui.draggable;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import net.infonode.gui.ScrollableBox;

class DraggableComponentBox$7 extends ComponentAdapter {
   DraggableComponentBox$7(DraggableComponentBox var1, ScrollableBox var2) {
      super();
      this.this$0 = var1;
      this.val$scrollableBox = var2;
   }

   public void componentResized(ComponentEvent var1) {
      DraggableComponentBox.access$1400(this.this$0).setButton1Enabled(!this.val$scrollableBox.isLeftEnd());
      DraggableComponentBox.access$1400(this.this$0).setButton2Enabled(!this.val$scrollableBox.isRightEnd());
   }
}
