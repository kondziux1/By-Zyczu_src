package com.sun.jna;

public class NativeLong extends IntegerType {
   public static final int SIZE = Native.LONG_SIZE;

   public NativeLong() {
      this(0L);
   }

   public NativeLong(long value) {
      super(SIZE, value);
   }
}
