package net.infonode.docking;

import java.awt.event.MouseEvent;
import net.infonode.docking.drag.DockingWindowDragger;
import net.infonode.docking.drag.DockingWindowDraggerProvider;

class View$6 implements DockingWindowDraggerProvider {
   View$6(View var1) {
      super();
      this.this$0 = var1;
   }

   public DockingWindowDragger getDragger(MouseEvent var1) {
      return this.this$0.getWindowProperties().getDragEnabled() ? this.this$0.startDrag(this.this$0.getRootWindow()) : null;
   }
}
