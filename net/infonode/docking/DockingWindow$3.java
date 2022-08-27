package net.infonode.docking;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class DockingWindow$3 extends MouseAdapter {
   DockingWindow$3(DockingWindow var1) {
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
