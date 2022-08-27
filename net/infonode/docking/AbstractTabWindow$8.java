package net.infonode.docking;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class AbstractTabWindow$8 extends MouseAdapter {
   AbstractTabWindow$8(AbstractTabWindow var1) {
      super();
      this.this$0 = var1;
   }

   public void mousePressed(MouseEvent var1) {
      if (var1.isPopupTrigger()) {
         this.this$0.showPopupMenu(var1);
      }

   }

   public void mouseReleased(MouseEvent var1) {
      this.mousePressed(var1);
   }
}
