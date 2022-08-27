package net.infonode.gui.icon.button;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import net.infonode.gui.GraphicsUtil;

public class MinimizeIcon extends AbstractButtonIcon {
   private static final long serialVersionUID = 6993801965272908275L;

   public MinimizeIcon() {
      super();
   }

   public MinimizeIcon(Color var1) {
      super(var1);
   }

   public MinimizeIcon(Color var1, int var2) {
      super(var1, var2);
   }

   public MinimizeIcon(int var1) {
      super(var1);
   }

   protected void paintIcon(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      GraphicsUtil.drawOptimizedLine(var2, var3, var6 - 1, var5, var6 - 1);
      GraphicsUtil.drawOptimizedLine(var2, var3, var6, var5, var6);
   }
}
