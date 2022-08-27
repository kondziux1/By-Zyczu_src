package net.infonode.tabbedpanel.theme;

import net.infonode.gui.hover.HoverEvent;
import net.infonode.gui.hover.HoverListener;
import net.infonode.tabbedpanel.Tab;

class LookAndFeelTheme$9 implements HoverListener {
   LookAndFeelTheme$9(LookAndFeelTheme$3 var1) {
      super();
      this.this$1 = var1;
   }

   public void mouseEntered(HoverEvent var1) {
      LookAndFeelTheme.access$300().setHoveredTab((Tab)var1.getSource());
   }

   public void mouseExited(HoverEvent var1) {
      LookAndFeelTheme.access$300().setHoveredTab(null);
   }
}
