package net.infonode.gui.colorprovider;

import java.awt.Color;
import java.awt.Component;
import net.infonode.util.ColorUtil;

public class ColorMultiplier extends AbstractColorProvider {
   private static final long serialVersionUID = 1L;
   private final ColorProvider colorProvider;
   private final double factor;

   public ColorMultiplier(ColorProvider var1, double var2) {
      super();
      this.colorProvider = var1;
      this.factor = var2;
   }

   public Color getColor(Component var1) {
      return ColorUtil.mult(this.colorProvider.getColor(var1), this.factor);
   }

   public Color getColor() {
      return ColorUtil.mult(this.colorProvider.getColor(), this.factor);
   }
}
