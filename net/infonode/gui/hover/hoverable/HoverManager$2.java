package net.infonode.gui.hover.hoverable;

import java.awt.Component;
import java.awt.event.HierarchyEvent;

class HoverManager$2 implements Runnable {
   HoverManager$2(HoverManager$1 var1, HierarchyEvent var2) {
      super();
      this.this$1 = var1;
      this.val$e = var2;
   }

   public void run() {
      if ((this.val$e.getChangeFlags() & 4L) != 0L) {
         if (((Component)this.val$e.getSource()).isShowing()) {
            HoverManager.access$100(HoverManager$1.access$000(this.this$1), (Hoverable)this.val$e.getSource());
         } else {
            HoverManager.access$200(HoverManager$1.access$000(this.this$1), (Hoverable)this.val$e.getSource());
         }
      }

   }
}
