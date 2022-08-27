package net.infonode.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class DynamicUIManager$2 implements PropertyChangeListener {
   DynamicUIManager$2(DynamicUIManager var1, PropertyChangeListener var2) {
      super();
      this.this$0 = var1;
      this.val$l = var2;
   }

   public void propertyChange(PropertyChangeEvent var1) {
      if (var1.getPropertyName().equals("lookAndFeel")) {
         DynamicUIManager.access$100(this.this$0, this.val$l);
         DynamicUIManager.access$200(this.this$0);
         DynamicUIManager.access$300(this.this$0);
      }

   }
}
