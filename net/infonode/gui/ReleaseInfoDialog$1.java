package net.infonode.gui;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JScrollPane;

class ReleaseInfoDialog$1 extends JScrollPane {
   ReleaseInfoDialog$1(Component var1, int var2, int var3, JComponent var4) {
      super(var1, var2, var3);
      this.val$message = var4;
   }

   public Dimension getPreferredSize() {
      Dimension var1 = this.val$message.getPreferredSize();
      int var2 = (int)var1.getHeight();
      return new Dimension((int)var1.getWidth() + 50, var2 < 300 ? (int)super.getPreferredSize().getHeight() : 400);
   }
}
