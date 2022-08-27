package net.infonode.docking.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.infonode.docking.AbstractTabWindow;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.RootWindow;
import net.infonode.docking.View;

class WindowMenuUtil$2 implements ActionListener {
   WindowMenuUtil$2(ViewFactory var1, DockingWindow var2) {
      super();
      this.val$vf = var1;
      this.val$window = var2;
   }

   public void actionPerformed(ActionEvent var1) {
      View var2 = this.val$vf.createView();
      if (var2.getRootWindow() != this.val$window.getRootWindow()) {
         var2.restore();
         if (var2.getRootWindow() != this.val$window.getRootWindow()) {
            if (this.val$window instanceof RootWindow) {
               ((RootWindow)this.val$window).setWindow(var2);
            } else {
               AbstractTabWindow var3 = WindowMenuUtil.access$000(this.val$window);
               if (var3 != null) {
                  var3.addTab(var2);
               }
            }

         }
      }
   }
}
