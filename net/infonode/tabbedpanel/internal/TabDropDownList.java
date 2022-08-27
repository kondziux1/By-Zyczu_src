package net.infonode.tabbedpanel.internal;

import javax.swing.AbstractButton;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import net.infonode.gui.PopupList;
import net.infonode.gui.TextIconListCellRenderer;
import net.infonode.tabbedpanel.TabDropDownListVisiblePolicy;
import net.infonode.tabbedpanel.TabListener;
import net.infonode.tabbedpanel.TabbedPanel;

public class TabDropDownList extends PopupList {
   private TabbedPanel tabbedPanel;
   private TextIconListCellRenderer cellRenderer;
   private TabListener tabListener = new TabDropDownList$1(this);

   public TabDropDownList(TabbedPanel var1, AbstractButton var2) {
      super(var2);
      this.tabbedPanel = var1;
      this.addPopupListListener(new TabDropDownList$2(this, var1));
      this.addListSelectionListener(new TabDropDownList$3(this, var1));
      if (var1.getProperties().getTabDropDownListVisiblePolicy() == TabDropDownListVisiblePolicy.MORE_THAN_ONE_TAB) {
         var1.addTabListener(this.tabListener);
         this.setVisible(var1.getTabCount() > 1);
      }

      this.cellRenderer = new TextIconListCellRenderer(this.getList().getCellRenderer());
      this.getList().setCellRenderer(this.cellRenderer);
      this.setOpaque(false);
   }

   public void dispose() {
      this.tabbedPanel.removeTabListener(this.tabListener);
   }

   public void updateUI() {
      super.updateUI();
      if (this.cellRenderer != null) {
         Object var1 = (ListCellRenderer)UIManager.get("List.cellRenderer");
         if (var1 == null) {
            var1 = new DefaultListCellRenderer();
         }

         this.cellRenderer.setRenderer((ListCellRenderer)var1);
      }

   }
}
