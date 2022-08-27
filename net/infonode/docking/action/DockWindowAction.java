package net.infonode.docking.action;

import java.io.ObjectStreamException;
import javax.swing.Icon;
import net.infonode.docking.DockingWindow;
import net.infonode.gui.icon.button.DockIcon;

public class DockWindowAction extends DockingWindowAction {
   private static final long serialVersionUID = 1L;
   public static final DockWindowAction INSTANCE = new DockWindowAction();
   private static final Icon icon = new DockIcon(10);

   private DockWindowAction() {
      super();
   }

   public Icon getIcon() {
      return icon;
   }

   public String getName() {
      return "Dock";
   }

   public boolean isPerformable(DockingWindow var1) {
      return var1.isDockable() && var1.isUndocked();
   }

   public void perform(DockingWindow var1) {
      if (this.isPerformable(var1)) {
         var1.dock();
      }

   }

   protected Object readResolve() throws ObjectStreamException {
      return INSTANCE;
   }
}
