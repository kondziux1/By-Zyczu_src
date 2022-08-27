package net.infonode.tabbedpanel.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.Serializable;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import net.infonode.gui.colorprovider.ColorProvider;
import net.infonode.gui.colorprovider.ColorProviderUtil;
import net.infonode.gui.colorprovider.UIManagerColorProvider;
import net.infonode.tabbedpanel.Tab;
import net.infonode.tabbedpanel.TabbedUtils;
import net.infonode.util.Direction;

/** @deprecated */
public class TabLineBorder implements Border, Serializable {
   private static final long serialVersionUID = 1L;
   private ColorProvider color;
   private Border border;
   private boolean last;
   private boolean afterHighlighted;
   private boolean highlighted;
   private boolean drawTopLine;
   private boolean drawBottomLine;
   private int index;
   private boolean tabSpacing;

   public TabLineBorder() {
      this(null);
   }

   public TabLineBorder(Color var1) {
      this(var1, false);
   }

   public TabLineBorder(Color var1, boolean var2) {
      this(var1, var2, true);
   }

   public TabLineBorder(boolean var1, boolean var2) {
      this((Color)null, var1, var2);
   }

   public TabLineBorder(Color var1, boolean var2, boolean var3) {
      this(ColorProviderUtil.getColorProvider(var1, UIManagerColorProvider.TABBED_PANE_DARK_SHADOW), var2, var3);
   }

   public TabLineBorder(ColorProvider var1, boolean var2, boolean var3) {
      super();
      this.color = var1;
      this.border = new TabLineBorder.LineBorder();
      this.drawBottomLine = var2;
      this.drawTopLine = var3;
   }

   public TabLineBorder(Color var1, Border var2) {
      this(var1, var2, false);
   }

   public TabLineBorder(Color var1, Border var2, boolean var3) {
      this(var1, var3);
      if (var2 != null) {
         this.border = new CompoundBorder(this.border, var2);
      }

   }

   public TabLineBorder(ColorProvider var1, Border var2, boolean var3, boolean var4) {
      this(var1, var3, var4);
      if (var2 != null) {
         this.border = new CompoundBorder(this.border, var2);
      }

   }

   public void paintBorder(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      this.border.paintBorder(var1, var2, var3, var4, var5, var6);
   }

   public Insets getBorderInsets(Component var1) {
      return this.border.getBorderInsets(var1);
   }

   public boolean isBorderOpaque() {
      return false;
   }

   private void initialize(Tab var1) {
      this.index = var1.getTabbedPanel().getTabIndex(var1);
      this.last = this.index == var1.getTabbedPanel().getTabCount() - 1;
      this.afterHighlighted = this.index > 0 && var1.getTabbedPanel().getTabAt(this.index - 1) == var1.getTabbedPanel().getHighlightedTab();
      this.highlighted = var1 == var1.getTabbedPanel().getHighlightedTab();
   }

   private class LineBorder implements Border {
      LineBorder() {
         super();
      }

      public void paintBorder(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
         Color var7 = TabLineBorder.this.color.getColor(var1);
         Tab var8 = TabbedUtils.getParentTab(var1);
         if (var8 != null && var8.getTabbedPanel() != null) {
            Direction var9 = var8.getTabbedPanel().getProperties().getTabAreaOrientation();
            TabLineBorder.this.tabSpacing = var8.getTabbedPanel().getProperties().getTabSpacing() > 0;
            TabLineBorder.this.initialize(var8);
            if (var9 == Direction.UP) {
               this.paintUpBorder(var2, var3, var4, var5, var6, var7);
            } else if (var9 == Direction.LEFT) {
               this.paintLeftBorder(var2, var3, var4, var5, var6, var7);
            } else if (var9 == Direction.DOWN) {
               this.paintDownBorder(var2, var3, var4, var5, var6, var7);
            } else {
               this.paintRightBorder(var2, var3, var4, var5, var6, var7);
            }
         }

      }

