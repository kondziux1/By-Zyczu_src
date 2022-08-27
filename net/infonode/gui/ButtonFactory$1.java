package net.infonode.gui;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class ButtonFactory$1 implements ChangeListener {
   ButtonFactory$1(ButtonFactory.ButtonHighlighter var1) {
      super();
      this.this$0 = var1;
   }

   public void stateChanged(ChangeEvent var1) {
      ButtonFactory.ButtonHighlighter.access$002(
         this.this$0,
         System.currentTimeMillis() - ButtonFactory.ButtonHighlighter.access$100(this.this$0) > 20L
            && ButtonFactory.ButtonHighlighter.access$200(this.this$0).getModel().isRollover()
      );
      ButtonFactory.ButtonHighlighter.access$300(this.this$0);
      if (ButtonFactory.ButtonHighlighter.access$200(this.this$0).getModel().isRollover()) {
         ButtonFactory.ButtonHighlighter.access$102(this.this$0, 0L);
      }

   }
}
