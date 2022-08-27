package com.sun.jna;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

final class Native$4 implements InvocationHandler {
   Native$4(Library.Handler var1, Library var2) {
      super();
      this.val$handler = var1;
      this.val$library = var2;
   }

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      synchronized(this.val$handler.getNativeLibrary()) {
         return this.val$handler.invoke(this.val$library, method, args);
      }
   }
}
