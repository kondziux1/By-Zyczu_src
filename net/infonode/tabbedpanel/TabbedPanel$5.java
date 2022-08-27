package net.infonode.tabbedpanel;

import java.util.Map;
import net.infonode.properties.propertymap.PropertyMapTreeListener;

class TabbedPanel$5 implements PropertyMapTreeListener {
   TabbedPanel$5(TabbedPanel var1) {
      super();
      this.this$0 = var1;
   }

   public void propertyValuesChanged(Map var1) {
      TabbedPanel.access$1800(this.this$0, var1);
      TabbedPanel.access$1900(this.this$0, var1);
      TabbedPanel.access$2000(this.this$0, var1);
      TabbedPanel.access$2100(this.this$0, var1);
      TabbedPanel.access$2200(this.this$0);
      TabbedPanel.access$2300(this.this$0, true);
   }
}
