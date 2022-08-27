package net.infonode.gui.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.LayoutManager;
import javax.swing.border.Border;

public class SimplePanel extends BaseContainer {
   private Component comp;

   public SimplePanel() {
      this(new BorderLayout());
   }

   public SimplePanel(Border var1) {
      this();
      this.setBorder(var1);
   }

   public SimplePanel(Border var1, Component var2) {
      this(var2);
      this.setBorder(var1);
   }

   public SimplePanel(LayoutManager var1) {
      super(false, var1);
   }

   public SimplePanel(Component var1) {
      this();
      this.setComponent(var1);
   }

   public SimplePanel(Component var1, Component var2) {
      this(var1);
      this.add(var2, "North");
   }

   public SimplePanel(Border var1, Component var2, Component var3) {
      this(var1, var2);
      this.add(var3, "North");
   }

   public void setComponent(Component var1) {
      if (this.comp != null) {
         this.remove(this.comp);
      }

      if (var1 != null) {
         this.add(var1, "Center");
         this.revalidate();
      }

      this.comp = var1;
   }

   public void setSouthComponent(Component var1) {
      this.add(var1, "South");
      this.revalidate();
   }
}
