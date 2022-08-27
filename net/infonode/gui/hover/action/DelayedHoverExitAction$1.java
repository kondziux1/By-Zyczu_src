package net.infonode.gui.hover.action;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class DelayedHoverExitAction$1 implements ActionListener {
   DelayedHoverExitAction$1(DelayedHoverExitAction var1, Component var2) {
      super();
      this.this$0 = var1;
      this.val$c = var2;
   }

   public void actionPerformed(ActionEvent var1) {
      this.this$0.forceExit(this.val$c);
   }
}
