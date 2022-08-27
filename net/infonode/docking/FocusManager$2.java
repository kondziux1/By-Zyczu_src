package net.infonode.docking;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class FocusManager$2 implements PropertyChangeListener {
   FocusManager$2(FocusManager var1) {
      super();
      this.this$0 = var1;
   }

   public void propertyChange(PropertyChangeEvent var1) {
      if (FocusManager.access$200(this.this$0) <= 0) {
         FocusManager.access$208(this.this$0);

         try {
            this.triggerFocusUpdate();
         } finally {
            FocusManager.access$210(this.this$0);
         }

      }
   }

   private void triggerFocusUpdate() {
      if (!FocusManager.access$100(this.this$0)) {
         FocusManager.access$102(this.this$0, true);
         FocusManager.access$300(this.this$0).setRepeats(false);
         FocusManager.access$300(this.this$0).start();
      }
   }
}
