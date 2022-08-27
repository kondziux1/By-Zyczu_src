package net.infonode.gui;

import java.awt.Insets;
import net.infonode.util.Direction;

public class InsetsUtil {
   public static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);

   private InsetsUtil() {
      super();
   }

   public static Insets getDiff(Insets var0, Insets var1) {
      int var2 = var1.top - var0.top;
      int var3 = var1.left - var0.left;
      int var4 = var1.bottom - var0.bottom;
      int var5 = var1.right - var0.right;
      return new Insets(var2 > 0 ? var2 : 0, var3 > 0 ? var3 : 0, var4 > 0 ? var4 : 0, var5 > 0 ? var5 : 0);
   }

   public static Insets sub(Insets var0, Insets var1) {
      return new Insets(var0.top - var1.top, var0.left - var1.left, var0.bottom - var1.bottom, var0.right - var1.right);
   }

   public static Insets add(Insets var0, Insets var1) {
      return new Insets(var0.top + var1.top, var0.left + var1.left, var0.bottom + var1.bottom, var0.right + var1.right);
   }

   public static final Insets flip(Insets var0, boolean var1, boolean var2) {
      return var1
         ? (var2 ? new Insets(var0.bottom, var0.right, var0.top, var0.left) : new Insets(var0.top, var0.right, var0.bottom, var0.left))
         : (var2 ? new Insets(var0.bottom, var0.left, var0.top, var0.right) : var0);
   }

   public static final Insets rotate(Direction var0, Insets var1) {
      if (var0 == Direction.LEFT) {
         return new Insets(var1.bottom, var1.right, var1.top, var1.left);
      } else if (var0 == Direction.DOWN) {
         return new Insets(var1.left, var1.bottom, var1.right, var1.top);
      } else {
         return var0 == Direction.UP ? new Insets(var1.right, var1.top, var1.left, var1.bottom) : (Insets)var1.clone();
      }
   }

   public static Insets max(Insets var0, Insets var1) {
      return new Insets(Math.max(var0.top, var1.top), Math.max(var0.left, var1.left), Math.max(var0.bottom, var1.bottom), Math.max(var0.right, var1.right));
   }

   public static int maxInset(Insets var0) {
      return Math.max(var0.top, Math.max(var0.bottom, Math.max(var0.left, var0.right)));
   }

   public static int getInset(Insets var0, Direction var1) {
      return var1 == Direction.UP ? var0.top : (var1 == Direction.LEFT ? var0.left : (var1 == Direction.DOWN ? var0.bottom : var0.right));
   }

   public static Insets setInset(Insets var0, Direction var1, int var2) {
      return var1 == Direction.UP
         ? new Insets(var2, var0.left, var0.bottom, var0.right)
         : (
            var1 == Direction.LEFT
               ? new Insets(var0.top, var2, var0.bottom, var0.right)
               : (var1 == Direction.DOWN ? new Insets(var0.top, var0.left, var2, var0.right) : new Insets(var0.top, var0.left, var0.bottom, var2))
         );
   }

   public static Insets copy(Insets var0) {
      return var0 == null ? null : new Insets(var0.top, var0.left, var0.bottom, var0.right);
   }

   public static void addTo(Insets var0, Insets var1) {
      var0.top += var1.top;
      var0.bottom += var1.bottom;
      var0.left += var1.left;
      var0.right += var1.right;
   }

   public static Insets flipHorizontal(Insets var0) {
      return new Insets(var0.top, var0.right, var0.bottom, var0.left);
   }

   public static Insets flipVertical(Insets var0) {
      return new Insets(var0.bottom, var0.left, var0.top, var0.right);
   }
}
