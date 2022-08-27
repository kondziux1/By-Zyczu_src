package net.infonode.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class ChangeNotifyList implements List {
   private List list;

   protected abstract void changed();

   protected ChangeNotifyList() {
      this(new ArrayList(2));
   }

   protected ChangeNotifyList(List var1) {
      super();
      this.list = var1;
   }

   protected List getList() {
      return this.list;
   }

   public int size() {
      return this.list.size();
   }

   public boolean isEmpty() {
      return this.list.isEmpty();
   }

   public Object[] toArray() {
      return this.list.toArray();
   }

   public Object get(int var1) {
      return this.list.get(var1);
   }

   public int indexOf(Object var1) {
      return this.list.indexOf(var1);
   }

   public int lastIndexOf(Object var1) {
      return this.list.lastIndexOf(var1);
   }

   public boolean contains(Object var1) {
      return this.list.contains(var1);
   }

   public boolean containsAll(Collection var1) {
      return this.list.containsAll(var1);
   }

   public Iterator iterator() {
      return this.listIterator();
   }

   public ListIterator listIterator() {
      return this.listIterator(0);
   }

   public ListIterator listIterator(int var1) {
      return new ChangeNotifyList.ChangeIterator(this.list.listIterator(var1));
   }

   public Object[] toArray(Object[] var1) {
      return this.list.toArray(var1);
   }

   public void clear() {
      this.list.clear();
      this.changed();
   }

   public Object remove(int var1) {
      Object var2 = this.list.remove(var1);
      this.changed();
      return var2;
   }

   public void add(int var1, Object var2) {
      this.list.add(var1, var2);
      this.changed();
   }

   public boolean add(Object var1) {
      if (this.list.add(var1)) {
         this.changed();
         return true;
      } else {
         return false;
      }
   }

   public boolean remove(Object var1) {
      if (this.list.remove(var1)) {
         this.changed();
         return true;
      } else {
         return false;
      }
   }

   public boolean addAll(int var1, Collection var2) {
      if (this.list.addAll(var1, var2)) {
         this.changed();
         return true;
      } else {
         return false;
      }
   }

   public boolean addAll(Collection var1) {
      if (this.list.addAll(var1)) {
         this.changed();
         return true;
      } else {
         return false;
      }
   }

   public boolean removeAll(Collection var1) {
      if (this.list.removeAll(var1)) {
         this.changed();
         return true;
      } else {
         return false;
      }
   }

   public boolean retainAll(Collection var1) {
      if (this.list.retainAll(var1)) {
         this.changed();
         return true;
      } else {
         return false;
      }
   }

   public List subList(int var1, int var2) {
      return new ChangeNotifyList$1(this, this.list.subList(var1, var2));
   }

   public Object set(int var1, Object var2) {
      Object var3 = this.list.set(var1, var2);
      this.changed();
      return var3;
   }

   private class ChangeIterator implements ListIterator {
      private ListIterator iterator;

      ChangeIterator(ListIterator var2) {
         super();
         this.iterator = var2;
      }

      public int nextIndex() {
         return this.iterator.nextIndex();
      }

      public int previousIndex() {
         return this.iterator.previousIndex();
      }

      public boolean hasPrevious() {
         return this.iterator.hasPrevious();
      }

      public Object previous() {
         return this.iterator.previous();
      }

      public void add(Object var1) {
         this.iterator.add(var1);
         ChangeNotifyList.this.changed();
      }

      public void set(Object var1) {
         this.iterator.set(var1);
         ChangeNotifyList.this.changed();
      }

      public void remove() {
         this.iterator.remove();
         ChangeNotifyList.this.changed();
      }

      public boolean hasNext() {
         return this.iterator.hasNext();
      }

      public Object next() {
         return this.iterator.next();
      }
   }
}
