package net.infonode.gui.draggable;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

class DraggableComponent$1 implements KeyEventDispatcher {
   DraggableComponent$1(DraggableComponent var1) {
      super();
      this.this$0 = var1;
   }

   public boolean dispatchKeyEvent(KeyEvent var1) {
      if (DraggableComponent.access$000(this.this$0) && var1.getKeyCode() == DraggableComponent.access$100(this.this$0)) {
         if (var1.getID() == 401) {
            DraggableComponent.access$200(this.this$0, null);
         }

         return true;
      } else {
         return false;
      }
   }
}
