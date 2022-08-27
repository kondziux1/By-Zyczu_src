package net.infonode.gui;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.SwingUtilities;

class PopupList$2 extends MouseMotionAdapter {
   PopupList$2(PopupList.Popup var1) {
      super();
      this.this$1 = var1;
   }

   public void mouseDragged(MouseEvent var1) {
      if (SwingUtilities.isLeftMouseButton(var1)) {
         Component var2 = (Component)var1.getSource();
         Point var3 = SwingUtilities.convertPoint(var2, var1.getPoint(), PopupList.Popup.access$000(this.this$1));
         int var4 = PopupList.Popup.access$100(this.this$1)
            .locationToIndex(SwingUtilities.convertPoint(PopupList.Popup.access$000(this.this$1), var3, PopupList.Popup.access$100(this.this$1)));
         if (!var2.contains(var1.getPoint())
            && (
               PopupList.Popup.access$000(this.this$1).contains(var3)
                  || var3.getY() > (double)(PopupList.Popup.access$000(this.this$1).getY() + PopupList.Popup.access$000(this.this$1).getHeight())
                  || var3.getY() < (double)PopupList.Popup.access$000(this.this$1).getY()
            )) {
            PopupList.Popup.access$100(this.this$1).setSelectedIndex(var4);
            PopupList.Popup.access$100(this.this$1).ensureIndexIsVisible(var4);
         }
      }

   }
}
