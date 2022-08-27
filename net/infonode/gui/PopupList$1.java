package net.infonode.gui;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class PopupList$1 implements ListSelectionListener {
   PopupList$1(PopupList.Popup var1) {
      super();
      this.this$1 = var1;
   }

   public void valueChanged(ListSelectionEvent var1) {
      if (!var1.getValueIsAdjusting()) {
         this.this$1.setVisible(false);
      }

   }
}
