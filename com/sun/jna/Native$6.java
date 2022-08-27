package com.sun.jna;

import java.io.File;
import java.io.FilenameFilter;

final class Native$6 implements FilenameFilter {
   Native$6() {
      super();
   }

   public boolean accept(File dir, String name) {
      return name.endsWith(".x") && name.indexOf("jna") != -1;
   }
}
