package net.infonode.tabbedpanel.internal;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import net.infonode.gui.ComponentUtil;
import net.infonode.gui.GraphicsUtil;
import net.infonode.gui.UIManagerUtil;
import net.infonode.util.ColorUtil;
import net.infonode.util.Direction;

public class ShadowPainter {
   private Color panelBackgroundColor;
   private Color tabBackgroundColor;
   private Component component;
   private JComponent componentsPanel;
   private JComponent highlightedTab;
   private JComponent contentPanel;
   private JComponent tabAreaComponentsPanel;
   private JComponent tabAreaContainer;
   private JComponent tabBox;
   private Direction tabOrientation;
   private boolean paintTabAreaShadow;
   private int shadowSize;
   private int shadowBlendSize;
   private Color shadowColor;
   private float shadowStrength;
   private boolean highlightedTabIsLast;

   public ShadowPainter(
      Component var1,
      JComponent var2,
      JComponent var3,
      JComponent var4,
      JComponent var5,
      JComponent var6,
      JComponent var7,
      Direction var8,
      boolean var9,
      int var10,
      int var11,
      Color var12,
      float var13,
      boolean var14
   ) {
      super();
      this.component = var1;
      this.componentsPanel = var2;
      this.highlightedTab = var3 == null ? null : (var3.isVisible() && var6.isVisible() ? var3 : null);
      this.contentPanel = var4;
      this.tabAreaComponentsPanel = var5;
      this.tabAreaContainer = var6;
      this.tabBox = var7;
      this.tabOrientation = var8;
      this.paintTabAreaShadow = var9 && var6.isVisible();
      this.shadowSize = var10;
      this.shadowBlendSize = var11;
      this.shadowColor = var12;
      this.shadowStrength = var13;
      this.highlightedTabIsLast = var14;
   }

   public void paint(Graphics var1) {
      this.panelBackgroundColor = ComponentUtil.getBackgroundColor(this.component);
      this.panelBackgroundColor = this.panelBackgroundColor == null ? UIManagerUtil.getColor("Panel.background", "control") : this.panelBackgroundColor;
      if (this.paintTabAreaShadow) {
         Rectangle var2 = SwingUtilities.calculateInnerArea(this.componentsPanel, new Rectangle());
         this.drawBottomRightEdgeShadow(var1, var2.y, var2.x + var2.width, var2.height, true, this.panelBackgroundColor);
         this.drawBottomRightEdgeShadow(var1, var2.x, var2.y + var2.height, var2.width, false, this.panelBackgroundColor);
         this.drawRightCornerShadow(var1, var2.x + var2.width, var2.y + var2.height, false, this.panelBackgroundColor);
      } else {
         this.tabBackgroundColor = this.highlightedTab == null ? this.panelBackgroundColor : ComponentUtil.getBackgroundColor(this.highlightedTab.getParent());
         this.tabBackgroundColor = this.tabBackgroundColor == null ? this.panelBackgroundColor : this.tabBackgroundColor;
         Rectangle var4 = this.contentPanel.getBounds();
         int var3 = 0;
         if (this.highlightedTab != null) {
            var3 = this.paintHighlightedTabShadow(var1, this.tabOrientation, var4);
         }

         if (this.tabAreaComponentsPanel.isVisible()) {
            var3 = this.tabOrientation.isHorizontal()
               ? (this.tabAreaContainer.getInsets().bottom == 0 ? this.tabAreaComponentsPanel.getWidth() : 0)
               : (this.tabAreaContainer.getInsets().right == 0 ? this.tabAreaComponentsPanel.getHeight() : 0);
         }

         if (!this.tabAreaContainer.isVisible()) {
            var3 = 0;
         }

         if (this.tabOrientation != Direction.RIGHT || this.highlightedTab == null) {
            this.drawBottomRightEdgeShadow(
               var1,
               var4.y - (this.tabOrientation == Direction.UP ? var3 : 0),
               var4.x + var4.width,
               var4.height + (!this.tabOrientation.isHorizontal() ? var3 : 0),
               true,
               this.highlightedTab == null ? null : this.panelBackgroundColor
            );
         }

         if (this.tabOrientation != Direction.DOWN || this.highlightedTab == null) {
            this.drawBottomRightEdgeShadow(
               var1,
               var4.x - (this.tabOrientation == Direction.LEFT ? var3 : 0),
               var4.y + var4.height,
               var4.width + (this.tabOrientation.isHorizontal() ? var3 : 0),
               false,
               this.highlightedTab == null ? null : this.panelBackgroundColor
            );
         }

         this.drawRightCornerShadow(
            var1,
            var4.x + var4.width + (this.tabOrientation == Direction.RIGHT ? var3 : 0),
            var4.y + var4.height + (this.tabOrientation == Direction.DOWN ? var3 : 0),
            false,
            this.panelBackgroundColor
         );
      }

   }

