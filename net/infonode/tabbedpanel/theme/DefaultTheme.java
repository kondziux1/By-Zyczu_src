package net.infonode.tabbedpanel.theme;

import net.infonode.tabbedpanel.TabbedPanelProperties;
import net.infonode.tabbedpanel.titledtab.TitledTabProperties;

public class DefaultTheme extends TabbedPanelTitledTabTheme {
   private TitledTabProperties tabProperties = new TitledTabProperties();
   private TabbedPanelProperties tabbedPanelProperties = new TabbedPanelProperties();

   public DefaultTheme() {
      super();
   }

   public String getName() {
      return "Default Theme";
   }

   public TabbedPanelProperties getTabbedPanelProperties() {
      return this.tabbedPanelProperties;
   }

   public TitledTabProperties getTitledTabProperties() {
      return this.tabProperties;
   }
}
