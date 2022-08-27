package net.infonode.gui;

import javax.swing.AbstractButton;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

class PopupList$4 implements PopupMenuListener {
   PopupList$4(PopupList var1) {
      super();
      this.this$0 = var1;
   }

   public void popupMenuCanceled(PopupMenuEvent var1) {
   }

   public void popupMenuWillBecomeInvisible(PopupMenuEvent var1) {
      ((PopupList.PopupButtonModel)((AbstractButton)this.this$0.getComponent(0)).getModel()).setPressedInternal(false);
   }

   public void popupMenuWillBecomeVisible(PopupMenuEvent var1) {
      ((PopupList.PopupButtonModel)((AbstractButton)this.this$0.getComponent(0)).getModel()).setPressedInternal(true);
   }
}