   private int paintHighlightedTabShadow(Graphics var1, Direction var2, Rectangle var3) {
      Point var4 = SwingUtilities.convertPoint(this.highlightedTab.getParent(), this.highlightedTab.getLocation(), this.component);
      Dimension var5 = this.tabBox.getSize();
      Rectangle var6 = this.tabAreaComponentsPanel.isVisible()
         ? SwingUtilities.convertRectangle(this.tabAreaComponentsPanel.getParent(), this.tabAreaComponentsPanel.getBounds(), this.component)
         : new Rectangle(var3.x + var3.width, var3.y + var3.height, 0, 0);
      Point var7 = SwingUtilities.convertPoint(this.tabBox, 0, 0, this.component);
      int var8 = (var2.isHorizontal() ? 0 : var7.x) + var5.width;
      int var9 = (var2.isHorizontal() ? var7.y : 0) + var5.height;
      if (var2 == Direction.DOWN) {
         this.drawBottomRightTabShadow(
            var1,
            var3.x,
            var3.y + var3.height,
            var3.width,
            var4.x,
            this.highlightedTab.getWidth(),
            this.highlightedTab.getHeight(),
            var6.x,
            var6.width,
            var6.height,
            false,
            this.highlightedTabIsLast
         );
      } else if (var2 == Direction.RIGHT) {
         this.drawBottomRightTabShadow(
            var1,
            var3.y,
            var3.x + var3.width,
            var3.height,
            var4.y,
            this.highlightedTab.getHeight(),
            this.highlightedTab.getWidth(),
            var6.y,
            var6.height,
            var6.width,
            true,
            this.highlightedTabIsLast
         );
      } else if (var2 == Direction.UP) {
         this.drawTopLeftTabShadow(
            var1, var4.x + this.highlightedTab.getWidth(), var4.y, this.highlightedTab.getHeight(), var6, var3.width, false, this.highlightedTabIsLast
         );
      } else {
         this.drawTopLeftTabShadow(
            var1,
            var4.y + this.highlightedTab.getHeight(),
            var4.x,
            this.highlightedTab.getWidth(),
            flipRectangle(var6),
            var3.height,
            true,
            this.highlightedTabIsLast
         );
      }

      if (!this.highlightedTabIsLast) {
         return 0;
      } else {
         return var2.isHorizontal()
            ? (var4.y + this.highlightedTab.getHeight() >= var3.height && var3.height == var9 ? this.highlightedTab.getWidth() : 0)
            : (var4.x + this.highlightedTab.getWidth() >= var3.width && var3.width == var8 ? this.highlightedTab.getHeight() : 0);
      }
   }

   private static Rectangle flipRectangle(Rectangle var0) {
      return new Rectangle(var0.y, var0.x, var0.height, var0.width);
   }

   private static Rectangle createRectangle(int var0, int var1, int var2, int var3, boolean var4) {
      return var4 ? new Rectangle(var1, var0, var3, var2) : new Rectangle(var0, var1, var2, var3);
   }

