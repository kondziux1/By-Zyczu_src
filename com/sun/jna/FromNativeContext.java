package com.sun.jna;

public class FromNativeContext {
   private Class type;

   FromNativeContext(Class javaType) {
      super();
      this.type = javaType;
   }

   public Class getTargetType() {
      return this.type;
   }
}
