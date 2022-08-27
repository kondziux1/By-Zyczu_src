package net.infonode.docking.theme.internal.laftheme;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import net.infonode.gui.componentpainter.ComponentPainter;
import net.infonode.util.Direction;

class TitleBarUI$3 implements ComponentPainter {
   TitleBarUI$3(TitleBarUI var1) {
      super();
      this.this$0 = var1;
   }

   public void paint(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public void paint(Component var1, Graphics var2, int var3, int var4, int var5, int var6, Direction var7, boolean var8, boolean var9) {
      var2.translate(-var3, -var4);
      this.this$0.paintTitleBar(var1, var2, false, var5, var6, Direction.UP);
      var2.translate(var3, var4);
   }

   public boolean isOpaque(Component var1) {
      return false;
   }

   public Color getColor(Component var1) {
      return this.this$0.getInactiveBackgroundColor();
   }
}
