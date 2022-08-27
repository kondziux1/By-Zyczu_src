package net.infonode.tabbedpanel.hover;

import net.infonode.gui.hover.HoverEvent;
import net.infonode.gui.hover.HoverListener;

class TitledTabDelayedMouseExitHoverAction$1 implements HoverListener {
   TitledTabDelayedMouseExitHoverAction$1(TitledTabDelayedMouseExitHoverAction var1) {
      super();
      this.this$0 = var1;
   }

   public void mouseEntered(HoverEvent var1) {
      this.this$0.getHoverListener().mouseEntered(var1);
   }

   public void mouseExited(HoverEvent var1) {
      this.this$0.getHoverListener().mouseExited(var1);
   }
}
