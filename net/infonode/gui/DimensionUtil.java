package net.infonode.gui;

import java.awt.Dimension;
import java.awt.Insets;
import net.infonode.util.Direction;

public class DimensionUtil {
   public static final Dimension ZERO = new Dimension(0, 0);

   public DimensionUtil() {
      super();
   }

   public static Dimension min(Dimension var0, Dimension var1) {
      return new Dimension(Math.min((int)var0.getWidth(), (int)var1.getWidth()), Math.min((int)var0.getHeight(), (int)var1.getHeight()));
   }

   public static Dimension max(Dimension var0, Dimension var1) {
      return new Dimension(Math.max((int)var0.getWidth(), (int)var1.getWidth()), Math.max((int)var0.getHeight(), (int)var1.getHeight()));
   }

   public static Dimension getInnerDimension(Dimension var0, Insets var1) {
      return new Dimension((int)(var0.getWidth() - (double)var1.left - (double)var1.right), (int)(var0.getHeight() - (double)var1.top - (double)var1.bottom));
   }

   public static Dimension add(Dimension var0, Insets var1) {
      return new Dimension(var0.width + var1.left + var1.right, var0.height + var1.top + var1.bottom);
   }

   public static Dimension add(Dimension var0, Dimension var1, boolean var2) {
      return new Dimension(
         var2 ? var0.width + var1.width : Math.max(var0.width, var1.width), var2 ? Math.max(var0.height, var1.height) : var0.height + var1.height
      );
   }

   public static Dimension rotate(Direction var0, Dimension var1, Direction var2) {
      return var0.isHorizontal() && var2.isHorizontal() ? var1 : new Dimension(var1.height, var1.width);
   }
}
