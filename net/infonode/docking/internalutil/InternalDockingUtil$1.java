package net.infonode.docking.internalutil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.infonode.docking.DockingWindow;
import net.infonode.util.Printer;

class InternalDockingUtil$1 implements ActionListener {
   InternalDockingUtil$1(DockingWindow var1) {
      super();
      this.val$window = var1;
   }

   public void actionPerformed(ActionEvent var1) {
      InternalDockingUtil.dump(this.val$window, new Printer());
   }
}
