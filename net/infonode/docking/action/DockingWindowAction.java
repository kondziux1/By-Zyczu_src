package net.infonode.docking.action;

import java.io.Serializable;
import javax.swing.Icon;
import net.infonode.docking.DockingWindow;
import net.infonode.gui.action.SimpleAction;
import net.infonode.gui.icon.IconProvider;

public abstract class DockingWindowAction implements Serializable, IconProvider {
   private static final long serialVersionUID = 1L;

   public DockingWindowAction() {
      super();
   }

   public abstract String getName();

   public abstract void perform(DockingWindow var1);

   public abstract boolean isPerformable(DockingWindow var1);

   public SimpleAction getAction(DockingWindow var1) {
      return new DockingWindowAction$1(this, var1);
   }

   public Icon getIcon() {
      return null;
   }

   public String toString() {
      return this.getName();
   }
}
