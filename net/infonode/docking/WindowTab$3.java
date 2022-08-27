package net.infonode.docking;

import java.util.Map;
import net.infonode.properties.propertymap.PropertyMapTreeListener;

class WindowTab$3 implements PropertyMapTreeListener {
   WindowTab$3(WindowTab var1) {
      super();
      this.this$0 = var1;
   }

   public void propertyValuesChanged(Map var1) {
      this.this$0.updateTabButtons(var1);
   }
}
