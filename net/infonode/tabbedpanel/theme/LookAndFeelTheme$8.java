package net.infonode.tabbedpanel.theme;

import java.awt.Component;
import java.awt.Dimension;
import net.infonode.gui.DimensionProvider;
import net.infonode.tabbedpanel.TabbedUtils;

class LookAndFeelTheme$8 implements DimensionProvider {
   LookAndFeelTheme$8(LookAndFeelTheme$3 var1) {
      super();
      this.this$1 = var1;
   }

   public Dimension getDimension(Component var1) {
      return LookAndFeelTheme.access$300().getTabExternalMinSize(TabbedUtils.getParentTab(var1).getTabbedPanel().getProperties().getTabAreaOrientation());
   }
}
