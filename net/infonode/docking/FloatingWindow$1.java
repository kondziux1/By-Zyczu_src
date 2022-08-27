package net.infonode.docking;

import java.util.Map;
import net.infonode.properties.propertymap.PropertyMapTreeListener;

class FloatingWindow$1 implements PropertyMapTreeListener {
   FloatingWindow$1(FloatingWindow var1) {
      super();
      this.this$0 = var1;
   }

   public void propertyValuesChanged(Map var1) {
      FloatingWindow.access$000(this.this$0, var1);
   }
}
