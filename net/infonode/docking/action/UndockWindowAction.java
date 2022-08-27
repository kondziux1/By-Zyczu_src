package net.infonode.docking.action;

import java.awt.Point;
import java.io.ObjectStreamException;
import javax.swing.Icon;
import javax.swing.SwingUtilities;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.FloatingWindow;
import net.infonode.docking.util.DockingUtil;
import net.infonode.gui.icon.button.UndockIcon;

public class UndockWindowAction extends DockingWindowAction {
   private static final long serialVersionUID = 1L;
   public static final UndockWindowAction INSTANCE = new UndockWindowAction();
   private static final Icon icon = new UndockIcon(10);

   private UndockWindowAction() {
      super();
   }

   public Icon getIcon() {
      return icon;
   }

   public String getName() {
      return "Undock";
   }

   public boolean isPerformable(DockingWindow var1) {
      if (!var1.isUndockable()) {
         return false;
      } else {
         FloatingWindow var2 = DockingUtil.getFloatingWindowFor(var1);
         return var2 == null || var2.getChildWindowCount() > 0 && var2.getChildWindow(0) != var1 && var2.getChildWindow(0).getChildWindowCount() > 1;
      }
   }

   public void perform(DockingWindow var1) {
      if (this.isPerformable(var1)) {
         Point var2 = var1.getLocation();
         SwingUtilities.convertPointToScreen(var2, var1.getParent());
         var1.undock(var2);
      }

   }

   protected Object readResolve() throws ObjectStreamException {
      return INSTANCE;
   }
}
