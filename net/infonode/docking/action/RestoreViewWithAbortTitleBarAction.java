package net.infonode.docking.action;

import java.io.ObjectStreamException;
import javax.swing.Icon;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.OperationAbortedException;
import net.infonode.gui.icon.button.RestoreIcon;

public class RestoreViewWithAbortTitleBarAction extends DockingWindowAction {
   private static final long serialVersionUID = 1L;
   public static final RestoreViewWithAbortTitleBarAction INSTANCE = new RestoreViewWithAbortTitleBarAction();
   private static final Icon icon = new RestoreIcon(10);

   private RestoreViewWithAbortTitleBarAction() {
      super();
   }

   public String getName() {
      return "Restore";
   }

   public boolean isPerformable(DockingWindow var1) {
      return var1 != null
         && (
            var1.isMinimized()
               || var1.isMaximized()
               || var1.getWindowParent() != null && var1.getWindowParent().isMaximized() && var1.getWindowParent().isRestorable()
         )
         && var1.isRestorable();
   }

   public void perform(DockingWindow var1) {
      try {
         if (var1 != null && var1.isRestorable()) {
            if (var1.isMaximized() || var1.isMinimized()) {
               var1.restoreWithAbort();
            } else if (var1.getWindowParent() != null) {
               var1.getWindowParent().restoreWithAbort();
            }
         }
      } catch (OperationAbortedException var3) {
      }

   }

   public Icon getIcon() {
      return icon;
   }

   protected Object readResolve() throws ObjectStreamException {
      return INSTANCE;
   }
}
