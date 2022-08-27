package net.infonode.docking.theme;

import java.awt.Color;
import java.awt.Insets;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.docking.properties.WindowBarProperties;
import net.infonode.gui.colorprovider.ColorMultiplier;
import net.infonode.gui.colorprovider.UIManagerColorProvider;
import net.infonode.gui.componentpainter.GradientComponentPainter;
import net.infonode.properties.gui.util.ComponentProperties;
import net.infonode.tabbedpanel.border.OpenContentBorder;
import net.infonode.tabbedpanel.border.TabAreaLineBorder;
import net.infonode.tabbedpanel.theme.GradientTheme;
import net.infonode.tabbedpanel.titledtab.TitledTabProperties;

public class GradientDockingTheme extends DockingWindowsTheme {
   private boolean opaqueTabArea;
   private boolean shadowEnabled;
   private boolean highlightedBold;
   private boolean focusHighlighterEnabled;
   private Color borderColor;
   private Color tabAreaBackgroundColor;
   private RootWindowProperties rootProperties;

   public GradientDockingTheme() {
      this(true, true, false, true);
   }

   public GradientDockingTheme(boolean var1, boolean var2, boolean var3, boolean var4) {
      this(var1, var2, var3, var4, Color.BLACK);
   }

   public GradientDockingTheme(boolean var1, boolean var2, boolean var3, boolean var4, Color var5) {
      this(var1, var2, var3, var4, var5, GradientTheme.DEFAULT_TAB_AREA_BACKGROUND_COLOR);
   }

   public GradientDockingTheme(boolean var1, boolean var2, boolean var3, boolean var4, Color var5, Color var6) {
      super();
      this.opaqueTabArea = var1;
      this.shadowEnabled = var2;
      this.highlightedBold = var3;
      this.focusHighlighterEnabled = var4;
      this.borderColor = var5;
      this.tabAreaBackgroundColor = var6;
      GradientTheme var7 = new GradientTheme(var1, var2, var5);
      this.rootProperties = new RootWindowProperties();
      this.createRootWindowProperties(var7);
      this.createWindowBarProperties(var7);
   }

   private void createRootWindowProperties(GradientTheme var1) {
      this.rootProperties.getTabWindowProperties().getTabbedPanelProperties().addSuperObject(var1.getTabbedPanelProperties());
      this.rootProperties.getTabWindowProperties().getTabProperties().getTitledTabProperties().addSuperObject(var1.getTitledTabProperties());
      this.rootProperties.getTabWindowProperties().getCloseButtonProperties().setVisible(false);
      if (!this.shadowEnabled) {
         this.rootProperties.getWindowAreaProperties().setInsets(new Insets(6, 6, 6, 6));
      }

      this.rootProperties
         .getWindowAreaShapedPanelProperties()
         .setComponentPainter(
            new GradientComponentPainter(
               UIManagerColorProvider.DESKTOP_BACKGROUND,
               new ColorMultiplier(UIManagerColorProvider.DESKTOP_BACKGROUND, 0.9F),
               new ColorMultiplier(UIManagerColorProvider.DESKTOP_BACKGROUND, 0.9F),
               new ColorMultiplier(UIManagerColorProvider.DESKTOP_BACKGROUND, 0.8F)
            )
         );
      this.rootProperties.getWindowAreaProperties().setBorder(new LineBorder(Color.BLACK));
      if (this.tabAreaBackgroundColor != null) {
         this.rootProperties.getComponentProperties().setBackgroundColor(this.tabAreaBackgroundColor);
      }

      if (!this.shadowEnabled) {
         this.rootProperties.getSplitWindowProperties().setDividerSize(6);
      }

      TitledTabProperties var2 = this.rootProperties.getTabWindowProperties().getTabProperties().getTitledTabProperties();
      var2.getNormalProperties().setIconVisible(false);
      var2.getHighlightedProperties().setIconVisible(true);
      if (!this.highlightedBold) {
         var2.getHighlightedProperties()
            .getComponentProperties()
            .getMap()
            .createRelativeRef(ComponentProperties.FONT, var2.getNormalProperties().getComponentProperties().getMap(), ComponentProperties.FONT);
      }

      if (this.focusHighlighterEnabled) {
         var2.getHighlightedProperties()
            .getComponentProperties()
            .setBorder(
               new CompoundBorder(
                  this.opaqueTabArea ? new TabAreaLineBorder(false, false, true, true) : new TabAreaLineBorder(this.borderColor),
                  var1.getTabAreaComponentsGradientBorder()
               )
            );
         this.rootProperties
            .getTabWindowProperties()
            .getTabProperties()
            .getFocusedProperties()
            .getComponentProperties()
            .setBorder(
               new CompoundBorder(
                  this.opaqueTabArea ? new TabAreaLineBorder(false, false, true, true) : new TabAreaLineBorder(this.borderColor),
                  var1.getHighlightedTabGradientBorder()
               )
            );
      }

      this.rootProperties
         .getTabWindowProperties()
         .getTabbedPanelProperties()
         .getTabAreaComponentsProperties()
         .getComponentProperties()
         .setInsets(this.opaqueTabArea ? new Insets(0, 3, 0, 3) : new Insets(1, 3, 1, 3));
      this.rootProperties.getTabWindowProperties().getTabProperties().getHighlightedButtonProperties().getCloseButtonProperties().setVisible(false);
      this.rootProperties.getTabWindowProperties().getTabProperties().getHighlightedButtonProperties().getMinimizeButtonProperties().setVisible(true);
      this.rootProperties.getTabWindowProperties().getTabProperties().getHighlightedButtonProperties().getRestoreButtonProperties().setVisible(true);
   }

