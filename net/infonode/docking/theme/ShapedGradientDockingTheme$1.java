package net.infonode.docking.theme;

import java.awt.Component;
import java.awt.Insets;
import java.awt.Polygon;
import javax.swing.border.Border;
import net.infonode.gui.colorprovider.ColorProvider;
import net.infonode.gui.shaped.border.RoundedCornerBorder;
import net.infonode.tabbedpanel.Tab;
import net.infonode.tabbedpanel.TabbedUtils;
import net.infonode.tabbedpanel.theme.ShapedGradientTheme;

class ShapedGradientDockingTheme$1 extends RoundedCornerBorder {
   private Border calculatedInsetsBorder;

   ShapedGradientDockingTheme$1(
      ShapedGradientDockingTheme var1,
      ColorProvider var2,
      ColorProvider var3,
      int var4,
      int var5,
      int var6,
      int var7,
      boolean var8,
      boolean var9,
      boolean var10,
      boolean var11,
      ShapedGradientTheme var12
   ) {
      super(var2, var3, var4, var5, var6, var7, var8, var9, var10, var11);
      this.this$0 = var1;
      this.val$theme = var12;
      this.calculatedInsetsBorder = this.val$theme.createTabBorder(this.val$theme.getLineColor(), null, 0.0F, 0.0F, false, true, true, false, true, true, 0);
   }

   protected Polygon createPolygon(Component var1, int var2, int var3) {
      Polygon var4 = super.createPolygon(var1, var2, var3);

      for(int var5 = 0; var5 < var4.npoints; ++var5) {
         if (var4.xpoints[var5] < var2 / 2) {
            var4.xpoints[var5] = var4.xpoints[var5] + (this.isFirst(var1) ? 0 : 4) + 1;
         } else {
            var4.xpoints[var5] = var4.xpoints[var5] - 4 - 1;
         }
      }

      return var4;
   }

   public Insets getBorderInsets(Component var1) {
      return this.calculatedInsetsBorder.getBorderInsets(var1);
   }

   private boolean isFirst(Component var1) {
      Tab var2 = TabbedUtils.getParentTab(var1);
      if (var2 != null && var2.getTabbedPanel() != null) {
         return var2.getTabbedPanel().getTabAt(0) == var2;
      } else {
         return false;
      }
   }
}
