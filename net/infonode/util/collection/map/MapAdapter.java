package net.infonode.util.collection.map;

import java.util.HashMap;
import java.util.Map.Entry;
import net.infonode.util.collection.map.base.ConstMapIterator;
import net.infonode.util.collection.map.base.Map;
import net.infonode.util.collection.map.base.MapIterator;

public class MapAdapter implements Map {
   private HashMap map;

   public MapAdapter() {
      super();
   }

   public MapAdapter(HashMap var1) {
      super();
      this.map = var1;
   }

   public Object put(Object var1, Object var2) {
      if (this.map == null) {
         this.map = new HashMap(4);
      }

      return this.map.put(var1, var2);
   }

   public Object remove(Object var1) {
      return this.map == null ? null : this.map.remove(var1);
   }

   public void clear() {
      if (this.map != null) {
         this.map.clear();
      }

   }

   public MapIterator iterator() {
      return (MapIterator)(this.map == null ? EmptyIterator.INSTANCE : new MapAdapter.Iterator(this.map.entrySet().iterator()));
   }

   public Object get(Object var1) {
      return this.map == null ? null : this.map.get(var1);
   }

   public boolean containsKey(Object var1) {
      return this.map != null && this.map.containsKey(var1);
   }

   public boolean containsValue(Object var1) {
      return this.map != null && this.map.containsValue(var1);
   }

   public boolean isEmpty() {
      return this.map == null || this.map.isEmpty();
   }

   public ConstMapIterator constIterator() {
      return this.iterator();
   }

   public int size() {
      return this.map == null ? 0 : this.map.size();
   }

   private static class Iterator implements MapIterator {
      private java.util.Iterator iterator;
      private Entry nextEntry;

      Iterator(java.util.Iterator var1) {
         super();
         this.iterator = var1;
         this.next();
      }

      public void remove() {
         this.iterator.remove();
      }

      public boolean atEntry() {
         return this.nextEntry != null;
      }

      public Object getKey() {
         return this.nextEntry.getKey();
      }

      public Object getValue() {
         return this.nextEntry.getValue();
      }

      public void next() {
         this.nextEntry = this.iterator.hasNext() ? (Entry)this.iterator.next() : null;
      }
   }
}
