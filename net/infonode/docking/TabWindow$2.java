package net.infonode.docking;

import java.util.Map;
import net.infonode.properties.propertymap.PropertyMapTreeListener;

class TabWindow$2 implements PropertyMapTreeListener {
   TabWindow$2(TabWindow var1) {
      super();
      this.this$0 = var1;
   }

   public void propertyValuesChanged(Map var1) {
      TabWindow.access$000(this.this$0, var1);
   }
}
