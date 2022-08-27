package net.infonode.tabbedpanel.theme;

import net.infonode.tabbedpanel.TabbedPanelProperties;
import net.infonode.tabbedpanel.titledtab.TitledTabProperties;

public abstract class TabbedPanelTitledTabTheme {
   public TabbedPanelTitledTabTheme() {
      super();
   }

   public abstract String getName();

   public abstract TabbedPanelProperties getTabbedPanelProperties();

   public abstract TitledTabProperties getTitledTabProperties();
}
