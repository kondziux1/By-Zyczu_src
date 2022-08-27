package net.infonode.docking;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class FloatingWindow$3 extends MouseAdapter {
   FloatingWindow$3(FloatingWindow var1) {
      super();
      this.this$0 = var1;
   }

   public void mouseEntered(MouseEvent var1) {
      this.this$0.getRootWindow().setCurrentDragRootPane(this.this$0.getRootPane());
   }

   public void mouseExited(MouseEvent var1) {
      if (!FloatingWindow.access$100(this.this$0).contains(var1.getPoint())
         && this.this$0.getRootWindow().getCurrentDragRootPane() == this.this$0.getRootPane()) {
         this.this$0.getRootWindow().setCurrentDragRootPane(null);
      }

   }
}
