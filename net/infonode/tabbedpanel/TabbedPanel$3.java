package net.infonode.tabbedpanel;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import net.infonode.gui.ComponentUtil;
import net.infonode.gui.hover.HoverListener;

class TabbedPanel$3 extends TabbedPanel.HoverablePanel {
   TabbedPanel$3(TabbedPanel var1, LayoutManager var2, HoverListener var3) {
      super(var2, var3);
      this.this$0 = var1;
   }

   public Dimension getMaximumSize() {
      return this.getPreferredSize();
   }

   public Dimension getMinimumSize() {
      return this.getPreferredSize();
   }

   public Dimension getPreferredSize() {
      Dimension var1 = super.getPreferredSize();
      Insets var2 = this.getInsets();
      if (ComponentUtil.hasVisibleChildren(this)) {
         if (TabbedPanel.access$300(this.this$0).isHorizontal()) {
            int var4 = ComponentUtil.getPreferredMaxWidth(this.getComponents()) + var2.left + var2.right;
            return new Dimension(var4, var1.height);
         } else {
            int var3 = ComponentUtil.getPreferredMaxHeight(this.getComponents()) + var2.top + var2.bottom;
            return new Dimension(var1.width, var3);
         }
      } else {
         return new Dimension(0, 0);
      }
   }
}
