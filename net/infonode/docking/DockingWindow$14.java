package net.infonode.docking;

import net.infonode.docking.model.SplitWindowItem;
import net.infonode.docking.model.TabWindowItem;
import net.infonode.docking.model.WindowItem;

class DockingWindow$14 implements Runnable {
   DockingWindow$14(DockingWindow var1, WindowItem var2, DockingWindow var3) {
      super();
      this.this$0 = var1;
      this.val$fitem = var2;
      this.val$w1 = var3;
   }

   public void run() {
      if (this.val$fitem.getParent() instanceof SplitWindowItem) {
         SplitWindowItem var1 = (SplitWindowItem)this.val$fitem.getParent();
         boolean var2 = var1.getWindow(0) == this.val$fitem;
         SplitWindow var3 = new SplitWindow(var1.isHorizontal(), var1.getDividerLocation(), null, null, var1);
         this.val$w1.getWindowParent().internalReplaceChildWindow(this.val$w1, var3);
         DockingWindow var4 = DockingWindow.access$900(this.this$0, var1, DockingWindow.access$800(this.this$0));
         DockingWindow var5 = DockingWindow.access$900(this.val$w1, var1, DockingWindow.access$800(this.val$w1));
         var3.setWindows(var2 ? var4 : var5, var2 ? var5 : var4);
      } else if (this.val$fitem.getParent() instanceof TabWindowItem) {
         TabWindowItem var6 = (TabWindowItem)this.val$fitem.getParent();
         TabWindow var7 = new TabWindow(null, var6);
         this.val$w1.getWindowParent().internalReplaceChildWindow(this.val$w1, var7);
         DockingWindow.access$1000(var7, this.this$0);
         DockingWindow.access$1000(var7, this.val$w1.getOptimizedWindow());
      }

   }
}
