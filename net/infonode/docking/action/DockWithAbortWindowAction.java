package net.infonode.docking.action;

import java.io.ObjectStreamException;
import javax.swing.Icon;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.OperationAbortedException;
import net.infonode.gui.icon.button.DockIcon;

public class DockWithAbortWindowAction extends DockingWindowAction {
   private static final long serialVersionUID = 1L;
   public static final DockWithAbortWindowAction INSTANCE = new DockWithAbortWindowAction();
   private static final Icon icon = new DockIcon(10);

   private DockWithAbortWindowAction() {
      super();
   }

   public Icon getIcon() {
      return icon;
   }

   public String getName() {
      return DockWindowAction.INSTANCE.getName();
   }

   public boolean isPerformable(DockingWindow var1) {
      return DockWindowAction.INSTANCE.isPerformable(var1);
   }

   public void perform(DockingWindow var1) {
      if (this.isPerformable(var1)) {
         try {
            var1.dockWithAbort();
         } catch (OperationAbortedException var3) {
         }
      }

   }

   protected Object readResolve() throws ObjectStreamException {
      return INSTANCE;
   }
}
