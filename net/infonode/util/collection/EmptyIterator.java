package net.infonode.util.collection;

import java.util.Iterator;

public class EmptyIterator implements Iterator {
   public static final EmptyIterator INSTANCE = new EmptyIterator();

   private EmptyIterator() {
      super();
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }

   public boolean hasNext() {
      return false;
   }

   public Object next() {
      return null;
   }
}
