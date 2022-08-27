package net.infonode.gui.laf;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;

class InfoNodeLookAndFeel$1 extends DefaultMetalTheme {
   InfoNodeLookAndFeel$1(InfoNodeLookAndFeel var1) {
      super();
      this.this$0 = var1;
   }

   public ColorUIResource getPrimaryControlHighlight() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getPrimaryControlHighlightColor();
   }

   public ColorUIResource getMenuBackground() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getControlColor();
   }

   public ColorUIResource getControlHighlight() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getControlHighlightColor();
   }

   public ColorUIResource getControl() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getControlColor();
   }

   public ColorUIResource getControlShadow() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getControlShadowColor();
   }

   public ColorUIResource getControlDarkShadow() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getControlDarkShadowColor();
   }

   public ColorUIResource getPrimaryControl() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getPrimaryControlColor();
   }

   public ColorUIResource getPrimaryControlShadow() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getPrimaryControlShadowColor();
   }

   public ColorUIResource getPrimaryControlDarkShadow() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getPrimaryControlDarkShadowColor();
   }

   public ColorUIResource getTextHighlightColor() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getSelectedTextBackgroundColor();
   }

   public ColorUIResource getMenuSelectedBackground() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getSelectedMenuBackgroundColor();
   }

   public ColorUIResource getWindowBackground() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getBackgroundColor();
   }

   protected ColorUIResource getWhite() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getBackgroundColor();
   }

   public ColorUIResource getDesktopColor() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getDesktopColor();
   }

   public ColorUIResource getHighlightedTextColor() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getSelectedTextColor();
   }

   protected ColorUIResource getBlack() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getTextColor();
   }

   public ColorUIResource getMenuForeground() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getTextColor();
   }

   public ColorUIResource getMenuSelectedForeground() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getSelectedMenuForegroundColor();
   }

   public ColorUIResource getFocusColor() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getFocusColor();
   }

   public ColorUIResource getControlDisabled() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getControlColor();
   }

   public ColorUIResource getSystemTextColor() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getTextColor();
   }

   public ColorUIResource getControlTextColor() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getTextColor();
   }

   public ColorUIResource getInactiveControlTextColor() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getInactiveTextColor();
   }

   public ColorUIResource getInactiveSystemTextColor() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getInactiveTextColor();
   }

   public ColorUIResource getUserTextColor() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getTextColor();
   }

   public FontUIResource getControlTextFont() {
      return this.getSystemTextFont();
   }

   public FontUIResource getSystemTextFont() {
      return InfoNodeLookAndFeel.access$000(this.this$0).getFont();
   }

   public FontUIResource getUserTextFont() {
      return this.getSystemTextFont();
   }

   public FontUIResource getMenuTextFont() {
      return this.getSystemTextFont();
   }

   public FontUIResource getWindowTitleFont() {
      return this.getSystemTextFont();
   }

   public FontUIResource getSubTextFont() {
      return this.getSystemTextFont();
   }
}
