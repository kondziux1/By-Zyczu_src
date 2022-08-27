package net.infonode.docking;

import java.awt.Dimension;
import net.infonode.tabbedpanel.TabContentPanel;

class AbstractTabWindow$2 extends TabContentPanel {
   AbstractTabWindow$2(AbstractTabWindow var1) {
      super();
      this.this$0 = var1;
   }

   public Dimension getMinimumSize() {
      return this.this$0.getTabWindowProperties().getRespectChildWindowMinimumSize() ? super.getMinimumSize() : new Dimension(0, 0);
   }
}
