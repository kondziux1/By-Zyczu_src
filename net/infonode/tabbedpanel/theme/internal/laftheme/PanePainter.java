package net.infonode.tabbedpanel.theme.internal.laftheme;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import net.infonode.util.Direction;

class PanePainter extends JTabbedPane {
   private boolean mouseEntered = false;
   private boolean focusActive = false;
   private boolean useMouseEnterExit = false;
   private Direction direction;

   PanePainter(Direction var1) {
      super();
      this.setTabPlacement(var1);
      this.setTabLayoutPolicy(0);
      this.useMouseEnterExit = UIManager.getLookAndFeel().getClass().getName().indexOf(".LiquidLookAndFeel") > -1;
      if (!this.useMouseEnterExit) {
         super.processMouseEvent(new MouseEvent(this, 504, System.currentTimeMillis(), 0, 0, 0, 0, false));
      }

   }

   void setTabAreaEntered(boolean var1) {
      if (var1) {
         super.processMouseEvent(new MouseEvent(this, 504, System.currentTimeMillis(), 0, 0, 0, 0, false));
      } else {
         super.processMouseEvent(new MouseEvent(this, 505, System.currentTimeMillis(), 0, -1, -1, 0, false));
      }

   }

   private void setTabPlacement(Direction var1) {
      this.direction = var1;
      if (var1 == Direction.UP) {
         this.setTabPlacement(1);
      } else if (var1 == Direction.LEFT) {
         this.setTabPlacement(2);
      } else if (var1 == Direction.RIGHT) {
         this.setTabPlacement(4);
      } else {
         this.setTabPlacement(3);
      }

   }

   void setMouseEntered(boolean var1) {
      if (this.useMouseEnterExit) {
         if (var1 && !this.mouseEntered) {
            super.processMouseEvent(new MouseEvent(this, 504, System.currentTimeMillis(), 0, 0, 0, 0, false));
         } else if (!var1 && this.mouseEntered) {
            super.processMouseEvent(new MouseEvent(this, 505, System.currentTimeMillis(), 0, -1, -1, 0, false));
         }
      } else if (!var1 && this.mouseEntered) {
         super.processMouseMotionEvent(new MouseEvent(this, 503, System.currentTimeMillis(), 0, -1, -1, 0, false));
      }

      this.mouseEntered = var1;
   }

   void setHoveredTab(int var1) {
      if (var1 > -1 && var1 < this.getTabCount()) {
         Rectangle var2 = this.getBoundsAt(var1);
         int var3 = var2.x + var2.width / 2;
         int var4 = var2.y + var2.height / 2;
         super.processMouseMotionEvent(new MouseEvent(this, 503, System.currentTimeMillis(), 0, var3, var4, 0, false));
      }

   }

   void setFocusActive(boolean var1) {
      if (var1 && !this.focusActive) {
         super.processFocusEvent(new FocusEvent(this, 1004));
      } else if (!var1 && this.focusActive) {
         super.processFocusEvent(new FocusEvent(this, 1005));
      }

      this.focusActive = var1;
   }

   Direction getDirection() {
      return this.direction;
   }

   void doValidation() {
      for(Object var1 = this; var1 != null; var1 = ((Component)var1).getParent()) {
         ((Component)var1).invalidate();
      }

      this.validate();
   }

   void removeAllTabs() {
      this.removeAll();
      this.doValidation();
   }

   public Font getFont() {
      Font var1 = UIManager.getFont("TabbedPane.font");
      return var1 == null ? super.getFont() : var1;
   }

   public void updateUI() {
      this.setBorder(null);
      this.setBackground(null);
      this.setForeground(null);
      this.setOpaque(false);
      super.updateUI();
      this.setTabLayoutPolicy(0);
      this.useMouseEnterExit = UIManager.getLookAndFeel().getClass().getName().indexOf(".LiquidLookAndFeel") > -1;
      if (!this.useMouseEnterExit) {
         super.processMouseEvent(new MouseEvent(this, 504, System.currentTimeMillis(), 0, 0, 0, 0, false));
      }

   }

   public boolean hasFocus() {
      return this.focusActive;
   }

   public void repaint() {
   }

   public void repaint(long var1, int var3, int var4, int var5, int var6) {
   }

   void paint(Graphics var1, int var2, int var3) {
      Rectangle var4 = var1.getClipBounds();
      if (var4 == null || var4.x != 0 || var4.y != 0 || var4.width != 0 || var4.height != 0) {
         var1.translate(var2, var3);
         this.update(var1);
         var1.translate(-var2, -var3);
      }
   }

   protected void processMouseEvent(MouseEvent var1) {
   }

   protected void processMouseMotionEvent(MouseEvent var1) {
   }

   protected void processFocusEvent(FocusEvent var1) {
   }

   protected void processMouseWheelEvent(MouseWheelEvent var1) {
   }
}
