package net.infonode.tabbedpanel.theme;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.Border;
import net.infonode.gui.GraphicsUtil;
import net.infonode.tabbedpanel.TabbedPanel;
import net.infonode.tabbedpanel.TabbedUtils;
import net.infonode.util.Direction;

class ClassicTheme$1 implements Border {
   ClassicTheme$1(ClassicTheme var1) {
      super();
      this.this$0 = var1;
   }

   public boolean isBorderOpaque() {
      return false;
   }

   public void paintBorder(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      TabbedPanel var7 = TabbedUtils.getParentTabbedPanel(var1);
      if (var7 != null) {
         Direction var8 = var7.getProperties().getTabAreaOrientation();
         var2.setColor(ClassicTheme.access$000(this.this$0).getColor());
         if (var8 != Direction.UP && var8 != Direction.DOWN) {
            GraphicsUtil.drawOptimizedLine(var2, var3, var4 + var6 - 1, var3 + var5 - 1, var4 + var6 - 1);
         } else {
            GraphicsUtil.drawOptimizedLine(var2, var3 + var5 - 1, var4, var3 + var5 - 1, var4 + var6 - 1);
         }
      }

   }

   public Insets getBorderInsets(Component var1) {
      TabbedPanel var2 = TabbedUtils.getParentTabbedPanel(var1);
      if (var2 == null) {
         return new Insets(0, 0, 0, 0);
      } else {
         Direction var3 = var2.getProperties().getTabAreaOrientation();
         return new Insets(0, 0, var3 != Direction.LEFT && var3 != Direction.RIGHT ? 0 : 1, var3 != Direction.UP && var3 != Direction.DOWN ? 0 : 1);
      }
   }
}
