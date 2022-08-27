package net.infonode.docking;

import java.awt.Dimension;
import javax.swing.JComponent;
import net.infonode.tabbedpanel.TabbedPanel;

class AbstractTabWindow$3 extends TabbedPanel {
   AbstractTabWindow$3(AbstractTabWindow var1, JComponent var2, boolean var3) {
      super(var2, var3);
      this.this$0 = var1;
   }

   public Dimension getMinimumSize() {
      return this.this$0.getTabWindowProperties().getRespectChildWindowMinimumSize()
         ? super.getMinimumSize()
         : AbstractTabWindow.access$400(this.this$0, super.getMinimumSize());
   }
}
