package net.infonode.docking;

class DockingWindow$9 implements Runnable {
   DockingWindow$9(DockingWindow var1, DockingWindow var2) {
      super();
      this.this$0 = var1;
      this.val$window = var2;
   }

   public void run() {
      if (this.val$window.isShowingInRootWindow()) {
         this.val$window.fireWindowHidden(this.val$window);
      }

      DockingWindow.access$200(this.val$window, null);
      if (DockingWindow.access$300(this.this$0) == this.val$window) {
         DockingWindow.access$302(this.this$0, null);
      }

      this.this$0.doRemoveWindow(this.val$window);
      this.this$0.fireTitleChanged();
      DockingWindow.access$400(this.val$window, this.this$0, this.val$window);
      DockingWindow.access$400(this.this$0, this.this$0, this.val$window);
      this.this$0.afterWindowRemoved(this.val$window);
   }
}
