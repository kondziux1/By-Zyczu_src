package net.infonode.gui;

import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import javax.swing.JComponent;

class ScrollableBox$3 implements HierarchyListener {
   ScrollableBox$3(ScrollableBox var1, JComponent var2) {
      super();
      this.this$0 = var1;
      this.val$scrollingContainer = var2;
   }

   public void hierarchyChanged(HierarchyEvent var1) {
      if (this.val$scrollingContainer.getParent() != this.this$0) {
         this.val$scrollingContainer.removeHierarchyListener(this);
         this.val$scrollingContainer.removeMouseWheelListener(ScrollableBox.access$400(this.this$0));
      }

   }
}
