package net.infonode.tabbedpanel.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.Serializable;
import javax.swing.JComponent;
import javax.swing.border.Border;
import net.infonode.gui.GraphicsUtil;
import net.infonode.gui.colorprovider.ColorProvider;
import net.infonode.gui.colorprovider.ColorProviderList;
import net.infonode.gui.colorprovider.ColorProviderUtil;
import net.infonode.gui.colorprovider.FixedColorProvider;
import net.infonode.gui.colorprovider.UIManagerColorProvider;
import net.infonode.tabbedpanel.TabbedPanel;
import net.infonode.tabbedpanel.TabbedUtils;
import net.infonode.util.Direction;

public class TabAreaLineBorder implements Border, Serializable {
   private static final long serialVersionUID = 1L;
   private ColorProvider color;
   private boolean drawTop;
   private boolean drawLeft;
   private boolean drawRight;
   private boolean flipLeftRight;

   public TabAreaLineBorder() {
      this(null);
   }

   public TabAreaLineBorder(Color var1) {
      this(var1, true, true, true, false);
   }

   public TabAreaLineBorder(boolean var1, boolean var2, boolean var3, boolean var4) {
      this((Color)null, var1, var2, var3, var4);
   }

   public TabAreaLineBorder(Color var1, boolean var2, boolean var3, boolean var4, boolean var5) {
      this(
         ColorProviderUtil.getColorProvider(
            var1, new ColorProviderList(UIManagerColorProvider.TABBED_PANE_DARK_SHADOW, UIManagerColorProvider.CONTROL_DARK_SHADOW, FixedColorProvider.BLACK)
         ),
         var2,
         var3,
         var4,
         var5
      );
   }

   public TabAreaLineBorder(ColorProvider var1, boolean var2, boolean var3, boolean var4, boolean var5) {
      super();
      this.color = var1;
      this.drawTop = var2;
      this.drawLeft = var3;
      this.drawRight = var4;
      this.flipLeftRight = var5;
   }

   public boolean isBorderOpaque() {
      return true;
   }

   public void paintBorder(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      Insets var7 = this.getBorderInsets(var1);
      var2.setColor(this.color.getColor(var1));
      if (var7.top == 1) {
         GraphicsUtil.drawOptimizedLine(var2, var3, var4, var3 + var5 - 1, var4);
      }

      if (var7.bottom == 1) {
         GraphicsUtil.drawOptimizedLine(var2, var3, var4 + var6 - 1, var3 + var5 - 1, var4 + var6 - 1);
      }

      if (var7.left == 1) {
         GraphicsUtil.drawOptimizedLine(var2, var3, var4, var3, var4 + var6 - 1);
      }

      if (var7.right == 1) {
         GraphicsUtil.drawOptimizedLine(var2, var3 + var5 - 1, var4, var3 + var5 - 1, var4 + var6 - 1);
      }

   }

   private boolean drawTop(Direction var1) {
      return var1 == Direction.UP
         ? this.drawTop
         : (var1 == Direction.LEFT ? (this.flipLeftRight ? this.drawLeft : this.drawRight) : (var1 == Direction.RIGHT ? this.drawLeft : false));
   }

   private boolean drawLeft(Direction var1) {
      return var1 == Direction.UP
         ? this.drawLeft
         : (var1 == Direction.LEFT ? this.drawTop : (var1 == Direction.DOWN ? (this.flipLeftRight ? this.drawLeft : this.drawRight) : false));
   }

   private boolean drawRight(Direction var1) {
      return var1 == Direction.UP
         ? this.drawRight
         : (var1 == Direction.LEFT ? false : (var1 == Direction.DOWN ? (this.flipLeftRight ? this.drawRight : this.drawLeft) : this.drawTop));
   }

   private boolean drawBottom(Direction var1) {
      return var1 == Direction.UP
         ? false
         : (var1 == Direction.LEFT ? (this.flipLeftRight ? this.drawRight : this.drawLeft) : (var1 == Direction.RIGHT ? this.drawRight : this.drawTop));
   }

   public Insets getBorderInsets(Component var1) {
      if (var1 instanceof JComponent && ((JComponent)var1).getComponentCount() == 0) {
         return new Insets(0, 0, 0, 0);
      } else {
         Direction var2 = getTabAreaDirection(var1);
         return var2 != null
            ? new Insets(this.drawTop(var2) ? 1 : 0, this.drawLeft(var2) ? 1 : 0, this.drawBottom(var2) ? 1 : 0, this.drawRight(var2) ? 1 : 0)
            : new Insets(0, 0, 0, 0);
      }
   }

   private static Direction getTabAreaDirection(Component var0) {
      TabbedPanel var1 = TabbedUtils.getParentTabbedPanel(var0);
      return var1 != null ? var1.getProperties().getTabAreaOrientation() : null;
   }
}
