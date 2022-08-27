package com.sun.jna.win32;

import com.sun.jna.FromNativeContext;
import com.sun.jna.ToNativeContext;
import com.sun.jna.TypeConverter;

class W32APITypeMapper$2 implements TypeConverter {
   W32APITypeMapper$2(W32APITypeMapper var1) {
      super();
      this.this$0 = var1;
   }

   public Object toNative(Object value, ToNativeContext context) {
      return new Integer(Boolean.TRUE.equals(value) ? 1 : 0);
   }

   public Object fromNative(Object value, FromNativeContext context) {
      return (Integer)value != 0 ? Boolean.TRUE : Boolean.FALSE;
   }

   public Class nativeType() {
      return W32APITypeMapper.class$java$lang$Integer == null
         ? (W32APITypeMapper.class$java$lang$Integer = W32APITypeMapper.class$("java.lang.Integer"))
         : W32APITypeMapper.class$java$lang$Integer;
   }
}
