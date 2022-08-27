package net.infonode.docking;

class FocusManager$6 implements Runnable {
   FocusManager$6(FocusManager$5 var1) {
      super();
      this.this$0 = var1;
   }

   public void run() {
      FocusManager$5.access$500(this.this$0).restoreFocus();
   }
}
