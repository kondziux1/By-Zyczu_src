package net.infonode.gui;

import java.awt.Component;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import javax.swing.SwingUtilities;

class ComponentPaintChecker$1 implements HierarchyListener {
   ComponentPaintChecker$1(ComponentPaintChecker var1, Component var2) {
      super();
      this.this$0 = var1;
      this.val$c = var2;
   }

   public void hierarchyChanged(HierarchyEvent var1) {
      if ((var1.getChangeFlags() & 4L) == 4L) {
         if (this.val$c.isDisplayable()) {
            SwingUtilities.invokeLater(new ComponentPaintChecker$2(this));
         } else {
            ComponentPaintChecker.access$102(this.this$0, false);
         }
      }

   }
}
