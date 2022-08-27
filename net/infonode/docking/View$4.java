package net.infonode.docking;

import java.awt.event.MouseEvent;
import net.infonode.docking.internalutil.DropAction;
import net.infonode.tabbedpanel.TabbedPanel;

class View$4 extends DropAction {
   View$4(View var1) {
      super();
      this.this$0 = var1;
   }

   public boolean showTitle() {
      return false;
   }

   public void execute(DockingWindow var1, MouseEvent var2) {
      this.removeGhostTab();
      ((AbstractTabWindow)this.this$0.getWindowParent()).addTab(var1);
   }

   public void clear(DockingWindow var1, DropAction var2) {
      if (var2 != this) {
         this.removeGhostTab();
      }

   }

   private void removeGhostTab() {
      if (View.access$200(this.this$0) != null) {
         TabbedPanel var1 = ((AbstractTabWindow)this.this$0.getWindowParent()).getTabbedPanel();
         var1.removeTab(View.access$200(this.this$0));
         View.access$202(this.this$0, null);
         if (var1.getProperties().getEnsureSelectedTabVisible() && var1.getSelectedTab() != null) {
            var1.scrollTabToVisibleArea(var1.getSelectedTab());
         }
      }

   }
}
