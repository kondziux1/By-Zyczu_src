package net.infonode.tabbedpanel.theme;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.Border;
import net.infonode.tabbedpanel.TabbedPanel;
import net.infonode.tabbedpanel.TabbedUtils;

class LookAndFeelTheme$7 implements Border {
   LookAndFeelTheme$7(LookAndFeelTheme$3 var1) {
      super();
      this.this$1 = var1;
   }

   public void paintBorder(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public Insets getBorderInsets(Component var1) {
      TabbedPanel var2 = TabbedUtils.getParentTabbedPanelContentPanel(var1).getTabbedPanel();
      return LookAndFeelTheme.access$300().getContentInsets(var2.getProperties().getTabAreaOrientation(), var2.isTabAreaVisible());
   }

   public boolean isBorderOpaque() {
      return false;
   }
}
