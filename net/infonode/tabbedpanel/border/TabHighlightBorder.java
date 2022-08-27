package net.infonode.tabbedpanel.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.Serializable;
import javax.swing.border.Border;
import net.infonode.gui.GraphicsUtil;
import net.infonode.gui.colorprovider.ColorProvider;
import net.infonode.gui.colorprovider.ColorProviderUtil;
import net.infonode.gui.colorprovider.UIManagerColorProvider;
import net.infonode.tabbedpanel.TabbedPanel;
import net.infonode.tabbedpanel.TabbedUtils;
import net.infonode.util.Direction;

public class TabHighlightBorder implements Border, Serializable {
   private static final long serialVersionUID = 1L;
   private ColorProvider color;
   private boolean openBorder;

   public TabHighlightBorder() {
      this((Color)null, false);
   }

   public TabHighlightBorder(Color var1, boolean var2) {
      this(ColorProviderUtil.getColorProvider(var1, UIManagerColorProvider.TABBED_PANE_HIGHLIGHT), var2);
   }

   public TabHighlightBorder(ColorProvider var1, boolean var2) {
      super();
      this.color = var1;
      this.openBorder = var2;
   }

   public void paintBorder(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      TabbedPanel var7 = TabbedUtils.getParentTabbedPanel(var1);
      if (var7 != null) {
         Direction var8 = var7.getProperties().getTabAreaOrientation();
         var2.setColor(this.color.getColor(var1));
         if (var8 == Direction.UP) {
            GraphicsUtil.drawOptimizedLine(var2, var3 + 1, var4, var3 + var5 - 2, var4);
            GraphicsUtil.drawOptimizedLine(var2, var3, var4, var3, var4 + var6 - (this.openBorder ? 1 : 2));
         } else if (var8 == Direction.LEFT) {
            GraphicsUtil.drawOptimizedLine(var2, var3 + 1, var4, var3 + var5 - (this.openBorder ? 1 : 2), var4);
            GraphicsUtil.drawOptimizedLine(var2, var3, var4, var3, var4 + var6 - 2);
         } else if (var8 == Direction.DOWN) {
            if (!this.openBorder) {
               GraphicsUtil.drawOptimizedLine(var2, var3 + 1, var4, var3 + var5 - 2, var4);
            }

            GraphicsUtil.drawOptimizedLine(var2, var3, var4, var3, var4 + var6 - 2);
         } else if (this.openBorder) {
            GraphicsUtil.drawOptimizedLine(var2, var3, var4, var3 + var5 - 2, var4);
         } else {
            GraphicsUtil.drawOptimizedLine(var2, var3 + 1, var4, var3 + var5 - 2, var4);
            GraphicsUtil.drawOptimizedLine(var2, var3, var4, var3, var4 + var6 - 2);
         }
      }

   }

   public Insets getBorderInsets(Component var1) {
      return new Insets(1, 1, 0, 0);
   }

   public boolean isBorderOpaque() {
      return false;
   }
}
