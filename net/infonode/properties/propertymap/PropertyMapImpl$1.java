package net.infonode.properties.propertymap;

import net.infonode.util.collection.map.base.ConstMap;
import net.infonode.util.signal.Signal;
import net.infonode.util.signal.SignalListener;

class PropertyMapImpl$1 implements SignalListener {
   PropertyMapImpl$1(PropertyMapImpl var1) {
      super();
      this.this$0 = var1;
   }

   public void signalEmitted(Signal var1, Object var2) {
      PropertyMapManager.getInstance().addMapChanges(this.this$0, (ConstMap)var2);
   }
}
