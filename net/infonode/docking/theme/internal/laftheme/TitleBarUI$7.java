package net.infonode.docking.theme.internal.laftheme;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import net.infonode.gui.componentpainter.ComponentPainter;
import net.infonode.util.Direction;

class TitleBarUI$7 implements ComponentPainter {
   TitleBarUI$7(TitleBarUI var1, Color var2, Color var3, ComponentPainter var4, Color var5) {
      super();
      this.this$0 = var1;
      this.val$gradient = var2;
      this.val$avgColor = var3;
      this.val$painter = var4;
      this.val$background = var5;
   }

   public void paint(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public void paint(Component var1, Graphics var2, int var3, int var4, int var5, int var6, Direction var7, boolean var8, boolean var9) {
      var2.setColor(this.val$gradient);
      var2.drawLine(var3, var4, var3 + var5 - 1, var4);
      var2.drawLine(var3, var4, var3, var4 + var6 - 1);
      var2.setColor(this.val$avgColor);
      var2.drawRect(var3 + 1, var4 + 1, var5 - 3, var6 - 3);
      this.val$painter.paint(var1, var2, var3 + 2, var4 + 2, var5 - 4, var6 - 4, var7, var8, var9);
      var2.setColor(this.val$background);
      var2.drawLine(var3 + 1, var6 - 1 + var4, var3 + var5 - 1, var6 - 1 + var4);
      var2.drawLine(var3 + var5 - 1, var4, var3 + var5 - 1, var4 + var6 - 1);
   }

   public boolean isOpaque(Component var1) {
      return this.val$painter.isOpaque(var1);
   }

   public Color getColor(Component var1) {
      return this.val$painter.getColor(var1);
   }
}
