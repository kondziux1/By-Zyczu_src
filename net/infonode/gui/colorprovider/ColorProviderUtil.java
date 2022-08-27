package net.infonode.gui.colorprovider;

import java.awt.Color;

public class ColorProviderUtil {
   private ColorProviderUtil() {
      super();
   }

   public static ColorProvider getColorProvider(Color var0, ColorProvider var1) {
      return (ColorProvider)(var0 == null ? var1 : new FixedColorProvider(var0));
   }
}
