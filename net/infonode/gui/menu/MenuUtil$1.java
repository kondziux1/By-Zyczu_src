package net.infonode.gui.menu;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

class MenuUtil$1 implements Icon {
   MenuUtil$1(Icon var1, int var2) {
      super();
      this.val$icon = var1;
      this.val$maxWidth = var2;
   }

   public int getIconHeight() {
      return this.val$icon == null ? 1 : this.val$icon.getIconHeight();
   }

   public int getIconWidth() {
      return this.val$maxWidth;
   }

   public void paintIcon(Component var1, Graphics var2, int var3, int var4) {
      if (this.val$icon != null) {
         this.val$icon.paintIcon(var1, var2, var3, var4);
      }

   }
}
