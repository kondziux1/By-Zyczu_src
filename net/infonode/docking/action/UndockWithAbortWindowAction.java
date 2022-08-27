package net.infonode.docking.action;

import java.awt.Point;
import java.io.ObjectStreamException;
import javax.swing.Icon;
import javax.swing.SwingUtilities;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.OperationAbortedException;
import net.infonode.gui.icon.button.UndockIcon;

public class UndockWithAbortWindowAction extends DockingWindowAction {
   private static final long serialVersionUID = 1L;
   public static final UndockWithAbortWindowAction INSTANCE = new UndockWithAbortWindowAction();
   private static final Icon icon = new UndockIcon(10);

   private UndockWithAbortWindowAction() {
      super();
   }

   public Icon getIcon() {
      return icon;
   }

   public String getName() {
      return UndockWindowAction.INSTANCE.getName();
   }

   public boolean isPerformable(DockingWindow var1) {
      return UndockWindowAction.INSTANCE.isPerformable(var1);
   }

   public void perform(DockingWindow var1) {
      if (this.isPerformable(var1)) {
         Point var2 = var1.getLocation();
         SwingUtilities.convertPointToScreen(var2, var1.getParent());

         try {
            var1.undockWithAbort(var2);
         } catch (OperationAbortedException var4) {
         }
      }

   }

   protected Object readResolve() throws ObjectStreamException {
      return INSTANCE;
   }
}
