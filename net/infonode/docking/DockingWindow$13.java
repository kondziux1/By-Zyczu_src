package net.infonode.docking;

class DockingWindow$13 implements Runnable {
   DockingWindow$13(DockingWindow$12 var1, DockingWindow var2) {
      super();
      this.this$1 = var1;
      this.val$window = var2;
   }

   public void run() {
      try {
         this.val$window.beforeDrop(DockingWindow$12.access$700(this.this$1));
         TabWindow var1 = new TabWindow();
         DockingWindow.access$100(DockingWindow$12.access$700(this.this$1)).replaceChildWindow(DockingWindow$12.access$700(this.this$1), var1);
         var1.addTab(DockingWindow$12.access$700(this.this$1));
         var1.addTab(this.val$window);
      } catch (OperationAbortedException var2) {
      }

   }
}
