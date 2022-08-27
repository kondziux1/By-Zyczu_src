package net.infonode.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class DynamicUIManager$3 implements PropertyChangeListener {
   DynamicUIManager$3(DynamicUIManager var1) {
      super();
      this.this$0 = var1;
   }

   public void propertyChange(PropertyChangeEvent var1) {
      if (!(var1.getNewValue() instanceof Class)) {
         DynamicUIManager.access$000(this.this$0);
      }

   }
}
