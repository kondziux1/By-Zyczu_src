package net.infonode.util.collection.notifymap;

import net.infonode.util.ValueChange;
import net.infonode.util.collection.map.MapAdapter;
import net.infonode.util.collection.map.base.ConstMapIterator;
import net.infonode.util.collection.map.base.Map;
import net.infonode.util.collection.map.base.MapIterator;

public class ChangeNotifyMapWrapper extends AbstractChangeNotifyMap {
   private Map map;

   public ChangeNotifyMapWrapper(Map var1) {
      super();
      this.map = var1;
   }

   public Map getMap() {
      return this.map;
   }

   public Object get(Object var1) {
      return this.map.get(var1);
   }

   public boolean containsKey(Object var1) {
      return this.map.containsKey(var1);
   }

   public boolean containsValue(Object var1) {
      return this.map.containsValue(var1);
   }

   public boolean isEmpty() {
      return this.map.isEmpty();
   }

   public Object put(Object var1, Object var2) {
      Object var3 = this.map.put(var1, var2);
      this.fireEntryChanged(var1, var3, var2);
      return var3;
   }

   public Object remove(Object var1) {
      Object var2 = this.map.remove(var1);
      this.fireEntryRemoved(var1, var2);
      return var2;
   }

   public void clear() {
      MapAdapter var1 = new MapAdapter();
      ConstMapIterator var2 = this.map.constIterator();

      while(var2.atEntry()) {
         var1.put(var2.getKey(), new ValueChange(var2.getValue(), null));
         var2.next();
      }

      this.map.clear();
      this.fireEntriesChanged(var1);
   }

   public MapIterator iterator() {
      return new ChangeNotifyMapWrapper.Iterator(this.map.iterator());
   }

   private class Iterator implements MapIterator {
      private MapIterator iterator;

      public Iterator(MapIterator var2) {
         super();
         this.iterator = var2;
      }

      public void remove() {
         this.iterator.remove();
         ChangeNotifyMapWrapper.this.fireEntryRemoved(this.iterator.getKey(), this.iterator.getValue());
      }

      public Object getKey() {
         return this.iterator.getKey();
      }

      public Object getValue() {
         return this.iterator.getValue();
      }

      public void next() {
         this.iterator.next();
      }

      public boolean atEntry() {
         return this.iterator.atEntry();
      }
   }
}
