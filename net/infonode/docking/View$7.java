package net.infonode.docking;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class View$7 extends MouseAdapter {
   View$7(View var1) {
      super();
      this.this$0 = var1;
   }

   public void mousePressed(MouseEvent var1) {
      this.this$0.fireTabWindowMouseButtonEvent(var1);
      this.checkPopupMenu(var1);
   }

   public void mouseClicked(MouseEvent var1) {
      this.this$0.fireTabWindowMouseButtonEvent(var1);
   }

   public void mouseReleased(MouseEvent var1) {
      this.this$0.fireTabWindowMouseButtonEvent(var1);
      this.checkPopupMenu(var1);
   }

   private void checkPopupMenu(MouseEvent var1) {
      if (var1.isPopupTrigger()) {
         this.this$0.showPopupMenu(var1);
      }

   }
}
