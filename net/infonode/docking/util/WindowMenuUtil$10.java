package net.infonode.docking.util;

import javax.swing.JPopupMenu;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.RootWindow;
import net.infonode.docking.WindowBar;
import net.infonode.docking.WindowPopupMenuFactory;
import net.infonode.gui.menu.MenuUtil;

class WindowMenuUtil$10 implements WindowPopupMenuFactory {
   WindowMenuUtil$10(boolean var1, boolean var2, ViewFactoryManager var3) {
      super();
      this.val$addTabItems = var1;
      this.val$addSplitWindowItems = var2;
      this.val$viewFactoryManager = var3;
   }

   public JPopupMenu createPopupMenu(DockingWindow var1) {
      JPopupMenu var2 = new JPopupMenu(var1.getTitle());
      if (!(var1 instanceof RootWindow)) {
         if (!(var1 instanceof WindowBar)) {
            WindowMenuUtil.access$100(var2, var1);
            var2.addSeparator();
         }

         if (this.val$addTabItems) {
            WindowMenuUtil.access$200(var2, var1);
            WindowMenuUtil.access$300(var2, var1);
            var2.addSeparator();
         }

         if (this.val$addSplitWindowItems) {
            WindowMenuUtil.access$400(var2, var1);
            var2.addSeparator();
         }
      }

      WindowMenuUtil.access$500(var2, var1, this.val$viewFactoryManager);
      MenuUtil.optimizeSeparators(var2);
      MenuUtil.align(var2);
      return var2;
   }
}
