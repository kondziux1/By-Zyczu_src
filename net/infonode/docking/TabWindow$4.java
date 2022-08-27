package net.infonode.docking;

import net.infonode.tabbedpanel.TabAdapter;
import net.infonode.tabbedpanel.TabEvent;
import net.infonode.tabbedpanel.TabRemovedEvent;

class TabWindow$4 extends TabAdapter {
   TabWindow$4(TabWindow var1) {
      super();
      this.this$0 = var1;
   }

   public void tabAdded(TabEvent var1) {
      TabWindow.access$000(this.this$0, null);
   }

   public void tabRemoved(TabRemovedEvent var1) {
      TabWindow.access$000(this.this$0, null);
   }
}
