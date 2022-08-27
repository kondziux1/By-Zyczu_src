package net.infonode.util;

import java.awt.Color;

public final class ColorUtil {
   private ColorUtil() {
      super();
   }

   public static Color getOpposite(Color var0) {
      return isDark(var0) ? Color.WHITE : Color.BLACK;
   }

   public static Color shade(Color var0, double var1) {
      return blend(var0, getOpposite(var0), var1);
   }

   public static final Color mult(Color var0, double var1) {
      return var0 == null
         ? null
         : new Color(
            Math.min(255, (int)((double)var0.getRed() * var1)),
            Math.min(255, (int)((double)var0.getGreen() * var1)),
            Math.min(255, (int)((double)var0.getBlue() * var1)),
            var0.getAlpha()
         );
   }

   public static Color setAlpha(Color var0, int var1) {
      return var0 == null ? null : new Color(var0.getRed(), var0.getGreen(), var0.getBlue(), var1);
   }

   public static final Color add(Color var0, Color var1) {
      return var0 == null
         ? var1
         : (
            var1 == null
               ? var0
               : new Color(
                  Math.min(255, var0.getRed() + var1.getRed()),
                  Math.min(255, var0.getGreen() + var1.getGreen()),
                  Math.min(255, var0.getBlue() + var1.getBlue()),
                  var0.getAlpha()
               )
         );
   }

   public static Color blend(Color var0, Color var1, double var2) {
      double var4 = 1.0 - var2;
      return var0 == null
         ? (var1 == null ? null : var1)
         : (
            var1 == null
               ? var0
               : new Color(
                  Math.min(255, (int)((double)var0.getRed() * var4 + (double)var1.getRed() * var2)),
                  Math.min(255, (int)((double)var0.getGreen() * var4 + (double)var1.getGreen() * var2)),
                  Math.min(255, (int)((double)var0.getBlue() * var4 + (double)var1.getBlue() * var2)),
                  Math.min(255, (int)((double)var0.getAlpha() * var4 + (double)var1.getAlpha() * var2))
               )
         );
   }

   public static boolean isDark(Color var0) {
      return var0.getRed() + var0.getGreen() + var0.getBlue() < 540;
   }

   public static Color highlight(Color var0) {
      return mult(var0, isDark(var0) ? 1.5 : 0.67F);
   }

   public static Color copy(Color var0) {
      return var0 == null ? null : new Color(var0.getRed(), var0.getGreen(), var0.getBlue(), var0.getAlpha());
   }
}