   private void drawTopLeftTabShadow(Graphics var1, int var2, int var3, int var4, Rectangle var5, int var6, boolean var7, boolean var8) {
      boolean var9 = var2 + this.shadowSize > var5.x;
      if (!var9 || var3 + this.shadowSize + this.shadowBlendSize <= var5.y) {
         this.drawLeftCornerShadow(var1, var3, Math.min(var2, var5.x), !var7, var8 ? this.tabBackgroundColor : null);
         this.drawEdgeShadow(var1, var3, var9 ? var5.y : var3 + var4, Math.min(var2, var5.x), false, !var7, var8 ? this.tabBackgroundColor : null);
      }

      int var10 = var5.x + var5.width;
      if (var10 < var6) {
         this.drawLeftCornerShadow(var1, var5.y, var10, !var7, this.tabBackgroundColor);
         this.drawEdgeShadow(var1, var5.y, var5.y + var5.height, var10, false, !var7, this.tabBackgroundColor);
      }

   }

   private void drawBottomRightTabShadow(
      Graphics var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, boolean var11, boolean var12
   ) {
      Shape var13 = var1.getClip();
      Rectangle var14 = createRectangle(var2, var3, Math.min(var5, var8) - var2, 1000000, var11);
      var1.clipRect(var14.x, var14.y, var14.width, var14.height);
      this.drawLeftCornerShadow(var1, var2, var3, var11, null);
      this.drawEdgeShadow(var1, var2, var5, var3, false, var11, null);
      boolean var16 = var5 < var8 && var5 + var6 >= var8;
      var1.setClip(var13);
      if (!var16) {
         Rectangle var15 = createRectangle(var2, var3, var8 - var2, 1000000, var11);
         var1.clipRect(var15.x, var15.y, var15.width, var15.height);
      }

      if (var5 < var8) {
         int var17 = var16 && var7 == var10 ? var8 + var9 : Math.min(var8, var5 + var6);
         if (var5 + this.shadowSize < var17) {
            this.drawLeftCornerShadow(var1, var5, var3 + var7, var11, this.panelBackgroundColor);
         }

         this.drawEdgeShadow(var1, var5, var17, var3 + var7, false, var11, this.panelBackgroundColor);
         if (var17 < var2 + var4 && (!var16 || var10 < var7)) {
            this.drawRightCornerShadow(var1, var17, var3 + var7, var11, this.panelBackgroundColor);
            this.drawEdgeShadow(var1, var3 + (var16 ? var10 : 0), var3 + var7, var17, true, !var11, var12 ? this.tabBackgroundColor : null);
         }
      }

      if (!var16) {
         this.drawEdgeShadow(var1, var5 + var6, var8, var3, true, var11, var12 ? this.tabBackgroundColor : null);
         var1.setClip(var13);
      }

      if (var10 > 0 && (!var16 || var7 != var10)) {
         if (var16 && var10 <= var7) {
            this.drawEdgeShadow(var1, var8, var8 + var9, var3 + var10, true, var11, this.panelBackgroundColor);
         } else {
            this.drawLeftCornerShadow(var1, var8, var3 + var10, var11, this.panelBackgroundColor);
            this.drawEdgeShadow(var1, var8, var8 + var9, var3 + var10, false, var11, this.panelBackgroundColor);
         }
      }

      if (var8 + var9 < var2 + var4) {
         this.drawRightCornerShadow(var1, var8 + var9, var3 + var10, var11, this.panelBackgroundColor);
         this.drawEdgeShadow(var1, var3, var3 + var10, var8 + var9, true, !var11, this.panelBackgroundColor);
         this.drawEdgeShadow(var1, var8 + var9, var2 + var4, var3, true, var11, this.panelBackgroundColor);
      }

   }

   private void drawBottomRightEdgeShadow(Graphics var1, int var2, int var3, int var4, boolean var5, Color var6) {
      this.drawLeftCornerShadow(var1, var2, var3, var5, var6);
      this.drawEdgeShadow(var1, var2, var2 + var4, var3, false, var5, var6);
   }

