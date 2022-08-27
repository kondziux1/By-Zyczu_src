package net.infonode.docking.action;

import java.io.ObjectStreamException;
import javax.swing.Icon;
import net.infonode.docking.DockingWindow;
import net.infonode.gui.icon.button.CloseIcon;

public final class CloseWindowAction extends DockingWindowAction {
   private static final long serialVersionUID = 1L;
   public static final CloseWindowAction INSTANCE = new CloseWindowAction();
   private static final Icon icon = new CloseIcon(10);

   private CloseWindowAction() {
      super();
   }

   public Icon getIcon() {
      return icon;
   }

   public String getName() {
      return "Close";
   }

   public boolean isPerformable(DockingWindow var1) {
      return var1.isClosable();
   }

   public void perform(DockingWindow var1) {
      if (this.isPerformable(var1)) {
         var1.close();
      }

   }

   protected Object readResolve() throws ObjectStreamException {
      return INSTANCE;
   }
}
