package net.infonode.docking;

class DockingWindow$5 implements Runnable {
   DockingWindow$5(DockingWindow var1) {
      super();
      this.this$0 = var1;
   }

   public void run() {
      DockingWindow.access$100(this.this$0).removeChildWindow(this.this$0);
   }
}
