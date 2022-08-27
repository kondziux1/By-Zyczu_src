package net.infonode.gui.laf.value;

import java.awt.Font;
import javax.swing.plaf.FontUIResource;

public class FontValue {
   private static final FontUIResource DEFAULT_FONT = new FontUIResource("Dialog", 0, 11);
   private FontUIResource font;
   private FontUIResource defaultFont;

   public FontValue() {
      this(DEFAULT_FONT);
   }

   public FontValue(FontUIResource var1) {
      super();
      this.defaultFont = var1;
   }

   public void setFont(Font var1) {
      this.setFont(new FontUIResource(var1));
   }

   public void setFont(FontUIResource var1) {
      this.font = var1;
   }

   public void setDefaultFont(FontUIResource var1) {
      this.defaultFont = var1;
   }

   public FontUIResource getFont() {
      return this.font == null ? this.defaultFont : this.font;
   }
}
