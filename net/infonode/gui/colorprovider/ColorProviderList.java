package net.infonode.gui.colorprovider;

import java.awt.Color;
import java.awt.Component;

public class ColorProviderList extends AbstractColorProvider {
   private ColorProvider[] providers;

   public ColorProviderList(ColorProvider var1, ColorProvider var2) {
      this(new ColorProvider[]{var1, var2});
   }

   public ColorProviderList(ColorProvider var1, ColorProvider var2, ColorProvider var3) {
      this(new ColorProvider[]{var1, var2, var3});
   }

   public ColorProviderList(ColorProvider[] var1) {
      super();
      this.providers = (ColorProvider[])var1.clone();
   }

   public Color getColor(Component var1) {
      Color var2 = null;

      for(int var3 = 0; var3 < this.providers.length; ++var3) {
         var2 = this.providers[var3].getColor(var1);
         if (var2 != null) {
            break;
         }
      }

      return var2;
   }

   public Color getColor() {
      Color var1 = null;

      for(int var2 = 0; var2 < this.providers.length; ++var2) {
         var1 = this.providers[var2].getColor();
         if (var1 != null) {
            break;
         }
      }

      return var1;
   }
}
