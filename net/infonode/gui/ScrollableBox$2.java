package net.infonode.gui;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

class ScrollableBox$2 implements MouseWheelListener {
   ScrollableBox$2(ScrollableBox var1) {
      super();
      this.this$0 = var1;
   }

   public void mouseWheelMoved(MouseWheelEvent var1) {
      ScrollableBox.access$300(this.this$0, ScrollableBox.access$200(this.this$0) + var1.getWheelRotation());
   }
}
