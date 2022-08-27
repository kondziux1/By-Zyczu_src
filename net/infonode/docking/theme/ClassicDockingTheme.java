package net.infonode.docking.theme;

import java.awt.Insets;
import javax.swing.border.CompoundBorder;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.gui.border.EdgeBorder;
import net.infonode.gui.colorprovider.UIManagerColorProvider;
import net.infonode.tabbedpanel.TabSelectTrigger;
import net.infonode.tabbedpanel.TabbedPanelProperties;
import net.infonode.tabbedpanel.theme.ClassicTheme;
import net.infonode.tabbedpanel.titledtab.TitledTabProperties;
import net.infonode.util.Direction;

public class ClassicDockingTheme extends DockingWindowsTheme {
   private RootWindowProperties rootWindowProperties = new RootWindowProperties();

   public ClassicDockingTheme() {
      super();
      ClassicTheme var1 = new ClassicTheme();
      TabbedPanelProperties var2 = var1.getTabbedPanelProperties();
      TitledTabProperties var3 = var1.getTitledTabProperties();
      ClassicDockingTheme$1 var4 = new ClassicDockingTheme$1(this);
      this.rootWindowProperties
         .getTabWindowProperties()
         .getTabbedPanelProperties()
         .setTabSelectTrigger(TabSelectTrigger.MOUSE_PRESS)
         .addSuperObject(var2)
         .getTabAreaComponentsProperties()
         .getComponentProperties()
         .setInsets(new Insets(0, 0, 0, 0));
      this.rootWindowProperties.getTabWindowProperties().getTabProperties().getTitledTabProperties().addSuperObject(var3);
      this.rootWindowProperties.getWindowBarProperties().getTabWindowProperties().getTabbedPanelProperties().addSuperObject(var2);
      this.rootWindowProperties.getWindowBarProperties().getTabWindowProperties().getTabProperties().getTitledTabProperties().addSuperObject(var3);
      this.rootWindowProperties
         .getWindowBarProperties()
         .getTabWindowProperties()
         .getTabbedPanelProperties()
         .getTabAreaComponentsProperties()
         .getComponentProperties()
         .setBorder(new CompoundBorder(var4, var1.createTabBorder(true, false, true)));
      this.rootWindowProperties.getWindowBarProperties().getComponentProperties().setInsets(new Insets(0, 0, 2, 0));
      this.rootWindowProperties
         .getWindowBarProperties()
         .getTabWindowProperties()
         .getTabProperties()
         .getTitledTabProperties()
         .getNormalProperties()
         .getComponentProperties()
         .setBorder(var1.createInsetsTabBorder(true, false, true));
      this.rootWindowProperties
         .getWindowAreaProperties()
         .setBackgroundColor(null)
         .setInsets(new Insets(0, 0, 0, 0))
         .setBorder(new EdgeBorder(UIManagerColorProvider.TABBED_PANE_DARK_SHADOW, UIManagerColorProvider.TABBED_PANE_HIGHLIGHT, true, true, true, true));
      this.rootWindowProperties.setDragRectangleBorderWidth(3);
      this.rootWindowProperties.getViewProperties().getViewTitleBarProperties().getNormalProperties().getShapedPanelProperties().setDirection(Direction.DOWN);
   }

   public String getName() {
      return "Classic Theme";
   }

   public RootWindowProperties getRootWindowProperties() {
      return this.rootWindowProperties;
   }
}
