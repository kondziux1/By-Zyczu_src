package net.infonode.gui.icon.button;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import net.infonode.gui.GraphicsUtil;

public class MaximizeIcon extends AbstractButtonIcon {
   private static final long serialVersionUID = -5926578998259734919L;

   public MaximizeIcon() {
      super();
   }

   public MaximizeIcon(Color var1) {
      super(var1);
   }

   public MaximizeIcon(Color var1, int var2) {
      super(var1, var2);
   }

   public MaximizeIcon(int var1) {
      super(var1);
   }

   protected void paintIcon(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      GraphicsUtil.drawOptimizedLine(var2, var3, var4, var5, var4);
      GraphicsUtil.drawOptimizedLine(var2, var3, var4 + 1, var5, var4 + 1);
      GraphicsUtil.drawOptimizedLine(var2, var3, var4 + 2, var3, var6);
      GraphicsUtil.drawOptimizedLine(var2, var5, var4 + 2, var5, var6);
      GraphicsUtil.drawOptimizedLine(var2, var3 + 1, var6, var5 - 1, var6);
   }
}
