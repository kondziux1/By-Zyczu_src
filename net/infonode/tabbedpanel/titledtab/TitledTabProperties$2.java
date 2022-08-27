package net.infonode.tabbedpanel.titledtab;

import javax.swing.border.CompoundBorder;
import net.infonode.tabbedpanel.TabbedUIDefaults;
import net.infonode.tabbedpanel.border.TabAreaLineBorder;
import net.infonode.tabbedpanel.border.TabHighlightBorder;

class TitledTabProperties$2 implements Runnable {
   TitledTabProperties$2() {
      super();
   }

   public void run() {
      int var1 = TabbedUIDefaults.getIconTextGap();
      TitledTabProperties.access$100().getNormalProperties().getShapedPanelProperties().setOpaque(true);
      TitledTabProperties.access$100()
         .getNormalProperties()
         .setIconTextGap(var1)
         .setTextTitleComponentGap(var1)
         .setIconVisible(true)
         .setTextVisible(true)
         .setTitleComponentVisible(true)
         .getComponentProperties()
         .setFont(TabbedUIDefaults.getFont())
         .setForegroundColor(TabbedUIDefaults.getNormalStateForeground())
         .setBackgroundColor(TabbedUIDefaults.getNormalStateBackground())
         .setBorder(new TabAreaLineBorder())
         .setInsets(TabbedUIDefaults.getTabInsets());
      TitledTabProperties.access$100()
         .getHighlightedProperties()
         .getComponentProperties()
         .setBackgroundColor(TabbedUIDefaults.getHighlightedStateBackground())
         .setBorder(new CompoundBorder(new TabAreaLineBorder(), new TabHighlightBorder(TabbedUIDefaults.getHighlight(), true)));
      TitledTabProperties.access$100()
         .getDisabledProperties()
         .getComponentProperties()
         .setForegroundColor(TabbedUIDefaults.getDisabledForeground())
         .setBackgroundColor(TabbedUIDefaults.getDisabledBackground());
   }
}
