package net.infonode.gui.shaped.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.Shape;
import net.infonode.gui.GraphicsUtil;
import net.infonode.gui.HighlightPainter;
import net.infonode.gui.InsetsUtil;
import net.infonode.gui.colorprovider.BackgroundPainterColorProvider;
import net.infonode.gui.colorprovider.ColorProvider;
import net.infonode.gui.colorprovider.FixedColorProvider;
import net.infonode.gui.shaped.ShapedUtil;
import net.infonode.gui.shaped.panel.ShapedPanel;

public abstract class AbstractPolygonBorder extends AbstractShapedBorder {
   private static final long serialVersionUID = 1L;
   private static final Insets HIGHLIGHT_INSETS = new Insets(1, 1, 0, 0);
   private ColorProvider lineColor;
   private ColorProvider highlightColor = new FixedColorProvider(new Color(255, 255, 255));
   private ColorProvider middleColor;
   private ColorProvider shadowColor;

   protected AbstractPolygonBorder(ColorProvider var1) {
      this(var1, FixedColorProvider.WHITE);
   }

   protected AbstractPolygonBorder(ColorProvider var1, ColorProvider var2) {
      this(var1, var2, BackgroundPainterColorProvider.INSTANCE, null);
   }

   protected AbstractPolygonBorder(ColorProvider var1, ColorProvider var2, ColorProvider var3, ColorProvider var4) {
      super();
      this.lineColor = var1;
      this.highlightColor = var2;
      this.middleColor = var3;
      this.shadowColor = var4;
   }

   public Shape getShape(Component var1, int var2, int var3, int var4, int var5) {
      int var6 = ShapedUtil.getWidth(var1, var4, var5);
      int var7 = ShapedUtil.getHeight(var1, var4, var5);
      return this.getPolygon(var1, var2, var3, var6, var7);
   }

   public boolean isBorderOpaque() {
      return false;
   }

   public void paintBorder(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      Shape var7 = var2.getClip();
      var2.clipRect(var3, var4, var5, var6);

      try {
         int var8 = ShapedUtil.getWidth(var1, var5, var6);
         var6 = ShapedUtil.getHeight(var1, var5, var6);
         Polygon var9 = this.getPolygon(var1, var3, var4, var8, var6);
         Graphics2D var10 = (Graphics2D)var2;
         if (this.highlightColor != null) {
            this.paintHighlight(var1, var10, var9, var8, var6);
         }

         if (this.lineColor != null) {
            var2.setColor(this.lineColor.getColor());
            this.paintPolygon(var1, var10, var9, var8, var6);
         }
      } finally {
         var2.setClip(var7);
      }

   }

   public Insets getBorderInsets(Component var1) {
      Insets var2 = this.getShapedBorderInsets(var1);
      var2 = ShapedUtil.transformInsets(var1, var2);
      return this.highlightColor != null ? InsetsUtil.add(this.getShapedBorderHighlightInsets(var1), var2) : var2;
   }

   protected Insets getShapedBorderInsets(Component var1) {
      return new Insets(0, 0, 0, 0);
   }

   protected Insets getShapedBorderHighlightInsets(Component var1) {
      return HIGHLIGHT_INSETS;
   }

   protected Polygon createPolygon(Component var1, int var2, int var3) {
      return new Polygon();
   }

   protected void paintPolygon(Component var1, Graphics2D var2, Polygon var3, int var4, int var5) {
      for(int var6 = 0; var6 < var3.npoints; ++var6) {
         if (this.lineIsDrawn(var6, var3)) {
            int var7 = (var6 + 1) % var3.npoints;
            GraphicsUtil.drawOptimizedLine(var2, var3.xpoints[var6], var3.ypoints[var6], var3.xpoints[var7], var3.ypoints[var7]);
         }
      }

   }

   protected void paintHighlight(Component var1, Graphics2D var2, Polygon var3, int var4, int var5) {
      Color var6 = this.highlightColor == null ? null : this.highlightColor.getColor(var1);
      Color var7 = this.middleColor.getColor(var1);
      Color var8 = this.shadowColor == null ? null : this.shadowColor.getColor(var1);
      boolean var9 = this.isPointsClockwise(var1);

      for(int var10 = 0; var10 < var3.npoints; ++var10) {
         int var11 = (var10 + 1) % var3.npoints;
         if (this.lineIsDrawn(var10, var3)) {
            HighlightPainter.drawLine(var2, var3.xpoints[var10], var3.ypoints[var10], var3.xpoints[var11], var3.ypoints[var11], var9, true, var6, var7, var8);
         }
      }

   }

   protected boolean lineIsDrawn(int var1, Polygon var2) {
      return true;
   }

   protected boolean isHighlightable(int var1, int var2) {
      return var1 > var2;
   }

   protected boolean isPointsClockwise(Component var1) {
      if (var1 instanceof ShapedPanel) {
         return !(((ShapedPanel)var1).isHorizontalFlip() ^ ((ShapedPanel)var1).isVerticalFlip());
      } else {
         return true;
      }
   }

   protected int getHighlightOffsetX(int var1, int var2) {
      return var2 - var1 > 0 ? (var1 + var2 > 0 ? -1 : 0) : (var1 + var2 > 0 ? 0 : 1);
   }

   protected int getHighlightOffsetY(int var1, int var2) {
      return var2 - var1 > 0 ? (var1 + var2 > 0 ? 0 : -1) : (var1 + var2 > 0 ? 1 : 0);
   }

   protected void setPoint(Polygon var1, int var2, int var3) {
      var1.xpoints[var1.npoints] = var2;
      var1.ypoints[var1.npoints] = var3;
      ++var1.npoints;
   }

   private Polygon getPolygon(Component var1, int var2, int var3, int var4, int var5) {
      Polygon var6 = this.createPolygon(var1, var4, var5);
      this.flipPolygon(var1, var6, var4, var5);
      this.rotatePolygon(var1, var6, var4, var5);
      this.fixGraphicsOffset(var1, var6, var2, var3);
      return var6;
   }

   private void flipPolygon(Component var1, Polygon var2, int var3, int var4) {
      if (var1 instanceof ShapedPanel) {
         if (((ShapedPanel)var1).isHorizontalFlip()) {
            for(int var5 = 0; var5 < var2.npoints; ++var5) {
               var2.xpoints[var5] = Math.abs(var3 - var2.xpoints[var5]) - 1;
            }
         }

         if (((ShapedPanel)var1).isVerticalFlip()) {
            for(int var6 = 0; var6 < var2.npoints; ++var6) {
               var2.ypoints[var6] = Math.abs(var4 - var2.ypoints[var6]) - 1;
            }
         }
      }

   }

   private void rotatePolygon(Component var1, Polygon var2, int var3, int var4) {
      ShapedUtil.rotate(var2, ShapedUtil.getDirection(var1), var3, var4);
   }

   private void fixGraphicsOffset(Component var1, Polygon var2, int var3, int var4) {
      for(int var5 = 0; var5 < var2.npoints; ++var5) {
         var2.xpoints[var5] += var3;
         var2.ypoints[var5] += var4;
      }

   }
}
