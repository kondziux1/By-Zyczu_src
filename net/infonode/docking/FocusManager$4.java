package net.infonode.docking;

class FocusManager$4 implements Runnable {
   FocusManager$4(FocusManager$3 var1) {
      super();
      this.this$1 = var1;
   }

   public void run() {
      FocusManager$3.access$400(this.this$1).requestFocusInWindow();
   }
}
