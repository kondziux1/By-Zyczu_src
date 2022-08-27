package net.infonode.util.signal;

public interface SignalHook {
   void add(SignalListener var1);

   void addWeak(SignalListener var1);

   boolean remove(SignalListener var1);
}
