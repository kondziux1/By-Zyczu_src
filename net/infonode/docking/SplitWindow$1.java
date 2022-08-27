package net.infonode.docking;

import net.infonode.docking.model.SplitWindowItem;
import net.infonode.gui.SimpleSplitPane;
import net.infonode.gui.SimpleSplitPaneListener;

class SplitWindow$1 implements SimpleSplitPaneListener {
   SplitWindow$1(SplitWindow var1) {
      super();
      this.this$0 = var1;
   }

   public void dividerLocationChanged(SimpleSplitPane var1) {
      ((SplitWindowItem)this.this$0.getWindowItem()).setDividerLocation(var1.getDividerLocation());
   }
}
