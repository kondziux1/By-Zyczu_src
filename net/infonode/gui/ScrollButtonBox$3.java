package net.infonode.gui;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

class ScrollButtonBox$3 implements MouseWheelListener {
   ScrollButtonBox$3(ScrollButtonBox var1) {
      super();
      this.this$0 = var1;
   }

   public void mouseWheelMoved(MouseWheelEvent var1) {
      if (var1.getWheelRotation() < 0) {
         ScrollButtonBox.access$000(this.this$0);
      } else {
         ScrollButtonBox.access$100(this.this$0);
      }

   }
}
