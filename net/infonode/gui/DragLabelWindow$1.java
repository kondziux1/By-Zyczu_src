package net.infonode.gui;

import java.awt.Dimension;
import javax.swing.JLabel;

class DragLabelWindow$1 extends JLabel {
   DragLabelWindow$1(DragLabelWindow var1) {
      super();
      this.this$0 = var1;
   }

   public Dimension getMinimumSize() {
      return this.getPreferredSize();
   }
}
