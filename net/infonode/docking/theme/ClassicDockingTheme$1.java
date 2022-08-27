package net.infonode.docking.theme;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.Border;
import net.infonode.tabbedpanel.TabbedPanel;
import net.infonode.tabbedpanel.TabbedUtils;
import net.infonode.util.Direction;

class ClassicDockingTheme$1 implements Border {
   ClassicDockingTheme$1(ClassicDockingTheme var1) {
      super();
      this.this$0 = var1;
   }

   public boolean isBorderOpaque() {
      return false;
   }

   public void paintBorder(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public Insets getBorderInsets(Component var1) {
      TabbedPanel var2 = TabbedUtils.getParentTabbedPanel(var1);
      if (var2 != null) {
         Direction var3 = var2.getProperties().getTabAreaOrientation();
         return new Insets(var3 == Direction.UP ? 2 : 0, var3 == Direction.LEFT ? 2 : 0, var3 == Direction.DOWN ? 2 : 0, var3 == Direction.RIGHT ? 2 : 0);
      } else {
         return new Insets(0, 0, 0, 0);
      }
   }
}
