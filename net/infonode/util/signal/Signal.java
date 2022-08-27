package net.infonode.util.signal;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Iterator;
import net.infonode.util.collection.CopyOnWriteArrayList;
import net.infonode.util.collection.EmptyIterator;

public class Signal {
   private static ReferenceQueue refQueue = new ReferenceQueue();
   private boolean reverseNotifyOrder;
   private CopyOnWriteArrayList listeners;
   private Signal.SignalHookImpl signalHook = new Signal.SignalHookImpl(null);

   public Signal() {
      this(true);
   }

   public Signal(boolean var1) {
      super();
      this.reverseNotifyOrder = var1;
   }

   protected void firstListenerAdded() {
   }

   protected void lastListenerRemoved() {
   }

   public synchronized void addListener(SignalListener var1) {
      if (this.listeners == null) {
         this.listeners = new CopyOnWriteArrayList(2);
      }

      this.listeners.add(var1);
      if (this.listeners.size() == 1) {
         this.firstListenerAdded();
      }

   }

   public synchronized boolean removeListener(SignalListener var1) {
      if (this.listeners != null) {
         for(int var2 = 0; var2 < this.listeners.size(); ++var2) {
            Object var3 = this.listeners.get(var2);
            if (var3 == var1 || var3 instanceof Signal.WeakListener && ((Signal.WeakListener)var3).get() == var1) {
               this.removeListener(var2);
               return true;
            }
         }
      }

      return false;
   }

   protected synchronized void removeWeakListener(Signal.WeakListener var1) {
      if (this.listeners != null) {
         for(int var2 = 0; var2 < this.listeners.size(); ++var2) {
            Object var3 = this.listeners.get(var2);
            if (var3 == var1) {
               this.removeListener(var2);
               break;
            }
         }
      }

   }

   protected synchronized void removeListener(int var1) {
      this.listeners.remove(var1);
      if (this.listeners.size() == 0) {
         this.listeners = null;
         this.lastListenerRemoved();
      }

   }

   public synchronized boolean hasListeners() {
      return this.listeners != null && this.listeners.size() > 0;
   }

   public synchronized Iterator iterator() {
      return (Iterator)(this.listeners == null ? EmptyIterator.INSTANCE : this.listeners.iterator());
   }

   public SignalHook getHook() {
      return this.signalHook;
   }

   public synchronized void emit(Object var1) {
      Object[] var2;
      int var3;
      synchronized(this) {
         if (this.listeners == null) {
            return;
         }

         var2 = this.listeners.getElements();
         var3 = this.listeners.size();
      }

      if (this.reverseNotifyOrder) {
         for(int var4 = var3 - 1; var4 >= 0; --var4) {
            ((SignalListener)var2[var4]).signalEmitted(this, var1);
         }
      } else {
         for(int var7 = 0; var7 < var3; ++var7) {
            ((SignalListener)var2[var7]).signalEmitted(this, var1);
         }
      }

   }

   public void removeListeners(Collection var1) {
      this.listeners.removeAll(var1);
   }

   static {
      Thread var0 = new Thread(new Signal$1());
      var0.setDaemon(true);
      var0.start();
   }

   private class SignalHookImpl implements SignalHook {
      private SignalHookImpl() {
         super();
      }

      public void add(SignalListener var1) {
         Signal.this.addListener(var1);
      }

      public void addWeak(SignalListener var1) {
         Signal.this.addListener(new Signal.WeakListener(var1, Signal.refQueue, this));
      }

      public boolean remove(SignalListener var1) {
         return Signal.this.removeListener(var1);
      }

      public void removeWeak(Signal.WeakListener var1) {
         Signal.this.removeWeakListener(var1);
      }
   }

   private static class WeakListener extends WeakReference implements SignalListener {
      private Signal.SignalHookImpl hook;

      protected WeakListener(SignalListener var1, ReferenceQueue var2, Signal.SignalHookImpl var3) {
         super(var1, var2);
         this.hook = var3;
      }

      public void remove() {
         this.hook.removeWeak(this);
      }

      public void signalEmitted(Signal var1, Object var2) {
         SignalListener var3 = (SignalListener)this.get();
         if (var3 != null) {
            var3.signalEmitted(var1, var2);
         }

      }
   }
}
