package net.infonode.gui.panel;

import java.awt.Component;

public class BasePanel extends BaseContainer {
   private Component comp;

   protected BasePanel() {
      super();
      this.setForcedOpaque(false);
   }

   protected void setComponent(Component var1) {
      if (this.comp != null) {
         this.remove(this.comp);
      }

      if (var1 != null) {
         this.add(var1, "Center");
         this.revalidate();
      }

      this.comp = var1;
   }

   protected void setSouthComponent(Component var1) {
      this.add(var1, "South");
      this.revalidate();
   }
}
