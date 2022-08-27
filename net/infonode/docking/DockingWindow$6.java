package net.infonode.docking;

import net.infonode.docking.internal.WindowAncestors;

class DockingWindow$6 implements Runnable {
   DockingWindow$6(DockingWindow var1, DockingWindow var2, DockingWindow var3, DockingWindow var4, WindowAncestors var5) {
      super();
      this.this$0 = var1;
      this.val$nw = var2;
      this.val$oldWindow = var3;
      this.val$newWindow = var4;
      this.val$oldAncestors = var5;
   }

   public void run() {
      if (this.val$nw != this.val$oldWindow) {
         if (this.val$nw.getWindowParent() != null) {
            this.val$nw.getWindowParent().removeChildWindow(this.val$nw);
         }

         DockingWindow.access$200(this.val$nw, this.this$0);
         if (this.val$oldWindow.isShowingInRootWindow()) {
            this.val$oldWindow.fireWindowHidden(this.val$oldWindow);
         }

         DockingWindow.access$200(this.val$oldWindow, null);
         if (this.val$oldWindow == DockingWindow.access$300(this.this$0)) {
            DockingWindow.access$302(this.this$0, null);
         }

         this.this$0.doReplace(this.val$oldWindow, this.val$nw);
         this.this$0.fireTitleChanged();
         DockingWindow.access$400(this.val$oldWindow, this.this$0, this.val$oldWindow);
         DockingWindow.access$400(this.this$0, this.this$0, this.val$oldWindow);
         DockingWindow.access$500(this.val$nw, this.this$0, this.val$nw);
         if (this.val$nw.isShowingInRootWindow()) {
            this.val$nw.fireWindowShown(this.val$nw);
         }

         this.val$newWindow.notifyListeners(this.val$oldAncestors);
      }
   }
}
