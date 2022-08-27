package net.infonode.tabbedpanel.theme;

import java.awt.Font;
import java.awt.Insets;
import net.infonode.tabbedpanel.TabbedPanelProperties;
import net.infonode.tabbedpanel.border.OpenContentBorder;
import net.infonode.tabbedpanel.border.TabAreaLineBorder;
import net.infonode.tabbedpanel.titledtab.TitledTabProperties;

public class SmallFlatTheme extends TabbedPanelTitledTabTheme {
   private TitledTabProperties tabProperties = new TitledTabProperties();
   private TabbedPanelProperties tabbedPanelProperties = new TabbedPanelProperties();

   public SmallFlatTheme() {
      super();
      TitledTabProperties var1 = TitledTabProperties.getDefaultProperties();
      TabAreaLineBorder var2 = new TabAreaLineBorder();
      Font var3 = var1.getNormalProperties().getComponentProperties().getFont();
      if (var3 != null) {
         var3 = var3.deriveFont(9.0F);
      }

      this.tabProperties.getNormalProperties().getComponentProperties().setBorder(var2).setFont(var3).setInsets(new Insets(0, 4, 0, 4));
      this.tabProperties.getHighlightedProperties().getComponentProperties().setBorder(var2);
      this.tabProperties.setHighlightedRaised(0);
      this.tabbedPanelProperties.getContentPanelProperties().getComponentProperties().setBorder(new OpenContentBorder());
      this.tabbedPanelProperties.getTabAreaComponentsProperties().getComponentProperties().setBorder(new TabAreaLineBorder());
   }

   public String getName() {
      return "Small Flat Theme";
   }

   public TitledTabProperties getTitledTabProperties() {
      return this.tabProperties;
   }

   public TabbedPanelProperties getTabbedPanelProperties() {
      return this.tabbedPanelProperties;
   }
}
