package net.infonode.docking.theme;

import java.awt.Color;
import java.awt.Insets;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.gui.colorprovider.ColorBlender;
import net.infonode.gui.colorprovider.ColorProvider;
import net.infonode.gui.componentpainter.SolidColorComponentPainter;
import net.infonode.gui.shaped.border.FixedInsetsShapedBorder;
import net.infonode.gui.shaped.border.ShapedBorder;
import net.infonode.tabbedpanel.TabAreaProperties;
import net.infonode.tabbedpanel.theme.SoftBlueIceTheme;

public class SoftBlueIceDockingTheme extends DockingWindowsTheme {
   private RootWindowProperties rootWindowProperties = new RootWindowProperties();
   private boolean slim;

   public SoftBlueIceDockingTheme() {
      this(false);
   }

   public SoftBlueIceDockingTheme(boolean var1) {
      this(SoftBlueIceTheme.DEFAULT_DARK_COLOR, SoftBlueIceTheme.DEFAULT_LIGHT_COLOR, 4, var1);
   }

   public SoftBlueIceDockingTheme(ColorProvider var1, ColorProvider var2, int var3, boolean var4) {
      super();
      SoftBlueIceTheme var5 = new SoftBlueIceTheme(var1, var2, var3);
      this.slim = var4;
      if (var4) {
         var5.getTabbedPanelProperties()
            .getTabAreaProperties()
            .getComponentProperties()
            .setBorder(
               new FixedInsetsShapedBorder(
                  new Insets(0, 0, 0, 0), (ShapedBorder)var5.getTabbedPanelProperties().getTabAreaProperties().getComponentProperties().getBorder()
               )
            );
         var5.getTabbedPanelProperties().getTabAreaProperties().getComponentProperties().setInsets(new Insets(0, 0, 0, 0));
         var5.getTabbedPanelProperties().setTabSpacing(-1);
         var5.getTabbedPanelProperties().getTabAreaComponentsProperties().getComponentProperties().setInsets(new Insets(2, 2, 2, 2));
      }

      this.rootWindowProperties.getWindowAreaProperties().setBorder(null).setInsets(new Insets(2, 2, 2, 2));
      this.rootWindowProperties.getWindowAreaShapedPanelProperties().setComponentPainter(new SolidColorComponentPainter(new ColorBlender(var1, var2, 0.5F)));
      this.rootWindowProperties.getTabWindowProperties().getTabbedPanelProperties().addSuperObject(var5.getTabbedPanelProperties());
      this.rootWindowProperties.getTabWindowProperties().getTabProperties().getTitledTabProperties().addSuperObject(var5.getTitledTabProperties());
      this.rootWindowProperties
         .getShapedPanelProperties()
         .setComponentPainter(var5.getTabbedPanelProperties().getTabAreaProperties().getShapedPanelProperties().getComponentPainter());
      this.rootWindowProperties
         .getTabWindowProperties()
         .getTabbedPanelProperties()
         .getContentPanelProperties()
         .getShapedPanelProperties()
         .setClipChildren(true);
      this.rootWindowProperties
         .getViewProperties()
         .getViewTitleBarProperties()
         .getNormalProperties()
         .getComponentProperties()
         .setForegroundColor(Color.BLACK)
         .setInsets(new Insets(0, 2, 0, 2));
      this.rootWindowProperties
         .getViewProperties()
         .getViewTitleBarProperties()
         .getNormalProperties()
         .getShapedPanelProperties()
         .setComponentPainter(null)
         .setOpaque(false);
      this.rootWindowProperties.getViewProperties().getViewTitleBarProperties().getFocusedProperties().getShapedPanelProperties().setComponentPainter(null);
      this.rootWindowProperties
         .getViewProperties()
         .getViewTitleBarProperties()
         .getFocusedProperties()
         .getComponentProperties()
         .setForegroundColor(Color.BLACK);
      TabAreaProperties var6 = this.rootWindowProperties.getWindowBarProperties().getTabWindowProperties().getTabbedPanelProperties().getTabAreaProperties();
      var6.getShapedPanelProperties().setComponentPainter(null);
      var6.getComponentProperties().setBorder(null);
      this.rootWindowProperties
         .getWindowBarProperties()
         .getTabWindowProperties()
         .getTabbedPanelProperties()
         .getTabAreaComponentsProperties()
         .getComponentProperties()
         .setBorder(null);
      this.rootWindowProperties
         .getWindowBarProperties()
         .getTabWindowProperties()
         .getTabbedPanelProperties()
         .getContentPanelProperties()
         .getShapedPanelProperties()
         .setOpaque(false);
   }

   public String getName() {
      return "Soft Blue Ice Theme" + (this.slim ? " - Slim" : "");
   }

   public RootWindowProperties getRootWindowProperties() {
      return this.rootWindowProperties;
   }
}
