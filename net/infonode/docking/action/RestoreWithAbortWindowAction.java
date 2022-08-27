package net.infonode.docking.action;

import java.io.ObjectStreamException;
import javax.swing.Icon;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.OperationAbortedException;
import net.infonode.gui.icon.button.RestoreIcon;

public class RestoreWithAbortWindowAction extends DockingWindowAction {
   private static final long serialVersionUID = 1L;
   public static final RestoreWithAbortWindowAction INSTANCE = new RestoreWithAbortWindowAction();
   private static final Icon icon = new RestoreIcon(10);

   private RestoreWithAbortWindowAction() {
      super();
   }

   public String getName() {
      return "Restore";
   }

   public boolean isPerformable(DockingWindow var1) {
      return var1 != null && (var1.isMinimized() || var1.isMaximized()) && var1.isRestorable();
   }

   public void perform(DockingWindow var1) {
      try {
         if (var1 != null && var1.isRestorable()) {
            var1.restoreWithAbort();
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
