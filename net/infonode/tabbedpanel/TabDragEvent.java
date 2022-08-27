package net.infonode.tabbedpanel;

import java.awt.Point;
import java.awt.event.MouseEvent;

public class TabDragEvent extends TabEvent {
   private MouseEvent mouseEvent;

   /** @deprecated */
   public TabDragEvent(Object var1, Tab var2, Point var3) {
      this(var1, new MouseEvent(var2, 506, System.currentTimeMillis(), 0, var3.x, var3.y, 0, false));
   }

   public TabDragEvent(Object var1, MouseEvent var2) {
      super(var1, (Tab)var2.getSource());
      this.mouseEvent = var2;
   }

   public Point getPoint() {
      return this.mouseEvent.getPoint();
   }

   public MouseEvent getMouseEvent() {
      return this.mouseEvent;
   }
}
