package net.infonode.tabbedpanel;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import net.infonode.gui.hover.HoverListener;

class TabbedPanel$2 extends TabbedPanel.HoverablePanel {
   TabbedPanel$2(TabbedPanel var1, LayoutManager var2, HoverListener var3) {
      super(var2, var3);
      this.this$0 = var1;
   }

   public Dimension getPreferredSize() {
      Dimension var1 = super.getPreferredSize();
      if (this.this$0.getTabCount() == 0) {
         Insets var2 = this.getInsets();
         Dimension var3 = TabbedPanel.access$200(this.this$0).getPreferredSize();
         var1 = new Dimension(var2.left + var2.right + var3.width, var2.top + var2.bottom + var3.height);
      }

      return var1;
   }
}
