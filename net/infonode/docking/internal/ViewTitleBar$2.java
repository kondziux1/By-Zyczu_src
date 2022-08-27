package net.infonode.docking.internal;

import java.util.Map;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapListener;

class ViewTitleBar$2 implements PropertyMapListener {
   ViewTitleBar$2(ViewTitleBar var1) {
      super();
      this.this$0 = var1;
   }

   public void propertyValuesChanged(PropertyMap var1, Map var2) {
      this.this$0.updateViewButtons(null);
   }
}