   private void drawLeftCornerShadow(Graphics var1, int var2, int var3, boolean var4, Color var5) {
      for(int var6 = 0; var6 < this.shadowBlendSize; ++var6) {
         var1.setColor(this.getShadowBlendColor(var6, var5));
         int var7 = var2 + this.shadowSize + this.shadowBlendSize - 1 - var6;
         int var8 = var3 + this.shadowSize - this.shadowBlendSize;
         if (var8 > var3) {
            drawLine(var1, var7, var3, var7, var8 - 1, var4);
         }

         drawLine(var1, var7, var8, var2 + this.shadowSize + this.shadowBlendSize - 1, var3 + this.shadowSize - this.shadowBlendSize + var6, var4);
      }

   }

   private void drawRightCornerShadow(Graphics var1, int var2, int var3, boolean var4, Color var5) {
      var1.setColor(this.getShadowColor(var5));

      for(int var6 = 0; var6 < this.shadowSize - this.shadowBlendSize; ++var6) {
         drawLine(var1, var2 + var6, var3, var2 + var6, var3 + this.shadowSize - this.shadowBlendSize, var4);
      }

      for(int var8 = 0; var8 < this.shadowBlendSize; ++var8) {
         var1.setColor(this.getShadowBlendColor(var8, var5));
         int var7 = this.shadowSize - this.shadowBlendSize + var8;
         drawLine(var1, var2 + var7, var3, var2 + var7, var3 + this.shadowSize - this.shadowBlendSize, var4);
         drawLine(var1, var2, var3 + var7, var2 + this.shadowSize - this.shadowBlendSize, var3 + var7, var4);
         drawLine(var1, var2 + var7, var3 + this.shadowSize - this.shadowBlendSize, var2 + this.shadowSize - this.shadowBlendSize, var3 + var7, var4);
      }

   }

   private void drawEdgeShadow(Graphics var1, int var2, int var3, int var4, boolean var5, boolean var6, Color var7) {
      if (var2 + (var5 ? 0 : this.shadowSize + this.shadowBlendSize) < var3) {
         var1.setColor(this.getShadowColor(var7));

         for(int var8 = 0; var8 < this.shadowSize - this.shadowBlendSize; ++var8) {
            drawLine(var1, var2 + (var5 ? var8 + (var6 ? 1 : 0) : this.shadowSize + this.shadowBlendSize), var4 + var8, var3 - 1, var4 + var8, var6);
         }

         for(int var10 = 0; var10 < this.shadowBlendSize; ++var10) {
            var1.setColor(this.getShadowBlendColor(var10, var7));
            int var9 = this.shadowSize - this.shadowBlendSize + var10;
            drawLine(var1, var2 + (var5 ? var9 + (var6 ? 1 : 0) : this.shadowSize + this.shadowBlendSize), var4 + var9, var3 - 1, var4 + var9, var6);
         }

      }
   }

   private static void drawLine(Graphics var0, int var1, int var2, int var3, int var4, boolean var5) {
      if (var5) {
         GraphicsUtil.drawOptimizedLine(var0, var2, var1, var4, var3);
      } else {
         GraphicsUtil.drawOptimizedLine(var0, var1, var2, var3, var4);
      }

   }

   private Color getShadowBlendColor(int var1, Color var2) {
      return var2 == null
         ? new Color(
            this.shadowColor.getRed(),
            this.shadowColor.getGreen(),
            this.shadowColor.getBlue(),
            (int)(255.0F * this.shadowStrength * (float)(this.shadowBlendSize - var1) / (float)(this.shadowBlendSize + 1))
         )
         : ColorUtil.blend(var2, this.shadowColor, (double)(this.shadowStrength * (float)(this.shadowBlendSize - var1) / (float)(this.shadowBlendSize + 1)));
   }

   private Color getShadowColor(Color var1) {
      return var1 == null
         ? new Color(this.shadowColor.getRed(), this.shadowColor.getGreen(), this.shadowColor.getBlue(), (int)(255.0F * this.shadowStrength))
         : ColorUtil.blend(var1, this.shadowColor, (double)this.shadowStrength);
   }
}
