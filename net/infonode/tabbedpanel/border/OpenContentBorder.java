package net.infonode.tabbedpanel.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.PathIterator;
import java.io.Serializable;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import net.infonode.gui.GraphicsUtil;
import net.infonode.gui.colorprovider.ColorProvider;
import net.infonode.gui.colorprovider.ColorProviderUtil;
import net.infonode.gui.colorprovider.FixedColorProvider;
import net.infonode.gui.colorprovider.UIManagerColorProvider;
import net.infonode.tabbedpanel.Tab;
import net.infonode.tabbedpanel.TabbedPanel;
import net.infonode.tabbedpanel.TabbedUtils;
import net.infonode.util.Direction;

public class OpenContentBorder implements Border, Serializable {
   private static final long serialVersionUID = 1L;
   private final ColorProvider topLeftLineColor;
   private final ColorProvider bottomRightLineColor;
   private final ColorProvider highlightColorProvider;
   private int tabLeftInset = 1;

   public OpenContentBorder(Color var1, int var2) {
      this(var1);
      this.tabLeftInset = var2;
   }

   public OpenContentBorder() {
      this(null);
   }

   public OpenContentBorder(Color var1) {
      this(var1, null);
   }

   public OpenContentBorder(Color var1, Color var2) {
      this(ColorProviderUtil.getColorProvider(var1, UIManagerColorProvider.TABBED_PANE_DARK_SHADOW), var2 == null ? null : new FixedColorProvider(var2), 1);
   }

   public OpenContentBorder(ColorProvider var1, ColorProvider var2, int var3) {
      this(var1, var1, var2, var3);
   }

   public OpenContentBorder(ColorProvider var1, ColorProvider var2, ColorProvider var3, int var4) {
      super();
      this.topLeftLineColor = var1;
      this.bottomRightLineColor = var2;
      this.highlightColorProvider = var3;
      this.tabLeftInset = var4;
   }

   private static int getLineIntersection(int var0, float var1, float var2, float var3, float var4, Direction var5) {
      return var5.isHorizontal()
         ? (
            var1 <= (float)var0 && var3 >= (float)var0 || var1 >= (float)var0 && var3 <= (float)var0
               ? Math.round(var3 == var1 ? var4 : var2 + ((float)var0 - var1) * (var4 - var2) / (var3 - var1))
               : Integer.MAX_VALUE
         )
         : (
            var2 <= (float)var0 && var4 >= (float)var0 || var2 >= (float)var0 && var4 <= (float)var0
               ? Math.round(var4 == var2 ? var3 : var1 + ((float)var0 - var2) * (var3 - var1) / (var4 - var2))
               : Integer.MAX_VALUE
         );
   }

   private static Point getTabBounds(Component var0, Tab var1, Direction var2, int var3, int var4, int var5, int var6) {
      Rectangle var7 = var1.getVisibleRect();
      var7 = SwingUtilities.convertRectangle(var1, var7, var0);
      int var8 = var2.isHorizontal() ? Math.max(var4, var7.y) : Math.max(var3, var7.x);
      int var9 = var8 + (var2.isHorizontal() ? var7.height : var7.width) - 1;
      Shape var10 = var1.getShape();
      if (var10 != null) {
         int var11 = var2 == Direction.UP ? var1.getHeight() : (var2 == Direction.RIGHT ? -1 : (var2 == Direction.DOWN ? -1 : var1.getWidth()));
         float[] var12 = new float[6];
         PathIterator var13 = var10.getPathIterator(null);
         var13.currentSegment(var12);
         var13.next();
         float var14 = var12[0];
         float var15 = var12[1];
         int var16 = Integer.MAX_VALUE;

         int var17;
         for(var17 = Integer.MIN_VALUE; !var13.isDone(); var13.next()) {
            float var18 = var12[0];
            float var19 = var12[1];
            var13.currentSegment(var12);
            int var20 = getLineIntersection(var11, var18, var19, var12[0], var12[1], var2);
            if (var20 != Integer.MAX_VALUE) {
               if (var20 < var16) {
                  var16 = var20;
               }

               if (var20 > var17) {
                  var17 = var20;
               }
            }
         }

         int var24 = getLineIntersection(var11, var12[0], var12[1], var14, var15, var2);
         if (var24 != Integer.MAX_VALUE) {
            if (var24 < var16) {
               var16 = var24;
            }

            if (var24 > var17) {
               var17 = var24;
            }
         }

         Point var25 = SwingUtilities.convertPoint(var1, 0, 0, var0);
         if (var2.isHorizontal()) {
            var16 += var25.y;
            var17 += var25.y;
         } else {
            var16 += var25.x;
            var17 += var25.x;
         }

         var8 = Math.max(var8, var16);
         var9 = Math.min(var9, var17);
      }

      return new Point(var8, var9);
   }

