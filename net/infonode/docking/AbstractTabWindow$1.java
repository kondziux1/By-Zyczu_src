package net.infonode.docking;

import java.awt.event.MouseEvent;
import net.infonode.docking.internalutil.DropAction;

class AbstractTabWindow$1 extends DropAction {
   AbstractTabWindow$1(AbstractTabWindow var1) {
      super();
      this.this$0 = var1;
   }

   public boolean showTitle() {
      return false;
   }

   public void execute(DockingWindow var1, MouseEvent var2) {
      if (var1.getWindowParent() == this.this$0) {
         this.this$0.updateWindowItem(var1);
      } else {
         int var3 = AbstractTabWindow.access$100(this.this$0).getTabIndex(AbstractTabWindow.access$000(this.this$0));
         AbstractTabWindow.access$200(this.this$0);

         try {
            var1.beforeDrop(this.this$0);
            if (var2.isShiftDown()) {
               this.this$0.addTabNoSelect(var1, var3);
            } else {
               this.this$0.addTab(var1, var3);
            }
         } catch (OperationAbortedException var5) {
         }
      }

   }

   public void clear(DockingWindow var1, DropAction var2) {
      if (var2 != this) {
         if (var1.getWindowParent() == this.this$0) {
            WindowTab var3 = var1.getTab();
            boolean var4 = var3.isSelected();
            AbstractTabWindow.access$100(this.this$0).removeTab(var3);
            AbstractTabWindow.access$100(this.this$0).insertTab(var3, AbstractTabWindow.access$300(this.this$0));
            if (var4) {
               var3.setSelected(true);
            }
         } else {
            AbstractTabWindow.access$200(this.this$0);
         }
      }

   }
}
