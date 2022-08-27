package net.infonode.docking.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.RootWindow;
import net.infonode.util.Direction;

class WindowMenuUtil$1 implements ActionListener {
   WindowMenuUtil$1(RootWindow var1, Direction var2, DockingWindow var3) {
      super();
      this.val$root = var1;
      this.val$dir = var2;
      this.val$window = var3;
   }

   public void actionPerformed(ActionEvent var1) {
      this.val$root.getWindowBar(this.val$dir).addTab(this.val$window);
   }
}
