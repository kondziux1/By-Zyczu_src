package net.infonode.docking.theme;

import java.awt.Color;
import java.awt.Insets;
import javax.swing.border.Border;
import net.infonode.docking.properties.ViewTitleBarProperties;
import net.infonode.docking.properties.WindowBarProperties;
import net.infonode.gui.colorprovider.BackgroundColorProvider;
import net.infonode.gui.componentpainter.ComponentPainter;
import net.infonode.gui.componentpainter.CompoundComponentPainter;
import net.infonode.gui.componentpainter.SolidColorComponentPainter;
import net.infonode.properties.gui.util.ShapedPanelProperties;
import net.infonode.tabbedpanel.titledtab.TitledTabSizePolicy;
import net.infonode.util.Direction;

class LookAndFeelDockingTheme$3 implements Runnable {
   LookAndFeelDockingTheme$3(LookAndFeelDockingTheme var1, boolean var2) {
      super();
      this.this$0 = var1;
      this.val$initial = var2;
   }

   public void run() {
      if (this.val$initial) {
         LookAndFeelDockingTheme.access$200()
            .getTabWindowProperties()
            .getTabbedPanelProperties()
            .addSuperObject(LookAndFeelDockingTheme.access$100().getTabbedPanelProperties());
         LookAndFeelDockingTheme.access$200()
            .getTabWindowProperties()
            .getTabProperties()
            .getTitledTabProperties()
            .addSuperObject(LookAndFeelDockingTheme.access$100().getTitledTabProperties());
      }

      LookAndFeelDockingTheme.access$200().getMap().clear(true);
      WindowBarProperties var1 = LookAndFeelDockingTheme.access$200().getWindowBarProperties();
      var1.getTabWindowProperties().getTabProperties().getTitledTabProperties().setSizePolicy(TitledTabSizePolicy.EQUAL_SIZE);
      var1.getComponentProperties().setBorder(null);
      Border var2 = LookAndFeelDockingTheme.access$100().getTabbedPanelProperties().getContentPanelProperties().getComponentProperties().getBorder();
      ComponentPainter var3 = LookAndFeelDockingTheme.access$100()
         .getTabbedPanelProperties()
         .getContentPanelProperties()
         .getShapedPanelProperties()
         .getComponentPainter();
      var1.getTabWindowProperties()
         .getTabbedPanelProperties()
         .getContentPanelProperties()
         .getShapedPanelProperties()
         .setOpaque(true)
         .setComponentPainter(new CompoundComponentPainter(new SolidColorComponentPainter(BackgroundColorProvider.INSTANCE), var3));
      var1.getTabWindowProperties().getTabbedPanelProperties().getTabAreaComponentsProperties().getComponentProperties().setBorder(null);
      var1.getTabWindowProperties()
         .getTabbedPanelProperties()
         .getContentPanelProperties()
         .getComponentProperties()
         .setBorder(new LookAndFeelDockingTheme$4(this, var2))
         .setInsets(new Insets(0, 0, 0, 0))
         .setBackgroundColor(null);
      Color var8 = LookAndFeelDockingTheme.access$100().getBorderColor(Direction.UP);
      Color var10 = LookAndFeelDockingTheme.access$100().getBorderColor(Direction.LEFT);
      Color var12 = LookAndFeelDockingTheme.access$100().getBorderColor(Direction.DOWN);
      Color var4 = LookAndFeelDockingTheme.access$100().getBorderColor(Direction.RIGHT);
      Insets var5 = new Insets(var8 == null ? 0 : 1, var10 == null ? 0 : 1, var12 == null ? 0 : 1, var4 == null ? 0 : 1);
      boolean var6 = var8 != null && var8.getAlpha() == 255
         || var10 != null && var10.getAlpha() == 255
         || var12 != null && var12.getAlpha() == 255
         || var4 != null && var4.getAlpha() == 255;
      LookAndFeelDockingTheme$5 var7 = new LookAndFeelDockingTheme$5(this, var8, var4, var12, var10, var5, var6);
      LookAndFeelDockingTheme.access$200().getWindowAreaProperties().setInsets(new Insets(2, 2, 2, 2)).setBorder(var7).setBackgroundColor(null);
      LookAndFeelDockingTheme.access$200()
         .getWindowAreaShapedPanelProperties()
         .setComponentPainter(new SolidColorComponentPainter(BackgroundColorProvider.INSTANCE))
         .setOpaque(true);
      ViewTitleBarProperties var9 = LookAndFeelDockingTheme.access$200().getViewProperties().getViewTitleBarProperties();
      var9.getNormalProperties().getShapedPanelProperties().setDirection(LookAndFeelDockingTheme.access$300().getRenderingDirection());
      ComponentPainter var11 = LookAndFeelDockingTheme.access$300().getInactiveComponentPainter();
      if (var11 == null) {
         var9.getNormalProperties().getShapedPanelProperties().getMap().removeValue(ShapedPanelProperties.COMPONENT_PAINTER);
      } else {
         var9.getNormalProperties().getShapedPanelProperties().setComponentPainter(var11);
      }

      ComponentPainter var13 = LookAndFeelDockingTheme.access$300().getActiveComponentPainter();
      if (var13 == null) {
         var9.getFocusedProperties().getShapedPanelProperties().getMap().removeValue(ShapedPanelProperties.COMPONENT_PAINTER);
      } else {
         var9.getFocusedProperties().getShapedPanelProperties().setComponentPainter(var13);
      }

      var9.setMinimumSizeProvider(LookAndFeelDockingTheme.access$300().getSizeDimensionProvider())
         .getNormalProperties()
         .setTitleVisible(!LookAndFeelDockingTheme.access$300().isRenderingTitle())
         .setIconVisible(!LookAndFeelDockingTheme.access$300().isRenderingIcon());
      var9.getFocusedProperties().getComponentProperties().setInsets(LookAndFeelDockingTheme.access$300().getInsets());
      var9.getNormalProperties().getComponentProperties().setInsets(LookAndFeelDockingTheme.access$300().getInsets());
      LookAndFeelDockingTheme.access$400(
         this.this$0, var9.getNormalProperties().getComponentProperties(), LookAndFeelDockingTheme.access$300().getInactiveBackgroundColor()
      );
      LookAndFeelDockingTheme.access$400(
         this.this$0, var9.getFocusedProperties().getComponentProperties(), LookAndFeelDockingTheme.access$300().getActiveBackgroundColor()
      );
      LookAndFeelDockingTheme.access$200().setDragRectangleBorderWidth(3);
   }
}
