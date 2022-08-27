package net.infonode.gui.hover.hoverable;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;

class HoverManager$4 implements AWTEventListener {
   HoverManager$4(HoverManager var1) {
      super();
      this.this$0 = var1;
   }

   public void eventDispatched(AWTEvent var1) {
      if (HoverManager.access$300(this.this$0)) {
         HoverManager.access$400(this.this$0, var1);
      }

   }
}
