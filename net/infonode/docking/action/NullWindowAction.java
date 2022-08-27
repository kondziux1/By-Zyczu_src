package net.infonode.docking.action;

import java.io.ObjectStreamException;
import net.infonode.docking.DockingWindow;

public class NullWindowAction extends DockingWindowAction {
   private static final long serialVersionUID = 1L;
   public static final NullWindowAction INSTANCE = new NullWindowAction();

   private NullWindowAction() {
      super();
   }

   public String getName() {
      return "Null";
   }

   public boolean isPerformable(DockingWindow var1) {
      return true;
   }

   public void perform(DockingWindow var1) {
   }

   protected Object readResolve() throws ObjectStreamException {
      return INSTANCE;
   }
}
