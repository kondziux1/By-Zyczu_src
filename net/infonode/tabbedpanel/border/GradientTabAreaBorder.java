package net.infonode.tabbedpanel.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.Serializable;
import javax.swing.border.Border;
import net.infonode.gui.colorprovider.ColorProvider;
import net.infonode.gui.colorprovider.ColorProviderUtil;
import net.infonode.gui.colorprovider.UIManagerColorProvider;
import net.infonode.gui.componentpainter.GradientComponentPainter;
import net.infonode.tabbedpanel.TabbedPanel;
import net.infonode.tabbedpanel.TabbedUtils;

public class GradientTabAreaBorder implements Border, Serializable {
   private static final long serialVersionUID = 1L;
   private GradientComponentPainter painter;

   public GradientTabAreaBorder(Color var1) {
      this(var1, null);
   }

   public GradientTabAreaBorder(Color var1, Color var2) {
      this(
         ColorProviderUtil.getColorProvider(var1, UIManagerColorProvider.CONTROL_COLOR),
         ColorProviderUtil.getColorProvider(var2, UIManagerColorProvider.CONTROL_COLOR)
      );
   }

   public GradientTabAreaBorder(ColorProvider var1, ColorProvider var2) {
      super();
      this.painter = new GradientComponentPainter(var1, var1, var2, var2);
   }

   public boolean isBorderOpaque() {
      return true;
   }

   public void paintBorder(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      TabbedPanel var7 = TabbedUtils.getParentTabbedPanel(var1);
      if (var7 != null) {
         this.painter.paint(var1, var2, var3, var4, var5, var6, var7.getProperties().getTabAreaOrientation().getNextCW(), false, false);
      }
   }

   public Insets getBorderInsets(Component var1) {
      return new Insets(0, 0, 0, 0);
   }
}
