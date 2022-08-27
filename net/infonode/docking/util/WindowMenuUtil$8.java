package net.infonode.docking.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.SplitWindow;

class WindowMenuUtil$8 implements ActionListener {
   WindowMenuUtil$8(DockingWindow var1) {
      super();
      this.val$window = var1;
   }

   public void actionPerformed(ActionEvent var1) {
      ((SplitWindow)this.val$window).setHorizontal(!((SplitWindow)this.val$window).isHorizontal());
   }
}
