package net.infonode.util.collection.notifymap;

import net.infonode.util.ValueChange;
import net.infonode.util.collection.map.SingleValueMap;
import net.infonode.util.collection.map.base.ConstMap;
import net.infonode.util.signal.Signal;
import net.infonode.util.signal.SignalHook;

public abstract class AbstractConstChangeNotifyMap implements ConstChangeNotifyMap {
   private Signal changeSignal = new AbstractConstChangeNotifyMap$1(this);

   public AbstractConstChangeNotifyMap() {
      super();
   }

   protected void firstListenerAdded() {
   }

   protected void lastListenerRemoved() {
   }

   protected void listenerRemoved() {
   }

   protected void listenerAdded() {
   }

   public SignalHook getChangeSignal() {
      return this.changeSignal.getHook();
   }

   protected Signal getChangeSignalInternal() {
      return this.changeSignal;
   }

   protected void fireEntryRemoved(Object var1, Object var2) {
      this.fireEntryChanged(var1, var2, null);
   }

   protected void fireEntryChanged(Object var1, Object var2, Object var3) {
      this.fireEntriesChanged(new SingleValueMap(var1, new ValueChange(var2, var3)));
   }

   protected void fireEntriesChanged(ConstMap var1) {
      if (!var1.isEmpty()) {
         this.changeSignal.emit(var1);
      }

   }
}
