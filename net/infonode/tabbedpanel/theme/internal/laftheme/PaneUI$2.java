package net.infonode.tabbedpanel.theme.internal.laftheme;

import java.awt.Color;
import java.awt.image.RGBImageFilter;
import net.infonode.util.Direction;

class PaneUI$2 extends RGBImageFilter {
   PaneUI$2(PaneUI var1, int var2, int var3, Direction var4) {
      super();
      this.this$0 = var1;
      this.val$px = var2;
      this.val$py = var3;
      this.val$direction = var4;
   }

   public int filterRGB(int var1, int var2, int var3) {
      if (this.val$px == var1 && this.val$py == var2) {
         int var4 = var3 >> 16 & 0xFF;
         int var5 = var3 >> 8 & 0xFF;
         int var6 = var3 & 0xFF;
         int var7 = var3 >> 24 & 0xFF;
         PaneUI.access$200(this.this$0)[PaneUI.access$300(this.this$0, this.val$direction.getOpposite())] = new Color(var4, var5, var6, var7);
      }

      return var3;
   }
}
