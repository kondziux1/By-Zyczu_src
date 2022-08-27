package net.infonode.gui.icon.button;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import net.infonode.gui.GraphicsUtil;

public class DockIcon extends AbstractButtonIcon {
   private static final long serialVersionUID = 1L;

   public DockIcon() {
      super();
   }

   public DockIcon(Color var1) {
      super(var1);
   }

   public DockIcon(Color var1, int var2) {
      super(var1, var2);
   }

   public DockIcon(int var1) {
      super(var1);
   }

   protected void paintIcon(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      int var7 = var5 - var3 > 6 ? 1 : 0;
      GraphicsUtil.drawOptimizedLine(var2, var3 + var7, var6 - var7, var3 + var7, var6 - var7 - 3);
      GraphicsUtil.drawOptimizedLine(var2, var3 + var7, var6 - var7, var3 + var7 + 3, var6 - var7);
      GraphicsUtil.drawOptimizedLine(var2, var5 - var7 - 1, var4 + var7, var5 - var7 - 1, var4 + var7 + 2);
      GraphicsUtil.drawOptimizedLine(var2, var5 - var7 - 2, var4 + var7 - 1, var5 - var7 - 2, var4 + var7 - 1);
      GraphicsUtil.drawOptimizedLine(var2, var5 - var7 - 1, var4 + var7, var3 + var7, var6 - var7 - 1);
      GraphicsUtil.drawOptimizedLine(var2, var5 - var7 - 1, var4 + var7 + 1, var3 + var7, var6 - var7);
      GraphicsUtil.drawOptimizedLine(var2, var5 - var7 - 1, var4 + var7 + 2, var3 + var7 + 1, var6 - var7);
   }
}
