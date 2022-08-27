package com.sun.jna;

final class Structure$2 extends ThreadLocal {
   Structure$2() {
      super();
   }

   protected synchronized Object initialValue() {
      return new Structure$2$StructureSet(this);
   }
}
