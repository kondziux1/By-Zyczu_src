package net.infonode.tabbedpanel.theme;

import java.awt.Component;
import java.awt.Insets;
import java.awt.Polygon;
import javax.swing.border.Border;
import net.infonode.gui.HighlightPainter;
import net.infonode.gui.InsetsUtil;
import net.infonode.gui.colorprovider.BackgroundPainterColorProvider;
import net.infonode.gui.colorprovider.ColorBlender;
import net.infonode.gui.colorprovider.ColorMultiplier;
import net.infonode.gui.colorprovider.ColorProvider;
import net.infonode.gui.colorprovider.UIManagerColorProvider;
import net.infonode.gui.componentpainter.GradientComponentPainter;
import net.infonode.tabbedpanel.Tab;
import net.infonode.tabbedpanel.TabbedPanelProperties;
import net.infonode.tabbedpanel.TabbedUtils;
import net.infonode.tabbedpanel.border.OpenContentBorder;
import net.infonode.tabbedpanel.internal.SlopedTabLineBorder;
import net.infonode.tabbedpanel.titledtab.TitledTabBorderSizePolicy;
import net.infonode.tabbedpanel.titledtab.TitledTabProperties;
import net.infonode.tabbedpanel.titledtab.TitledTabStateProperties;

public class ShapedGradientTheme extends TabbedPanelTitledTabTheme {
   private static final int CORNER_INSET = 3;
   private ColorProvider highlightColor;
   private ColorProvider lineColor;
   private ColorProvider controlColor;
   private ColorProvider darkControlColor;
   private ColorProvider alternateHighlight;
   private int leftSlopeHeight;
   private int rightSlopeHeight;
   private TabbedPanelProperties tabbedPanelProperties = new TabbedPanelProperties();
   private TitledTabProperties titledTabProperties = new TitledTabProperties();

   public ShapedGradientTheme() {
      this(0.0F, 0.5F);
   }

   public ShapedGradientTheme(float var1, float var2) {
      this(var1, var2, UIManagerColorProvider.TABBED_PANE_DARK_SHADOW, UIManagerColorProvider.TABBED_PANE_HIGHLIGHT);
   }

   public ShapedGradientTheme(float var1, float var2, ColorProvider var3, ColorProvider var4) {
      this(var1, var2, 25, var3, var4);
   }

   public ShapedGradientTheme(float var1, float var2, int var3, ColorProvider var4, ColorProvider var5) {
      super();
      this.leftSlopeHeight = var3;
      this.rightSlopeHeight = var3;
      this.highlightColor = var5;
      this.lineColor = var4;
      this.controlColor = UIManagerColorProvider.CONTROL_COLOR;
      this.darkControlColor = UIManagerColorProvider.TABBED_PANE_BACKGROUND;
      this.alternateHighlight = (ColorProvider)(var5 != null ? new ColorBlender(var5, this.controlColor, 0.3F) : new ColorMultiplier(this.controlColor, 1.2));
      GradientComponentPainter var6 = new GradientComponentPainter(this.alternateHighlight, this.alternateHighlight, this.controlColor, this.controlColor);
      int var7 = (int)(var1 * (float)this.leftSlopeHeight);
      int var8 = (int)(var2 * (float)this.rightSlopeHeight);
      byte var9 = 2;
      boolean var10 = true;
      boolean var11 = true;
      boolean var12 = true;
      boolean var13 = true;
      this.titledTabProperties.setHighlightedRaised(var9).setBorderSizePolicy(TitledTabBorderSizePolicy.EQUAL_SIZE);
      TitledTabStateProperties var14 = this.titledTabProperties.getNormalProperties();
      TitledTabStateProperties var15 = this.titledTabProperties.getHighlightedProperties();
      TitledTabStateProperties var16 = this.titledTabProperties.getDisabledProperties();
      ShapedGradientTheme.TabBorder var17 = new ShapedGradientTheme.TabBorder(
         var4, null, var1, var2, this.leftSlopeHeight, this.rightSlopeHeight, false, var11, var12, false, true, true, var9
      );
      ShapedGradientTheme.TabBorder var18 = new ShapedGradientTheme.TabBorder(
         var4, var5, var1, var2, this.leftSlopeHeight, this.rightSlopeHeight, var10, var11, var12, var13, false, true, var9
      );
      var14.getComponentProperties().setBorder(var17).setInsets(new Insets(0, 0, 0, 0));
      var15.getComponentProperties().setBorder(var18);
      ColorMultiplier var19 = new ColorMultiplier(this.darkControlColor, 1.1);
      ColorMultiplier var20 = new ColorMultiplier(this.darkControlColor, 0.92);
      GradientComponentPainter var21 = new GradientComponentPainter(var19, var19, var20, var20);
      var14.getShapedPanelProperties().setOpaque(false).setComponentPainter(var21);
      var16.getShapedPanelProperties().setComponentPainter(var21);
      if (var5 == null) {
         var15.getShapedPanelProperties().setComponentPainter(var6);
      } else {
         var15.getShapedPanelProperties().setComponentPainter(new GradientComponentPainter(var5, var5, this.controlColor, this.controlColor));
      }

      Insets var22 = var17.getBorderInsets(null);
      int var23 = 1 + var22.left + var22.right - (var11 ? 3 : 0) - (var12 ? 3 : 0) - (int)(0.2 * (double)(var7 + var8));
      this.tabbedPanelProperties.setTabSpacing(-var23).setShadowEnabled(false);
      this.tabbedPanelProperties
         .getTabAreaComponentsProperties()
         .getComponentProperties()
         .setBorder(new SlopedTabLineBorder(var4, var5, false, 0.0F, 0.0F, 0, 0, false, var11, var12, false))
         .setInsets(new Insets(0, 0, 0, 0));
      this.tabbedPanelProperties.getTabAreaComponentsProperties().getShapedPanelProperties().setComponentPainter(var6);
      this.tabbedPanelProperties.getTabAreaProperties().getShapedPanelProperties().setOpaque(false);
      this.tabbedPanelProperties
         .getContentPanelProperties()
         .getComponentProperties()
         .setBorder(
            new OpenContentBorder(
               var4, var4, var5 == null ? null : new ColorBlender(var5, BackgroundPainterColorProvider.INSTANCE, HighlightPainter.getBlendFactor(1, 0)), 1
            )
         );
   }

