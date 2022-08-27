package com.sun.jna.win32;

import com.sun.jna.DefaultTypeMapper;
import com.sun.jna.TypeConverter;
import com.sun.jna.TypeMapper;

public class W32APITypeMapper extends DefaultTypeMapper {
   public static final TypeMapper UNICODE = new W32APITypeMapper(true);
   public static final TypeMapper ASCII = new W32APITypeMapper(false);

   protected W32APITypeMapper(boolean unicode) {
      super();
      if (unicode) {
         TypeConverter stringConverter = new W32APITypeMapper$1(this);
         this.addTypeConverter(String.class, stringConverter);
         this.addToNativeConverter(String[].class, stringConverter);
      }

      TypeConverter booleanConverter = new W32APITypeMapper$2(this);
      this.addTypeConverter(Boolean.class, booleanConverter);
   }
}
