package net.infonode.gui.componentpainter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import net.infonode.gui.colorprovider.BackgroundColorProvider;
import net.infonode.gui.colorprovider.ColorProvider;
import net.infonode.util.Direction;

public class SolidColorComponentPainter extends AbstractComponentPainter {
   private static final long serialVersionUID = 1L;
   public static final SolidColorComponentPainter BACKGROUND_COLOR_PAINTER = new SolidColorComponentPainter(BackgroundColorProvider.INSTANCE);
   private ColorProvider colorProvider;

   public SolidColorComponentPainter(ColorProvider var1) {
      super();
      this.colorProvider = var1;
   }

   public void paint(Component var1, Graphics var2, int var3, int var4, int var5, int var6, Direction var7, boolean var8, boolean var9) {
      var2.setColor(this.colorProvider.getColor(var1));
      var2.fillRect(var3, var4, var5, var6);
   }

   public boolean isOpaque(Component var1) {
      return this.colorProvider.getColor(var1).getAlpha() == 255;
   }

   public Color getColor(Component var1) {
      return this.colorProvider.getColor(var1);
   }
}