   public void paintBorder(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      TabbedPanel var7 = TabbedUtils.getParentTabbedPanelContentPanel(var1).getTabbedPanel();
      if (var1 != null && var7 != null) {
         Tab var8 = var7.getHighlightedTab();
         int var9 = -1;
         int var10 = -1;
         byte var11 = 0;
         Direction var12 = var7.getProperties().getTabAreaOrientation();
         if (var8 != null) {
            byte var10000;
            label127: {
               label126: {
                  Point var13 = getTabBounds(var1, var8, var12, var3, var4, var5, var6);
                  var9 = var13.x;
                  var10 = var13.y;
                  Rectangle var14 = var8.getVisibleRect();
                  int var15 = (int)var14.getWidth();
                  int var16 = (int)var14.getHeight();
                  if (var12.isHorizontal()) {
                     if (var8.getHeight() > var16) {
                        break label126;
                     }
                  } else if (var8.getWidth() > var15) {
                     break label126;
                  }

                  var10000 = 0;
                  break label127;
               }

               var10000 = -1;
            }

            var11 = var10000;
         }

         Color var17 = this.topLeftLineColor != null ? this.topLeftLineColor.getColor(var1) : null;
         Color var18 = this.bottomRightLineColor != null ? this.bottomRightLineColor.getColor(var1) : null;
         Color var19 = this.highlightColorProvider == null ? null : this.highlightColorProvider.getColor(var1);
         if (var12 == Direction.UP && var8 != null) {
            if (var17 != null) {
               var2.setColor(var17);
               drawLine(var2, var3, var4, var9 - 1 + this.tabLeftInset, var4);
               drawLine(var2, var10 - var11, var4, var3 + var5 - 1, var4);
            }

            if (this.highlightColorProvider != null) {
               var2.setColor(var19);
               drawLine(var2, var3 + 1, var4 + 1, var9 + this.tabLeftInset - 1, var4 + 1);
               if (var10 > var9) {
                  drawLine(var2, var9 + this.tabLeftInset, var4, var9 + this.tabLeftInset, var4 + 1);
               }

               drawLine(var2, var10, var4 + 1, var3 + var5 - 3, var4 + 1);
            }
         } else {
            if (var17 != null) {
               var2.setColor(var17);
               drawLine(var2, var3, var4, var3 + var5 - 1, var4);
            }

            if (this.highlightColorProvider != null) {
               var2.setColor(var19);
               drawLine(var2, var3 + 1, var4 + 1, var3 + var5 - (var12 == Direction.RIGHT && var9 == 0 ? 1 : 3), var4 + 1);
            }
         }

         if (var12 == Direction.LEFT && var8 != null) {
            if (var17 != null) {
               var2.setColor(var17);
               drawLine(var2, var3, var4 + 1, var3, var9 - 1 + this.tabLeftInset);
               drawLine(var2, var3, var10 - var11, var3, var4 + var6 - 1);
            }

            if (this.highlightColorProvider != null) {
               var2.setColor(var19);
               drawLine(var2, var3 + 1, var4 + 2, var3 + 1, var9 + this.tabLeftInset - 1);
               if (var10 > var9) {
                  drawLine(var2, var3, var9 + this.tabLeftInset, var3 + 1, var9 + this.tabLeftInset);
               }

               drawLine(var2, var3 + 1, var10, var3 + 1, var4 + var6 - 3);
            }
         } else {
            if (var17 != null) {
               var2.setColor(var17);
               drawLine(var2, var3, var4 + 1, var3, var4 + var6 - 1);
            }

            if (this.highlightColorProvider != null) {
               var2.setColor(var19);
               drawLine(var2, var3 + 1, var4 + 2, var3 + 1, var4 + var6 - (var12 == Direction.DOWN && var9 == 0 ? 1 : 3));
            }
         }

         if (var18 != null) {
            var2.setColor(var18);
            if (var12 == Direction.RIGHT && var8 != null) {
               drawLine(var2, var3 + var5 - 1, var4 + 1, var3 + var5 - 1, var9 - 1 + this.tabLeftInset);
               drawLine(var2, var3 + var5 - 1, var10 - var11, var3 + var5 - 1, var4 + var6 - 1);
            } else {
               drawLine(var2, var3 + var5 - 1, var4 + 1, var3 + var5 - 1, var4 + var6 - 1);
            }

            if (var12 == Direction.DOWN && var8 != null) {
               var2.setColor(var18);
               drawLine(var2, var3 + 1, var4 + var6 - 1, var9 - 1 + this.tabLeftInset, var4 + var6 - 1);
               drawLine(var2, var10 - var11, var4 + var6 - 1, var3 + var5 - 2, var4 + var6 - 1);
            } else {
               drawLine(var2, var3 + 1, var4 + var6 - 1, var3 + var5 - 2, var4 + var6 - 1);
            }
         }
      }

   }

   private static void drawLine(Graphics var0, int var1, int var2, int var3, int var4) {
      if (var3 >= var1 && var4 >= var2) {
         GraphicsUtil.drawOptimizedLine(var0, var1, var2, var3, var4);
      }
   }

   public Insets getBorderInsets(Component var1) {
      int var2 = this.highlightColorProvider != null ? 1 : 0;
      int var3 = (this.topLeftLineColor != null ? 1 : 0) + var2;
      int var4 = this.bottomRightLineColor != null ? 1 : 0;
      return new Insets(var3, var3, var4, var4);
   }

   public boolean isBorderOpaque() {
      return true;
   }
}
