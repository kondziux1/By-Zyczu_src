package net.infonode.docking;

import java.awt.Dialog;
import java.awt.Frame;

class FloatingWindow$5 implements Runnable {
   FloatingWindow$5(FloatingWindow var1) {
      super();
      this.this$0 = var1;
   }

   public void run() {
      if (FloatingWindow.access$200(this.this$0) != null) {
         if (FloatingWindow.access$200(this.this$0) instanceof Dialog) {
            ((Dialog)FloatingWindow.access$200(this.this$0))
               .setTitle(FloatingWindow.access$300(this.this$0) == null ? "" : FloatingWindow.access$300(this.this$0).getTitle());
         } else {
            ((Frame)FloatingWindow.access$200(this.this$0))
               .setTitle(FloatingWindow.access$300(this.this$0) == null ? "" : FloatingWindow.access$300(this.this$0).getTitle());
         }
      }

      FloatingWindow.access$402(this.this$0, null);
   }
}
