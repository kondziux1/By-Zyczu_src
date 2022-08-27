package net.infonode.gui;

import java.awt.Color;
import java.awt.Graphics;
import net.infonode.util.ColorUtil;

public class HighlightPainter {
   private HighlightPainter() {
      super();
   }

   public static void drawLine(Graphics var0, int var1, int var2, int var3, int var4, boolean var5, boolean var6, Color var7, Color var8, Color var9) {
      int var10 = var5 ? 1 : -1;
      int var11 = (var3 - var1) * var10;
      int var12 = (var4 - var2) * var10;
      int var13 = 2 * (var11 * var11 + var12 * var12);
      int var14 = var11 - var12;
      Color var15 = var14 > 0 ? var7 : var9;
      if (var15 != null) {
         var0.setColor(ColorUtil.blend(var8, var15, (double)((float)var14 * (float)var14 / (float)var13)));
         int var16 = var6 ? getHighlightOffsetX(var11, var12) : 0;
         int var17 = var6 ? getHighlightOffsetY(var11, var12) : 0;
         GraphicsUtil.drawOptimizedLine(var0, var1 + var16, var2 + var17, var3 + var16, var4 + var17);
      }

   }

   public static float getBlendFactor(int var0, int var1) {
      int var2 = 2 * (var0 * var0 + var1 * var1);
      int var3 = var0 - var1;
      return 1.0F - (float)var3 * (float)var3 / (float)var2;
   }

   protected static int getHighlightOffsetX(int var0, int var1) {
      return var1 - var0 > 0 ? (var0 + var1 > 0 ? -1 : 0) : (var0 + var1 > 0 ? 0 : 1);
   }

   protected static int getHighlightOffsetY(int var0, int var1) {
      return var1 - var0 > 0 ? (var0 + var1 > 0 ? 0 : -1) : (var0 + var1 > 0 ? 1 : 0);
   }
}
