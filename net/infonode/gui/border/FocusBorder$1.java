package net.infonode.gui.border;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

class FocusBorder$1 implements FocusListener {
   FocusBorder$1(FocusBorder var1, Component var2) {
      super();
      this.this$0 = var1;
      this.val$focusComponent = var2;
   }

   public void focusGained(FocusEvent var1) {
      if (FocusBorder.access$000(this.this$0)) {
         this.val$focusComponent.repaint();
      }

   }

   public void focusLost(FocusEvent var1) {
      if (FocusBorder.access$000(this.this$0)) {
         this.val$focusComponent.repaint();
      }

   }
}
