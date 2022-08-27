package net.infonode.tabbedpanel.theme;

import java.awt.Color;
import javax.swing.border.Border;
import net.infonode.properties.propertymap.PropertyMapManager;
import net.infonode.tabbedpanel.TabbedPanelProperties;
import net.infonode.tabbedpanel.theme.internal.laftheme.PaneUI;
import net.infonode.tabbedpanel.titledtab.TitledTabProperties;
import net.infonode.util.Direction;

public class LookAndFeelTheme extends TabbedPanelTitledTabTheme {
   private static TabbedPanelProperties tpProps = new TabbedPanelProperties();
   private static TitledTabProperties tabProps = new TitledTabProperties();
   private TabbedPanelProperties themeTpProps = new TabbedPanelProperties();
   private TitledTabProperties themeTabProps = new TitledTabProperties();
   private static int themeCounter = 0;
   private static PaneUI ui;
   private boolean disposed = false;

   public LookAndFeelTheme() {
      super();
      if (themeCounter == 0) {
         ui = new PaneUI(new LookAndFeelTheme$1(this));
         ui.init();
      }

      ++themeCounter;
      this.themeTpProps.addSuperObject(tpProps);
      this.themeTabProps.addSuperObject(tabProps);
   }

   public String getName() {
      return "Look and Feel Theme";
   }

   public TabbedPanelProperties getTabbedPanelProperties() {
      return this.themeTpProps;
   }

   public TitledTabProperties getTitledTabProperties() {
      return this.themeTabProps;
   }

   public void dispose() {
      if (!this.disposed) {
         this.disposed = true;
         --themeCounter;
         if (themeCounter == 0) {
            ui.dispose();
            PropertyMapManager.runBatch(new LookAndFeelTheme$2(this));
         }
      }

   }

   private void initTheme() {
      PropertyMapManager.runBatch(new LookAndFeelTheme$3(this));
   }

   private Border createTabInsetsBorder(boolean var1) {
      return new LookAndFeelTheme$10(this, var1);
   }

   public Color getBorderColor(Direction var1) {
      return ui.getContentTabAreaBorderColor(var1);
   }
}
