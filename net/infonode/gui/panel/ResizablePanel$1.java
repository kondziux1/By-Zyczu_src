package net.infonode.gui.panel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLayeredPane;
import net.infonode.util.Direction;

class ResizablePanel$1 extends MouseAdapter {
   ResizablePanel$1(ResizablePanel var1) {
      super();
      this.this$0 = var1;
   }

   public void mouseExited(MouseEvent var1) {
      if (ResizablePanel.access$000(this.this$0) == -1) {
         ResizablePanel.access$100(this.this$0);
      }

      ResizablePanel.access$202(this.this$0, false);
   }

   public void mouseEntered(MouseEvent var1) {
      ResizablePanel.access$202(this.this$0, true);
   }

   public void mousePressed(MouseEvent var1) {
      if (!ResizablePanel.access$300(this.this$0) && ResizablePanel.access$400(this.this$0) != null) {
         if (ResizablePanel.access$400(this.this$0) instanceof JLayeredPane) {
            ResizablePanel.access$400(this.this$0).add(ResizablePanel.access$500(this.this$0), JLayeredPane.DRAG_LAYER);
         } else {
            ResizablePanel.access$400(this.this$0).add(ResizablePanel.access$500(this.this$0), 0);
         }

         ResizablePanel.access$400(this.this$0).repaint();
         ResizablePanel.access$600(this.this$0, var1);
      }

      if (ResizablePanel.access$700(this.this$0)) {
         ResizablePanel.access$002(
            this.this$0,
            ResizablePanel.access$800(this.this$0) == Direction.LEFT
               ? var1.getPoint().x
               : (
                  ResizablePanel.access$800(this.this$0) == Direction.RIGHT
                     ? this.this$0.getWidth() - var1.getPoint().x
                     : (ResizablePanel.access$800(this.this$0) == Direction.UP ? var1.getPoint().y : this.this$0.getHeight() - var1.getPoint().y)
               )
         );
      }

   }

   public void mouseReleased(MouseEvent var1) {
      if (!ResizablePanel.access$300(this.this$0) && ResizablePanel.access$400(this.this$0) != null) {
         ResizablePanel.access$400(this.this$0).remove(ResizablePanel.access$500(this.this$0));
         ResizablePanel.access$400(this.this$0).repaint();
      }

      ResizablePanel.access$002(this.this$0, -1);
      ResizablePanel.access$900(this.this$0, var1.getPoint());
      if (!ResizablePanel.access$300(this.this$0) && ResizablePanel.access$1000(this.this$0) != null) {
         this.this$0.setPreferredSize(ResizablePanel.access$1000(this.this$0));
         this.this$0.revalidate();
      }

      ResizablePanel.access$1002(this.this$0, null);
   }
}
