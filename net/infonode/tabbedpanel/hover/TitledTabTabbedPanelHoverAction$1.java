package net.infonode.tabbedpanel.hover;

import net.infonode.tabbedpanel.TabAdapter;
import net.infonode.tabbedpanel.TabStateChangedEvent;

class TitledTabTabbedPanelHoverAction$1 extends TabAdapter {
   TitledTabTabbedPanelHoverAction$1(TitledTabTabbedPanelHoverAction var1) {
      super();
      this.this$0 = var1;
   }

   public void tabHighlighted(TabStateChangedEvent var1) {
      if (var1.getCurrentTab() != null && TitledTabTabbedPanelHoverAction.access$000(this.this$0)) {
         TitledTabTabbedPanelHoverAction.access$100(this.this$0, var1.getCurrentTab().getTabbedPanel());
      }

   }

   public void tabDehighlighted(TabStateChangedEvent var1) {
      if (var1.getPreviousTab() != null && TitledTabTabbedPanelHoverAction.access$000(this.this$0)) {
         TitledTabTabbedPanelHoverAction.access$200(this.this$0, var1.getPreviousTab().getTabbedPanel());
      }

   }
}
