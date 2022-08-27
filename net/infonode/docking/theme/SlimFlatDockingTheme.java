package net.infonode.docking.theme;

import java.awt.Font;
import java.awt.Insets;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.docking.properties.ViewTitleBarStateProperties;
import net.infonode.docking.properties.WindowBarProperties;
import net.infonode.docking.properties.WindowTabProperties;
import net.infonode.gui.icon.button.CloseIcon;
import net.infonode.gui.icon.button.DockIcon;
import net.infonode.gui.icon.button.MaximizeIcon;
import net.infonode.gui.icon.button.MinimizeIcon;
import net.infonode.gui.icon.button.RestoreIcon;
import net.infonode.gui.icon.button.UndockIcon;
import net.infonode.tabbedpanel.TabLayoutPolicy;
import net.infonode.tabbedpanel.TabbedPanelProperties;
import net.infonode.tabbedpanel.border.TabAreaLineBorder;
import net.infonode.tabbedpanel.theme.SmallFlatTheme;

public final class SlimFlatDockingTheme extends DockingWindowsTheme {
   private RootWindowProperties rootWindowProperties = createRootWindowProperties();

   public SlimFlatDockingTheme() {
      super();
   }

   public String getName() {
      return "Slim Flat Theme";
   }

   public RootWindowProperties getRootWindowProperties() {
      return this.rootWindowProperties;
   }

   public static final RootWindowProperties createRootWindowProperties() {
      SmallFlatTheme var0 = new SmallFlatTheme();
      RootWindowProperties var1 = new RootWindowProperties();
      var1.getWindowAreaProperties().setInsets(new Insets(0, 0, 0, 0)).setBorder(null);
      var1.getSplitWindowProperties().setDividerSize(3);
      TabbedPanelProperties var2 = var1.getTabWindowProperties().getTabbedPanelProperties();
      var2.addSuperObject(var0.getTabbedPanelProperties());
      var2.setShadowEnabled(false).setTabLayoutPolicy(TabLayoutPolicy.COMPRESSION);
      var2.getTabAreaComponentsProperties().getComponentProperties().setInsets(new Insets(0, 1, 0, 1));
      WindowTabProperties var3 = var1.getTabWindowProperties().getTabProperties();
      var3.getTitledTabProperties().addSuperObject(var0.getTitledTabProperties());
      Font var4 = var3.getTitledTabProperties().getHighlightedProperties().getComponentProperties().getFont();
      if (var4 != null) {
         var4 = var4.deriveFont(var3.getTitledTabProperties().getNormalProperties().getComponentProperties().getFont().getSize2D());
      }

      var3.getTitledTabProperties().getHighlightedProperties().getComponentProperties().setFont(var4);
      CloseIcon var5 = new CloseIcon(8);
      RestoreIcon var6 = new RestoreIcon(8);
      MinimizeIcon var7 = new MinimizeIcon(8);
      MaximizeIcon var8 = new MaximizeIcon(8);
      DockIcon var9 = new DockIcon(8);
      UndockIcon var10 = new UndockIcon(8);
      var3.getNormalButtonProperties().getCloseButtonProperties().setIcon(var5);
      var3.getNormalButtonProperties().getRestoreButtonProperties().setIcon(var6);
      var3.getNormalButtonProperties().getMinimizeButtonProperties().setIcon(var7);
      var3.getNormalButtonProperties().getDockButtonProperties().setIcon(var9);
      var3.getNormalButtonProperties().getUndockButtonProperties().setIcon(var10);
      var1.getTabWindowProperties().getCloseButtonProperties().setIcon(var5);
      var1.getTabWindowProperties().getRestoreButtonProperties().setIcon(var6);
      var1.getTabWindowProperties().getMinimizeButtonProperties().setIcon(var7);
      var1.getTabWindowProperties().getMaximizeButtonProperties().setIcon(var8);
      var1.getTabWindowProperties().getDockButtonProperties().setIcon(var9);
      var1.getTabWindowProperties().getUndockButtonProperties().setIcon(var10);
      ViewTitleBarStateProperties var11 = var1.getViewProperties().getViewTitleBarProperties().getNormalProperties();
      var11.getCloseButtonProperties().setIcon(var5);
      var11.getRestoreButtonProperties().setIcon(var6);
      var11.getMaximizeButtonProperties().setIcon(var8);
      var11.getMinimizeButtonProperties().setIcon(var7);
      var11.getDockButtonProperties().setIcon(var9);
      var11.getUndockButtonProperties().setIcon(var10);
      var1.getViewProperties()
         .getViewTitleBarProperties()
         .getNormalProperties()
         .getComponentProperties()
         .setFont(var3.getTitledTabProperties().getNormalProperties().getComponentProperties().getFont());
      setWindowBarProperties(var1.getWindowBarProperties());
      return var1;
   }

   private static void setWindowBarProperties(WindowBarProperties var0) {
      var0.setMinimumWidth(3);
      TabAreaLineBorder var1 = new TabAreaLineBorder(false, true, true, false);
      var0.getTabWindowProperties()
         .getTabProperties()
         .getTitledTabProperties()
         .getNormalProperties()
         .getComponentProperties()
         .setInsets(new Insets(0, 4, 0, 4))
         .setBorder(var1);
      var0.getTabWindowProperties().getTabProperties().getTitledTabProperties().getHighlightedProperties().getComponentProperties().setBorder(var1);
   }

   /** @deprecated */
   public static final WindowBarProperties createWindowBarProperties() {
      return new WindowBarProperties();
   }
}
