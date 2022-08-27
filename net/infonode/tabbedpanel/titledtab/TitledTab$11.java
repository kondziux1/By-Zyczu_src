package net.infonode.tabbedpanel.titledtab;

import net.infonode.properties.propertymap.PropertyMapWeakListenerManager;
import net.infonode.tabbedpanel.TabAdapter;
import net.infonode.tabbedpanel.TabEvent;
import net.infonode.tabbedpanel.TabRemovedEvent;
import net.infonode.tabbedpanel.TabbedPanelProperties;

class TitledTab$11 extends TabAdapter {
   TitledTab$11(TitledTab var1) {
      super();
      this.this$0 = var1;
   }

   public void tabAdded(TabEvent var1) {
      PropertyMapWeakListenerManager.addWeakPropertyChangeListener(
         this.this$0.getTabbedPanel().getProperties().getMap(), TabbedPanelProperties.TAB_AREA_ORIENTATION, TitledTab.access$1100(this.this$0)
      );
      TitledTab.access$700(this.this$0, this.this$0.getTabbedPanel().getProperties().getTabAreaOrientation());
   }

   public void tabRemoved(TabRemovedEvent var1) {
      PropertyMapWeakListenerManager.removeWeakPropertyChangeListener(
         var1.getTabbedPanel().getProperties().getMap(), TabbedPanelProperties.TAB_AREA_ORIENTATION, TitledTab.access$1100(this.this$0)
      );
   }
}
