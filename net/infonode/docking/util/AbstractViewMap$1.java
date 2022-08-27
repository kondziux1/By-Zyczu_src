package net.infonode.docking.util;

import javax.swing.Icon;
import net.infonode.docking.View;

class AbstractViewMap$1 implements ViewFactory {
   AbstractViewMap$1(AbstractViewMap var1, View var2) {
      super();
      this.this$0 = var1;
      this.val$view = var2;
   }

   public Icon getIcon() {
      return this.val$view.getIcon();
   }

   public String getTitle() {
      return this.val$view.getTitle();
   }

   public View createView() {
      return this.val$view;
   }
}
