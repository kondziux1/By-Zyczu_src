package net.infonode.gui.icon.button;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

public class WindowIcon extends AbstractButtonIcon {
   public WindowIcon() {
      super();
   }

   public WindowIcon(Color var1) {
      super(var1);
   }

   public WindowIcon(Color var1, int var2) {
      super(var1, var2);
   }

   public WindowIcon(int var1) {
      super(var1);
   }

   protected void paintIcon(Component var1, Graphics var2, int var3, int var4, int var5, int var6, boolean var7) {
      var2.fillRect(var3, var4, var5 - var3 + 1, var6 - var4 + 1);
      if (!var7) {
         var2.setColor(Color.WHITE);
      }

      var2.fillRect(var3 + 1, var4 + 2, var5 - var3 - 1, var6 - var4 - 2);
   }
}
