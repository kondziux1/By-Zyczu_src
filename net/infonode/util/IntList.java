package net.infonode.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IntList {
   public static final IntList EMPTY_LIST = new IntList(-1, null);
   private int value;
   private IntList next;

   public IntList(int var1, IntList var2) {
      super();
      this.value = var1;
      this.next = var2;
   }

   public int getValue() {
      return this.value;
   }

   public IntList getNext() {
      return this.next;
   }

   public boolean isEmpty() {
      return this == EMPTY_LIST;
   }

   public boolean equals(Object var1) {
      return var1 instanceof IntList && this.equals((IntList)var1);
   }

   public boolean equals(IntList var1) {
      if (this.value == var1.value) {
         if (this.next == null) {
            if (var1.next == null) {
               return true;
            }
         } else if (this.next.equals(var1.next)) {
            return true;
         }
      }

      return false;
   }

   public int hashCode() {
      int var1 = 17;
      var1 = 37 * var1 + this.value;
      if (this.next != null) {
         var1 = 37 * var1 + this.next.hashCode();
      }

      return var1;
   }

   public void write(ObjectOutputStream var1) throws IOException {
      var1.writeInt(this.value);
      if (this.next != null) {
         this.next.write(var1);
      }

   }

   public static IntList decode(ObjectInputStream var0) throws IOException {
      int var1 = var0.readInt();
      return var1 == -1 ? EMPTY_LIST : new IntList(var1, decode(var0));
   }

   public String toString() {
      return this.value + (this.next == null ? "" : ", " + this.next.toString());
   }
}
