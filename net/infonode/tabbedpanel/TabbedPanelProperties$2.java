package net.infonode.tabbedpanel;

import javax.swing.border.CompoundBorder;
import net.infonode.gui.border.HighlightBorder;
import net.infonode.tabbedpanel.border.OpenContentBorder;
import net.infonode.tabbedpanel.border.TabAreaLineBorder;

class TabbedPanelProperties$2 implements Runnable {
   TabbedPanelProperties$2() {
      super();
   }

   public void run() {
      TabbedPanelProperties.access$100()
         .getContentPanelProperties()
         .getComponentProperties()
         .setBorder(new OpenContentBorder(TabbedUIDefaults.getDarkShadow(), TabbedUIDefaults.getHighlight()))
         .setInsets(TabbedUIDefaults.getContentAreaInsets())
         .setBackgroundColor(TabbedUIDefaults.getContentAreaBackground());
      TabbedPanelProperties.access$100().getContentPanelProperties().getShapedPanelProperties().setOpaque(true);
      TabbedPanelProperties.access$100()
         .getTabAreaComponentsProperties()
         .setStretchEnabled(false)
         .getComponentProperties()
         .setBorder(new CompoundBorder(new TabAreaLineBorder(TabbedUIDefaults.getDarkShadow()), new HighlightBorder(false, TabbedUIDefaults.getHighlight())))
         .setBackgroundColor(TabbedUIDefaults.getContentAreaBackground());
      TabbedPanelProperties.access$100().getTabAreaComponentsProperties().getShapedPanelProperties().setOpaque(true);
      TabbedPanelProperties.access$100().getTabAreaProperties().getShapedPanelProperties().setOpaque(false);
   }
}
