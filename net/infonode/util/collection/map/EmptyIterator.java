package net.infonode.util.collection.map;

import net.infonode.util.collection.map.base.MapIterator;

public class EmptyIterator implements MapIterator {
   public static final EmptyIterator INSTANCE = new EmptyIterator();

   private EmptyIterator() {
      super();
   }

   public void remove() {
   }

   public Object getKey() {
      return null;
   }

   public Object getValue() {
      return null;
   }

   public void next() {
   }

   public boolean atEntry() {
      return false;
   }
}
