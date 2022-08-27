package net.infonode.tabbedpanel;

class TabContentPanel$1 extends TabAdapter {
   TabContentPanel$1(TabContentPanel var1) {
      super();
      this.this$0 = var1;
   }

   public void tabSelected(TabStateChangedEvent var1) {
      TabContentPanel.access$000(this.this$0).showComponent(var1.getTab() == null ? null : var1.getTab().getContentComponent());
   }

   public void tabRemoved(TabRemovedEvent var1) {
      if (var1.getTab().getContentComponent() != null) {
         this.this$0.remove(var1.getTab().getContentComponent());
      }

   }

   public void tabAdded(TabEvent var1) {
      if (var1.getTab().getContentComponent() != null) {
         this.this$0.add(var1.getTab().getContentComponent());
      }

   }
}
