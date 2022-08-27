package net.infonode.tabbedpanel;

import net.infonode.gui.draggable.DraggableComponentBoxEvent;
import net.infonode.gui.draggable.DraggableComponentBoxListener;

class TabbedPanel$4 implements DraggableComponentBoxListener {
   private boolean selectedMoved;

   TabbedPanel$4(TabbedPanel var1) {
      super();
      this.this$0 = var1;
   }

   public void componentSelected(DraggableComponentBoxEvent var1) {
      if (var1.getDraggableComponent() == var1.getOldDraggableComponent()) {
         if (!this.selectedMoved && TabbedPanel.access$000(this.this$0).getTabDeselectable()) {
            TabbedPanel.access$500(this.this$0).selectDraggableComponent(null);
         }
      } else {
         Tab var2 = TabbedPanel.access$700(this.this$0, var1.getDraggableComponent());
         this.this$0.setHighlightedTab(var2);
         Tab var3 = TabbedPanel.access$700(this.this$0, var1.getOldDraggableComponent());
         TabbedPanel.access$800(this.this$0, var2, var3);
         if (TabbedPanel.access$900(this.this$0)) {
            TabbedPanel.access$902(this.this$0, false);
            if (var3 != null) {
               var3.setTabbedPanel(null);
            }
         }
      }

      TabbedPanel.access$400(this.this$0).repaint();
   }

   public void componentRemoved(DraggableComponentBoxEvent var1) {
      Tab var2 = TabbedPanel.access$700(this.this$0, var1.getDraggableComponent());
      if (TabbedPanel.access$1000(this.this$0) == var2) {
         TabbedPanel.access$1002(this.this$0, null);
      }

      TabbedPanel.access$100(this.this$0);
      TabbedPanel.access$1100(this.this$0);
      TabbedPanel.access$400(this.this$0).repaint();
      TabbedPanel.access$1200(this.this$0, var2);
   }

   public void componentAdded(DraggableComponentBoxEvent var1) {
      TabbedPanel.access$1100(this.this$0);
      TabbedPanel.access$400(this.this$0).repaint();
      TabbedPanel.access$1300(this.this$0, TabbedPanel.access$700(this.this$0, var1.getDraggableComponent()));
   }

   public void componentDragged(DraggableComponentBoxEvent var1) {
      TabbedPanel.access$1400(
         this.this$0, TabbedPanel.access$700(this.this$0, var1.getDraggableComponent()), var1.getDraggableComponentEvent().getMouseEvent()
      );
   }

   public void componentDropped(DraggableComponentBoxEvent var1) {
      if (!TabbedPanel.access$500(this.this$0).contains(var1.getDraggableComponentBoxPoint())) {
         this.this$0.setHighlightedTab(TabbedPanel.access$700(this.this$0, TabbedPanel.access$500(this.this$0).getSelectedDraggableComponent()));
      }

      TabbedPanel.access$1500(
         this.this$0, TabbedPanel.access$700(this.this$0, var1.getDraggableComponent()), var1.getDraggableComponentEvent().getMouseEvent()
      );
   }

   public void componentDragAborted(DraggableComponentBoxEvent var1) {
      TabbedPanel.access$1600(this.this$0, TabbedPanel.access$700(this.this$0, var1.getDraggableComponent()));
   }

   public void changed(DraggableComponentBoxEvent var1) {
      if (var1.getDraggableComponentEvent() != null) {
         int var2 = var1.getDraggableComponentEvent().getType();
         if (var2 == 1 && TabbedPanel.access$000(this.this$0).getHighlightPressedTab()) {
            if (TabbedPanel.access$1000(this.this$0) != null) {
               this.this$0.setHighlightedTab(TabbedPanel.access$700(this.this$0, var1.getDraggableComponent()));
            }
         } else if (var2 == 2) {
            this.selectedMoved = false;
            this.this$0.setHighlightedTab(this.this$0.getSelectedTab());
         } else if (var2 == 4
            && TabbedPanel.access$1000(this.this$0) != null
            && TabbedPanel.access$1000(this.this$0).getDraggableComponent() == var1.getDraggableComponent()) {
            this.this$0.setHighlightedTab(null);
         } else if (var2 == 3 && TabbedPanel.access$500(this.this$0).getSelectedDraggableComponent() == var1.getDraggableComponent()) {
            this.this$0.setHighlightedTab(TabbedPanel.access$700(this.this$0, var1.getDraggableComponent()));
         } else if (var2 == 0) {
            TabbedPanel.access$400(this.this$0).repaint();
            this.selectedMoved = var1.getDraggableComponent() == TabbedPanel.access$500(this.this$0).getSelectedDraggableComponent();
            TabbedPanel.access$1700(this.this$0, TabbedPanel.access$700(this.this$0, var1.getDraggableComponent()));
         }
      } else {
         TabbedPanel.access$400(this.this$0).repaint();
      }

      TabbedPanel.access$600(this.this$0);
   }
}
