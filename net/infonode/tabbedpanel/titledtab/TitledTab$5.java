package net.infonode.tabbedpanel.titledtab;

import net.infonode.properties.base.Property;
import net.infonode.properties.util.PropertyChangeListener;
import net.infonode.util.Direction;

class TitledTab$5 implements PropertyChangeListener {
   TitledTab$5(TitledTab var1) {
      super();
      this.this$0 = var1;
   }

   public void propertyChanged(Property var1, Object var2, Object var3, Object var4) {
      TitledTab.access$700(this.this$0, (Direction)var4);
   }
}
