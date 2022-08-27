package net.infonode.util.collection.notifymap;

import java.util.ArrayList;
import net.infonode.util.ValueChange;
import net.infonode.util.collection.map.ConstVectorMap;
import net.infonode.util.collection.map.MapAdapter;
import net.infonode.util.collection.map.base.ConstMap;
import net.infonode.util.collection.map.base.ConstMapIterator;
import net.infonode.util.signal.SignalListener;

public class ConstChangeNotifyVectorMap extends AbstractConstChangeNotifyMap {
   private ConstVectorMap vectorMap = new ConstVectorMap();
   private ArrayList mapListeners;

   public ConstChangeNotifyVectorMap() {
      super();
   }

   protected void firstListenerAdded() {
      this.mapListeners = new ArrayList(this.vectorMap.getMapCount() + 2);

      for(int var1 = 0; var1 < this.vectorMap.getMapCount(); ++var1) {
         this.addMapListener(var1);
      }

   }

   protected void lastListenerRemoved() {
      for(int var1 = this.vectorMap.getMapCount() - 1; var1 >= 0; --var1) {
         this.removeMapListener(var1);
      }

      this.mapListeners = null;
   }

   private Object getValue(Object var1, int var2, int var3) {
      for(int var4 = var2; var4 < var3; ++var4) {
         Object var5 = this.getMap(var4).get(var1);
         if (var5 != null) {
            return var5;
         }
      }

      return null;
   }

   public int getMapIndex(ConstMap var1) {
      return this.vectorMap.getMapIndex(var1);
   }

   public void addMap(ConstChangeNotifyMap var1) {
      this.addMap(this.vectorMap.getMapCount(), var1);
   }

   public void addMap(int var1, ConstChangeNotifyMap var2) {
      this.vectorMap.addMap(var1, var2);
      if (this.getChangeSignalInternal().hasListeners()) {
         this.addMapListener(var1);
         MapAdapter var3 = new MapAdapter();

         for(ConstMapIterator var4 = var2.constIterator(); var4.atEntry(); var4.next()) {
            Object var5 = this.getValue(var4.getKey(), 0, var1);
            if (var5 == null) {
               Object var6 = var4.getValue();
               var3.put(var4.getKey(), new ValueChange(this.getValue(var4.getKey(), var1 + 1, this.getMapCount()), var6));
            }
         }

         this.fireEntriesChanged(var3);
      }

   }

   private void addMapListener(int var1) {
      if (this.mapListeners == null) {
         this.mapListeners = new ArrayList(var1 + 2);
      }

      ConstChangeNotifyMap var2 = this.getMap(var1);
      ConstChangeNotifyVectorMap$1 var3 = new ConstChangeNotifyVectorMap$1(this, var2);
      this.mapListeners.add(var1, var3);
      var2.getChangeSignal().add(var3);
   }

   private void removeMapListener(int var1) {
      ConstChangeNotifyMap var2 = this.getMap(var1);
      var2.getChangeSignal().remove((SignalListener)this.mapListeners.get(var1));
      this.mapListeners.remove(var1);
   }

   public int getMapCount() {
      return this.vectorMap.getMapCount();
   }

   public void removeMap(int var1) {
      if (this.getChangeSignalInternal().hasListeners()) {
         this.removeMapListener(var1);
      }

      ConstMap var2 = this.vectorMap.removeMap(var1);
      if (this.getChangeSignalInternal().hasListeners()) {
         MapAdapter var3 = new MapAdapter();

         for(ConstMapIterator var4 = var2.constIterator(); var4.atEntry(); var4.next()) {
            Object var5 = this.getValue(var4.getKey(), 0, var1);
            if (var5 == null) {
               Object var6 = var4.getValue();
               var3.put(var4.getKey(), new ValueChange(var6, this.getValue(var4.getKey(), var1, this.getMapCount())));
            }
         }

         this.fireEntriesChanged(var3);
      }

   }

   public Object get(Object var1) {
      return this.vectorMap.get(var1);
   }

   public boolean containsKey(Object var1) {
      return this.vectorMap.containsKey(var1);
   }

   public boolean containsValue(Object var1) {
      return this.vectorMap.containsValue(var1);
   }

   public boolean isEmpty() {
      return this.vectorMap.isEmpty();
   }

   public ConstChangeNotifyMap getMap(int var1) {
      return (ConstChangeNotifyMap)this.vectorMap.getMap(var1);
   }

   public ConstMapIterator constIterator() {
      return this.vectorMap.constIterator();
   }
}
