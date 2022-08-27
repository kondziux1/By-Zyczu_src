package net.infonode.tabbedpanel;

public class TabStateChangedEvent extends TabEvent {
   private TabbedPanel tabbedPanel;
   private Tab previousTab;
   private Tab currentTab;

   public TabStateChangedEvent(Object var1, TabbedPanel var2, Tab var3, Tab var4, Tab var5) {
      super(var1, var3);
      this.tabbedPanel = var2;
      this.previousTab = var4;
      this.currentTab = var5;
   }

   public TabbedPanel getTabbedPanel() {
      return this.tabbedPanel;
   }

   public Tab getPreviousTab() {
      return this.previousTab;
   }

   public Tab getCurrentTab() {
      return this.currentTab;
   }
}
