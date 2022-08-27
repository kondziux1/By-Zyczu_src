package net.infonode.gui.icon.button;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import net.infonode.util.Direction;

public class DropDownIcon extends ArrowIcon {
   public DropDownIcon(int var1, Direction var2) {
      this(null, var1, var2);
   }

   public DropDownIcon(Color var1, int var2, Direction var3) {
      super(var1, var2, var3);
   }

   protected void paintIcon(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      if (this.getDirection() == Direction.DOWN) {
         int var7 = this.getIconWidth() / 4;
         var2.fillRect(var3, var4, var5 - var3 + 1, 2);
         super.paintIcon(var1, var2, var3, var4 + var7, var5, var6 + var7);
      }

   }
}
