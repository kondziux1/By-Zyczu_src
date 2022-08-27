package net.infonode.docking.action;

import javax.swing.Icon;
import net.infonode.docking.DockingWindow;
import net.infonode.gui.action.SimpleAction;

class DockingWindowAction$1 extends SimpleAction {
   DockingWindowAction$1(DockingWindowAction var1, DockingWindow var2) {
      super();
      this.this$0 = var1;
      this.val$window = var2;
   }

   public String getName() {
      return this.this$0.getName();
   }

   public boolean isEnabled() {
      return this.this$0.isPerformable(this.val$window);
   }

   public void perform() {
      this.this$0.perform(this.val$window);
   }

   public Icon getIcon() {
      return this.this$0.getIcon();
   }
}
