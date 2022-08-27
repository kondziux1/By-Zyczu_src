package net.infonode.docking;

class DockingWindow$8 implements Runnable {
   DockingWindow$8(DockingWindow var1, Runnable var2) {
      super();
      this.val$window = var1;
      this.val$runnable = var2;
   }

   public void run() {
      DockingWindow.beginOptimize(this.val$window);

      try {
         this.val$runnable.run();
      } finally {
         DockingWindow.endOptimize();
      }

   }
}
