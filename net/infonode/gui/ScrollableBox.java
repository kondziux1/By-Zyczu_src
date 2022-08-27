package net.infonode.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import javax.swing.JComponent;
import net.infonode.gui.panel.SimplePanel;

public class ScrollableBox extends SimplePanel {
   private LayoutManager l = new ScrollableBox$1(this);
   private int leftIndex;
   private boolean vertical;
   private int scrollOffset;
   private boolean leftEnd = true;
   private boolean rightEnd = false;
   private ArrayList layoutOrderList;
   private MouseWheelListener mouseWheelListener = new ScrollableBox$2(this);
   private ArrayList listeners = new ArrayList(1);

   public ScrollableBox(JComponent var1, boolean var2, int var3) {
      super();
      this.setLayout(this.l);
      this.vertical = var2;
      this.scrollOffset = var3;
      this.add(var1);
      var1.addMouseWheelListener(this.mouseWheelListener);
      var1.addHierarchyListener(new ScrollableBox$3(this, var1));
   }

   public void addScrollableBoxListener(ScrollableBoxListener var1) {
      this.listeners.add(var1);
   }

   public void removeScrollableBoxListener(ScrollableBoxListener var1) {
      this.listeners.remove(var1);
   }

   public void setScrollingContainer(JComponent var1) {
   }

   public JComponent getScrollingComponent() {
      return this.getComponentCount() == 0 ? null : (JComponent)this.getComponent(0);
   }

   public void scrollLeft(int var1) {
      this.setLeftIndex(this.leftIndex - var1);
   }

   public void scrollRight(int var1) {
      this.setLeftIndex(this.leftIndex + var1);
   }

   public void ensureVisible(int var1) {
      if (this.leftIndex > var1) {
         this.setLeftIndex(var1);
      } else if (this.leftIndex < var1) {
         int var2 = this.findFitIndex(var1);
         if (var2 > this.leftIndex) {
            this.setLeftIndex(var2);
         }
      }

   }

   public boolean isLeftEnd() {
      return this.leftEnd;
   }

   public boolean isRightEnd() {
      return this.rightEnd;
   }

   public void setScrollOffset(int var1) {
      if (var1 != this.scrollOffset) {
         this.scrollOffset = var1;
         this.update();
      }

   }

   public int getScrollOffset() {
      return this.scrollOffset;
   }

   public boolean isVertical() {
      return this.vertical;
   }

   public void setVertical(boolean var1) {
      this.vertical = var1;
      this.update();
      this.fireChanged();
   }

   public void setLayoutOrderList(ArrayList var1) {
      this.layoutOrderList = var1;
   }

   private int getDimensionSize(Dimension var1) {
      return (int)(this.vertical ? var1.getHeight() : var1.getWidth());
   }

   private Point createPos(int var1) {
      return this.vertical ? new Point(0, var1) : new Point(var1, 0);
   }

   private int getPos(Point var1) {
      return this.vertical ? var1.y : var1.x;
   }

   private int getScrollOffset(int var1) {
      if (var1 == 0) {
         return 0;
      } else {
         Component var2 = this.getScrollingComponents()[var1 - 1];
         return Math.min(this.scrollOffset, Math.max(this.getDimensionSize(var2.getMinimumSize()), this.getDimensionSize(var2.getPreferredSize()) / 2));
      }
   }

   private Component[] getScrollingComponents() {
      JComponent var1 = this.getScrollingComponent();
      if (var1 == null) {
         return new Component[0];
      } else if (this.layoutOrderList == null) {
         return var1.getComponents();
      } else {
         Component[] var2 = new Component[this.layoutOrderList.size()];

         for(int var3 = 0; var3 < this.layoutOrderList.size(); ++var3) {
            var2[var3] = (Component)this.layoutOrderList.get(var3);
         }

         return var2;
      }
   }

   private int getScrollingComponentCount() {
      JComponent var1 = this.getScrollingComponent();
      return var1 == null ? 0 : var1.getComponentCount();
   }

   private int findFitIndex(int var1) {
      int var2 = this.getDimensionSize(this.getSize());
      if (var2 != 0 && var1 >= 0) {
         Component[] var3 = this.getScrollingComponents();
         int var4 = this.getPos(var3[var1].getLocation()) + this.getDimensionSize(var3[var1].getSize());

         for(int var5 = var1; var5 >= 0; --var5) {
            if (var4 - this.getPos(var3[var5].getLocation()) + this.getScrollOffset(var5) > var2) {
               return Math.min(var3.length - 1, var5 + 1);
            }
         }

         return 0;
      } else {
         return 0;
      }
   }

   private void update() {
      this.setLeftIndex(this.leftIndex);
   }

   private void setLeftIndex(int var1) {
      JComponent var2 = this.getScrollingComponent();
      int var3 = this.leftIndex;
      if (var2 != null) {
         int var4 = this.getScrollingComponentCount();
         int var5 = this.findFitIndex(var4 - 1);
         this.leftIndex = Math.min(var5, Math.max(0, var1));
         this.leftEnd = this.leftIndex == 0;
         this.rightEnd = this.leftIndex >= var5;
         var2.setLocation(
            this.createPos((var4 == 0 ? 0 : -this.getPos(this.getScrollingComponents()[this.leftIndex].getLocation())) + this.getScrollOffset(this.leftIndex))
         );
         Object[] var6 = this.listeners.toArray();

         for(int var7 = 0; var7 < var6.length; ++var7) {
            if (var3 < var1) {
               this.fireScrolledRight();
            } else if (var3 > var1) {
               this.fireScrolledLeft();
            }
         }
      }

   }

   public void updateUI() {
      super.updateUI();
      if (this.listeners != null) {
         this.fireChanged();
      }

   }

   private void fireScrolledLeft() {
      Object[] var1 = this.listeners.toArray();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         ((ScrollableBoxListener)var1[var2]).scrolledLeft(this);
      }

   }

   private void fireScrolledRight() {
      Object[] var1 = this.listeners.toArray();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         ((ScrollableBoxListener)var1[var2]).scrolledRight(this);
      }

   }

   private void fireChanged() {
      Object[] var1 = this.listeners.toArray();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         ((ScrollableBoxListener)var1[var2]).changed(this);
      }

   }
}
