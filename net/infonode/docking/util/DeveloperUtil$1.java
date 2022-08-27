package net.infonode.docking.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextArea;
import net.infonode.docking.DockingWindow;

class DeveloperUtil$1 implements ActionListener {
   DeveloperUtil$1(JTextArea var1, DockingWindow var2) {
      super();
      this.val$layoutArea = var1;
      this.val$window = var2;
   }

   public void actionPerformed(ActionEvent var1) {
      this.val$layoutArea.setText(DeveloperUtil.getWindowLayoutAsString(this.val$window));
   }
}
