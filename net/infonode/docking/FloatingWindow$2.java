package net.infonode.docking;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class FloatingWindow$2 extends WindowAdapter {
   FloatingWindow$2(FloatingWindow var1) {
      super();
      this.this$0 = var1;
   }

   public void windowClosing(WindowEvent var1) {
      try {
         if (this.this$0.getWindowProperties().getCloseEnabled()) {
            this.this$0.closeWithAbort();
         }
      } catch (OperationAbortedException var3) {
      }

   }
}
