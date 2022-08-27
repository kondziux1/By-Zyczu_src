package net.infonode.docking.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.SplitWindow;

class WindowMenuUtil$9 implements ActionListener {
   WindowMenuUtil$9(DockingWindow var1) {
      super();
      this.val$window = var1;
   }

   public void actionPerformed(ActionEvent var1) {
      SplitWindow var2 = (SplitWindow)this.val$window;
      var2.setWindows(this.val$window.getChildWindow(1), this.val$window.getChildWindow(0));
      var2.setDividerLocation(1.0F - var2.getDividerLocation());
   }
}
