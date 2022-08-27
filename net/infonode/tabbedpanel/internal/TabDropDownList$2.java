package net.infonode.tabbedpanel.internal;

import net.infonode.gui.PopupList;
import net.infonode.gui.PopupListListener;
import net.infonode.tabbedpanel.Tab;
import net.infonode.tabbedpanel.TabbedPanel;

class TabDropDownList$2 implements PopupListListener {
   TabDropDownList$2(TabDropDownList var1, TabbedPanel var2) {
      super();
      this.this$0 = var1;
      this.val$tabbedPanel = var2;
   }

   public void willBecomeVisible(PopupList var1) {
      int var2 = this.val$tabbedPanel.getTabCount();
      Tab[] var3 = new Tab[var2];

      for(int var4 = 0; var4 < var2; ++var4) {
         var3[var4] = this.val$tabbedPanel.getTabAt(var4);
      }

      TabDropDownList.access$000(this.this$0).calculateMaximumIconWidth(var3);
      this.this$0.getList().setListData(var3);
      this.this$0.getList().setSelectedValue(this.val$tabbedPanel.getSelectedTab(), true);
   }
}
