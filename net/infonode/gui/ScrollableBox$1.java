package net.infonode.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import net.infonode.gui.layout.LayoutUtil;

class ScrollableBox$1 implements LayoutManager {
   ScrollableBox$1(ScrollableBox var1) {
      super();
      this.this$0 = var1;
   }

   public void addLayoutComponent(String var1, Component var2) {
   }

   public void layoutContainer(Container var1) {
      if (var1.getComponentCount() > 0) {
         Component var2 = var1.getComponent(0);
         var2.setBounds(0, 0, var2.getPreferredSize().width, var2.getPreferredSize().height);
         var2.validate();
         ScrollableBox.access$000(this.this$0);
      }

   }

   public Dimension minimumLayoutSize(Container var1) {
      Dimension var2 = var1.getComponentCount() == 0 ? new Dimension(0, 0) : var1.getComponent(0).getMinimumSize();
      return LayoutUtil.add(ScrollableBox.access$100(this.this$0) ? new Dimension(var2.width, 0) : new Dimension(0, var2.height), var1.getInsets());
   }

   public Dimension preferredLayoutSize(Container var1) {
      return var1.getComponentCount() == 0 ? new Dimension(0, 0) : var1.getComponent(0).getPreferredSize();
   }

   public void removeLayoutComponent(Component var1) {
   }
}
