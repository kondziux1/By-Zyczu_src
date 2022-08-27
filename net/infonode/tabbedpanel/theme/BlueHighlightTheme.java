package net.infonode.tabbedpanel.theme;

import java.awt.Color;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import net.infonode.gui.border.HighlightBorder;
import net.infonode.tabbedpanel.TabbedPanelProperties;
import net.infonode.tabbedpanel.border.TabAreaLineBorder;
import net.infonode.tabbedpanel.titledtab.TitledTabProperties;

public class BlueHighlightTheme extends TabbedPanelTitledTabTheme {
   private TitledTabProperties tabProperties = new TitledTabProperties();
   private TabbedPanelProperties tabbedPanelProperties = new TabbedPanelProperties();

   public BlueHighlightTheme() {
      this(new Color(90, 120, 200));
   }

   public BlueHighlightTheme(Color var1) {
      super();
      this.tabProperties
         .getHighlightedProperties()
         .getComponentProperties()
         .setForegroundColor(Color.WHITE)
         .setBackgroundColor(var1)
         .setBorder(new TabAreaLineBorder(Color.BLACK));
      this.tabbedPanelProperties
         .getContentPanelProperties()
         .getComponentProperties()
         .setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new HighlightBorder()));
      this.tabbedPanelProperties.getTabAreaComponentsProperties().getComponentProperties().setBorder(new TabAreaLineBorder(Color.BLACK));
   }

   public String getName() {
      return "Blue Highlight Theme";
   }

   public TitledTabProperties getTitledTabProperties() {
      return this.tabProperties;
   }

   public TabbedPanelProperties getTabbedPanelProperties() {
      return this.tabbedPanelProperties;
   }
}
