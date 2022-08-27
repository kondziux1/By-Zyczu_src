package net.infonode.util.collection.notifymap;

import net.infonode.util.signal.Signal;
import net.infonode.util.signal.SignalListener;

class AbstractConstChangeNotifyMap$1 extends Signal {
   AbstractConstChangeNotifyMap$1(AbstractConstChangeNotifyMap var1) {
      super();
      this.this$0 = var1;
   }

   protected void firstListenerAdded() {
      this.this$0.firstListenerAdded();
   }

   protected void lastListenerRemoved() {
      this.this$0.lastListenerRemoved();
   }

   protected synchronized void removeListener(int var1) {
      super.removeListener(var1);
      this.this$0.listenerRemoved();
   }

   public synchronized void addListener(SignalListener var1) {
      super.addListener(var1);
      this.this$0.listenerAdded();
   }
}
