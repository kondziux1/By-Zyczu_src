package net.infonode.tabbedpanel.internal;

import java.awt.Component;
import java.awt.Insets;
import java.awt.Polygon;
import net.infonode.gui.colorprovider.ColorProvider;
import net.infonode.gui.colorprovider.UIManagerColorProvider;
import net.infonode.gui.shaped.border.AbstractPolygonBorder;

public class SlopedTabLineBorder extends AbstractPolygonBorder {
   private static final long serialVersionUID = 1L;
   private static final int[][][] corners = new int[][][]{
      {{0, 0}, {-1, 0, 0, -1}, {-2, 0, -1, -1, 0, -1}, {-4, 0, -3, -1, -2, -1, -1, -2, -1, -3, 0, -4}},
      {{0, 0}, {0, 0}, {0, 1, 1, 1, 2, 0}, {0, 4, 1, 3, 1, 2, 2, 1, 3, 1, 4, 0}},
      {{0, 0}, {0, 0}, {-2, 0, -1, 1, 0, 1}, {-4, 0, -3, 1, -2, 1, -1, 2, -1, 3, 0, 4}},
      {{0, 0}, {0, -1, 1, 0}, {0, -1, 1, -1, 2, 0}, {0, -4, 1, -3, 1, -2, 2, -1, 3, -1, 4, 0}},
      {{0, 0}, {0, 0}, {0, 2, 1, 1, 1, 0}, {0, 4, 1, 3, 1, 2, 2, 1, 3, 1, 4, 0}},
      {{0, 0}, {0, 0}, {-1, 0, -1, 1, 0, 2}, {-4, 0, -3, 1, -2, 1, -1, 2, -1, 3, 0, 4}}
   };
   private boolean drawBottomLine;
   private float leftSlope;
   private float rightSlope;
   private boolean bottomLeftRounded;
   private boolean topLeftRounded;
   private boolean topRightRounded;
   private boolean bottomRightRounded;
   private int leftHeight;
   private int rightHeight;
   private static int[] xCoords = new int[100];
   private static int[] yCoords = new int[100];
   private static int x;
   private static int y;
   private static int index;

   public SlopedTabLineBorder() {
      this(0.0F, 1.0F);
   }

   public SlopedTabLineBorder(float var1, float var2) {
      this(var1, var2, 22, 22);
   }

   public SlopedTabLineBorder(float var1, float var2, int var3, int var4) {
      this(var1, var2, var3, var4, false, false, false, false);
   }

   public SlopedTabLineBorder(float var1, float var2, boolean var3, boolean var4, boolean var5, boolean var6) {
      this(var1, var2, 22, 22, var3, var4, var5, var6);
   }

   public SlopedTabLineBorder(float var1, float var2, int var3, int var4, boolean var5, boolean var6, boolean var7, boolean var8) {
      this(UIManagerColorProvider.TABBED_PANE_DARK_SHADOW, UIManagerColorProvider.TABBED_PANE_HIGHLIGHT, false, var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public SlopedTabLineBorder(
      ColorProvider var1,
      ColorProvider var2,
      boolean var3,
      float var4,
      float var5,
      int var6,
      int var7,
      boolean var8,
      boolean var9,
      boolean var10,
      boolean var11
   ) {
      super(var1, var2);
      this.drawBottomLine = var3;
      this.leftHeight = var6;
      this.rightHeight = var7;
      this.leftSlope = var4;
      this.rightSlope = var5;
      this.bottomLeftRounded = var8;
      this.topLeftRounded = var9;
      this.topRightRounded = var10;
      this.bottomRightRounded = var11;
   }

   protected boolean lineIsDrawn(int var1, Polygon var2) {
      return this.drawBottomLine || var1 < var2.npoints - 1;
   }

   protected Insets getShapedBorderInsets(Component var1) {
      return new Insets(
         1,
         (this.isBottomLeftRounded(var1) ? 4 : 1)
            + (this.topLeftRounded ? (this.leftSlope <= 0.5F ? 4 : 1) : 0)
            + (int)(this.leftSlope * (float)this.leftHeight),
         this.drawBottomLine ? 1 : 0,
         (this.bottomRightRounded ? 4 : 1) + (this.topRightRounded ? (this.rightSlope <= 0.5F ? 4 : 1) : 0) + (int)(this.rightSlope * (float)this.rightHeight)
      );
   }

   protected boolean isBottomLeftRounded(Component var1) {
      return this.bottomLeftRounded;
   }

   private static int[] getCorner(int var0, float var1, boolean var2) {
      return corners[var0][!var2 ? 0 : (var0 < 4 ? (var1 >= 2.0F ? 1 : (var1 >= 1.0F ? 2 : 3)) : (var1 <= 0.5F ? 1 : (var1 <= 1.0F ? 2 : 3)))];
   }

   private static void addPoint(int var0, int var1) {
      xCoords[index] = var0;
      yCoords[index++] = var1;
      x = var0;
      y = var1;
   }

   private static void addCorner(int var0, int var1, int[] var2) {
      for(int var3 = 0; var3 < var2.length; ++var3) {
         addPoint(var0 + var2[var3++], var1 + var2[var3]);
      }

   }

   private static int getStartY(int[] var0) {
      return var0[1];
   }

   private static int getEndY(int[] var0) {
      return var0[var0.length - 1];
   }

   protected Polygon createPolygon(Component var1, int var2, int var3) {
      boolean var4 = this.isBottomLeftRounded(var1);
      int var5 = (int)((float)this.leftHeight * this.leftSlope + (float)(var4 ? 4 : 0));
      int var6 = var2 - 1 - (int)((float)this.rightHeight * this.rightSlope + (float)(this.bottomRightRounded ? 4 : 0));
      int var7 = var3 - (this.drawBottomLine ? 1 : 0);
      int[] var8 = getCorner(1, this.leftSlope, this.topLeftRounded);
      int[] var9 = getCorner(2, this.rightSlope, this.topRightRounded);
      int[] var10 = getCorner(0, this.leftSlope, var4);
      int[] var11 = getCorner(3, this.rightSlope, this.bottomRightRounded);
      index = 0;
      y = var7;
      int var12 = var3 - getStartY(var8) + getEndY(var10);
      if (var12 <= this.leftHeight) {
         x = var5 - (int)((float)var12 * this.leftSlope);
         addCorner(x, y, var10);
      } else {
         x = var5 - (int)((float)this.leftHeight * this.leftSlope);
         addCorner(x, y, getCorner(0, 0.0F, var4));
         addPoint(x, getStartY(var8) + this.leftHeight);
      }

      addCorner(var5, 0, var8);
      addCorner(var6, 0, var9);
      var12 = var3 - getEndY(var9) + getStartY(var11);
      if (var12 <= this.rightHeight) {
         addCorner((int)((float)var6 + (float)var12 * this.rightSlope), var7, var11);
      } else {
         addPoint((int)((float)var6 + this.rightSlope * (float)this.rightHeight), getEndY(var9) + this.rightHeight);
         addCorner(x, var7, getCorner(3, 0.0F, this.bottomRightRounded));
      }

      return new Polygon(xCoords, yCoords, index);
   }
}
