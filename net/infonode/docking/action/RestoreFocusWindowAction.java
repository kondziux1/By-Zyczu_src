package net.infonode.docking.action;

import java.io.ObjectStreamException;
import net.infonode.docking.DockingWindow;

public final class RestoreFocusWindowAction extends DockingWindowAction {
   private static final long serialVersionUID = 1L;
   public static final RestoreFocusWindowAction INSTANCE = new RestoreFocusWindowAction();

   private RestoreFocusWindowAction() {
      super();
   }

   public String getName() {
      return "Restore Focus";
   }

   public boolean isPerformable(DockingWindow var1) {
      return true;
   }

   public void perform(DockingWindow var1) {
      var1.restoreFocus();
   }

   protected Object readResolve() throws ObjectStreamException {
      return INSTANCE;
   }
}
