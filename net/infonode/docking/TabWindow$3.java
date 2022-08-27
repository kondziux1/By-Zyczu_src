package net.infonode.docking;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import net.infonode.docking.drag.DockingWindowDragger;
import net.infonode.docking.drag.DockingWindowDraggerProvider;

class TabWindow$3 implements DockingWindowDraggerProvider {
   TabWindow$3(TabWindow var1) {
      super();
      this.this$0 = var1;
   }

   public DockingWindowDragger getDragger(MouseEvent var1) {
      if (!this.this$0.getWindowProperties().getDragEnabled()) {
         return null;
      } else {
         Point var2 = SwingUtilities.convertPoint((Component)var1.getSource(), var1.getPoint(), this.this$0.getTabbedPanel());
         return this.this$0.getTabbedPanel().tabAreaContainsPoint(var2)
            ? ((DockingWindow)(this.this$0.getChildWindowCount() == 1 ? this.this$0.getChildWindow(0) : this.this$0)).startDrag(this.this$0.getRootWindow())
            : null;
      }
   }
}
