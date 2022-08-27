package net.infonode.docking.drag;

import net.infonode.gui.draggable.DraggableComponentAdapter;
import net.infonode.gui.draggable.DraggableComponentEvent;

class DockingWindowDragSource$1 extends DraggableComponentAdapter {
   DockingWindowDragSource$1(DockingWindowDragSource var1, DockingWindowDraggerProvider var2) {
      super();
      this.this$0 = var1;
      this.val$draggerProvider = var2;
   }

   public void dragAborted(DraggableComponentEvent var1) {
      this.this$0.abortDrag();
   }

   public void dragged(DraggableComponentEvent var1) {
      if (DockingWindowDragSource.access$000(this.this$0) == null) {
         DockingWindowDragSource.access$002(this.this$0, this.val$draggerProvider.getDragger(var1.getMouseEvent()));
         if (DockingWindowDragSource.access$000(this.this$0) == null) {
            DockingWindowDragSource.access$100(this.this$0).abortDrag();
            return;
         }

         DockingWindowDragSource.access$100(this.this$0)
            .setAbortDragKeyCode(DockingWindowDragSource.access$000(this.this$0).getDropTarget().getRootWindowProperties().getAbortDragKey());
      }

      DockingWindowDragSource.access$000(this.this$0).dragWindow(var1.getMouseEvent());
   }

   public void dropped(DraggableComponentEvent var1) {
      if (DockingWindowDragSource.access$000(this.this$0) != null) {
         DockingWindowDragSource.access$000(this.this$0).dropWindow(var1.getMouseEvent());
         DockingWindowDragSource.access$002(this.this$0, null);
      }

   }
}
