package net.infonode.gui.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;

class SimpleAction$1 extends AbstractAction {
   SimpleAction$1(SimpleAction var1, String var2, Icon var3) {
      super(var2, var3);
      this.this$0 = var1;
   }

   public void actionPerformed(ActionEvent var1) {
      this.this$0.perform();
   }
}
