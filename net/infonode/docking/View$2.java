package net.infonode.docking;

import net.infonode.properties.base.Property;
import net.infonode.properties.util.PropertyChangeListener;

class View$2 implements PropertyChangeListener {
   View$2(View var1) {
      super();
      this.this$0 = var1;
   }

   public void propertyChanged(Property var1, Object var2, Object var3, Object var4) {
      this.this$0.fireTitleChanged();
   }
}
