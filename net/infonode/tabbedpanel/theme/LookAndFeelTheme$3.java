package net.infonode.tabbedpanel.theme;

import net.infonode.gui.InsetsUtil;
import net.infonode.tabbedpanel.titledtab.TitledTabBorderSizePolicy;
import net.infonode.tabbedpanel.titledtab.TitledTabSizePolicy;
import net.infonode.util.Direction;

class LookAndFeelTheme$3 implements Runnable {
   LookAndFeelTheme$3(LookAndFeelTheme var1) {
      super();
      this.this$0 = var1;
   }

   public void run() {
      LookAndFeelTheme.access$100().getMap().clear(true);
      LookAndFeelTheme.access$200().getMap().clear(true);
      LookAndFeelTheme.access$100()
         .setShadowEnabled(false)
         .setTabSpacing(LookAndFeelTheme.access$300().getTabSpacing())
         .setTabScrollingOffset(LookAndFeelTheme.access$300().getScrollOffset())
         .setEnsureSelectedTabVisible(true);
      LookAndFeelTheme.access$100().getTabAreaComponentsProperties().getComponentProperties().setBorder(null).setInsets(InsetsUtil.EMPTY_INSETS);
      LookAndFeelTheme.access$100().getTabAreaProperties().getComponentProperties().setInsets(InsetsUtil.EMPTY_INSETS).setBorder(new LookAndFeelTheme$4(this));
      LookAndFeelTheme.access$100()
         .getTabAreaComponentsProperties()
         .getShapedPanelProperties()
         .setOpaque(LookAndFeelTheme.access$300().isTabAreaComponentsOpaque());
      LookAndFeelTheme.access$100()
         .getTabAreaProperties()
         .getShapedPanelProperties()
         .setOpaque(LookAndFeelTheme.access$300().isTabAreaOpaque())
         .setComponentPainter(new LookAndFeelTheme$5(this));
      LookAndFeelTheme.access$100()
         .getContentPanelProperties()
         .getShapedPanelProperties()
         .setOpaque(LookAndFeelTheme.access$300().isContentOpaque())
         .setComponentPainter(new LookAndFeelTheme$6(this));
      LookAndFeelTheme.access$100()
         .getContentPanelProperties()
         .getComponentProperties()
         .setInsets(InsetsUtil.EMPTY_INSETS)
         .setBorder(new LookAndFeelTheme$7(this));
      LookAndFeelTheme.access$200()
         .setSizePolicy(TitledTabSizePolicy.INDIVIDUAL_SIZE)
         .setBorderSizePolicy(TitledTabBorderSizePolicy.EQUAL_SIZE)
         .setHighlightedRaised(LookAndFeelTheme.access$300().getSelectedRaised(Direction.UP))
         .setFocusMarkerEnabled(false);
      LookAndFeelTheme.access$200()
         .getNormalProperties()
         .setIconTextGap(LookAndFeelTheme.access$300().getTextIconGap())
         .setTextTitleComponentGap(LookAndFeelTheme.access$300().getTextIconGap());
      LookAndFeelTheme.access$200()
         .getNormalProperties()
         .getComponentProperties()
         .setInsets(InsetsUtil.EMPTY_INSETS)
         .setBorder(LookAndFeelTheme.access$400(this.this$0, false))
         .setFont(LookAndFeelTheme.access$300().getFont());
      LookAndFeelTheme.access$200().getHighlightedProperties().getComponentProperties().setBorder(LookAndFeelTheme.access$400(this.this$0, true));
      LookAndFeelTheme.access$200().getDisabledProperties().getComponentProperties().setBorder(LookAndFeelTheme.access$400(this.this$0, false));
      LookAndFeelTheme.access$200().getNormalProperties().getShapedPanelProperties().setOpaque(false).setComponentPainter(null);
      LookAndFeelTheme.access$200().setMinimumSizeProvider(new LookAndFeelTheme$8(this));
      LookAndFeelTheme.access$200().setHoverListener(new LookAndFeelTheme$9(this));
   }
}
