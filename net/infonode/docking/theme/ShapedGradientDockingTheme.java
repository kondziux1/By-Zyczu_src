package net.infonode.docking.theme;

import java.awt.Insets;
import javax.swing.border.Border;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.gui.colorprovider.ColorBlender;
import net.infonode.gui.colorprovider.ColorMultiplier;
import net.infonode.gui.colorprovider.ColorProvider;
import net.infonode.gui.colorprovider.UIManagerColorProvider;
import net.infonode.gui.componentpainter.GradientComponentPainter;
import net.infonode.gui.componentpainter.SolidColorComponentPainter;
import net.infonode.gui.shaped.border.RoundedCornerBorder;
import net.infonode.tabbedpanel.TabbedPanelProperties;
import net.infonode.tabbedpanel.theme.ShapedGradientTheme;
import net.infonode.tabbedpanel.titledtab.TitledTabBorderSizePolicy;
import net.infonode.tabbedpanel.titledtab.TitledTabProperties;

public class ShapedGradientDockingTheme extends DockingWindowsTheme {
   private RootWindowProperties rootWindowProperties = new RootWindowProperties();
   private String name;

   public ShapedGradientDockingTheme() {
      this(0.0F, 0.5F);
   }

   public ShapedGradientDockingTheme(float var1, float var2) {
      this(var1, var2, UIManagerColorProvider.TABBED_PANE_DARK_SHADOW, UIManagerColorProvider.TABBED_PANE_HIGHLIGHT, true);
   }

   public ShapedGradientDockingTheme(float var1, float var2, ColorProvider var3, ColorProvider var4, boolean var5) {
      this(var1, var2, 25, var3, var4, var5);
   }

   public ShapedGradientDockingTheme(float var1, float var2, int var3, ColorProvider var4, ColorProvider var5, boolean var6) {
      super();
      ShapedGradientTheme var7 = new ShapedGradientTheme(var1, var2, var3, var4, var5);
      this.name = var7.getName();
      byte var8 = 3;
      TabbedPanelProperties var9 = var7.getTabbedPanelProperties();
      TitledTabProperties var10 = var7.getTitledTabProperties();
      this.rootWindowProperties.getTabWindowProperties().getTabbedPanelProperties().addSuperObject(var9);
      this.rootWindowProperties.getTabWindowProperties().getTabProperties().getTitledTabProperties().addSuperObject(var10);
      if (var6) {
         this.rootWindowProperties
            .getTabWindowProperties()
            .getTabProperties()
            .getFocusedProperties()
            .getShapedPanelProperties()
            .setComponentPainter(
               this.rootWindowProperties
                  .getTabWindowProperties()
                  .getTabProperties()
                  .getTitledTabProperties()
                  .getHighlightedProperties()
                  .getShapedPanelProperties()
                  .getComponentPainter()
            );
         ColorMultiplier var11 = new ColorMultiplier(var7.getControlColor(), 0.85F);
         this.rootWindowProperties
            .getTabWindowProperties()
            .getTabProperties()
            .getTitledTabProperties()
            .getHighlightedProperties()
            .getShapedPanelProperties()
            .setComponentPainter(new GradientComponentPainter(var11, var7.getControlColor(), var7.getControlColor(), var7.getControlColor()));
         this.rootWindowProperties
            .getTabWindowProperties()
            .getTabbedPanelProperties()
            .getTabAreaComponentsProperties()
            .getShapedPanelProperties()
            .setComponentPainter(
               new GradientComponentPainter(new ColorMultiplier(var7.getControlColor(), 1.1F), var7.getControlColor(), var7.getControlColor(), var11)
            );
      }

      Border var17 = var7.createTabBorder(var7.getLineColor(), var7.getHighlightColor(), 0.0F, 0.0F, true, true, true, true, false, true, 0);
      ShapedGradientDockingTheme$1 var14 = new ShapedGradientDockingTheme$1(
         this, var7.getLineColor(), null, var8, var8, var8, var8, true, true, true, true, var7
      );
      TitledTabProperties var15 = this.rootWindowProperties.getWindowBarProperties().getTabWindowProperties().getTabProperties().getTitledTabProperties();
      var15.getNormalProperties().getComponentProperties().setBorder(var14).setInsets(new Insets(1, 0, 1, 3));
      var15.getHighlightedProperties().getComponentProperties().setBorder(var17);
      var15.setHighlightedRaised(0).setBorderSizePolicy(TitledTabBorderSizePolicy.EQUAL_SIZE);
      this.rootWindowProperties
         .getWindowBarProperties()
         .getTabWindowProperties()
         .getTabbedPanelProperties()
         .setTabSpacing(-8)
         .getTabAreaComponentsProperties()
         .getComponentProperties()
         .setBorder(new RoundedCornerBorder(var7.getLineColor(), var7.getHighlightColor(), var8, var8, var8, var8, true, true, true, true))
         .setInsets(new Insets(0, 3, 0, 3));
      this.rootWindowProperties.setDragRectangleBorderWidth(3).getWindowBarProperties().getComponentProperties().setInsets(new Insets(2, 0, 2, 0));
      this.rootWindowProperties.getWindowAreaProperties().setBorder(null).setInsets(new Insets(2, 2, 2, 2));
      this.rootWindowProperties.getComponentProperties().setBackgroundColor(null);
      this.rootWindowProperties
         .getShapedPanelProperties()
         .setComponentPainter(
            new SolidColorComponentPainter(new ColorBlender(UIManagerColorProvider.TABBED_PANE_BACKGROUND, UIManagerColorProvider.CONTROL_COLOR, 0.5F))
         );
      this.rootWindowProperties.getWindowAreaShapedPanelProperties().setComponentPainter(new SolidColorComponentPainter(UIManagerColorProvider.CONTROL_COLOR));
      Insets var16 = this.rootWindowProperties
         .getTabWindowProperties()
         .getTabbedPanelProperties()
         .getContentPanelProperties()
         .getComponentProperties()
         .getInsets();
      if (var5 == null) {
         this.rootWindowProperties
            .getTabWindowProperties()
            .getTabbedPanelProperties()
            .getContentPanelProperties()
            .getComponentProperties()
            .setInsets(new Insets(var16.top, var16.top, var16.top, var16.top));
      }

   }

   public String getName() {
      return this.name;
   }

   public RootWindowProperties getRootWindowProperties() {
      return this.rootWindowProperties;
   }
}
