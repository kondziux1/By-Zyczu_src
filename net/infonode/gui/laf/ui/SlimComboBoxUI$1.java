package net.infonode.gui.laf.ui;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

class SlimComboBoxUI$1 extends BasicComboBoxRenderer {
   SlimComboBoxUI$1(SlimComboBoxUI var1) {
      super();
      this.this$0 = var1;
   }

   public Component getListCellRendererComponent(JList var1, Object var2, int var3, boolean var4, boolean var5) {
      JLabel var6 = (JLabel)super.getListCellRendererComponent(var1, var2, var3, var4, var5);
      var6.setBorder(var3 == -1 ? noFocusBorder : (var5 ? SlimComboBoxUI.FOCUS_BORDER : SlimComboBoxUI.NORMAL_BORDER));
      return var6;
   }
}
