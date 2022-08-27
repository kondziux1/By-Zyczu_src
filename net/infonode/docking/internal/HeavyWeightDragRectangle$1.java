package net.infonode.docking.internal;

import java.awt.Canvas;
import java.awt.Dimension;

class HeavyWeightDragRectangle$1 extends Canvas {
   HeavyWeightDragRectangle$1(HeavyWeightDragRectangle var1) {
      super();
      this.this$0 = var1;
   }

   public Dimension getPreferredSize() {
      return new Dimension(HeavyWeightDragRectangle.access$000(this.this$0), HeavyWeightDragRectangle.access$000(this.this$0));
   }
}
