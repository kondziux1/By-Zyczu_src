package net.infonode.docking;

class AbstractTabWindow$7 implements Runnable {
   AbstractTabWindow$7(AbstractTabWindow$5 var1) {
      super();
      this.this$1 = var1;
   }

   public void run() {
      AbstractTabWindow$5.access$500(this.this$1).updateButtonVisibility();
   }
}
