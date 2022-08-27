package net.infonode.gui.draggable;

public interface DraggableComponentBoxListener {
   void componentDragged(DraggableComponentBoxEvent var1);

   void componentDropped(DraggableComponentBoxEvent var1);

   void componentDragAborted(DraggableComponentBoxEvent var1);

   void componentSelected(DraggableComponentBoxEvent var1);

   void componentRemoved(DraggableComponentBoxEvent var1);

   void componentAdded(DraggableComponentBoxEvent var1);

   void changed(DraggableComponentBoxEvent var1);
}
