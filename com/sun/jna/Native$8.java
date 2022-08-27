package com.sun.jna;

import java.util.Iterator;
import java.util.Map.Entry;

final class Native$8 {
   Native$8() {
      super();
   }

   protected void finalize() {
      synchronized(Native.access$100()) {
         Iterator i = Native.access$100().entrySet().iterator();

         while(i.hasNext()) {
            Entry e = (Entry)i.next();
            Native.access$200((Class)e.getKey(), (long[])e.getValue());
            i.remove();
         }

      }
   }
}