      public Insets getBorderInsets(Component var1) {
         Tab var2 = TabbedUtils.getParentTab(var1);
         if (var2 != null && var2.getTabbedPanel() != null && var2.getParent() != null) {
            Direction var7 = var2.getTabbedPanel().getProperties().getTabAreaOrientation();
            TabLineBorder.this.initialize(var2);
            int var3;
            int var4;
            int var5;
            int var6;
            if (var7 == Direction.UP) {
               var3 = TabLineBorder.this.drawTopLine ? 1 : 0;
               var4 = 1;
               var5 = 0;
               var6 = 1;
            } else if (var7 == Direction.LEFT) {
               var3 = 1;
               var4 = TabLineBorder.this.drawTopLine ? 1 : 0;
               var5 = 1;
               var6 = 0;
            } else if (var7 == Direction.DOWN) {
               var3 = 0;
               var4 = 1;
               var5 = TabLineBorder.this.drawTopLine ? 1 : 0;
               var6 = 1;
            } else {
               var3 = 1;
               var4 = 0;
               var5 = 1;
               var6 = TabLineBorder.this.drawTopLine ? 1 : 0;
            }

            return new Insets(var3, var4, var5, var6);
         } else {
            return new Insets(0, 0, 0, 0);
         }
      }

      public boolean isBorderOpaque() {
         return true;
      }

      private void paintUpBorder(Graphics var1, int var2, int var3, int var4, int var5, Color var6) {
         var1.setColor(var6);
         if (!TabLineBorder.this.afterHighlighted || TabLineBorder.this.tabSpacing) {
            var1.drawLine(var2, var3, var2, var3 + var5 - 1);
         }

         if (TabLineBorder.this.highlighted || TabLineBorder.this.last || TabLineBorder.this.tabSpacing) {
            var1.drawLine(var2 + var4 - 1, var3, var2 + var4 - 1, var3 + var5 - 1);
         }

         if (TabLineBorder.this.drawTopLine) {
            var1.drawLine(var2, var3, var2 + var4 - 1, var3);
         }

         if (TabLineBorder.this.drawBottomLine) {
            var1.drawLine(var2, var3 + var5 - 1, var2 + var4 - 1, var3 + var5 - 1);
         }

      }

      private void paintLeftBorder(Graphics var1, int var2, int var3, int var4, int var5, Color var6) {
         var1.setColor(var6);
         if (!TabLineBorder.this.afterHighlighted || TabLineBorder.this.tabSpacing) {
            var1.drawLine(var2, var3, var2 + var4 - 1, var3);
         }

         if (TabLineBorder.this.drawTopLine) {
            var1.drawLine(var2, var3, var2, var3 + var5 - 1);
         }

         if (TabLineBorder.this.highlighted || TabLineBorder.this.last || TabLineBorder.this.tabSpacing) {
            var1.drawLine(var2, var3 + var5 - 1, var2 + var4 - 1, var3 + var5 - 1);
         }

         if (TabLineBorder.this.drawBottomLine) {
            var1.drawLine(var2 + var4 - 1, var3, var2 + var4 - 1, var3 + var5 - 1);
         }

      }

      private void paintDownBorder(Graphics var1, int var2, int var3, int var4, int var5, Color var6) {
         var1.setColor(var6);
         if (!TabLineBorder.this.afterHighlighted || TabLineBorder.this.tabSpacing) {
            var1.drawLine(var2, var3, var2, var3 + var5 - 1);
         }

         if (TabLineBorder.this.highlighted || TabLineBorder.this.last || TabLineBorder.this.tabSpacing) {
            var1.drawLine(var2 + var4 - 1, var3, var2 + var4 - 1, var3 + var5 - 1);
         }

         if (TabLineBorder.this.drawTopLine) {
            var1.drawLine(var2, var3 + var5 - 1, var2 + var4, var3 + var5 - 1);
         }

         if (TabLineBorder.this.drawBottomLine) {
            var1.drawLine(var2, var3, var2 + var4, var3);
         }

      }

      private void paintRightBorder(Graphics var1, int var2, int var3, int var4, int var5, Color var6) {
         var1.setColor(var6);
         if (!TabLineBorder.this.afterHighlighted || TabLineBorder.this.tabSpacing) {
            var1.drawLine(var2, var3, var2 + var4 - 1, var3);
         }

         if (TabLineBorder.this.drawTopLine) {
            var1.drawLine(var2 + var4 - 1, var3, var2 + var4 - 1, var3 + var5 - 1);
         }

         if (TabLineBorder.this.highlighted || TabLineBorder.this.last || TabLineBorder.this.tabSpacing) {
            var1.drawLine(var2, var3 + var5 - 1, var2 + var4 - 1, var3 + var5 - 1);
         }

         if (TabLineBorder.this.drawBottomLine) {
            var1.drawLine(var2, var3, var2, var3 + var5 - 1);
         }

      }
   }
}
