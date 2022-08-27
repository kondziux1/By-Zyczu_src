package net.infonode.gui;

import java.awt.Font;

public class FontUtil {
   private FontUtil() {
      super();
   }

   public static Font copy(Font var0) {
      return var0 == null ? null : new Font(var0.getName(), var0.getStyle(), var0.getSize());
   }
}
