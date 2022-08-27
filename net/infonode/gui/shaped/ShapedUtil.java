package net.infonode.gui.shaped;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.Rectangle;
import net.infonode.gui.InsetsUtil;
import net.infonode.gui.RectangleUtil;
import net.infonode.gui.shaped.panel.ShapedPanel;
import net.infonode.util.Direction;

public class ShapedUtil {
   private ShapedUtil() {
      super();
   }

   public static Direction getDirection(Component var0) {
      return var0 instanceof ShapedPanel ? ((ShapedPanel)var0).getDirection() : Direction.RIGHT;
   }

   public static Insets transformInsets(Component var0, Insets var1) {
      return InsetsUtil.rotate(getDirection(var0), flipInsets(var0, var1));
   }

   public static Insets flipInsets(Component var0, Insets var1) {
      if (var0 instanceof ShapedPanel) {
         if (((ShapedPanel)var0).isHorizontalFlip()) {
            var1 = InsetsUtil.flipHorizontal(var1);
         }

         if (((ShapedPanel)var0).isVerticalFlip()) {
            var1 = InsetsUtil.flipVertical(var1);
         }
      }

      return var1;
   }

   public static void rotateCW(Polygon var0, int var1) {
      for(int var2 = 0; var2 < var0.npoints; ++var2) {
         int var3 = var0.ypoints[var2];
         var0.ypoints[var2] = var0.xpoints[var2];
         var0.xpoints[var2] = var1 - 1 - var3;
      }

   }

   public static void rotate(Polygon var0, Direction var1, int var2, int var3) {
      if (var1 == Direction.UP) {
         rotateCW(var0, var3);
         rotateCW(var0, var2);
         rotateCW(var0, var3);
      } else if (var1 == Direction.LEFT) {
         rotateCW(var0, var3);
         rotateCW(var0, var2);
      } else if (var1 == Direction.DOWN) {
         rotateCW(var0, var3);
      }

   }

   public static Rectangle transform(Component var0, Rectangle var1) {
      if (var0 instanceof ShapedPanel) {
         ShapedPanel var2 = (ShapedPanel)var0;
         return RectangleUtil.transform(var1, var2.getDirection(), var2.isHorizontalFlip(), var2.isVerticalFlip(), var0.getWidth(), var0.getHeight());
      } else {
         return var1;
      }
   }

   public static Dimension transform(Component var0, Dimension var1) {
      return getDirection(var0).isHorizontal() ? var1 : new Dimension(var1.height, var1.width);
   }

   public static int getWidth(Component var0, int var1, int var2) {
      return getDirection(var0).isHorizontal() ? var1 : var2;
   }

   public static int getHeight(Component var0, int var1, int var2) {
      return getDirection(var0).isHorizontal() ? var2 : var1;
   }
}
