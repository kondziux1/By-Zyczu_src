package net.infonode.docking.action;

import java.io.ObjectStreamException;
import javax.swing.Icon;
import net.infonode.docking.AbstractTabWindow;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.OperationAbortedException;
import net.infonode.gui.icon.button.CloseIcon;

public class CloseOthersWindowAction extends DockingWindowAction {
   private static final long serialVersionUID = 1L;
   public static final CloseOthersWindowAction INSTANCE = new CloseOthersWindowAction();
   private static final Icon icon = new CloseIcon(10);

   private CloseOthersWindowAction() {
      super();
   }

   public Icon getIcon() {
      return icon;
   }

   public String getName() {
      return "Close Others";
   }

   public boolean isPerformable(DockingWindow var1) {
      return var1.getWindowParent() instanceof AbstractTabWindow;
   }

   public void perform(DockingWindow var1) {
      if (this.isPerformable(var1)) {
         AbstractTabWindow var2 = (AbstractTabWindow)var1.getWindowParent();
         int var3 = 0;

         while(var3 < var2.getChildWindowCount()) {
            if (var2.getChildWindow(var3) != var1 && var2.getChildWindow(var3).isClosable()) {
               try {
                  var2.getChildWindow(var3).closeWithAbort();
               } catch (OperationAbortedException var5) {
                  ++var3;
               }
            } else {
               ++var3;
            }
         }
      }

   }

   protected Object readResolve() throws ObjectStreamException {
      return INSTANCE;
   }
}
