package net.infonode.util;

public class Utils {
   private Utils() {
      super();
   }

   public static final short unsigned(byte var0) {
      return (short)(var0 & 255);
   }

   public static final boolean equals(Object var0, Object var1) {
      return var0 == var1 || var0 != null && var1 != null && var0.equals(var1);
   }
}
