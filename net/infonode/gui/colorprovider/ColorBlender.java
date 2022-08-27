package net.infonode.gui.colorprovider;

import java.awt.Color;
import java.awt.Component;
import net.infonode.util.ColorUtil;

public class ColorBlender extends AbstractColorProvider {
   private static final long serialVersionUID = 1L;
   private final ColorProvider color1;
   private final ColorProvider color2;
   private final float blendAmount;

   public ColorBlender(ColorProvider var1, ColorProvider var2, float var3) {
      super();
      this.color1 = var1;
      this.color2 = var2;
      this.blendAmount = var3;
   }

   public Color getColor(Component var1) {
      return ColorUtil.blend(this.color1.getColor(var1), this.color2.getColor(var1), (double)this.blendAmount);
   }

   public Color getColor() {
      return ColorUtil.blend(this.color1.getColor(), this.color2.getColor(), (double)this.blendAmount);
   }
}
