package net.infonode.gui.draggable;

import java.awt.Point;

public class DraggableComponentBoxEvent {
   private DraggableComponentBox source;
   private DraggableComponent draggableComponent;
   private DraggableComponent oldDraggableComponent;
   private DraggableComponentEvent draggableComponentEvent;
   private Point draggableComponentBoxPoint;

   public DraggableComponentBoxEvent(DraggableComponentBox var1) {
      this(var1, null);
   }

   public DraggableComponentBoxEvent(DraggableComponentBox var1, DraggableComponent var2) {
      this(var1, var2, null, null);
   }

   public DraggableComponentBoxEvent(DraggableComponentBox var1, DraggableComponent var2, DraggableComponentEvent var3) {
      this(var1, var2, var3, null);
   }

   public DraggableComponentBoxEvent(DraggableComponentBox var1, DraggableComponent var2, DraggableComponentEvent var3, Point var4) {
      super();
      this.source = var1;
      this.draggableComponent = var2;
      this.draggableComponentEvent = var3;
      this.draggableComponentBoxPoint = var4;
   }

   public DraggableComponentBoxEvent(DraggableComponentBox var1, DraggableComponent var2, DraggableComponent var3) {
      this(var1, var2);
      this.oldDraggableComponent = var3;
   }

   public DraggableComponentBox getSource() {
      return this.source;
   }

   public DraggableComponent getDraggableComponent() {
      return this.draggableComponent;
   }

   public DraggableComponent getOldDraggableComponent() {
      return this.oldDraggableComponent;
   }

   public Point getDraggableComponentBoxPoint() {
      return this.draggableComponentBoxPoint;
   }

   public DraggableComponentEvent getDraggableComponentEvent() {
      return this.draggableComponentEvent;
   }
}
