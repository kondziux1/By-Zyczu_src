package net.infonode.docking;

import java.awt.Component;
import net.infonode.docking.internal.HeavyWeightContainer;

class WindowBar$4 extends HeavyWeightContainer {
   WindowBar$4(WindowBar var1, Component var2) {
      super(var2);
      this.this$0 = var1;
   }

   public void setVisible(boolean var1) {
      if (this.this$0.getRootWindow() != null) {
         this.this$0.getRootWindow().paintImmediately(0, 0, this.this$0.getRootWindow().getWidth(), this.this$0.getRootWindow().getHeight());
      }

      super.setVisible(var1);
   }
}
