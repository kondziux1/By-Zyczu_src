package net.infonode.tabbedpanel.hover;

import net.infonode.tabbedpanel.TabbedPanel;
import net.infonode.tabbedpanel.titledtab.TitledTab;

class TitledTabDelayedMouseExitHoverAction$2 implements Runnable {
   TitledTabDelayedMouseExitHoverAction$2(TitledTabDelayedMouseExitHoverAction var1, TitledTab var2, TabbedPanel var3) {
      super();
      this.this$0 = var1;
      this.val$tab = var2;
      this.val$tp = var3;
   }

   public void run() {
      if (this.val$tab.getTabbedPanel() != this.val$tp) {
         TitledTabDelayedMouseExitHoverAction.access$000(this.this$0).forceExit(this.val$tab);
      }

   }
}
