package net.infonode.tabbedpanel.theme;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.Border;
import net.infonode.tabbedpanel.TabbedUtils;
import net.infonode.tabbedpanel.titledtab.TitledTab;
import net.infonode.util.Direction;

class LookAndFeelTheme$10 implements Border {
   LookAndFeelTheme$10(LookAndFeelTheme var1, boolean var2) {
      super();
      this.this$0 = var1;
      this.val$selected = var2;
   }

   public void paintBorder(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public Insets getBorderInsets(Component var1) {
      TitledTab var2 = (TitledTab)TabbedUtils.getParentTab(var1);
      if (var2.getTabbedPanel() == null) {
         return new Insets(0, 0, 0, 0);
      } else {
         Direction var3 = var2.getTabbedPanel().getProperties().getTabAreaOrientation();
         Direction var4 = this.val$selected
            ? var2.getProperties().getHighlightedProperties().getDirection()
            : var2.getProperties().getNormalProperties().getDirection();
         return this.val$selected
            ? LookAndFeelTheme.access$300().getSelectedTabInsets(var3, var4)
            : LookAndFeelTheme.access$300().getNormalTabInsets(var3, var4);
      }
   }

   public boolean isBorderOpaque() {
      return false;
   }
}
