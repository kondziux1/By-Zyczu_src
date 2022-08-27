package net.infonode.docking;

import javax.swing.SwingUtilities;

class FocusManager$5 implements Runnable {
   FocusManager$5(DockingWindow var1) {
      super();
      this.val$window = var1;
   }

   public void run() {
      SwingUtilities.invokeLater(new FocusManager$6(this));
   }
}
