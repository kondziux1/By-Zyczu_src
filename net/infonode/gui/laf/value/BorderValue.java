package net.infonode.gui.laf.value;

import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;

public class BorderValue {
   private BorderUIResource defaultBorder;
   private BorderUIResource border;

   public BorderValue() {
      super();
   }

   public BorderValue(Border var1) {
      super();
      this.defaultBorder = new BorderUIResource(var1);
   }

   public BorderUIResource getDefaultBorder() {
      return this.defaultBorder;
   }

   public void setDefaultBorder(Border var1) {
      this.setDefaultBorder(new BorderUIResource(var1));
   }

   public void setDefaultBorder(BorderUIResource var1) {
      this.defaultBorder = var1;
   }

   public BorderUIResource getBorder() {
      return this.border == null ? this.defaultBorder : this.border;
   }

   public void setBorder(BorderUIResource var1) {
      this.border = var1;
   }
}
