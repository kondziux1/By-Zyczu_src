package net.infonode.docking.action;

import net.infonode.docking.DockingWindow;
import net.infonode.docking.TabWindow;
import net.infonode.docking.util.DockingUtil;

public class StateDependentWindowAction extends DockingWindowAction {
   private static final long serialVersionUID = 1L;
   public static final StateDependentWindowAction MAXIMIZE_RESTORE = new StateDependentWindowAction(
      MaximizeWindowAction.INSTANCE, RestoreParentWindowAction.INSTANCE, RestoreParentWindowAction.INSTANCE
   );
   public static final StateDependentWindowAction MAXIMIZE_RESTORE_WITH_ABORT = new StateDependentWindowAction(
      MaximizeWithAbortWindowAction.INSTANCE, RestoreParentWithAbortWindowAction.INSTANCE, RestoreParentWithAbortWindowAction.INSTANCE
   );
   private DockingWindowAction normalAction;
   private DockingWindowAction minimizedAction;
   private DockingWindowAction maximizedAction;

   public StateDependentWindowAction(DockingWindowAction var1, DockingWindowAction var2, DockingWindowAction var3) {
      super();
      this.normalAction = var1;
      this.minimizedAction = var2;
      this.maximizedAction = var3;
   }

   public String getName() {
      return "State Dependent";
   }

   public boolean isPerformable(DockingWindow var1) {
      return this.getActionProvider(var1).isPerformable(var1);
   }

   public void perform(DockingWindow var1) {
      this.getActionProvider(var1).perform(var1);
   }

   private DockingWindowAction getActionProvider(DockingWindow var1) {
      if (var1.isMinimized()) {
         return this.minimizedAction;
      } else {
         TabWindow var2 = DockingUtil.getTabWindowFor(var1);
         return var2 != null && var2.isMaximized() ? this.maximizedAction : this.normalAction;
      }
   }
}
