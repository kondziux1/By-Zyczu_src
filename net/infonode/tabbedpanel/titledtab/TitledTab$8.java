package net.infonode.tabbedpanel.titledtab;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import net.infonode.tabbedpanel.TabSelectTrigger;
import net.infonode.tabbedpanel.TabbedUtils;

class TitledTab$8 extends MouseAdapter {
   TitledTab$8(TitledTab var1) {
      super();
      this.this$0 = var1;
   }

   public void mousePressed(MouseEvent var1) {
      this.updateFocus(TabSelectTrigger.MOUSE_PRESS);
   }

   public void mouseReleased(MouseEvent var1) {
      this.updateFocus(TabSelectTrigger.MOUSE_RELEASE);
   }

   private void updateFocus(TabSelectTrigger var1) {
      if (this.this$0.isEnabled()
         && TitledTab.access$200(this.this$0).getFocusable()
         && this.this$0.getTabbedPanel() != null
         && this.this$0.getTabbedPanel().getProperties().getTabSelectTrigger() == var1) {
         Component var2 = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
         if (var2 instanceof TitledTab && ((TitledTab)var2).getTabbedPanel() == this.this$0.getTabbedPanel()) {
            this.this$0.requestFocusInWindow();
         } else if (this.this$0.isSelected() || TabbedUtils.getParentTabbedPanel(var2) != this.this$0.getTabbedPanel()) {
            this.this$0.requestFocusInWindow();
         }
      }

   }
}
