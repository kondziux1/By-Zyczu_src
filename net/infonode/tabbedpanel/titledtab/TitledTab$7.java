package net.infonode.tabbedpanel.titledtab;

import java.awt.Container;
import net.infonode.gui.layout.StackableLayout;

class TitledTab$7 extends StackableLayout {
   TitledTab$7(TitledTab var1, Container var2) {
      super(var2);
      this.this$0 = var1;
   }

   public void layoutContainer(Container var1) {
      super.layoutContainer(var1);
      TitledTab.StatePanel var2 = (TitledTab.StatePanel)this.getVisibleComponent();
      var2.activateTitleComponent();
   }
}
