package net.infonode.docking;

import net.infonode.docking.model.SplitWindowItem;
import net.infonode.docking.model.WindowItem;

class DockingWindow$15 implements Runnable {
   DockingWindow$15(DockingWindow var1, RootWindow var2, WindowItem var3) {
      super();
      this.this$0 = var1;
      this.val$rootWindow = var2;
      this.val$topItem = var3;
   }

   public void run() {
      DockingWindow var1 = this.val$rootWindow.getWindow();
      if (var1 == null) {
         WindowItem var2 = this.val$rootWindow.getWindowItem();
         if (var2.getWindowCount() == 0) {
            var2.addWindow(this.val$topItem);
         } else {
            SplitWindowItem var3 = new SplitWindowItem();
            var3.addWindow(var2.getWindow(0));
            var3.addWindow(this.val$topItem);
            var2.addWindow(var3);
         }

         this.val$rootWindow.setWindow(DockingWindow.access$900(this.this$0, this.val$topItem, this.this$0.getWindowItem()));
      } else {
         SplitWindow var4 = new SplitWindow(true);
         var4.getWindowItem().addWindow(this.val$rootWindow.getWindowItem().getWindow(0));
         var4.getWindowItem().addWindow(this.val$topItem);
         this.val$rootWindow.setWindow(var4);
         var4.setWindows(var1, DockingWindow.access$900(this.this$0, this.val$topItem, this.this$0.getWindowItem()));
         this.val$rootWindow.getWindowItem().addWindow(var4.getWindowItem());
      }

   }
}
