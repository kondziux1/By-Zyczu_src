package net.infonode.gui;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

class PopupList$3 extends MouseAdapter {
   PopupList$3(PopupList.Popup var1) {
      super();
      this.this$1 = var1;
   }

   public void mousePressed(MouseEvent var1) {
      if (SwingUtilities.isLeftMouseButton(var1)) {
         if (this.this$1.isVisible()) {
            this.this$1.setVisible(false);
            return;
         }

         PopupList.Popup.access$200(this.this$1);
         PopupList.Popup.access$000(this.this$1).setViewportView(null);
         PopupList.Popup.access$100(this.this$1).setValueIsAdjusting(true);
         PopupList.access$400(PopupList.Popup.access$300(this.this$1));
         PopupList.Popup.access$100(this.this$1).setVisibleRowCount(Math.min(PopupList.Popup.access$100(this.this$1).getModel().getSize(), 8));
         PopupList.Popup.access$502(this.this$1, PopupList.Popup.access$100(this.this$1).getSelectedIndex());
         PopupList.Popup.access$100(this.this$1).ensureIndexIsVisible(PopupList.Popup.access$500(this.this$1));
         PopupList.Popup.access$000(this.this$1).setViewportView(PopupList.Popup.access$100(this.this$1));
         Component var2 = (Component)var1.getSource();
         this.this$1.show(var2, 0, var2.getHeight());
      }

   }

   public void mouseReleased(MouseEvent var1) {
      if (SwingUtilities.isLeftMouseButton(var1)) {
         if (!this.this$1.isVisible()) {
            return;
         }

         Point var2 = SwingUtilities.convertPoint((Component)var1.getSource(), var1.getPoint(), PopupList.Popup.access$000(this.this$1));
         if (PopupList.Popup.access$000(this.this$1).contains(var2)) {
            PopupList.Popup.access$100(this.this$1).setValueIsAdjusting(false);
         } else if (!((Component)var1.getSource()).contains(var1.getPoint())) {
            PopupList.Popup.access$100(this.this$1).setSelectedIndex(PopupList.Popup.access$500(this.this$1));
            PopupList.Popup.access$100(this.this$1).setValueIsAdjusting(false);
         }
      }

   }
}
