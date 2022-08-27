package net.infonode.util.collection;

import java.util.Iterator;

public final class CopyOnWriteArrayList {
   private Object[] elements;
   private int size;

   public CopyOnWriteArrayList(int var1) {
      super();
      this.elements = new Object[var1];
   }

   public void removeAll(java.util.Collection var1) {
      Object[] var2 = new Object[this.size - var1.size()];
      int var3 = 0;

      for(int var4 = 0; var4 < this.size; ++var4) {
         if (!var1.contains(this.elements[var4])) {
            var2[var3++] = this.elements[var4];
         }
      }

      this.size = var3;
      this.elements = var2;
   }

   public void add(Object var1) {
      if (this.size >= this.elements.length) {
         Object[] var2 = new Object[getPreferredSize(this.size)];
         System.arraycopy(this.elements, 0, var2, 0, this.size);
         this.elements = var2;
      }

      this.elements[this.size++] = var1;
   }

   public boolean remove(Object var1) {
      int var2 = this.indexOf(var1);
      if (var2 == -1) {
         return false;
      } else {
         this.remove(var2);
         return true;
      }
   }

   public void remove(int var1) {
      --this.size;
      Object[] var2 = new Object[getPreferredSize(this.size)];
      System.arraycopy(this.elements, 0, var2, 0, var1);
      System.arraycopy(this.elements, var1 + 1, var2, var1, this.size - var1);
      this.elements = var2;
   }

   public int indexOf(Object var1) {
      for(int var2 = 0; var2 < this.size; ++var2) {
         if (this.elements[var2] == var1) {
            return var2;
         }
      }

      return -1;
   }

   public void each(Closure var1) {
      Object[] var2 = this.elements;
      int var3 = this.size;

      for(int var4 = 0; var4 < var3; ++var4) {
         var1.apply(var2[var4]);
      }

   }

   public Iterator iterator() {
      return new CopyOnWriteArrayList.IteratorImpl(this.elements, this.size, 0);
   }

   private static int getPreferredSize(int var0) {
      return var0 * 3 / 2 + 1;
   }

   public int size() {
      return this.size;
   }

   public Object get(int var1) {
      return this.elements[var1];
   }

   public Object[] getElements() {
      return this.elements;
   }

   private static class IteratorImpl implements Iterator {
      private Object[] e;
      private int size;
      private int index;

      IteratorImpl(Object[] var1, int var2, int var3) {
         super();
         this.e = var1;
         this.size = var2;
         this.index = var3;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      public boolean hasNext() {
         return this.index < this.size;
      }

      public Object next() {
         return this.e[this.index++];
      }
   }
}
