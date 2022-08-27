package net.infonode.docking;

class AbstractTabWindow$6 implements Runnable {
   AbstractTabWindow$6(AbstractTabWindow$5 var1) {
      super();
      this.this$1 = var1;
   }

   public void run() {
      AbstractTabWindow$5.access$500(this.this$1).updateButtonVisibility();
   }
}
