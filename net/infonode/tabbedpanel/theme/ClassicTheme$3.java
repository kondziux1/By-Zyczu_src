package net.infonode.tabbedpanel.theme;

import java.awt.Color;
import java.awt.Component;
import net.infonode.gui.colorprovider.ColorProvider;
import net.infonode.tabbedpanel.TabbedUIDefaults;

class ClassicTheme$3 implements ColorProvider {
   ClassicTheme$3(ClassicTheme var1) {
      super();
      this.this$0 = var1;
   }

   public Color getColor() {
      Color var1 = TabbedUIDefaults.getHighlightedStateBackground();
      var1 = new Color(var1.getRed() == 0 ? 1 : var1.getRed(), var1.getGreen() == 0 ? 1 : var1.getGreen(), var1.getBlue() == 0 ? 1 : var1.getBlue());
      Color var2 = TabbedUIDefaults.getNormalStateBackground();
      Color var3 = ClassicTheme.access$200(this.this$0).getColor();
      int var4 = (int)((float)var2.getRed() * ((float)var3.getRed() / (float)var1.getRed()));
      int var5 = (int)((float)var2.getGreen() * ((float)var3.getGreen() / (float)var1.getGreen()));
      int var6 = (int)((float)var2.getBlue() * ((float)var3.getBlue() / (float)var1.getBlue()));
      var4 = (var4 + var3.getRed()) / 2;
      var5 = (var5 + var3.getGreen()) / 2;
      var6 = (var6 + var3.getBlue()) / 2;
      return new Color(var4 > 255 ? 255 : var4, var5 > 255 ? 255 : var5, var6 > 255 ? 255 : var6);
   }

   public Color getColor(Component var1) {
      return this.getColor();
   }
}
