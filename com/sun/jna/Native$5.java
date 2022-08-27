package com.sun.jna;

import java.lang.reflect.Method;
import java.security.PrivilegedAction;

final class Native$5 implements PrivilegedAction {
   Native$5() {
      super();
   }

   public Object run() {
      try {
         Method m = (Native.class$java$lang$ClassLoader == null
               ? (Native.class$java$lang$ClassLoader = Native.class$("java.lang.ClassLoader"))
               : Native.class$java$lang$ClassLoader)
            .getDeclaredMethod(
               "findLibrary",
               Native.class$java$lang$String == null ? (Native.class$java$lang$String = Native.class$("java.lang.String")) : Native.class$java$lang$String
            );
         m.setAccessible(true);
         return m;
      } catch (Exception var2) {
         return null;
      }
   }
}
