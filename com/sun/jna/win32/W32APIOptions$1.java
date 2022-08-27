package com.sun.jna.win32;

import java.util.HashMap;

final class W32APIOptions$1 extends HashMap {
   W32APIOptions$1() {
      super();
      this.put("type-mapper", W32APITypeMapper.UNICODE);
      this.put("function-mapper", W32APIFunctionMapper.UNICODE);
   }
}
