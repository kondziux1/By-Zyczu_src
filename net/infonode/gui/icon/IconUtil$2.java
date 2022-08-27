package net.infonode.gui.icon;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

class IconUtil$2 implements Icon {
   IconUtil$2(Icon var1) {
      super();
      this.val$icon = var1;
   }

   public void paintIcon(Component var1, Graphics var2, int var3, int var4) {
      this.val$icon.paintIcon(var1, var2, var3, var4);
   }

   public int getIconWidth() {
      return this.val$icon.getIconWidth();
   }

   public int getIconHeight() {
      return this.val$icon.getIconHeight();
   }
}
