package net.infonode.tabbedpanel.titledtab;

import java.awt.LayoutManager;

class TitledTab$3 extends TitledTab.HoverablePanel {
   TitledTab$3(TitledTab var1, LayoutManager var2) {
      super(var2);
      this.this$0 = var1;
   }

   public boolean contains(int var1, int var2) {
      return this.getComponentCount() > 0 && this.getComponent(0).contains(var1, var2);
   }

   public boolean inside(int var1, int var2) {
      return this.getComponentCount() > 0 && this.getComponent(0).inside(var1, var2);
   }
}
