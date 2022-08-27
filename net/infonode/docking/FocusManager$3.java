package net.infonode.docking;

import java.awt.Component;
import javax.swing.SwingUtilities;

class FocusManager$3 implements Runnable {
   FocusManager$3(FocusManager var1, Component var2) {
      super();
      this.this$0 = var1;
      this.val$c = var2;
   }

   public void run() {
      SwingUtilities.invokeLater(new FocusManager$4(this));
   }
}
