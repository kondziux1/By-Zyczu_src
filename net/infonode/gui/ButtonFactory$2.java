package net.infonode.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

class ButtonFactory$2 extends MouseAdapter {
   ButtonFactory$2(JButton var1) {
      super();
      this.val$button = var1;
   }

   public void mousePressed(MouseEvent var1) {
      this.val$button.setBorder(ButtonFactory.access$400());
   }

   public void mouseReleased(MouseEvent var1) {
      this.val$button.setBorder(ButtonFactory.access$500());
   }
}
