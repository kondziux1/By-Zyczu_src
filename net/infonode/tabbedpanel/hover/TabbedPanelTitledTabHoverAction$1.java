package net.infonode.tabbedpanel.hover;

import net.infonode.tabbedpanel.TabAdapter;
import net.infonode.tabbedpanel.TabEvent;
import net.infonode.tabbedpanel.TabRemovedEvent;
import net.infonode.tabbedpanel.TabStateChangedEvent;
import net.infonode.tabbedpanel.titledtab.TitledTab;

class TabbedPanelTitledTabHoverAction$1 extends TabAdapter {
   TabbedPanelTitledTabHoverAction$1(TabbedPanelTitledTabHoverAction var1) {
      super();
      this.this$0 = var1;
   }

   public void tabAdded(TabEvent var1) {
      if (var1.getTab() instanceof TitledTab
         && (
            !TabbedPanelTitledTabHoverAction.access$000(this.this$0)
               || TabbedPanelTitledTabHoverAction.access$000(this.this$0) && var1.getTab().isHighlighted()
         )) {
         ((TitledTab)var1.getTab()).getProperties().addSuperObject(TabbedPanelTitledTabHoverAction.access$100(this.this$0));
      }

   }

   public void tabRemoved(TabRemovedEvent var1) {
      if (var1.getTab() instanceof TitledTab) {
         ((TitledTab)var1.getTab()).getProperties().removeSuperObject(TabbedPanelTitledTabHoverAction.access$100(this.this$0));
      }

   }

   public void tabHighlighted(TabStateChangedEvent var1) {
      if (var1.getCurrentTab() != null && var1.getCurrentTab() instanceof TitledTab) {
         TabbedPanelTitledTabHoverAction.access$200(this.this$0, var1.getTabbedPanel(), (TitledTab)var1.getCurrentTab());
      }

   }

   public void tabDehighlighted(TabStateChangedEvent var1) {
      if (var1.getPreviousTab() != null && var1.getPreviousTab() instanceof TitledTab) {
         TabbedPanelTitledTabHoverAction.access$300(this.this$0, var1.getTabbedPanel(), (TitledTab)var1.getPreviousTab());
      }

   }
}
