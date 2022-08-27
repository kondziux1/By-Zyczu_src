package net.infonode.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

class SimpleSplitPane$3 extends MouseMotionAdapter {
   SimpleSplitPane$3(SimpleSplitPane var1) {
      super();
      this.this$0 = var1;
   }

   public void mouseDragged(MouseEvent var1) {
      if (SimpleSplitPane.access$1100(this.this$0) && (var1.getModifiersEx() & 1024) != 0) {
         float var2 = (float)(
               SimpleSplitPane.access$1300(this.this$0, SimpleSplitPane.access$800(this.this$0).getLocation())
                  - SimpleSplitPane.access$1400(this.this$0)
                  + SimpleSplitPane.access$1300(this.this$0, var1.getPoint())
            )
            / (float)SimpleSplitPane.access$300(this.this$0);
         if (SimpleSplitPane.access$1200(this.this$0)) {
            this.this$0.setDividerLocation(var2);
         } else {
            SimpleSplitPane.access$1500(this.this$0, var2);
         }
      }

   }
}
