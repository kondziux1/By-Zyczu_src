package net.infonode.gui;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.plaf.ButtonUI;

class ButtonFactory$3 extends JButton {
   ButtonFactory$3(Icon var1) {
      super(var1);
   }

   public void setUI(ButtonUI var1) {
      super.setUI(new FlatIconButtonUI());
   }
}
