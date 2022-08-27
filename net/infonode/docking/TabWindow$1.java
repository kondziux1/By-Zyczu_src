package net.infonode.docking;

import net.infonode.properties.base.Property;
import net.infonode.properties.util.PropertyChangeListener;

class TabWindow$1 implements PropertyChangeListener {
   TabWindow$1(TabWindow var1) {
      super();
      this.this$0 = var1;
   }

   public void propertyChanged(Property var1, Object var2, Object var3, Object var4) {
      this.this$0.revalidate();
   }
}
