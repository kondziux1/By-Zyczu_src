package net.infonode.docking.theme;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.Border;
import net.infonode.gui.InsetsUtil;
import net.infonode.tabbedpanel.TabbedUtils;
import net.infonode.util.Direction;

class LookAndFeelDockingTheme$4 implements Border {
   LookAndFeelDockingTheme$4(LookAndFeelDockingTheme$3 var1, Border var2) {
      super();
      this.this$1 = var1;
      this.val$contentBorder = var2;
   }

   public void paintBorder(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public Insets getBorderInsets(Component var1) {
      Insets var2 = (Insets)this.val$contentBorder.getBorderInsets(var1).clone();
      Direction var3 = TabbedUtils.getParentTabbedPanelContentPanel(var1).getTabbedPanel().getProperties().getTabAreaOrientation();
      int var4 = InsetsUtil.maxInset(var2);
      if (var3 == Direction.UP) {
         var2.bottom = Math.max(var4, var2.bottom);
      } else if (var3 == Direction.DOWN) {
         var2.top = Math.max(var4, var2.top);
      } else if (var3 == Direction.LEFT) {
         var2.right = Math.max(var4, var2.right);
      } else {
         var2.left = Math.max(var4, var2.left);
      }

      return var2;
   }

   public boolean isBorderOpaque() {
      return false;
   }
}
