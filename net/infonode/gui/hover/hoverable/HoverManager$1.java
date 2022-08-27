package net.infonode.gui.hover.hoverable;

import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import javax.swing.SwingUtilities;

class HoverManager$1 implements HierarchyListener {
   HoverManager$1(HoverManager var1) {
      super();
      this.this$0 = var1;
   }

   public void hierarchyChanged(HierarchyEvent var1) {
      SwingUtilities.invokeLater(new HoverManager$2(this, var1));
   }
}
