package net.infonode.gui.hover.hoverable;

import java.awt.Component;
import java.awt.Point;
import javax.swing.SwingUtilities;

class HoverManager$5 implements Runnable {
   HoverManager$5(HoverManager var1, Point var2, Component var3) {
      super();
      this.this$0 = var1;
      this.val$p = var2;
      this.val$top = var3;
   }

   public void run() {
      SwingUtilities.invokeLater(new HoverManager$6(this));
   }
}
