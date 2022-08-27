package net.infonode.docking.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.SplitWindow;

class WindowMenuUtil$7 implements ActionListener {
   WindowMenuUtil$7(DockingWindow var1) {
      super();
      this.val$window = var1;
   }

   public void actionPerformed(ActionEvent var1) {
      ((SplitWindow)this.val$window).setDividerLocation(0.75F);
   }
}
