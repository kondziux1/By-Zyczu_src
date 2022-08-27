package net.infonode.gui;

import java.awt.Rectangle;
import net.infonode.util.Direction;

public class RectangleUtil {
   private RectangleUtil() {
      super();
   }

   public static Rectangle transform(Rectangle var0, Direction var1, boolean var2, boolean var3, int var4, int var5) {
      int var6 = var2 ? (var1.isHorizontal() ? var4 : var5) - var0.width - var0.x : var0.x;
      int var7 = var3 ? (var1.isHorizontal() ? var5 : var4) - var0.height - var0.y : var0.y;
      return var1 == Direction.DOWN
         ? new Rectangle(var4 - var7 - var0.height, var6, var0.height, var0.width)
         : (
            var1 == Direction.LEFT
               ? new Rectangle(var4 - var6 - var0.width, var5 - var7 - var0.height, var0.width, var0.height)
               : (
                  var1 == Direction.UP
                     ? new Rectangle(var7, var5 - var6 - var0.width, var0.height, var0.width)
                     : new Rectangle(var6, var7, var0.width, var0.height)
               )
         );
   }
}
