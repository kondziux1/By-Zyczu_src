package net.infonode.docking.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.infonode.docking.AbstractTabWindow;
import net.infonode.util.Direction;

class WindowMenuUtil$4 implements ActionListener {
   WindowMenuUtil$4(AbstractTabWindow var1, Direction var2) {
      super();
      this.val$tabWindow = var1;
      this.val$dir = var2;
   }

   public void actionPerformed(ActionEvent var1) {
      this.val$tabWindow.getTabWindowProperties().getTabProperties().getTitledTabProperties().getNormalProperties().setDirection(this.val$dir);
   }
}
