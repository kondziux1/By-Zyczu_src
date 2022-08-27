package net.infonode.gui.border;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.Border;

class BorderUtil$1 implements Border {
   BorderUtil$1(Border var1) {
      super();
      this.val$border = var1;
   }

   public void paintBorder(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      this.val$border.paintBorder(var1, var2, var3, var4, var5, var6);
   }

   public Insets getBorderInsets(Component var1) {
      return this.val$border.getBorderInsets(var1);
   }

   public boolean isBorderOpaque() {
      return this.val$border.isBorderOpaque();
   }
}
