package net.infonode.tabbedpanel.internal;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Polygon;
import net.infonode.gui.GraphicsUtil;
import net.infonode.gui.colorprovider.ColorProvider;
import net.infonode.gui.shaped.border.RoundedCornerBorder;
import net.infonode.tabbedpanel.TabbedPanel;
import net.infonode.tabbedpanel.TabbedUtils;
import net.infonode.util.Direction;

public class TwoColoredLineBorder extends RoundedCornerBorder {
   private static final long serialVersionUID = 1L;
   private ColorProvider topLeftColor;
   private ColorProvider bottomRightColor;
   private boolean roundCorners;
   private boolean open;

   public TwoColoredLineBorder(ColorProvider var1, ColorProvider var2, boolean var3, boolean var4) {
      super(var1, null, var3 ? 2 : 0, var3 ? 2 : 0, var3 && !var4 ? 2 : 0, var3 && !var4 ? 2 : 0, true, true, !var4, true);
      this.topLeftColor = var1;
      this.bottomRightColor = var2;
      this.roundCorners = var3;
      this.open = var4;
   }

   protected void paintPolygon(Component var1, Graphics2D var2, Polygon var3, int var4, int var5) {
      TabbedPanel var6 = TabbedUtils.getParentTabbedPanel(var1);
      if (var6 != null) {
         Direction var7 = var6.getProperties().getTabAreaOrientation();
         int var8 = 0;
         Color var9 = this.topLeftColor.getColor();
         Color var10 = this.bottomRightColor.getColor();
         if (var7 == Direction.UP) {
            var2.setColor(var9);

            while(var8 < (this.roundCorners ? 3 : 1)) {
               GraphicsUtil.drawOptimizedLine(var2, var3.xpoints[var8], var3.ypoints[var8], var3.xpoints[var8 + 1], var3.ypoints[var8 + 1]);
               ++var8;
            }

            var2.setColor(var10);

            while(var8 < var3.npoints - 1) {
               GraphicsUtil.drawOptimizedLine(var2, var3.xpoints[var8], var3.ypoints[var8], var3.xpoints[var8 + 1], var3.ypoints[var8 + 1]);
               ++var8;
            }

            var2.setColor(var9);
            GraphicsUtil.drawOptimizedLine(var2, var3.xpoints[var8], var3.ypoints[var8], var3.xpoints[0], var3.ypoints[0]);
         } else if (var7 == Direction.RIGHT) {
            var2.setColor(var10);

            while(var8 < var3.npoints - (this.open ? 2 : (this.roundCorners ? 3 : 2))) {
               GraphicsUtil.drawOptimizedLine(var2, var3.xpoints[var8], var3.ypoints[var8], var3.xpoints[var8 + 1], var3.ypoints[var8 + 1]);
               ++var8;
            }

            var2.setColor(var9);

            for(int var11 = var8 - 1; var11 < var3.npoints - 2; ++var11) {
               GraphicsUtil.drawOptimizedLine(var2, var3.xpoints[var8], var3.ypoints[var8], var3.xpoints[var8 + 1], var3.ypoints[var8 + 1]);
               ++var8;
            }

            GraphicsUtil.drawOptimizedLine(var2, var3.xpoints[var8], var3.ypoints[var8], var3.xpoints[0], var3.ypoints[0]);
         } else if (var7 == Direction.DOWN) {
            var2.setColor(var10);

            while(var8 < (this.roundCorners ? 5 : 2)) {
               GraphicsUtil.drawOptimizedLine(var2, var3.xpoints[var8], var3.ypoints[var8], var3.xpoints[var8 + 1], var3.ypoints[var8 + 1]);
               ++var8;
            }

            var2.setColor(var9);

            while(var8 < var3.npoints - 1) {
               GraphicsUtil.drawOptimizedLine(var2, var3.xpoints[var8], var3.ypoints[var8], var3.xpoints[var8 + 1], var3.ypoints[var8 + 1]);
               ++var8;
            }

            GraphicsUtil.drawOptimizedLine(var2, var3.xpoints[var8], var3.ypoints[var8], var3.xpoints[0], var3.ypoints[0]);
         } else {
            var2.setColor(var9);

            while(var8 < (this.roundCorners ? 3 : 1)) {
               GraphicsUtil.drawOptimizedLine(var2, var3.xpoints[var8], var3.ypoints[var8], var3.xpoints[var8 + 1], var3.ypoints[var8 + 1]);
               ++var8;
            }

            var2.setColor(var10);

            while(var8 < var3.npoints - 1) {
               GraphicsUtil.drawOptimizedLine(var2, var3.xpoints[var8], var3.ypoints[var8], var3.xpoints[var8 + 1], var3.ypoints[var8 + 1]);
               ++var8;
            }

            var2.setColor(var9);
            GraphicsUtil.drawOptimizedLine(var2, var3.xpoints[var8], var3.ypoints[var8], var3.xpoints[0], var3.ypoints[0]);
         }
      }

   }
}
