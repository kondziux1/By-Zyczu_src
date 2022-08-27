package net.infonode.gui.shaped.border;

import java.awt.Component;
import java.awt.Insets;
import net.infonode.gui.colorprovider.ColorProvider;
import net.infonode.gui.colorprovider.FixedColorProvider;

public class RoundedCornerBorder extends PolygonBorder {
   private static final long serialVersionUID = 1L;
   private static int[][] corner1 = new int[][]{
      {0, 0}, {0, 1, 1, 0}, {0, 2, 2, 0}, {0, 4, 1, 3, 1, 2, 2, 1, 3, 1, 4, 0}, {0, 5, 1, 4, 1, 3, 3, 1, 4, 1, 5, 0}
   };
   private static int[][] corner2 = new int[][]{
      {-1, 0}, {-2, 0, -1, 1}, {-3, 0, -1, 2}, {-5, 0, -4, 1, -3, 1, -2, 2, -2, 3, -1, 4}, {-6, 0, -5, 1, -4, 1, -2, 3, -2, 4, -1, 5}
   };
   private static int[][] corner3 = new int[][]{
      {-1, -1}, {-1, -2, -2, -1}, {-1, -3, -3, -1}, {-1, -5, -2, -4, -2, -3, -3, -2, -4, -2, -5, -1}, {-1, -6, -2, -5, -2, -4, -4, -2, -5, -2, -6, -1}
   };
   private static int[][] corner4 = new int[][]{
      {0, -1}, {1, -1, 0, -2}, {2, -1, 0, -3}, {4, -1, 3, -2, 2, -2, 1, -3, 1, -4, 0, -5}, {5, -1, 4, -2, 3, -2, 1, -4, 1, -5, 0, -6}
   };
   private Insets insets;

   public RoundedCornerBorder(ColorProvider var1, int var2) {
      this(var1, var2, true, true, true, true);
   }

   public RoundedCornerBorder(ColorProvider var1, int var2, boolean var3, boolean var4, boolean var5, boolean var6) {
      this(var1, FixedColorProvider.WHITE, var2, var2, var2, var2, var3, var4, var5, var6);
   }

   public RoundedCornerBorder(ColorProvider var1, ColorProvider var2, int var3, int var4, int var5, int var6) {
      this(var1, var2, var3, var4, var5, var6, true, true, true, true);
   }

   public RoundedCornerBorder(
      ColorProvider var1, ColorProvider var2, int var3, int var4, int var5, int var6, boolean var7, boolean var8, boolean var9, boolean var10
   ) {
      super(
         var1,
         var2,
         createCoordinates(var3, var4, var5, var6, var7, var8, var9, var10),
         createXScales(var3, var4, var5, var6),
         createYScales(var3, var4, var5, var6)
      );
      this.insets = new Insets(var7 ? 1 : 0, var8 ? 1 : 0, var9 ? 1 : 0, var10 ? 1 : 0);
   }

   protected Insets getShapedBorderInsets(Component var1) {
      return this.insets;
   }

   private static int[] createCoordinates(int var0, int var1, int var2, int var3, boolean var4, boolean var5, boolean var6, boolean var7) {
      int[] var8 = corner1[var0];
      int[] var9 = corner2[var1];
      int[] var10 = corner3[var2];
      int[] var11 = corner4[var3];
      int[] var12 = new int[var8.length + var9.length + var10.length + var11.length];
      int var13 = 0;

      for(int var14 = 0; var14 < var8.length; ++var14) {
         var12[var13] = var5 ? var8[var14] : var8[var14] - 1;
         ++var13;
         ++var14;
         var12[var13] = var4 ? var8[var14] : var8[var14] - 1;
         ++var13;
      }

      for(int var20 = 0; var20 < var9.length; ++var20) {
         var12[var13] = var7 ? var9[var20] : var9[var20] + 1;
         ++var13;
         ++var20;
         var12[var13] = var4 ? var9[var20] : var9[var20] - 1;
         ++var13;
      }

      for(int var22 = 0; var22 < var10.length; ++var22) {
         var12[var13] = var7 ? var10[var22] : var10[var22] + 1;
         ++var13;
         ++var22;
         var12[var13] = var6 ? var10[var22] : var10[var22] + 1;
         ++var13;
      }

      for(int var24 = 0; var24 < var11.length; ++var24) {
         var12[var13] = var5 ? var11[var24] : var11[var24] - 1;
         ++var13;
         ++var24;
         var12[var13] = var6 ? var11[var24] : var11[var24] + 1;
         ++var13;
      }

      return var12;
   }

   private static float[] createXScales(int var0, int var1, int var2, int var3) {
      int[][] var4 = new int[4][1];
      var4[0] = corner1[var0];
      var4[1] = corner2[var1];
      var4[2] = corner3[var2];
      var4[3] = corner4[var3];
      float[] var5 = new float[var4[0].length + var4[1].length + var4[2].length + var4[3].length];
      int var6 = 0;

      for(int var7 = 0; var7 < var4.length; ++var7) {
         for(int var8 = 0; var8 < var4[var7].length; ++var8) {
            var5[var6] = var4[var7][var8] < 0 ? 1.0F : 0.0F;
            ++var8;
            var6 += 2;
         }
      }

      return var5;
   }

   private static float[] createYScales(int var0, int var1, int var2, int var3) {
      int[][] var4 = new int[4][1];
      var4[0] = corner1[var0];
      var4[1] = corner2[var1];
      var4[2] = corner3[var2];
      var4[3] = corner4[var3];
      float[] var5 = new float[var4[0].length + var4[1].length + var4[2].length + var4[3].length];
      int var6 = 1;

      for(int var7 = 0; var7 < var4.length; ++var7) {
         for(int var8 = 1; var8 < var4[var7].length; ++var8) {
            var5[var6] = var4[var7][var8] < 0 ? 1.0F : 0.0F;
            ++var8;
            var6 += 2;
         }
      }

      return var5;
   }
}
