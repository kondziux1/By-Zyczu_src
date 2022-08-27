package net.infonode.gui.icon.button;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.Icon;

public class BorderIcon implements Icon {
   private Icon icon;
   private Color color;
   private Insets insets;

   public BorderIcon(Icon var1, int var2) {
      this(var1, null, new Insets(var2, var2, var2, var2));
   }

   public BorderIcon(Icon var1, Color var2, Insets var3) {
      super();
      this.icon = var1;
      this.color = var2;
      this.insets = var3;
   }

   public void paintIcon(Component var1, Graphics var2, int var3, int var4) {
      if (this.color != null) {
         Color var5 = var2.getColor();
         var2.setColor(this.color);
         var2.fillRect(var3, var4, this.getIconWidth(), this.insets.top);
         var2.fillRect(var3, var4 + this.getIconHeight() - this.insets.bottom, this.getIconWidth(), this.insets.bottom);
         var2.fillRect(var3, var4 + this.insets.top, this.insets.left, this.getIconHeight() - this.insets.top - this.insets.bottom);
         var2.fillRect(
            var3 + this.getIconWidth() - this.insets.right,
            var4 + this.insets.top,
            this.insets.right,
            this.getIconHeight() - this.insets.top - this.insets.bottom
         );
         var2.setColor(var5);
      }

      this.icon.paintIcon(var1, var2, var3 + this.insets.left, var4 + this.insets.top);
   }

   public int getIconWidth() {
      return this.insets.left + this.insets.right + this.icon.getIconWidth();
   }

   public int getIconHeight() {
      return this.insets.top + this.insets.bottom + this.icon.getIconHeight();
   }
}
