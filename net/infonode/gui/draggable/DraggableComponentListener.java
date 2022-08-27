package net.infonode.gui.draggable;

public interface DraggableComponentListener {
   void changed(DraggableComponentEvent var1);

   void selected(DraggableComponentEvent var1);

   void dragged(DraggableComponentEvent var1);

   void dropped(DraggableComponentEvent var1);

   void dragAborted(DraggableComponentEvent var1);
}
