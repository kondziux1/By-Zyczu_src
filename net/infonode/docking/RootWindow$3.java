package net.infonode.docking;

import java.awt.event.MouseEvent;
import net.infonode.docking.action.MaximizeWithAbortWindowAction;
import net.infonode.docking.action.NullWindowAction;
import net.infonode.docking.action.RestoreFocusWindowAction;
import net.infonode.docking.action.RestoreParentWithAbortWindowAction;
import net.infonode.docking.action.RestoreWithAbortWindowAction;
import net.infonode.docking.action.StateDependentWindowAction;
import net.infonode.gui.mouse.MouseButtonListener;

class RootWindow$3 implements MouseButtonListener {
   RootWindow$3(RootWindow var1) {
      super();
      this.this$0 = var1;
   }

   public void mouseButtonEvent(MouseEvent var1) {
      if (!var1.isConsumed()) {
         DockingWindow var2 = (DockingWindow)var1.getSource();
         if (var1.getID() == 501 && var1.getButton() == 1 && !var1.isShiftDown() && var2.isShowing()) {
            RestoreFocusWindowAction.INSTANCE.perform(var2);
         } else if (var1.getID() == 500 && var1.getButton() == 1 && var1.getClickCount() == 2) {
            if (var2.getWindowParent() instanceof WindowBar && this.this$0.getRootWindowProperties().getDoubleClickRestoresWindow()) {
               RestoreWithAbortWindowAction.INSTANCE.perform(var2);
            } else {
               new StateDependentWindowAction(MaximizeWithAbortWindowAction.INSTANCE, NullWindowAction.INSTANCE, RestoreParentWithAbortWindowAction.INSTANCE)
                  .perform(var2);
            }
         }

      }
   }
}
