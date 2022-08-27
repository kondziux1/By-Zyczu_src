package net.infonode.docking.action;

import java.io.ObjectStreamException;
import javax.swing.Icon;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.OperationAbortedException;

public final class CloseWithAbortWindowAction extends DockingWindowAction {
   private static final long serialVersionUID = 1L;
   public static final CloseWithAbortWindowAction INSTANCE = new CloseWithAbortWindowAction();

   private CloseWithAbortWindowAction() {
      super();
   }

   public String getName() {
      return CloseWindowAction.INSTANCE.getName();
   }

   public boolean isPerformable(DockingWindow var1) {
      return var1.isClosable();
   }

   public void perform(DockingWindow var1) {
      try {
         if (this.isPerformable(var1)) {
            var1.closeWithAbort();
         }
      } catch (OperationAbortedException var3) {
      }

   }

   public Icon getIcon() {
      return CloseWindowAction.INSTANCE.getIcon();
   }

   protected Object readResolve() throws ObjectStreamException {
      return INSTANCE;
   }
}
