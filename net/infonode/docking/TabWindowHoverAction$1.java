package net.infonode.docking;

import net.infonode.tabbedpanel.TabAdapter;
import net.infonode.tabbedpanel.TabStateChangedEvent;

class TabWindowHoverAction$1 extends TabAdapter {
   TabWindowHoverAction$1(TabWindowHoverAction var1) {
      super();
      this.this$0 = var1;
   }

   public void tabSelected(TabStateChangedEvent var1) {
      if (var1.getTab() != null) {
         DockingWindow var2 = ((WindowTab)var1.getTab()).getWindow();
         if (!TabWindowHoverAction.access$000(this.this$0) && var2 instanceof View) {
            TabWindowHoverAction.access$100(this.this$0, (View)var2);
         }
      }

   }

   public void tabDeselected(TabStateChangedEvent var1) {
      if (var1.getTab() != null) {
         DockingWindow var2 = ((WindowTab)var1.getTab()).getWindow();
         if (TabWindowHoverAction.access$000(this.this$0) && var2 instanceof View) {
            TabWindowHoverAction.access$200(this.this$0, (View)var2);
         }
      }

   }
}
