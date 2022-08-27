package net.infonode.tabbedpanel.theme;

import java.awt.Color;
import java.awt.Insets;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import net.infonode.gui.colorprovider.ColorMultiplier;
import net.infonode.gui.colorprovider.ColorProvider;
import net.infonode.gui.colorprovider.ColorProviderUtil;
import net.infonode.gui.colorprovider.UIManagerColorProvider;
import net.infonode.tabbedpanel.TabbedPanelProperties;
import net.infonode.tabbedpanel.border.GradientTabAreaBorder;
import net.infonode.tabbedpanel.border.OpenContentBorder;
import net.infonode.tabbedpanel.border.TabAreaLineBorder;
import net.infonode.tabbedpanel.titledtab.TitledTabProperties;

public class GradientTheme extends TabbedPanelTitledTabTheme {
   private static final float HUE = 0.61F;
   private static final float SATURATION = 0.06F;
   private static final float BRIGHTNESS = 0.72F;
   public static final Color DEFAULT_TAB_AREA_BACKGROUND_COLOR = Color.getHSBColor(0.61F, 0.06F, 0.72F);
   private static final Border HIGHLIGHTED_TAB_GRADIENT_BORDER = new GradientTabAreaBorder(Color.WHITE);
   private static final Border TAB_AREA_COMPONENTS_GRADIENT_BORDER = new GradientTabAreaBorder(
      new ColorMultiplier(UIManagerColorProvider.CONTROL_COLOR, 0.88), UIManagerColorProvider.CONTROL_COLOR
   );
   private boolean opaqueTabArea;
   private boolean shadowEnabled;
   private Color borderColor;
   private Color tabAreaBackgroundColor;
   private Border normalTabGradientBorder;
   private TitledTabProperties titledTabProperties = new TitledTabProperties();
   private TabbedPanelProperties tabbedPanelProperties = new TabbedPanelProperties();

   public GradientTheme() {
      this(false, true);
   }

   public GradientTheme(boolean var1, boolean var2) {
      this(var1, var2, null);
   }

   public GradientTheme(boolean var1, boolean var2, Color var3) {
      this(var1, var2, var3, DEFAULT_TAB_AREA_BACKGROUND_COLOR);
   }

   public GradientTheme(boolean var1, boolean var2, Color var3, Color var4) {
      super();
      this.opaqueTabArea = var1;
      this.shadowEnabled = var2;
      this.borderColor = var3;
      this.tabAreaBackgroundColor = var4;
      ColorProvider var5 = ColorProviderUtil.getColorProvider(var4, UIManagerColorProvider.TABBED_PANE_BACKGROUND);
      this.normalTabGradientBorder = new GradientTabAreaBorder(var5, new ColorMultiplier(var5, 1.1));
      this.initTabbedPanelProperties();
      this.initTitledTabProperties();
   }

   public String getName() {
      return "Gradient Theme" + (this.opaqueTabArea ? " - Opaque Tab Area" : "");
   }

   private void initTabbedPanelProperties() {
      this.tabbedPanelProperties
         .getContentPanelProperties()
         .getComponentProperties()
         .setInsets(new Insets(3, 3, 3, 3))
         .setBorder(new OpenContentBorder(this.borderColor, this.opaqueTabArea ? 0 : 1));
      this.tabbedPanelProperties.setShadowEnabled(this.shadowEnabled).setPaintTabAreaShadow(this.opaqueTabArea).setTabSpacing(this.opaqueTabArea ? 0 : -1);
      if (this.opaqueTabArea) {
         if (this.tabAreaBackgroundColor != null) {
            this.tabbedPanelProperties.getTabAreaProperties().getComponentProperties().setBackgroundColor(this.tabAreaBackgroundColor);
         }

         this.tabbedPanelProperties
            .getTabAreaProperties()
            .getComponentProperties()
            .setBorder(new CompoundBorder(new TabAreaLineBorder(this.borderColor), this.normalTabGradientBorder));
      }

      this.tabbedPanelProperties
         .getTabAreaComponentsProperties()
         .setStretchEnabled(this.opaqueTabArea)
         .getComponentProperties()
         .setBorder(
            new CompoundBorder(
               new TabAreaLineBorder(this.opaqueTabArea ? null : this.borderColor, !this.opaqueTabArea, true, !this.opaqueTabArea, true),
               TAB_AREA_COMPONENTS_GRADIENT_BORDER
            )
         )
         .setInsets(this.opaqueTabArea ? new Insets(0, 3, 0, 3) : new Insets(1, 3, 1, 3));
   }

   private void initTitledTabProperties() {
      if (this.opaqueTabArea) {
         this.titledTabProperties.setHighlightedRaised(0);
      }

      this.titledTabProperties
         .getNormalProperties()
         .getComponentProperties()
         .setBorder(
            (Border)(this.opaqueTabArea
               ? new TabAreaLineBorder(false, false, true, true)
               : new CompoundBorder(new TabAreaLineBorder(), this.normalTabGradientBorder))
         );
      if (this.opaqueTabArea) {
         this.titledTabProperties.getNormalProperties().getComponentProperties().setBackgroundColor(null);
      }

      if (!this.opaqueTabArea && this.tabAreaBackgroundColor != null) {
         this.titledTabProperties.getNormalProperties().getComponentProperties().setBackgroundColor(this.tabAreaBackgroundColor);
      }

      this.titledTabProperties
         .getHighlightedProperties()
         .setIconVisible(true)
         .getComponentProperties()
         .setBorder(
            new CompoundBorder(
               this.opaqueTabArea ? new TabAreaLineBorder(false, false, true, true) : new TabAreaLineBorder(this.borderColor), HIGHLIGHTED_TAB_GRADIENT_BORDER
            )
         );
   }

   public TitledTabProperties getTitledTabProperties() {
      return this.titledTabProperties;
   }

   public TabbedPanelProperties getTabbedPanelProperties() {
      return this.tabbedPanelProperties;
   }

   public Border getHighlightedTabGradientBorder() {
      return HIGHLIGHTED_TAB_GRADIENT_BORDER;
   }

   public Border getTabAreaComponentsGradientBorder() {
      return TAB_AREA_COMPONENTS_GRADIENT_BORDER;
   }

   public Border getNormalTabGradientBorder() {
      return this.normalTabGradientBorder;
   }
}
