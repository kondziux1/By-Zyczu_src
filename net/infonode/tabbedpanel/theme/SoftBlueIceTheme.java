package net.infonode.tabbedpanel.theme;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import net.infonode.gui.colorprovider.ColorBlender;
import net.infonode.gui.colorprovider.ColorProvider;
import net.infonode.gui.colorprovider.FixedColorProvider;
import net.infonode.gui.componentpainter.FixedTransformComponentPainter;
import net.infonode.gui.componentpainter.GradientComponentPainter;
import net.infonode.gui.shaped.border.RoundedCornerBorder;
import net.infonode.properties.base.Property;
import net.infonode.properties.gui.util.ComponentProperties;
import net.infonode.tabbedpanel.TabbedPanelProperties;
import net.infonode.tabbedpanel.titledtab.TitledTabProperties;
import net.infonode.util.ColorUtil;

public class SoftBlueIceTheme extends TabbedPanelTitledTabTheme {
   public static final FixedColorProvider DEFAULT_DARK_COLOR = new FixedColorProvider(ColorUtil.mult(new Color(160, 170, 190), 0.9));
   public static final FixedColorProvider DEFAULT_LIGHT_COLOR = new FixedColorProvider(new Color(220, 230, 240));
   private ColorProvider darkColor;
   private ColorProvider lightColor;
   private TabbedPanelProperties tabbedPanelProperties = new TabbedPanelProperties();
   private TitledTabProperties titledTabProperties = new TitledTabProperties();

   public SoftBlueIceTheme() {
      this(DEFAULT_DARK_COLOR, DEFAULT_LIGHT_COLOR, 4);
   }

   public SoftBlueIceTheme(ColorProvider var1, ColorProvider var2, int var3) {
      super();
      this.darkColor = var1;
      this.lightColor = var2;
      ColorBlender var5 = new ColorBlender(var1, var2, 0.3F);
      ColorBlender var6 = new ColorBlender(var1, FixedColorProvider.WHITE, 0.1F);
      RoundedCornerBorder var8 = new RoundedCornerBorder(var1, var2, var3, var3, var3, var3, true, true, true, true);
      RoundedCornerBorder var10 = new RoundedCornerBorder(var1, var2, var3, var3, var3, var3, false, true, true, true);
      FixedTransformComponentPainter var11 = new FixedTransformComponentPainter(new GradientComponentPainter(var6, var2, var2, var6));
      FixedTransformComponentPainter var12 = new FixedTransformComponentPainter(new GradientComponentPainter(var2, var6, var6, var2));
      FixedTransformComponentPainter var13 = new FixedTransformComponentPainter(new GradientComponentPainter(FixedColorProvider.WHITE, var2, var2, var2));
      FixedTransformComponentPainter var14 = new FixedTransformComponentPainter(new GradientComponentPainter(var2, var5, var5, var5));
      this.tabbedPanelProperties.setPaintTabAreaShadow(true).setTabSpacing(2).setShadowEnabled(false);
      this.tabbedPanelProperties.getTabAreaProperties().getComponentProperties().setBorder(var8).setInsets(new Insets(2, 2, 3, 3));
      this.tabbedPanelProperties.getTabAreaProperties().getShapedPanelProperties().setClipChildren(true).setComponentPainter(var11).setOpaque(false);
      this.tabbedPanelProperties
         .getTabAreaComponentsProperties()
         .setStretchEnabled(true)
         .getComponentProperties()
         .setBorder(null)
         .setInsets(new Insets(0, 0, 0, 0));
      this.tabbedPanelProperties.getTabAreaComponentsProperties().getShapedPanelProperties().setOpaque(false);
      this.tabbedPanelProperties.getContentPanelProperties().getComponentProperties().setBorder(var10).setInsets(new Insets(3, 3, 4, 4));
      this.tabbedPanelProperties.getContentPanelProperties().getShapedPanelProperties().setComponentPainter(var12).setClipChildren(true).setOpaque(false);
      this.titledTabProperties.setHighlightedRaised(0);
      Font var15 = this.titledTabProperties.getNormalProperties().getComponentProperties().getFont();
      if (var15 != null) {
         var15 = var15.deriveFont(0).deriveFont(11.0F);
      }

      this.titledTabProperties
         .getNormalProperties()
         .getComponentProperties()
         .setBorder(var8)
         .setInsets(new Insets(1, 4, 2, 5))
         .setBackgroundColor(this.titledTabProperties.getHighlightedProperties().getComponentProperties().getBackgroundColor())
         .setFont(var15);
      this.titledTabProperties.getNormalProperties().getShapedPanelProperties().setComponentPainter(var14).setOpaque(false);
      Property[] var16 = new Property[]{ComponentProperties.BORDER, ComponentProperties.INSETS, ComponentProperties.FONT};

      for(int var17 = 0; var17 < var16.length; ++var17) {
         this.titledTabProperties
            .getHighlightedProperties()
            .getComponentProperties()
            .getMap()
            .createRelativeRef(var16[var17], this.titledTabProperties.getNormalProperties().getComponentProperties().getMap(), var16[var17]);
      }

      this.titledTabProperties.getHighlightedProperties().getShapedPanelProperties().setComponentPainter(var13);
   }

   public String getName() {
      return "Soft Blue Ice Theme";
   }

   public TabbedPanelProperties getTabbedPanelProperties() {
      return this.tabbedPanelProperties;
   }

   public TitledTabProperties getTitledTabProperties() {
      return this.titledTabProperties;
   }

   public ColorProvider getDarkColor() {
      return this.darkColor;
   }

   public ColorProvider getLightColor() {
      return this.lightColor;
   }
}
