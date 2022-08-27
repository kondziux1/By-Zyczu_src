package net.infonode.docking;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class RootWindow$7 implements PropertyChangeListener {
   RootWindow$7(RootWindow var1) {
      super();
      this.this$0 = var1;
   }

   public void propertyChange(PropertyChangeEvent var1) {
      this.this$0.updateButtonVisibility();
   }
}
