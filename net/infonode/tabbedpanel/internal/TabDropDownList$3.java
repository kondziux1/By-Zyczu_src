package net.infonode.tabbedpanel.internal;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.infonode.tabbedpanel.Tab;
import net.infonode.tabbedpanel.TabbedPanel;

class TabDropDownList$3 implements ListSelectionListener {
   TabDropDownList$3(TabDropDownList var1, TabbedPanel var2) {
      super();
      this.this$0 = var1;
      this.val$tabbedPanel = var2;
   }

   public void valueChanged(ListSelectionEvent var1) {
      if (!var1.getValueIsAdjusting()) {
         this.val$tabbedPanel.setSelectedTab((Tab)this.this$0.getList().getSelectedValue());
      }

   }
}
