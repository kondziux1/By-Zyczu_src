package net.infonode.docking;

class RootWindow$2 implements Runnable {
   RootWindow$2(RootWindow var1) {
      super();
      this.this$0 = var1;
   }

   public void run() {
      if (RootWindow.access$200(this.this$0)) {
         RootWindow.access$202(this.this$0, false);
         this.this$0.getWindowItem().cleanUp();
      }

   }
}
