package net.infonode.gui.draggable;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class DraggableComponentBox$4 extends ComponentAdapter {
   DraggableComponentBox$4(DraggableComponentBox var1) {
      super();
      this.this$0 = var1;
   }

   public void componentResized(ComponentEvent var1) {
   }

   public void componentMoved(ComponentEvent var1) {
      DraggableComponentBox.access$1000(this.this$0);
   }
}
