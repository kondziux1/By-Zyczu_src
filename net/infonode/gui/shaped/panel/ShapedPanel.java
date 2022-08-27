package net.infonode.gui.shaped.panel;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Shape;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import net.infonode.gui.BackgroundPainter;
import net.infonode.gui.InsetsUtil;
import net.infonode.gui.componentpainter.ComponentPainter;
import net.infonode.gui.panel.BaseContainer;
import net.infonode.gui.shaped.border.ShapedBorder;
import net.infonode.util.Direction;

public class ShapedPanel extends BaseContainer implements BackgroundPainter {
   private Direction direction = Direction.RIGHT;
   private boolean horizontalFlip;
   private boolean verticalFlip;
   private boolean clipChildren;
   private ComponentPainter painter;
   private ShapedBorder shapedBorder;
   private Insets shapedInsets;

   public ShapedPanel() {
      super();
   }

   public ShapedPanel(LayoutManager var1) {
      super(var1);
   }

   public ShapedPanel(ComponentPainter var1) {
      this();
      this.painter = var1;
   }

   public ShapedPanel(ComponentPainter var1, Border var2) {
      this(var1);
      this.setBorder(var2);
   }

   public ShapedPanel(Component var1) {
      this();
      this.add(var1, "Center");
   }

   public Shape getShape() {
      ShapedBorder var1 = this.getShapedBorder();
      return var1 == null
         ? null
         : var1.getShape(
            this,
            this.shapedInsets.left,
            this.shapedInsets.top,
            this.getWidth() - this.shapedInsets.left - this.shapedInsets.right,
            this.getHeight() - this.shapedInsets.top - this.shapedInsets.bottom
         );
   }

   public ComponentPainter getComponentPainter() {
      return this.painter;
   }

   public void setComponentPainter(ComponentPainter var1) {
      if (this.painter != var1) {
         this.painter = var1;
         this.repaint();
      }

   }

   public Direction getDirection() {
      return this.direction;
   }

   public boolean isHorizontalFlip() {
      return this.horizontalFlip;
   }

   public void setHorizontalFlip(boolean var1) {
      if (this.horizontalFlip != var1) {
         this.horizontalFlip = var1;
         this.revalidate();
      }

   }

   public boolean isVerticalFlip() {
      return this.verticalFlip;
   }

   public void setVerticalFlip(boolean var1) {
      if (this.verticalFlip != var1) {
         this.verticalFlip = var1;
         this.revalidate();
      }

   }

   public void setDirection(Direction var1) {
      if (this.direction != var1) {
         this.direction = var1;
         this.revalidate();
         this.repaint();
      }

   }

   public boolean isClipChildren() {
      return this.clipChildren;
   }

   public void setClipChildren(boolean var1) {
      this.clipChildren = var1;
   }

   public ShapedBorder getShapedBorder() {
      return this.shapedBorder;
   }

   public void setBorder(Border var1) {
      super.setBorder(var1);
      this.shapedBorder = null;
      this.findShapedBorder(this.getBorder(), new Insets(0, 0, 0, 0));
   }

   protected void paintChildren(Graphics var1) {
      if (this.clipChildren) {
         Shape var2 = this.getShape();
         if (var2 != null) {
            Graphics2D var3 = (Graphics2D)var1;
            Shape var4 = var3.getClip();
            var3.clip(var2);
            super.paintChildren(var1);
            var3.setClip(var4);
            return;
         }
      }

      super.paintChildren(var1);
   }

   protected void paintComponent(Graphics var1) {
      super.paintComponent(var1);
      if (this.painter != null) {
         Shape var2 = this.getShape();
         if (var2 != null) {
            Shape var3 = var1.getClip();
            var1.clipRect(
               this.shapedInsets.left,
               this.shapedInsets.top,
               this.getWidth() - this.shapedInsets.left - this.shapedInsets.right,
               this.getHeight() - this.shapedInsets.top - this.shapedInsets.bottom
            );
            ((Graphics2D)var1).clip(var2);
            this.painter.paint(this, var1, 0, 0, this.getWidth(), this.getHeight(), this.direction, this.horizontalFlip, this.verticalFlip);
            var1.setClip(var3);
         } else {
            this.painter.paint(this, var1, 0, 0, this.getWidth(), this.getHeight(), this.direction, this.horizontalFlip, this.verticalFlip);
         }
      }

   }

   public boolean contains(int var1, int var2) {
      if (var1 >= 0 && var2 >= 0 && var1 < this.getWidth() && var2 < this.getHeight()) {
         Shape var3 = this.getShape();
         return var3 == null ? super.contains(var1, var2) : var3.contains((double)var1, (double)var2);
      } else {
         return false;
      }
   }

   public boolean inside(int var1, int var2) {
      if (var1 >= 0 && var2 >= 0 && var1 < this.getWidth() && var2 < this.getHeight()) {
         Shape var3 = this.getShape();
         return var3 == null ? super.inside(var1, var2) : var3.contains((double)var1, (double)var2);
      } else {
         return false;
      }
   }

   private boolean findShapedBorder(Border var1, Insets var2) {
      if (var1 == null) {
         return false;
      } else if (var1 instanceof ShapedBorder) {
         this.shapedBorder = (ShapedBorder)var1;
         this.shapedInsets = var2;
         return true;
      } else if (var1 instanceof CompoundBorder) {
         CompoundBorder var3 = (CompoundBorder)var1;
         return this.findShapedBorder(var3.getOutsideBorder(), var2)
            ? true
            : this.findShapedBorder(var3.getInsideBorder(), InsetsUtil.add(var3.getOutsideBorder().getBorderInsets(this), var2));
      } else {
         return false;
      }
   }
}
