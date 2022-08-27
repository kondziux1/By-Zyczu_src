package net.infonode.docking.drag;

import javax.swing.JComponent;
import net.infonode.gui.draggable.DraggableComponent;

public class DockingWindowDragSource {
   private final DraggableComponent draggableComponent;
   private DockingWindowDragger dragger;

   public DockingWindowDragSource(JComponent var1, DockingWindowDraggerProvider var2) {
      super();
      this.draggableComponent = new DraggableComponent(var1);
      this.draggableComponent.setReorderEnabled(false);
      this.draggableComponent.setEnableInsideDrag(true);
      this.draggableComponent.addListener(new DockingWindowDragSource$1(this, var2));
   }

   public void abortDrag() {
      if (this.dragger != null) {
         this.dragger.abortDrag();
      }

      this.dragger = null;
   }
}
