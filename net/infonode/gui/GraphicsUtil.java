package net.infonode.gui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import javax.swing.SwingUtilities;

public class GraphicsUtil {
   public GraphicsUtil() {
      super();
   }

   public static void drawOptimizedLine(Graphics var0, int var1, int var2, int var3, int var4) {
      if (var0.getColor().getAlpha() < 255 && (var1 == var3 || var2 == var4)) {
         var0.fillRect(var1 < var3 ? var1 : var3, var2 < var4 ? var2 : var4, Math.abs(var3 - var1) + 1, Math.abs(var4 - var2) + 1);
      } else {
         var0.drawLine(var1, var2, var3, var4);
      }

   }

   public static Rectangle calculateIntersectionClip(int var0, int var1, int var2, int var3, Shape var4) {
      Rectangle var5 = var4.getBounds();
      SwingUtilities.computeIntersection(var0, var1, var2, var3, var5);
      return var5;
   }
}
