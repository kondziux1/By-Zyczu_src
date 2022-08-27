package net.infonode.docking.action;

import java.io.ObjectStreamException;
import javax.swing.Icon;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.OperationAbortedException;
import net.infonode.gui.icon.button.MinimizeIcon;

public final class MinimizeWithAbortWindowAction extends DockingWindowAction {
   private static final long serialVersionUID = 1L;
   public static final MinimizeWithAbortWindowAction INSTANCE = new MinimizeWithAbortWindowAction();
   private static final Icon icon = new MinimizeIcon(10);

   private MinimizeWithAbortWindowAction() {
      super();
   }

   public String getName() {
      return "Minimize";
   }

   public Icon getIcon() {
      return icon;
   }

   public boolean isPerformable(DockingWindow var1) {
      return var1 != null && !var1.isMinimized() && var1.isMinimizable();
   }

   public void perform(DockingWindow var1) {
      try {
         if (this.isPerformable(var1)) {
            var1.minimizeWithAbort();
         }
      } catch (OperationAbortedException var3) {
      }

   }

   protected Object readResolve() throws ObjectStreamException {
      return INSTANCE;
   }
}
