package net.infonode.docking.util;

import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.tabbedpanel.TabAreaVisiblePolicy;
import net.infonode.tabbedpanel.TabLayoutPolicy;
import net.infonode.util.Direction;

public class PropertiesUtil {
   private PropertiesUtil() {
      super();
   }

   public static RootWindowProperties createTitleBarStyleRootWindowProperties() {
      RootWindowProperties var0 = new RootWindowProperties();
      setupTitleBarStyleProperties(var0);
      return var0;
   }

   public static void setTitleBarStyle(RootWindowProperties var0) {
      setupTitleBarStyleProperties(var0);
   }

   private static void setupTitleBarStyleProperties(RootWindowProperties var0) {
      var0.getViewProperties().getViewTitleBarProperties().setVisible(true);
      var0.getTabWindowProperties().getTabbedPanelProperties().setTabAreaOrientation(Direction.DOWN).setTabLayoutPolicy(TabLayoutPolicy.SCROLLING);
      var0.getTabWindowProperties().getTabbedPanelProperties().getTabAreaProperties().setTabAreaVisiblePolicy(TabAreaVisiblePolicy.MORE_THAN_ONE_TAB);
      var0.getTabWindowProperties().getTabProperties().getHighlightedButtonProperties().getMinimizeButtonProperties().setVisible(false);
      var0.getTabWindowProperties().getTabProperties().getHighlightedButtonProperties().getRestoreButtonProperties().setVisible(false);
      var0.getTabWindowProperties().getTabProperties().getHighlightedButtonProperties().getCloseButtonProperties().setVisible(false);
      var0.getTabWindowProperties().getTabProperties().getHighlightedButtonProperties().getUndockButtonProperties().setVisible(false);
      var0.getTabWindowProperties().getTabProperties().getHighlightedButtonProperties().getDockButtonProperties().setVisible(false);
      var0.getTabWindowProperties().getCloseButtonProperties().setVisible(false);
      var0.getTabWindowProperties().getMaximizeButtonProperties().setVisible(false);
      var0.getTabWindowProperties().getMinimizeButtonProperties().setVisible(false);
      var0.getTabWindowProperties().getUndockButtonProperties().setVisible(false);
      var0.getTabWindowProperties().getDockButtonProperties().setVisible(false);
      var0.getTabWindowProperties().getRestoreButtonProperties().setVisible(false);
      var0.getWindowBarProperties()
         .getTabWindowProperties()
         .getTabProperties()
         .getHighlightedButtonProperties()
         .getMinimizeButtonProperties()
         .setVisible(true);
      var0.getWindowBarProperties().getTabWindowProperties().getTabProperties().getHighlightedButtonProperties().getRestoreButtonProperties().setVisible(true);
      var0.getWindowBarProperties().getTabWindowProperties().getTabProperties().getHighlightedButtonProperties().getCloseButtonProperties().setVisible(true);
      var0.getWindowBarProperties().getTabWindowProperties().getTabProperties().getHighlightedButtonProperties().getUndockButtonProperties().setVisible(true);
      var0.getWindowBarProperties().getTabWindowProperties().getTabProperties().getHighlightedButtonProperties().getDockButtonProperties().setVisible(true);
      var0.getWindowBarProperties().getTabWindowProperties().getTabbedPanelProperties().setTabLayoutPolicy(TabLayoutPolicy.SCROLLING);
   }
}
