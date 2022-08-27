package net.infonode.tabbedpanel.theme;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.Border;
import net.infonode.tabbedpanel.TabbedPanel;
import net.infonode.tabbedpanel.TabbedUtils;
import net.infonode.util.Direction;

class ClassicTheme$2 implements Border {
   ClassicTheme$2(ClassicTheme var1) {
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
      if (var2 == null) {
         return new Insets(0, 0, 0, 0);
      } else {
         Direction var3 = var2.getProperties().getTabAreaOrientation();
         return new Insets(
            var3 != Direction.RIGHT && var3 != Direction.LEFT ? 0 : ClassicTheme.access$100(this.this$0),
            var3 != Direction.UP && var3 != Direction.DOWN ? 0 : ClassicTheme.access$100(this.this$0),
            var3 != Direction.RIGHT && var3 != Direction.LEFT ? 0 : 1,
            var3 != Direction.UP && var3 != Direction.DOWN ? 0 : 1
         );
      }
   }
}
