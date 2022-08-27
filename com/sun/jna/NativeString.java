package com.sun.jna;

import java.nio.CharBuffer;

class NativeString implements CharSequence, Comparable {
   private Pointer pointer;
   private boolean wide;

   public NativeString(String string) {
      this(string, false);
   }

   public NativeString(String string, boolean wide) {
      super();
      if (string == null) {
         throw new NullPointerException("String must not be null");
      } else {
         this.wide = wide;
         if (wide) {
            int len = (string.length() + 1) * Native.WCHAR_SIZE;
            this.pointer = new Memory((long)len);
            this.pointer.setString(0L, string, true);
         } else {
            byte[] data = Native.getBytes(string);
            this.pointer = new Memory((long)(data.length + 1));
            this.pointer.write(0L, data, 0, data.length);
            this.pointer.setByte((long)data.length, (byte)0);
         }

      }
   }

   public int hashCode() {
      return this.toString().hashCode();
   }

   public boolean equals(Object other) {
      if (other instanceof CharSequence) {
         return this.compareTo(other) == 0;
      } else {
         return false;
      }
   }

   public String toString() {
      String s = this.wide ? "const wchar_t*" : "const char*";
      return s + "(" + this.pointer.getString(0L, this.wide) + ")";
   }

   public Pointer getPointer() {
      return this.pointer;
   }

   public char charAt(int index) {
      return this.toString().charAt(index);
   }

   public int length() {
      return this.toString().length();
   }

   public CharSequence subSequence(int start, int end) {
      return CharBuffer.wrap(this.toString()).subSequence(start, end);
   }

   public int compareTo(Object other) {
      return other == null ? 1 : this.toString().compareTo(other.toString());
   }
}
