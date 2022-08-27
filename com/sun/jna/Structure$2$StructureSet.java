package com.sun.jna;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

class Structure$2$StructureSet extends AbstractCollection implements Set {
   private Structure[] elements;
   private int count;

   Structure$2$StructureSet(Structure$2 var1) {
      super();
      this.this$0 = var1;
   }

   private void ensureCapacity(int size) {
      if (this.elements == null) {
         this.elements = new Structure[size * 3 / 2];
      } else if (this.elements.length < size) {
         Structure[] e = new Structure[size * 3 / 2];
         System.arraycopy(this.elements, 0, e, 0, this.elements.length);
         this.elements = e;
      }

   }

   public int size() {
      return this.count;
   }

   public boolean contains(Object o) {
      return this.indexOf(o) != -1;
   }

   public boolean add(Object o) {
      if (!this.contains(o)) {
         this.ensureCapacity(this.count + 1);
         this.elements[this.count++] = (Structure)o;
      }

      return true;
   }

   private int indexOf(Object o) {
      Structure s1 = (Structure)o;

      for(int i = 0; i < this.count; ++i) {
         Structure s2 = this.elements[i];
         if (s1 == s2 || s1.getClass() == s2.getClass() && s1.size() == s2.size() && s1.getPointer().equals(s2.getPointer())) {
            return i;
         }
      }

      return -1;
   }

   public boolean remove(Object o) {
      int idx = this.indexOf(o);
      if (idx != -1) {
         if (--this.count > 0) {
            this.elements[idx] = this.elements[this.count];
            this.elements[this.count] = null;
         }

         return true;
      } else {
         return false;
      }
   }

   public Iterator iterator() {
      Structure[] e = new Structure[this.count];
      if (this.count > 0) {
         System.arraycopy(this.elements, 0, e, 0, this.count);
      }

      return Arrays.asList(e).iterator();
   }
}
