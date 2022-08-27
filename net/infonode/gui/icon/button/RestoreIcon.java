package net.infonode.gui.icon.button;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import net.infonode.gui.GraphicsUtil;

public class RestoreIcon extends AbstractButtonIcon {
   private static final long serialVersionUID = 4019344427358669254L;

   public RestoreIcon() {
      super();
   }

   public RestoreIcon(Color var1) {
      super(var1);
   }

   public RestoreIcon(Color var1, int var2) {
      super(var1, var2);
   }

   public RestoreIcon(int var1) {
      super(var1);
   }

   protected void paintIcon(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      int var7 = 2 * (var6 - var4 + 1) / 3 - 1;
      int var8 = 5 * (var5 - var3 + 1) / 6 - 1;
      GraphicsUtil.drawOptimizedLine(var2, var5 - var8, var4, var5, var4);
      GraphicsUtil.drawOptimizedLine(var2, var5, var4, var5, var4 + var7);
      GraphicsUtil.drawOptimizedLine(var2, var5 - var8, var4 + 1, var5 - var8, var6 - var7);
      GraphicsUtil.drawOptimizedLine(var2, var3 + var8 + 1, var4 + var7, var5 - 1, var4 + var7);
      GraphicsUtil.drawOptimizedLine(var2, var3, var6 - var7, var3, var6);
      GraphicsUtil.drawOptimizedLine(var2, var3 + 1, var6, var3 + var8, var6);
      GraphicsUtil.drawOptimizedLine(var2, var3 + var8, var6 - 1, var3 + var8, var6 - var7 + 1);
      GraphicsUtil.drawOptimizedLine(var2, var3 + 1, var6 - var7, var3 + var8, var6 - var7);
   }
}
