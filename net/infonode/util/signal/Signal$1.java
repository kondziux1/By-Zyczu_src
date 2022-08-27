package net.infonode.util.signal;

class Signal$1 implements Runnable {
   Signal$1() {
      super();
   }

   public void run() {
      try {
         while(true) {
            ((Signal.WeakListener)Signal.access$000().remove()).remove();
         }
      } catch (InterruptedException var2) {
      }
   }
}
