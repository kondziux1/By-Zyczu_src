package net.infonode.docking;

import javax.swing.SwingUtilities;
import net.infonode.tabbedpanel.TabAdapter;
import net.infonode.tabbedpanel.TabEvent;
import net.infonode.tabbedpanel.TabRemovedEvent;
import net.infonode.tabbedpanel.TabStateChangedEvent;

class AbstractTabWindow$5 extends TabAdapter {
   AbstractTabWindow$5(AbstractTabWindow var1) {
      super();
      this.this$0 = var1;
   }

   public void tabAdded(TabEvent var1) {
      SwingUtilities.invokeLater(new AbstractTabWindow$6(this));
   }

   public void tabRemoved(TabRemovedEvent var1) {
      SwingUtilities.invokeLater(new AbstractTabWindow$7(this));
   }

   public void tabSelected(TabStateChangedEvent var1) {
      this.this$0.tabSelected((WindowTab)var1.getTab());
      DockingWindow var2 = this.this$0.getSelectedWindow();
      if (!this.this$0.getIgnoreSelected() && var2 != null) {
         var2.fireWindowShown(var2);
      }

   }

   public void tabDeselected(TabStateChangedEvent var1) {
      WindowTab var2 = (WindowTab)var1.getTab();
      if (var2 != null && !this.this$0.getIgnoreSelected()) {
         var2.getWindow().fireWindowHidden(var2.getWindow());
      }

   }

   public void tabMoved(TabEvent var1) {
      if (!this.this$0.getIgnoreSelected()) {
         this.this$0.fireTitleChanged();
      }

   }
}