   public String getName() {
      return "Shaped Gradient Theme";
   }

   public TabbedPanelProperties getTabbedPanelProperties() {
      return this.tabbedPanelProperties;
   }

   public TitledTabProperties getTitledTabProperties() {
      return this.titledTabProperties;
   }

   public ColorProvider getLineColor() {
      return this.lineColor;
   }

   public ColorProvider getHighlightColor() {
      return this.highlightColor;
   }

   public ColorProvider getAlternateHighlightColor() {
      return this.alternateHighlight;
   }

   public ColorProvider getControlColor() {
      return this.controlColor;
   }

   public ColorProvider getDarkControlColor() {
      return this.darkControlColor;
   }

   public Border createTabBorder(
      ColorProvider var1,
      ColorProvider var2,
      float var3,
      float var4,
      boolean var5,
      boolean var6,
      boolean var7,
      boolean var8,
      boolean var9,
      boolean var10,
      int var11
   ) {
      return new ShapedGradientTheme.TabBorder(var1, var2, var3, var4, 25, 25, var5, var6, var7, var8, var9, var10, var11);
   }

   private static class TabBorder extends SlopedTabLineBorder {
      private boolean bottomLeftRounded;
      private boolean isNormal;
      private boolean hasLeftSlope;
      private int raised;
      private int cornerInset;

      TabBorder(
         ColorProvider var1,
         ColorProvider var2,
         float var3,
         float var4,
         int var5,
         int var6,
         boolean var7,
         boolean var8,
         boolean var9,
         boolean var10,
         boolean var11,
         boolean var12,
         int var13
      ) {
         super(var1, var2, false, var3, var4, var5, var6, var11 ? false : var7, var8, var9, var10);
         this.bottomLeftRounded = var7;
         this.isNormal = var11;
         this.raised = var13;
         this.hasLeftSlope = var3 > 0.0F;
         this.cornerInset = var12 ? 3 : 0;
      }

      protected Polygon createPolygon(Component var1, int var2, int var3) {
         Polygon var4 = super.createPolygon(var1, var2, var3);
         if (this.isNormal) {
            int var5 = var2 / 2;
            boolean var6 = this.isFirst(var1);

            for(int var7 = 0; var7 < var4.npoints; ++var7) {
               if (var4.xpoints[var7] < var5) {
                  var4.xpoints[var7] = var4.xpoints[var7] + this.raised + (var6 ? 0 : this.cornerInset);
               } else {
                  var4.xpoints[var7] = var4.xpoints[var7] - this.raised - this.cornerInset;
               }
            }
         }

         return var4;
      }

      protected Insets getShapedBorderInsets(Component var1) {
         Insets var2 = super.getShapedBorderInsets(var1);
         Insets var3 = new Insets(0, 0, 0, 1 + this.raised);
         if (this.isNormal && !this.isFirst(var1)) {
            var3.left += this.cornerInset;
         }

         if (!this.isNormal) {
            var3.right -= this.cornerInset;
         }

         return InsetsUtil.add(var2, var3);
      }

      private boolean isFirst(Component var1) {
         if (!this.hasLeftSlope) {
            Tab var2 = TabbedUtils.getParentTab(var1);
            if (var2 != null && var2.getTabbedPanel() != null) {
               return var2.getTabbedPanel().getTabAt(0) == var2;
            }
         }

         return false;
      }

      protected boolean isBottomLeftRounded(Component var1) {
         return this.isFirst(var1) ? false : this.bottomLeftRounded;
      }
   }
}
