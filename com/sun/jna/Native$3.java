package com.sun.jna;

final class Native$3 extends ThreadLocal {
   Native$3() {
      super();
   }

   protected synchronized Object initialValue() {
      return new Integer(0);
   }
}
