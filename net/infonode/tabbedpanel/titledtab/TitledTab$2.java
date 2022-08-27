package net.infonode.tabbedpanel.titledtab;

import java.awt.Dimension;
import javax.swing.Icon;
import net.infonode.gui.RotatableLabel;

class TitledTab$2 extends RotatableLabel {
   TitledTab$2(TitledTab.StatePanel var1, String var2, Icon var3) {
      super(var2, var3);
      this.this$1 = var1;
   }

   public Dimension getPreferredSize() {
      Dimension var1 = super.getPreferredSize();
      String var2 = this.getText();
      Icon var3 = this.getIcon();
      if (var2 == null || var3 == null) {
         this.setText(" ");
         this.setIcon(TitledTab.StatePanel.access$000(this.this$1));
         if (this.getDirection().isHorizontal()) {
            var1 = new Dimension(var1.width, super.getPreferredSize().height);
         } else {
            var1 = new Dimension(super.getPreferredSize().width, var1.height);
         }

         this.setText(var2);
         this.setIcon(var3);
      }

      return var1;
   }
}
