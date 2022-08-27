package net.infonode.gui;

import java.awt.Dimension;

class ContentTitleBar$1 extends RotatableLabel {
   ContentTitleBar$1(ContentTitleBar var1, String var2) {
      super(var2);
      this.this$0 = var1;
   }

   public Dimension getMinimumSize() {
      Dimension var1 = super.getMinimumSize();
      return this.getDirection().isHorizontal() ? new Dimension(0, var1.height) : new Dimension(var1.width, 0);
   }
}
