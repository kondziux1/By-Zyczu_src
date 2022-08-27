package net.infonode.docking.internalutil;

import java.awt.event.MouseEvent;
import net.infonode.docking.DockingWindow;

public abstract class DropAction {
   public abstract void execute(DockingWindow var1, MouseEvent var2);

   protected DropAction() {
      super();
   }

   public boolean showTitle() {
      return true;
   }

   public void clear(DockingWindow var1, DropAction var2) {
   }
}
