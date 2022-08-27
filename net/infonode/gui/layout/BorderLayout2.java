package net.infonode.gui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Point;
import java.awt.Rectangle;

public class BorderLayout2 implements LayoutManager2 {
   private Component[][] components = new Component[3][];

   public BorderLayout2() {
      super();

      for(int var1 = 0; var1 < this.components.length; ++var1) {
         this.components[var1] = new Component[3];
      }

   }

   public void addLayoutComponent(String var1, Component var2) {
   }

   public void addLayoutComponent(Component var1, Object var2) {
      if (var2 instanceof Point) {
         Point var3 = (Point)var2;
         this.components[var3.x][var3.y] = var1;
      } else {
         throw new RuntimeException("BorderLayout2 constraint must be a Point!");
      }
   }

   public float getLayoutAlignmentX(Container var1) {
      return var1.getAlignmentX();
   }

   public float getLayoutAlignmentY(Container var1) {
      return var1.getAlignmentY();
   }

   public void invalidateLayout(Container var1) {
   }

   public Dimension maximumLayoutSize(Container var1) {
      int var2 = 0;
      int var3 = 0;

      for(int var4 = 0; var4 < 3; ++var4) {
         var2 += this.getMaximumWidth(var4);
         var3 += this.getMaximumHeight(var4);
      }

      return new Dimension(var2, var3);
   }

   private int getPreferredHeight(int var1) {
      int var2 = 0;

      for(int var3 = 0; var3 < 3; ++var3) {
         Component var4 = this.components[var3][var1];
         if (var4 != null && var4.isVisible()) {
            int var5 = var4.getPreferredSize().height;
            if (var5 > var2) {
               var2 = var5;
            }
         }
      }

      return var2;
   }

   private int getPreferredWidth(int var1) {
      int var2 = 0;

      for(int var3 = 0; var3 < 3; ++var3) {
         Component var4 = this.components[var1][var3];
         if (var4 != null && var4.isVisible()) {
            int var5 = var4.getPreferredSize().width;
            if (var5 > var2) {
               var2 = var5;
            }
         }
      }

      return var2;
   }

   private int getMinimumHeight(int var1) {
      int var2 = 0;

      for(int var3 = 0; var3 < 3; ++var3) {
         Component var4 = this.components[var3][var1];
         if (var4 != null && var4.isVisible()) {
            int var5 = var4.getMinimumSize().height;
            if (var5 > var2) {
               var2 = var5;
            }
         }
      }

      return var2;
   }

   private int getMinimumWidth(int var1) {
      int var2 = 0;

      for(int var3 = 0; var3 < 3; ++var3) {
         Component var4 = this.components[var1][var3];
         if (var4 != null && var4.isVisible()) {
            int var5 = var4.getMinimumSize().width;
            if (var5 > var2) {
               var2 = var5;
            }
         }
      }

      return var2;
   }

   private int getMaximumHeight(int var1) {
      int var2 = Integer.MAX_VALUE;

      for(int var3 = 0; var3 < 3; ++var3) {
         Component var4 = this.components[var3][var1];
         if (var4 != null && var4.isVisible()) {
            int var5 = var4.getMaximumSize().height;
            if (var5 < var2) {
               var2 = var5;
            }
         }
      }

      return var2;
   }

   private int getMaximumWidth(int var1) {
      int var2 = 0;

      for(int var3 = 0; var3 < 3; ++var3) {
         Component var4 = this.components[var1][var3];
         if (var4 != null && var4.isVisible()) {
            int var5 = var4.getMaximumSize().width;
            if (var5 < var2) {
               var2 = var5;
            }
         }
      }

      return var2;
   }

   private static void setBounds(Component var0, Rectangle var1) {
      int var2 = Math.min(var0.getMaximumSize().width, var1.width);
      int var3 = Math.min(var0.getMaximumSize().height, var1.height);
      Rectangle var4 = new Rectangle(
         var1.x + (int)(var0.getAlignmentX() * (float)(var1.width - var2)), var1.y + (int)(var0.getAlignmentY() * (float)(var1.height - var3)), var2, var3
      );
      var0.setBounds(var4);
   }

   public void layoutContainer(Container var1) {
      Insets var2 = var1.getInsets();
      Dimension var3 = LayoutUtil.getInteriorSize(var1);
      int[] var4 = new int[]{this.getPreferredWidth(0), this.getPreferredWidth(2)};
      int[] var5 = new int[]{this.getPreferredHeight(0), this.getPreferredHeight(2)};
      int var6 = var2.top;

      for(int var7 = 0; var7 < 3; ++var7) {
         int var8 = var7 == 1 ? var3.height - var5[0] - var5[1] : var5[var7 / 2];
         int var9 = var2.left;

         for(int var10 = 0; var10 < 3; ++var10) {
            int var11 = var10 == 1 ? var3.width - var4[0] - var4[1] : var4[var10 / 2];
            Component var12 = this.components[var10][var7];
            if (var12 != null && var12.isVisible()) {
               setBounds(var12, new Rectangle(var9, var6, var11, var8));
            }

            var9 += var11;
         }

         var6 += var8;
      }

   }

   public Dimension minimumLayoutSize(Container var1) {
      int var2 = 0;
      int var3 = 0;

      for(int var4 = 0; var4 < 3; ++var4) {
         var2 += this.getMinimumWidth(var4);
         var3 += this.getMinimumHeight(var4);
      }

      return new Dimension(var2, var3);
   }

   public Dimension preferredLayoutSize(Container var1) {
      int var2 = 0;
      int var3 = 0;

      for(int var4 = 0; var4 < 3; ++var4) {
         var2 += this.getPreferredWidth(var4);
         var3 += this.getPreferredHeight(var4);
      }

      return new Dimension(var2, var3);
   }

   public void removeLayoutComponent(Component var1) {
      for(int var2 = 0; var2 < 3; ++var2) {
         for(int var3 = 0; var3 < 3; ++var3) {
            if (this.components[var2][var3] == var1) {
               this.components[var2][var3] = null;
               return;
            }
         }
      }

   }
}
