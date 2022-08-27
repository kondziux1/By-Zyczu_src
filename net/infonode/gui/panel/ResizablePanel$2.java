package net.infonode.gui.panel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import net.infonode.util.Direction;

class ResizablePanel$2 extends MouseMotionAdapter {
   ResizablePanel$2(ResizablePanel var1) {
      super();
      this.this$0 = var1;
   }

   public void mouseMoved(MouseEvent var1) {
      ResizablePanel.access$900(this.this$0, var1.getPoint());
   }

   public void mouseDragged(MouseEvent var1) {
      if (ResizablePanel.access$000(this.this$0) != -1) {
         int var2 = ResizablePanel.access$800(this.this$0).isHorizontal()
            ? (
               ResizablePanel.access$800(this.this$0) == Direction.LEFT
                  ? this.this$0.getWidth() - var1.getPoint().x + ResizablePanel.access$000(this.this$0)
                  : var1.getPoint().x + ResizablePanel.access$000(this.this$0)
            )
            : (
               ResizablePanel.access$800(this.this$0) == Direction.UP
                  ? this.this$0.getHeight() - var1.getPoint().y + ResizablePanel.access$000(this.this$0)
                  : var1.getPoint().y + ResizablePanel.access$000(this.this$0)
            );
         ResizablePanel.access$1002(this.this$0, ResizablePanel.access$1100(this.this$0, var2));
         if (ResizablePanel.access$300(this.this$0)) {
            this.this$0.setPreferredSize(ResizablePanel.access$1000(this.this$0));
            this.this$0.revalidate();
         } else {
            ResizablePanel.access$600(this.this$0, var1);
         }
      }

   }
}
