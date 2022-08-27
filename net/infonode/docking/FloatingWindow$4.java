package net.infonode.docking;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.event.AWTEventListener;
import net.infonode.gui.ComponentUtil;

class FloatingWindow$4 implements AWTEventListener {
   FloatingWindow$4(FloatingWindow var1) {
      super();
      this.this$0 = var1;
   }

   public void eventDispatched(AWTEvent var1) {
      if (var1.getID() == 504) {
         Component var2 = (Component)var1.getSource();
         if (ComponentUtil.getTopLevelAncestor(var2) == FloatingWindow.access$200(this.this$0)) {
            this.this$0.getRootWindow().setCurrentDragRootPane(this.this$0.getRootPane());
         }
      }

   }
}
