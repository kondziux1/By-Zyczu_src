package net.infonode.docking.theme;

import java.awt.Color;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.docking.theme.internal.laftheme.TitleBarUI;
import net.infonode.properties.gui.util.ComponentProperties;
import net.infonode.properties.propertymap.PropertyMapManager;
import net.infonode.tabbedpanel.theme.LookAndFeelTheme;

public class LookAndFeelDockingTheme extends DockingWindowsTheme {
   private static LookAndFeelTheme tpTheme;
   private static RootWindowProperties rootProps = new RootWindowProperties();
   private RootWindowProperties themeRootProps = new RootWindowProperties();
   private static TitleBarUI titleBarUI;
   private static int themeCounter = 0;
   private boolean disposed = false;

   public LookAndFeelDockingTheme() {
      super();
      if (themeCounter == 0) {
         tpTheme = new LookAndFeelTheme();
         titleBarUI = new TitleBarUI(new LookAndFeelDockingTheme$1(this), false);
         titleBarUI.init();
         this.initTheme(true);
      }

      ++themeCounter;
      this.themeRootProps.addSuperObject(rootProps);
   }

   public String getName() {
      return "Look and Feel Theme";
   }

   public RootWindowProperties getRootWindowProperties() {
      return this.themeRootProps;
   }

   public void dispose() {
      if (!this.disposed) {
         this.disposed = true;
         --themeCounter;
         if (themeCounter == 0) {
            titleBarUI.dispose();
            PropertyMapManager.runBatch(new LookAndFeelDockingTheme$2(this));
            tpTheme.dispose();
         }
      }

   }

   private void initTheme(boolean var1) {
      PropertyMapManager.runBatch(new LookAndFeelDockingTheme$3(this, var1));
   }

   private void updateBackgroundColor(ComponentProperties var1, Color var2) {
      if (var2 == null) {
         var1.getMap().removeValue(ComponentProperties.BACKGROUND_COLOR);
      } else {
         var1.setBackgroundColor(var2);
      }

   }
}