   private void createWindowBarProperties(GradientTheme var1) {
      WindowBarProperties var2 = this.rootProperties.getWindowBarProperties();
      var2.getTabWindowProperties()
         .getTabbedPanelProperties()
         .getContentPanelProperties()
         .getComponentProperties()
         .setBorder(new OpenContentBorder(Color.BLACK, 1));
      var2.getTabWindowProperties().getTabProperties().getNormalButtonProperties().getCloseButtonProperties().setVisible(false);
      var2.getTabWindowProperties()
         .getTabProperties()
         .getTitledTabProperties()
         .getNormalProperties()
         .setIconVisible(true)
         .getComponentProperties()
         .setBorder(new CompoundBorder(new TabAreaLineBorder(), var1.getTabAreaComponentsGradientBorder()));
      var2.getTabWindowProperties()
         .getTabProperties()
         .getFocusedProperties()
         .getComponentProperties()
         .setBorder(new CompoundBorder(new TabAreaLineBorder(Color.BLACK), var1.getHighlightedTabGradientBorder()));
      var2.getTabWindowProperties()
         .getTabProperties()
         .getTitledTabProperties()
         .getHighlightedProperties()
         .getComponentProperties()
         .setBorder(new CompoundBorder(new TabAreaLineBorder(Color.BLACK), var1.getHighlightedTabGradientBorder()));
      var2.getTabWindowProperties().getTabbedPanelProperties().setTabSpacing(-1);
      var2.getTabWindowProperties().getTabbedPanelProperties().getTabAreaProperties().getComponentProperties().setBorder(null).setBackgroundColor(null);
   }

   public String getName() {
      String var1 = (this.opaqueTabArea ? "" : "Transparent Tab Area, ")
         + (this.shadowEnabled ? "" : "No Shadow, ")
         + (this.focusHighlighterEnabled ? "" : "No Focus Highlight, ")
         + (this.highlightedBold ? "Highlighted Bold, " : "");
      return "Gradient Theme" + (var1.length() > 0 ? " - " + var1.substring(0, var1.length() - 2) : "");
   }

   public RootWindowProperties getRootWindowProperties() {
      return this.rootProperties;
   }
}
