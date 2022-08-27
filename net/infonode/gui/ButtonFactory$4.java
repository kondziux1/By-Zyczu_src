package net.infonode.gui;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.plaf.ButtonUI;

class ButtonFactory$4 extends JButton {
   ButtonFactory$4(Icon var1) {
      super(var1);
   }

   public void setUI(ButtonUI var1) {
      super.setUI(new FlatIconButtonUI());
   }
}
