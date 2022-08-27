package com.sun.jna;

class NativeLibrary$1 extends Function {
   NativeLibrary$1(NativeLibrary var1, NativeLibrary x0, String x1, int x2) {
      super(x0, x1, x2);
      this.this$0 = var1;
   }

   Object invoke(Object[] args, Class returnType, boolean b) {
      return new Integer(Native.getLastError());
   }
}
