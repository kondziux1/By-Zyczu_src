package net.infonode.tabbedpanel.titledtab;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

class TitledTab$6 implements FocusListener {
   TitledTab$6(TitledTab var1) {
      super();
      this.this$0 = var1;
   }

   public void focusGained(FocusEvent var1) {
      this.this$0.repaint();
   }

   public void focusLost(FocusEvent var1) {
      this.this$0.repaint();
   }
}
