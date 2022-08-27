package net.infonode.gui.draggable;

import java.awt.event.MouseEvent;

public class DraggableComponentEvent {
   public static final int TYPE_UNDEFINED = -1;
   public static final int TYPE_MOVED = 0;
   public static final int TYPE_PRESSED = 1;
   public static final int TYPE_RELEASED = 2;
   public static final int TYPE_ENABLED = 3;
   public static final int TYPE_DISABLED = 4;
   private DraggableComponent source;
   private int type = -1;
   private MouseEvent mouseEvent;

   public DraggableComponentEvent(DraggableComponent var1) {
      this(var1, null);
   }

   public DraggableComponentEvent(DraggableComponent var1, MouseEvent var2) {
      this(var1, -1, var2);
   }

   public DraggableComponentEvent(DraggableComponent var1, int var2) {
      this(var1, var2, null);
   }

   public DraggableComponentEvent(DraggableComponent var1, int var2, MouseEvent var3) {
      super();
      this.source = var1;
      this.type = var2;
      this.mouseEvent = var3;
   }

   public DraggableComponent getSource() {
      return this.source;
   }

   public int getType() {
      return this.type;
   }

   public MouseEvent getMouseEvent() {
      return this.mouseEvent;
   }
}
