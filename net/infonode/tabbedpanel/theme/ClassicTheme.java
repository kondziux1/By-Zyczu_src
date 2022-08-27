package net.infonode.tabbedpanel.theme;

import java.awt.Insets;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import net.infonode.gui.colorprovider.ColorProvider;
import net.infonode.gui.colorprovider.UIManagerColorProvider;
import net.infonode.tabbedpanel.TabbedPanelProperties;
import net.infonode.tabbedpanel.border.OpenContentBorder;
import net.infonode.tabbedpanel.titledtab.TitledTabBorderSizePolicy;
import net.infonode.tabbedpanel.titledtab.TitledTabProperties;

public class ClassicTheme extends TabbedPanelTitledTabTheme {
   private TabbedPanelProperties tabbedPanelProperties = new TabbedPanelProperties();
   private TitledTabProperties titledTabProperties = new TitledTabProperties();
   private ColorProvider highlightColor = UIManagerColorProvider.TABBED_PANE_HIGHLIGHT;
   private ColorProvider shadowColor = UIManagerColorProvider.TABBED_PANE_SHADOW;
   private ColorProvider darkShadow = UIManagerColorProvider.TABBED_PANE_DARK_SHADOW;
   private int raised;
   private Border shadowBorder;

   public ClassicTheme() {
      this(2);
   }

   public ClassicTheme(int var1) {
      super();
      this.raised = var1;
      this.shadowBorder = new ClassicTheme$1(this);
      CompoundBorder var2 = new CompoundBorder(
         new OpenContentBorder(this.highlightColor, this.darkShadow, null, 1), new OpenContentBorder(null, this.shadowColor, null, 1)
      );
      this.tabbedPanelProperties.getContentPanelProperties().getComponentProperties().setBorder(var2).setInsets(new Insets(1, 1, 1, 1));
      this.tabbedPanelProperties.setTabSpacing(-var1 - 1).setShadowEnabled(false);
      this.tabbedPanelProperties.getTabAreaComponentsProperties().getComponentProperties().setBorder(this.doCreateTabBorder(false, false, true, true));
      Border var3 = this.createInsetsTabBorder(true, true, false);
      Border var4 = this.doCreateTabBorder(true, true, true, false);
      Insets var5 = new Insets(0, 3, 0, 3);
      Insets var6 = new Insets(0, (var1 + 1) / 2 + ((var1 + 1 & 1) == 1 ? 1 : 0) + var5.left, 1, (var1 + 1) / 2 + var5.right);
      this.titledTabProperties.setHighlightedRaised(var1).setBorderSizePolicy(TitledTabBorderSizePolicy.INDIVIDUAL_SIZE);
      this.titledTabProperties.getNormalProperties().getComponentProperties().setBorder(var3).setInsets(var5);
      this.titledTabProperties.getHighlightedProperties().getComponentProperties().setBorder(var4).setInsets(var6);
   }

   public String getName() {
      return "Classic Theme";
   }

   public TabbedPanelProperties getTabbedPanelProperties() {
      return this.tabbedPanelProperties;
   }

   public TitledTabProperties getTitledTabProperties() {
      return this.titledTabProperties;
   }

   public Border createInsetsTabBorder(boolean var1, boolean var2, boolean var3) {
      ClassicTheme$2 var4 = new ClassicTheme$2(this);
      return new CompoundBorder(var4, this.doCreateTabBorder(var1, var2, var3, true));
   }

   public Border createTabBorder(boolean var1, boolean var2, boolean var3) {
      return this.doCreateTabBorder(var1, var2, true, var3);
   }

   private ColorProvider createNormalHighlightColorProvider() {
      return new ClassicTheme$3(this);
   }

   private Border doCreateTabBorder(boolean var1, boolean var2, boolean var3, boolean var4) {
      return new CompoundBorder(
         new ClassicTheme$4(this, var3 ? this.highlightColor : this.createNormalHighlightColorProvider(), this.darkShadow, var1, var2, var4),
         this.shadowBorder
      );
   }
}
