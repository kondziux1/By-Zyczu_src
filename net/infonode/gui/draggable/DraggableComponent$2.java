package net.infonode.gui.draggable;

import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;

class DraggableComponent$2 extends MouseInputAdapter {
   DraggableComponent$2(DraggableComponent var1) {
      super();
      this.this$0 = var1;
   }

   public void mousePressed(MouseEvent var1) {
      DraggableComponent.access$300(this.this$0, var1);
   }

   public void mouseReleased(MouseEvent var1) {
      DraggableComponent.access$400(this.this$0, var1);
   }

   public void mouseDragged(MouseEvent var1) {
      DraggableComponent.access$500(this.this$0, var1);
   }
}
