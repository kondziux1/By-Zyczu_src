package net.infonode.gui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.ArrayList;
import java.util.HashMap;
import net.infonode.util.Direction;

public class DirectionLayout implements LayoutManager2 {
   private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);
   private Direction direction;
   private HashMap componentInsets;
   private int componentSpacing;
   private boolean compressing;
   private ArrayList layoutOrderList;

   public DirectionLayout() {
      this(Direction.RIGHT);
   }

   public DirectionLayout(int var1) {
      this(Direction.RIGHT, var1);
   }

   public DirectionLayout(Direction var1) {
      this(var1, 0);
   }

   public DirectionLayout(Direction var1, int var2) {
      super();
      this.direction = var1;
      this.componentSpacing = var2;
   }

   public int getComponentSpacing() {
      return this.componentSpacing;
   }

   public void setComponentSpacing(int var1) {
      this.componentSpacing = var1;
   }

   public void addLayoutComponent(String var1, Component var2) {
   }

   public Direction getDirection() {
      return this.direction;
   }

   public void setDirection(Direction var1) {
      this.direction = var1;
   }

   public boolean isVertical() {
      return !this.direction.isHorizontal();
   }

   public boolean isCompressing() {
      return this.compressing;
   }

   public void setCompressing(boolean var1) {
      this.compressing = var1;
   }

   public void setLayoutOrderList(ArrayList var1) {
      this.layoutOrderList = var1;
   }

   private int getSize(Dimension var1) {
      return (int)(this.isVertical() ? var1.getHeight() : var1.getWidth());
   }

   private static int getBeforeSpacing(Insets var0) {
      return var0.left;
   }

   private int getAfterSpacing(Component var1, boolean var2) {
      return this.getInsets(var1).right + (var2 ? 0 : this.componentSpacing);
   }

   private Dimension createSize(int var1, int var2) {
      return this.isVertical() ? new Dimension(var2, var1) : new Dimension(var1, var2);
   }

   private static Dimension getSize(Dimension var0, Container var1) {
      Insets var2 = var1.getInsets();
      return new Dimension(var0.width + var2.left + var2.right, var0.height + var2.top + var2.bottom);
   }

   private int getOtherSize(Dimension var1) {
      return (int)(this.isVertical() ? var1.getWidth() : var1.getHeight());
   }

   private void setSize(Component var1, int var2, int var3) {
      int var4 = this.getOtherSize(var1.getMaximumSize());
      var1.setSize(this.createSize(var2, Math.min(var4, var3)));
   }

   public void layoutContainer(Container var1) {
      Component[] var2 = this.getVisibleChildren(var1);
      int var3 = var2.length;
      if (var2.length != 0) {
         int var4 = 0;

         for(int var5 = 0; var5 < var2.length; ++var5) {
            var4 += this.getSpacing(var2[var5], var5 == var2.length - 1);
         }

         boolean[] var16 = new boolean[var2.length];
         Dimension var6 = LayoutUtil.getInteriorSize(var1);
         int var7 = this.getSize(var6) - var4;
         int var8 = var7 / var2.length;
         int var10 = this.getOtherSize(var6);

         int var9;
         do {
            var9 = var3;

            for(int var11 = 0; var11 < var2.length; ++var11) {
               if (!var16[var11]) {
                  int var12 = this.getSize(var2[var11].getPreferredSize());
                  if (var12 <= var8) {
                     this.setSize(var2[var11], var12, var10);
                     var7 -= var12;
                     var16[var11] = true;
                     if (--var3 == 0) {
                        break;
                     }

                     var8 = var7 / var3;
                  }
               }
            }
         } while(var9 > var3);

         if (var3 > 0) {
            do {
               var9 = var3;

               for(int var18 = 0; var18 < var2.length; ++var18) {
                  if (!var16[var18]) {
                     int var20 = this.getSize(var2[var18].getMinimumSize());
                     if (var20 >= var8) {
                        this.setSize(var2[var18], var20, var10);
                        var7 -= var20;
                        var16[var18] = true;
                        if (--var3 == 0) {
                           break;
                        }

                        var8 = var7 / var3;
                     }
                  }
               }
            } while(var9 > var3);
         }

         Insets var19 = var1.getInsets();
         int var21 = this.direction == Direction.RIGHT
            ? var19.left
            : (this.direction == Direction.DOWN ? var19.top : (this.direction == Direction.LEFT ? var19.right : var19.bottom));
         int var13 = this.isVertical() ? var19.left : var19.top;

         for(int var14 = 0; var14 < var2.length; ++var14) {
            var21 += getBeforeSpacing(this.getInsets(var2[var14]));
            if (!var16[var14]) {
               int var15 = Math.max(this.getSize(var2[var14].getMinimumSize()), var7 / var3);
               this.setSize(var2[var14], var15, var10);
               --var3;
               var7 -= var15;
            }

            int var23 = var13
               + (int)(
                  (float)(var10 - this.getOtherSize(var2[var14].getSize()))
                     * (
                        this.direction != Direction.DOWN && this.direction != Direction.LEFT
                           ? var2[var14].getAlignmentY()
                           : 1.0F - var2[var14].getAlignmentY()
                     )
               );
            if (this.isVertical()) {
               var2[var14].setLocation(var23, this.direction == Direction.DOWN ? var21 : var1.getHeight() - var21 - var2[var14].getHeight());
            } else {
               var2[var14].setLocation(this.direction == Direction.RIGHT ? var21 : var1.getWidth() - var21 - var2[var14].getWidth(), var23);
            }

            var21 += this.getSize(var2[var14].getSize()) + this.getAfterSpacing(var2[var14], var14 == var2.length - 1);
         }

      }
   }

   public void setComponentInsets(Component var1, Insets var2) {
      if (var2 == null) {
         this.removeLayoutComponent(var1);
      } else {
         if (this.componentInsets == null) {
            this.componentInsets = new HashMap(4);
         }

         this.componentInsets.put(var1, var2);
      }

   }

   private Component[] getVisibleChildren(Container var1) {
      if (this.layoutOrderList == null) {
         return LayoutUtil.getVisibleChildren(var1);
      } else {
         Component[] var2 = new Component[this.layoutOrderList.size()];

         for(int var3 = 0; var3 < this.layoutOrderList.size(); ++var3) {
            var2[var3] = (Component)this.layoutOrderList.get(var3);
         }

         return LayoutUtil.getVisibleChildren(var2);
      }
   }

   private int getSpacing(Component var1, boolean var2) {
      Insets var3 = this.getInsets(var1);
      return var3.left + var3.right + (var2 ? 0 : this.componentSpacing);
   }

   private Insets getInsets(Component var1) {
      Object var2 = this.componentInsets == null ? null : this.componentInsets.get(var1);
      return var2 == null ? EMPTY_INSETS : (Insets)var2;
   }

   public Dimension minimumLayoutSize(Container var1) {
      Component[] var2 = this.getVisibleChildren(var1);
      int var3 = 0;
      int var4 = 0;

      for(int var5 = 0; var5 < var2.length; ++var5) {
         var3 += this.getSize(var2[var5].getMinimumSize()) + this.getSpacing(var2[var5], var5 == var2.length - 1);
         var4 = Math.max(this.getOtherSize(var2[var5].getMinimumSize()), var4);
      }

      return getSize(this.isVertical() ? new Dimension(var4, var3) : new Dimension(var3, var4), var1);
   }

   public Dimension preferredLayoutSize(Container var1) {
      Component[] var2 = this.getVisibleChildren(var1);
      int var3 = 0;
      int var4 = 0;

      for(int var5 = 0; var5 < var2.length; ++var5) {
         if (!this.compressing) {
            var3 += this.getSize(var2[var5].getPreferredSize()) + this.getSpacing(var2[var5], var5 == var2.length - 1);
         }

         var4 = Math.max(this.getOtherSize(var2[var5].getPreferredSize()), var4);
      }

      return getSize(this.isVertical() ? new Dimension(var4, var3) : new Dimension(var3, var4), var1);
   }

   public void removeLayoutComponent(Component var1) {
      if (this.componentInsets != null) {
         this.componentInsets.remove(var1);
         if (this.componentInsets.size() == 0) {
            this.componentInsets = null;
         }
      }

   }

   public void addLayoutComponent(Component var1, Object var2) {
      this.setComponentInsets(var1, (Insets)var2);
   }

   public float getLayoutAlignmentX(Container var1) {
      return 0.0F;
   }

   public float getLayoutAlignmentY(Container var1) {
      return 0.0F;
   }

   public void invalidateLayout(Container var1) {
   }

   public Dimension maximumLayoutSize(Container var1) {
      Component[] var2 = this.getVisibleChildren(var1);
      int var3 = 0;
      int var4 = Integer.MAX_VALUE;

      for(int var5 = 0; var5 < var2.length; ++var5) {
         var3 += this.getSize(var2[var5].getMaximumSize()) + this.getSpacing(var2[var5], var5 == var2.length - 1);
      }

      return getSize(this.isVertical() ? new Dimension(var4, var3) : new Dimension(var3, var4), var1);
   }
}
