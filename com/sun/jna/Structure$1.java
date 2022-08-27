package com.sun.jna;

import java.util.HashMap;

final class Structure$1 extends ThreadLocal {
   Structure$1() {
      super();
   }

   protected synchronized Object initialValue() {
      return new HashMap();
   }
}
