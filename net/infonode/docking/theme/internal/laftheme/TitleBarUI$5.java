package net.infonode.docking.theme.internal.laftheme;

import java.awt.Color;
import java.awt.image.RGBImageFilter;

class TitleBarUI$5 extends RGBImageFilter {
   TitleBarUI$5(TitleBarUI var1, int var2, int var3) {
      super();
      this.this$0 = var1;
      this.val$px = var2;
      this.val$py = var3;
   }

   public int filterRGB(int var1, int var2, int var3) {
      if (this.val$px == var1 && this.val$py == var2) {
         int var4 = var3 >> 16 & 0xFF;
         int var5 = var3 >> 8 & 0xFF;
         int var6 = var3 & 0xFF;
         int var7 = var3 >> 24 & 0xFF;
         TitleBarUI.access$802(this.this$0, new Color(var4, var5, var6, var7));
      }

      return var3;
   }
}
