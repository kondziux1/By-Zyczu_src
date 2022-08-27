package net.infonode.docking;

import java.util.Map;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapListener;

class DockingWindow$1 implements PropertyMapListener {
   DockingWindow$1(DockingWindow var1) {
      super();
      this.this$0 = var1;
   }

   public void propertyValuesChanged(PropertyMap var1, Map var2) {
      DockingWindow.access$000(this.this$0);
      this.this$0.updateButtonVisibility();
   }
}
