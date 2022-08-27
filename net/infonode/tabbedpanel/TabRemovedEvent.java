package net.infonode.tabbedpanel;

public class TabRemovedEvent extends TabEvent {
   private TabbedPanel tabbedPanel;

   public TabRemovedEvent(Object var1, Tab var2, TabbedPanel var3) {
      super(var1, var2);
      this.tabbedPanel = var3;
   }

   public TabbedPanel getTabbedPanel() {
      return this.tabbedPanel;
   }
}
