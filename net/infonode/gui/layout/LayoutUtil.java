package net.infonode.gui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import net.infonode.util.Direction;

public class LayoutUtil {
   private LayoutUtil() {
      super();
   }

   public static Component[] getVisibleChildren(Container var0) {
      return getVisibleChildren(var0.getComponents());
   }

   public static Component[] getVisibleChildren(Component[] var0) {
      int var1 = 0;

      for(int var2 = 0; var2 < var0.length; ++var2) {
         if (var0[var2].isVisible()) {
            ++var1;
         }
      }

      Component[] var5 = new Component[var1];
      int var3 = 0;

      for(int var4 = 0; var4 < var0.length; ++var4) {
         if (var0[var4].isVisible()) {
            var5[var3++] = var0[var4];
         }
      }

      return var5;
   }

   public static Rectangle getInteriorArea(Container var0) {
      Insets var1 = var0.getInsets();
      return new Rectangle(var1.left, var1.top, var0.getWidth() - var1.left - var1.right, var0.getHeight() - var1.top - var1.bottom);
   }

   public static Dimension getInteriorSize(Container var0) {
      Insets var1 = var0.getInsets();
      return new Dimension(var0.getWidth() - var1.left - var1.right, var0.getHeight() - var1.top - var1.bottom);
   }

   public static Dimension rotate(Dimension var0, Direction var1) {
      return rotate(var0, var1.isHorizontal());
   }

   public static Dimension rotate(Dimension var0, boolean var1) {
      return var0 == null ? null : (var1 ? var0 : new Dimension(var0.height, var0.width));
   }

   public static boolean isDescendingFrom(Component var0, Component var1) {
      return var0 == var1 || var0 != null && isDescendingFrom(var0.getParent(), var1);
   }

   public static Dimension getMaxMinimumSize(Component[] var0) {
      int var1 = 0;
      int var2 = 0;

      for(int var3 = 0; var3 < var0.length; ++var3) {
         if (var0[var3] != null) {
            Dimension var4 = var0[var3].getMinimumSize();
            int var5 = var4.width;
            int var6 = var4.height;
            if (var2 < var6) {
               var2 = var6;
            }

            if (var1 < var5) {
               var1 = var5;
            }
         }
      }

      return new Dimension(var1, var2);
   }

   public static Dimension getMaxPreferredSize(Component[] var0) {
      int var1 = 0;
      int var2 = 0;

      for(int var3 = 0; var3 < var0.length; ++var3) {
         if (var0[var3] != null) {
            Dimension var4 = var0[var3].getPreferredSize();
            int var5 = var4.width;
            int var6 = var4.height;
            if (var2 < var6) {
               var2 = var6;
            }

            if (var1 < var5) {
               var1 = var5;
            }
         }
      }

      return new Dimension(var1, var2);
   }

   public static Dimension getMinMaximumSize(Component[] var0) {
      int var1 = Integer.MAX_VALUE;
      int var2 = Integer.MAX_VALUE;

      for(int var3 = 0; var3 < var0.length; ++var3) {
         if (var0[var3] != null) {
            Dimension var4 = var0[var3].getMaximumSize();
            int var5 = var4.width;
            int var6 = var4.height;
            if (var1 > var5) {
               var1 = var5;
            }

            if (var2 > var6) {
               var2 = var6;
            }
         }
      }

      return new Dimension(var1, var2);
   }

   public static Insets rotate(Direction var0, Insets var1) {
      return var0 == Direction.RIGHT
         ? var1
         : (
            var0 == Direction.DOWN
               ? new Insets(var1.right, var1.top, var1.left, var1.bottom)
               : (var0 == Direction.LEFT ? new Insets(var1.bottom, var1.right, var1.top, var1.left) : new Insets(var1.left, var1.bottom, var1.right, var1.top))
         );
   }

   public static Insets unrotate(Direction var0, Insets var1) {
      return var0 == Direction.RIGHT
         ? var1
         : (
            var0 == Direction.DOWN
               ? new Insets(var1.left, var1.bottom, var1.right, var1.top)
               : (var0 == Direction.LEFT ? new Insets(var1.bottom, var1.right, var1.top, var1.left) : new Insets(var1.right, var1.top, var1.left, var1.bottom))
         );
   }

   public static Dimension add(Dimension var0, Insets var1) {
      return new Dimension(var0.width + var1.left + var1.right, var0.height + var1.top + var1.bottom);
   }

   public static Dimension getValidSize(Dimension var0, Component var1) {
      Dimension var2 = var1.getMinimumSize();
      Dimension var3 = var1.getMaximumSize();
      return new Dimension(Math.max(var2.width, Math.min(var0.width, var3.width)), Math.max(var2.height, Math.min(var0.height, var3.height)));
   }

   public static Component getChildContaining(Component var0, Component var1) {
      return var1 == null ? null : (var1.getParent() == var0 ? var1 : getChildContaining(var0, var1.getParent()));
   }

   public static String getBorderLayoutOrientation(Direction var0) {
      return var0 == Direction.UP ? "North" : (var0 == Direction.DOWN ? "South" : (var0 == Direction.LEFT ? "West" : "East"));
   }
}
