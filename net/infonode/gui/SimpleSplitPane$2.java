package net.infonode.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class SimpleSplitPane$2 extends MouseAdapter {
   SimpleSplitPane$2(SimpleSplitPane var1) {
      super();
      this.this$0 = var1;
   }

   public void mousePressed(MouseEvent var1) {
      if (var1.getButton() == 1) {
         CursorManager.setGlobalCursor(this.this$0.getRootPane(), SimpleSplitPane.access$800(this.this$0).getCursor());
         if (SimpleSplitPane.access$1100(this.this$0) && !SimpleSplitPane.access$1200(this.this$0)) {
            float var2 = (float)(
                  SimpleSplitPane.access$1300(this.this$0, SimpleSplitPane.access$800(this.this$0).getLocation())
                     - SimpleSplitPane.access$1400(this.this$0)
                     + SimpleSplitPane.access$1300(this.this$0, var1.getPoint())
               )
               / (float)SimpleSplitPane.access$300(this.this$0);
            SimpleSplitPane.access$1500(this.this$0, var2);
         }
      }

   }

   public void mouseReleased(MouseEvent var1) {
      if (var1.getButton() == 1) {
         CursorManager.resetGlobalCursor(this.this$0.getRootPane());
         if (SimpleSplitPane.access$1100(this.this$0) && !SimpleSplitPane.access$1200(this.this$0)) {
            SimpleSplitPane.access$1600(this.this$0).setVisible(false);
            this.this$0.setDividerLocation(SimpleSplitPane.access$1700(this.this$0));
         }
      }

   }
}
