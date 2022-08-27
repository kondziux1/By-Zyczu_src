package net.infonode.docking;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class WindowTab$5 extends MouseAdapter {
   WindowTab$5(WindowTab var1) {
      super();
      this.this$0 = var1;
   }

   public void mousePressed(MouseEvent var1) {
      this.this$0.getWindow().fireTabWindowMouseButtonEvent(var1);
      this.checkPopupMenu(var1);
   }

   public void mouseClicked(MouseEvent var1) {
      this.this$0.getWindow().fireTabWindowMouseButtonEvent(var1);
   }

   public void mouseReleased(MouseEvent var1) {
      this.this$0.getWindow().fireTabWindowMouseButtonEvent(var1);
      this.checkPopupMenu(var1);
   }

   private void checkPopupMenu(MouseEvent var1) {
      if (var1.isPopupTrigger() && this.this$0.contains(var1.getPoint())) {
         WindowTab.access$000(this.this$0).showPopupMenu(var1);
      }

   }
}
