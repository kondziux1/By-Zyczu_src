package net.infonode.gui.panel;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import net.infonode.gui.CursorManager;
import net.infonode.util.Direction;

public class ResizablePanel extends BaseContainer {
   private Direction direction;
   private int resizeWidth = 4;
   private boolean cursorChanged;
   private int offset = -1;
   private boolean mouseInside;
   private boolean heavyWeight = true;
   private boolean continuousLayout = false;
   private Component dragIndicator;
   private JComponent layeredPane;
   private JComponent innerArea;
   private Dimension lastSize;
   private int dragIndicatorThickness = 4;
   private Component comp;

   public ResizablePanel(Direction var1) {
      this(false, var1, null);
   }

   public ResizablePanel(boolean var1, Direction var2, Component var3) {
      super(new BorderLayout());
      this.heavyWeight = var1;
      this.direction = var2;
      if (this.heavyWeight) {
         this.dragIndicator = new Canvas();
      } else {
         this.dragIndicator = new BaseContainer();
      }

      this.setDragIndicatorColor(null);
      if (var3 == null) {
         var3 = this;
      }

      ((Component)var3).addMouseListener(new ResizablePanel$1(this));
      ((Component)var3).addMouseMotionListener(new ResizablePanel$2(this));
   }

   public void setComponent(Component var1) {
      if (this.comp != null) {
         this.remove(this.comp);
      }

      if (var1 != null) {
         this.add(var1, "Center");
         this.revalidate();
      }

      this.comp = var1;
   }

   public void setDragIndicatorColor(Color var1) {
      this.dragIndicator.setBackground(var1 == null ? Color.DARK_GRAY : var1);
   }

   public void setLayeredPane(JComponent var1) {
      this.layeredPane = var1;
      if (this.innerArea == null) {
         this.innerArea = var1;
      }

   }

   public void setInnerArea(JComponent var1) {
      if (var1 == null) {
         var1 = this.layeredPane;
      } else {
         this.innerArea = var1;
      }

   }

   public boolean isContinuousLayout() {
      return this.continuousLayout;
   }

   public void setContinuousLayout(boolean var1) {
      this.continuousLayout = var1;
   }

   public Dimension getPreferredSize() {
      Dimension var1 = super.getPreferredSize();
      return this.getBoundedSize(this.direction.isHorizontal() ? var1.width : var1.height);
   }

   private void updateDragIndicator(MouseEvent var1) {
      if (this.layeredPane != null) {
         Point var2 = SwingUtilities.convertPoint((Component)var1.getSource(), var1.getPoint(), this.layeredPane);
         Point var3 = SwingUtilities.convertPoint(this.getParent(), this.getLocation(), this.layeredPane);
         Dimension var4 = this.innerArea.getSize();
         Dimension var5 = this.getMinimumSize();
         Point var6 = SwingUtilities.convertPoint(this.innerArea, 0, 0, this.layeredPane);
         if (this.direction.isHorizontal()) {
            int var7 = 0;
            if (this.direction == Direction.LEFT) {
               var7 = Math.min(Math.max(var6.x, var2.x), var6.x + var4.width - var5.width);
            } else {
               var7 = Math.min(Math.max(var6.x + var5.width, var2.x), var6.x + var4.width) - this.dragIndicatorThickness;
            }

            this.dragIndicator.setBounds(var7, var3.y, this.dragIndicatorThickness, this.getHeight());
         } else {
            int var9 = 0;
            if (this.direction == Direction.UP) {
               var9 = Math.min(Math.max(var6.y, var2.y), var6.y + var4.height - var5.height);
            } else {
               var9 = Math.min(Math.max(var6.y + var5.height, var2.y), var6.y + var4.height) - this.dragIndicatorThickness;
            }

            this.dragIndicator.setBounds(var3.x, var9, this.getWidth(), this.dragIndicatorThickness);
         }
      }

   }

   private Dimension getBoundedSize(int var1) {
      return this.direction.isHorizontal()
         ? new Dimension(Math.max(this.getMinimumSize().width, Math.min(var1, this.getMaximumSize().width)), 0)
         : new Dimension(0, Math.max(this.getMinimumSize().height, Math.min(var1, this.getMaximumSize().height)));
   }

   public void setResizeWidth(int var1) {
      this.resizeWidth = var1;
   }

   public int getResizeWidth() {
      return this.resizeWidth;
   }

   private void checkCursor(Point var1) {
      if (this.offset == -1) {
         int var2 = this.direction == Direction.UP
            ? var1.y
            : (this.direction == Direction.DOWN ? this.getHeight() - var1.y : (this.direction == Direction.LEFT ? var1.x : this.getWidth() - var1.x));
         if (var2 < 0 || var2 >= this.resizeWidth || !this.mouseInside) {
            this.resetCursor();
         } else if (!this.cursorChanged) {
            this.cursorChanged = true;
            CursorManager.setGlobalCursor(
               this.getRootPane(),
               new Cursor(this.direction == Direction.LEFT ? 10 : (this.direction == Direction.RIGHT ? 11 : (this.direction == Direction.UP ? 8 : 9)))
            );
         }

      }
   }

   private void resetCursor() {
      CursorManager.resetGlobalCursor(this.getRootPane());
      this.cursorChanged = false;
   }

   public Direction getDirection() {
      return this.direction;
   }

   public void setVisible(boolean var1) {
      super.setVisible(var1);
   }
}
