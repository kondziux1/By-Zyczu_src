package net.infonode.tabbedpanel;

import net.infonode.gui.draggable.DraggableComponentBoxAdapter;
import net.infonode.gui.draggable.DraggableComponentBoxEvent;

class TabbedPanelContentPanel$3 extends DraggableComponentBoxAdapter {
   TabbedPanelContentPanel$3(TabbedPanelContentPanel var1) {
      super();
      this.this$0 = var1;
   }

   public void changed(DraggableComponentBoxEvent var1) {
      if (var1.getDraggableComponent() == null || var1.getDraggableComponentEvent().getType() == -1) {
         TabbedPanelContentPanel.access$500(this.this$0);
      }

   }
}
