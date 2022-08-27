package net.infonode.docking;

import java.awt.Dimension;
import net.infonode.tabbedpanel.TabContentPanel;
import net.infonode.tabbedpanel.TabbedPanel;

class WindowBar$2 extends TabContentPanel {
   WindowBar$2(WindowBar var1, TabbedPanel var2) {
      super(var2);
      this.this$0 = var1;
   }

   public Dimension getMinimumSize() {
      return this.this$0.getWindowBarProperties().getTabWindowProperties().getRespectChildWindowMinimumSize() ? super.getMinimumSize() : new Dimension(0, 0);
   }
}
