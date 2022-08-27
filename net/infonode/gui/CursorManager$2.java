package net.infonode.gui;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JRootPane;

class CursorManager$2 extends ComponentAdapter {
   CursorManager$2(JRootPane var1) {
      super();
      this.val$root = var1;
   }

   public void componentResized(ComponentEvent var1) {
      ((CursorManager.RootCursorInfo)CursorManager.access$000().get(this.val$root)).getComponent().setSize(this.val$root.getSize());
   }
}
