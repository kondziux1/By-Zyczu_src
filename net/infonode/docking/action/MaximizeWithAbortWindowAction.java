package net.infonode.docking.action;

import java.io.ObjectStreamException;
import javax.swing.Icon;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.OperationAbortedException;
import net.infonode.docking.TabWindow;
import net.infonode.docking.util.DockingUtil;
import net.infonode.gui.icon.button.MaximizeIcon;

public final class MaximizeWithAbortWindowAction extends DockingWindowAction {
   private static final long serialVersionUID = 1L;
   public static final MaximizeWithAbortWindowAction INSTANCE = new MaximizeWithAbortWindowAction();
   private static final Icon icon = new MaximizeIcon(10);

   private MaximizeWithAbortWindowAction() {
      super();
   }

   public Icon getIcon() {
      return icon;
   }

   public String getName() {
      return "Maximize";
   }

   public boolean isPerformable(DockingWindow var1) {
      if (!var1.isMaximizable()) {
         return false;
      } else {
         TabWindow var2 = DockingUtil.getTabWindowFor(var1);
         return var2 != null && !var2.isMaximized() && var2.isMaximizable();
      }
   }

   public void perform(DockingWindow var1) {
      try {
         TabWindow var2 = DockingUtil.getTabWindowFor(var1);
         if (var2 != null && !var2.isMaximized() && var2.isMaximizable()) {
            var2.maximizeWithAbort();
         }
      } catch (OperationAbortedException var3) {
      }

   }

   protected Object readResolve() throws ObjectStreamException {
      return INSTANCE;
   }
}
