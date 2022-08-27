package net.infonode.docking;

import net.infonode.docking.internal.WindowAncestors;

class SplitWindow$3 implements Runnable {
   SplitWindow$3(SplitWindow var1, DockingWindow var2, DockingWindow var3) {
      super();
      this.this$0 = var1;
      this.val$leftWindow = var2;
      this.val$rightWindow = var3;
   }

   public void run() {
      WindowAncestors var1 = this.val$leftWindow.storeAncestors();
      WindowAncestors var2 = this.val$rightWindow.storeAncestors();
      DockingWindow var3 = this.val$leftWindow.getContentWindow(this.this$0);
      DockingWindow var4 = this.val$rightWindow.getContentWindow(this.this$0);
      var3.detach();
      var4.detach();
      if (this.this$0.getLeftWindow() != null) {
         this.this$0.removeWindow(this.this$0.getLeftWindow());
      }

      if (this.this$0.getRightWindow() != null) {
         this.this$0.removeWindow(this.this$0.getRightWindow());
      }

      SplitWindow.access$002(this.this$0, var3);
      SplitWindow.access$102(this.this$0, var4);
      SplitWindow.access$200(this.this$0).setComponents(var3, var4);
      this.this$0.addWindow(var3);
      this.this$0.addWindow(var4);
      if (this.this$0.getUpdateModel()) {
         this.this$0.addWindowItem(this.this$0.getLeftWindow(), -1);
         this.this$0.addWindowItem(this.this$0.getRightWindow(), -1);
         this.this$0.cleanUpModel();
      }

      this.val$leftWindow.notifyListeners(var1);
      this.val$rightWindow.notifyListeners(var2);
   }
}
