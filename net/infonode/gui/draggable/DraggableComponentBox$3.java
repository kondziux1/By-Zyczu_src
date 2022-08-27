package net.infonode.gui.draggable;

import java.awt.LayoutManager;
import net.infonode.gui.panel.SimplePanel;

class DraggableComponentBox$3 extends SimplePanel {
   DraggableComponentBox$3(DraggableComponentBox var1, LayoutManager var2) {
      super(var2);
      this.this$0 = var1;
   }

   public boolean isOptimizedDrawingEnabled() {
      return this.this$0 != null && this.this$0.getComponentSpacing() >= 0;
   }
}
