package net.infonode.gui.hover.hoverable;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import net.infonode.gui.ComponentUtil;

class HoverManager$6 implements Runnable {
   HoverManager$6(HoverManager$5 var1) {
      super();
      this.this$1 = var1;
   }

   public void run() {
      Component var1 = ComponentUtil.findComponentUnderGlassPaneAt(HoverManager$5.access$500(this.this$1), HoverManager$5.access$600(this.this$1));
      if (var1 != null) {
         Point var2 = SwingUtilities.convertPoint(HoverManager$5.access$600(this.this$1), HoverManager$5.access$500(this.this$1), var1);
         HoverManager.access$400(HoverManager$5.access$700(this.this$1), new MouseEvent(var1, 504, 0L, 0, var2.x, var2.y, 0, false));
      }

   }
}
