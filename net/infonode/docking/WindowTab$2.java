package net.infonode.docking;

import java.util.Map;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapListener;

class WindowTab$2 implements PropertyMapListener {
   WindowTab$2(WindowTab var1) {
      super();
      this.this$0 = var1;
   }

   public void propertyValuesChanged(PropertyMap var1, Map var2) {
      this.this$0.updateTabButtons(null);
   }
}
