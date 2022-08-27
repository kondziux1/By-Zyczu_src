package net.infonode.tabbedpanel.internal;

import net.infonode.tabbedpanel.TabAdapter;
import net.infonode.tabbedpanel.TabEvent;
import net.infonode.tabbedpanel.TabRemovedEvent;

class TabDropDownList$1 extends TabAdapter {
   TabDropDownList$1(TabDropDownList var1) {
      super();
      this.this$0 = var1;
   }

   public void tabAdded(TabEvent var1) {
      if (var1.getTab().getTabbedPanel().getTabCount() == 2) {
         this.this$0.setVisible(true);
      }

   }

   public void tabRemoved(TabRemovedEvent var1) {
      if (var1.getTabbedPanel().getTabCount() == 1) {
         this.this$0.setVisible(false);
      }

   }
}
