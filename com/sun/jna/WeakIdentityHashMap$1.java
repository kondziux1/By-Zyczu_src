package com.sun.jna;

import java.util.Map.Entry;

class WeakIdentityHashMap$1 implements Entry {
   WeakIdentityHashMap$1(WeakIdentityHashMap var1, Object var2, Object var3) {
      super();
      this.this$0 = var1;
      this.val$key = var2;
      this.val$value = var3;
   }

   public Object getKey() {
      return this.val$key;
   }

   public Object getValue() {
      return this.val$value;
   }

   public Object setValue(Object value) {
      throw new UnsupportedOperationException();
   }
}
