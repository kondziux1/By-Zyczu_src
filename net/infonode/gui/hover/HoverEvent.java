package net.infonode.gui.hover;

import java.awt.Component;

public class HoverEvent {
   private Component source;

   public HoverEvent(Component var1) {
      super();
      this.source = var1;
   }

   public Component getSource() {
      return this.source;
   }
}
