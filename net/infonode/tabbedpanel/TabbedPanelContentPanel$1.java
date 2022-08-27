package net.infonode.tabbedpanel;

import java.util.Map;
import net.infonode.gui.hover.HoverListener;
import net.infonode.properties.propertymap.PropertyMapTreeListener;
import net.infonode.util.Direction;
import net.infonode.util.ValueChange;

class TabbedPanelContentPanel$1 implements PropertyMapTreeListener {
   TabbedPanelContentPanel$1(TabbedPanelContentPanel var1) {
      super();
      this.this$0 = var1;
   }

   public void propertyValuesChanged(Map var1) {
      Map var2 = (Map)var1.get(TabbedPanelContentPanel.access$000(this.this$0).getProperties().getContentPanelProperties().getMap());
      if (var2 != null && var2.keySet().contains(TabbedPanelContentPanelProperties.HOVER_LISTENER)) {
         TabbedPanelContentPanel.access$100(this.this$0)
            .setHoverListener((HoverListener)((ValueChange)var2.get(TabbedPanelContentPanelProperties.HOVER_LISTENER)).getNewValue());
      }

      var2 = (Map)var1.get(TabbedPanelContentPanel.access$000(this.this$0).getProperties().getContentPanelProperties().getComponentProperties().getMap());
      if (var2 != null) {
         TabbedPanelContentPanel.access$200(this.this$0);
      }

      var2 = (Map)var1.get(TabbedPanelContentPanel.access$000(this.this$0).getProperties().getContentPanelProperties().getShapedPanelProperties().getMap());
      if (var2 != null) {
         TabbedPanelContentPanel.access$200(this.this$0);
      }

      var2 = (Map)var1.get(TabbedPanelContentPanel.access$000(this.this$0).getProperties().getMap());
      if (var2 != null && var2.keySet().contains(TabbedPanelProperties.TAB_AREA_ORIENTATION)) {
         TabbedPanelContentPanel.access$100(this.this$0)
            .setDirection(((Direction)((ValueChange)var2.get(TabbedPanelProperties.TAB_AREA_ORIENTATION)).getNewValue()).getNextCW());
      }

   }
}
