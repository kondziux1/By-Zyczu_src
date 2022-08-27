package net.infonode.docking;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class WindowBar$3 extends ComponentAdapter {
   WindowBar$3(WindowBar var1) {
      super();
      this.this$0 = var1;
   }

   public void componentResized(ComponentEvent var1) {
      if (WindowBar.access$100(this.this$0).getParent() != null) {
         WindowBar.access$100(this.this$0).getParent().repaint();
      }

   }
}
