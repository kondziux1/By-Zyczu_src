package net.infonode.docking.theme;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.Border;

class LookAndFeelDockingTheme$5 implements Border {
   LookAndFeelDockingTheme$5(LookAndFeelDockingTheme$3 var1, Color var2, Color var3, Color var4, Color var5, Insets var6, boolean var7) {
      super();
      this.this$1 = var1;
      this.val$top = var2;
      this.val$right = var3;
      this.val$bottom = var4;
      this.val$left = var5;
      this.val$borderInsets = var6;
      this.val$borderOpaque = var7;
   }

   public void paintBorder(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      if (this.val$top != null) {
         var2.setColor(this.val$top);
         var2.drawLine(var3, var4, var3 + var5 - (this.val$right == null ? 1 : 2), var4);
      }

      if (this.val$right != null) {
         var2.setColor(this.val$right);
         var2.drawLine(var3 + var5 - 1, var4, var3 + var5 - 1, var4 + var6 - (this.val$bottom == null ? 1 : 2));
      }

      if (this.val$bottom != null) {
         var2.setColor(this.val$bottom);
         var2.drawLine(this.val$left == null ? var3 : var3 + 1, var4 + var6 - 1, var3 + var5 - 1, var4 + var6 - 1);
      }

      if (this.val$left != null) {
         var2.setColor(this.val$left);
         var2.drawLine(var3, this.val$top == null ? var4 : var4 + 1, var3, var4 + var6 - 1);
      }

   }

   public Insets getBorderInsets(Component var1) {
      return this.val$borderInsets;
   }

   public boolean isBorderOpaque() {
      return this.val$borderOpaque;
   }
}
