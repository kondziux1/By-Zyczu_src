package net.infonode.gui.draggable;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class DraggableComponent$3 extends ComponentAdapter {
   DraggableComponent$3(DraggableComponent var1) {
      super();
      this.this$0 = var1;
   }

   public void componentResized(ComponentEvent var1) {
      DraggableComponent.access$600(this.this$0, -1);
   }
}
