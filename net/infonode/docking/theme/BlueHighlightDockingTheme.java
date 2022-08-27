package net.infonode.docking.theme;

import java.awt.Color;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.docking.properties.WindowTabProperties;
import net.infonode.gui.icon.button.CloseIcon;
import net.infonode.gui.icon.button.MinimizeIcon;
import net.infonode.gui.icon.button.RestoreIcon;
import net.infonode.tabbedpanel.theme.BlueHighlightTheme;

public final class BlueHighlightDockingTheme extends DockingWindowsTheme {
   private RootWindowProperties rootWindowProperties = createRootWindowProperties();

   public BlueHighlightDockingTheme() {
      super();
   }

   public String getName() {
      return "Blue Highlight Theme";
   }

   public RootWindowProperties getRootWindowProperties() {
      return this.rootWindowProperties;
   }

   public static final RootWindowProperties createRootWindowProperties() {
      BlueHighlightTheme var0 = new BlueHighlightTheme();
      BlueHighlightTheme var1 = new BlueHighlightTheme(new Color(150, 150, 150));
      RootWindowProperties var2 = new RootWindowProperties();
      var2.getTabWindowProperties().getTabbedPanelProperties().addSuperObject(var1.getTabbedPanelProperties());
      WindowTabProperties var3 = var2.getTabWindowProperties().getTabProperties();
      var3.getTitledTabProperties().addSuperObject(var1.getTitledTabProperties());
      var3.getFocusedProperties().addSuperObject(var0.getTitledTabProperties().getHighlightedProperties());
      var3.getHighlightedButtonProperties().getCloseButtonProperties().setIcon(new CloseIcon(Color.WHITE, 10));
      var3.getHighlightedButtonProperties().getMinimizeButtonProperties().setIcon(new MinimizeIcon(Color.WHITE, 10));
      var3.getHighlightedButtonProperties().getRestoreButtonProperties().setIcon(new RestoreIcon(Color.WHITE, 10));
      var2.getComponentProperties().setBackgroundColor(new Color(180, 190, 220));
      return var2;
   }
}
