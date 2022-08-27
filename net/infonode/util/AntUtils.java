package net.infonode.util;

public class AntUtils {
   public AntUtils() {
      super();
   }

   public static ProductVersion createProductVersion(int var0, int var1, int var2) {
      return new ProductVersion(var0, var1, var2);
   }

   public static ProductVersion createProductVersion(String var0, String var1, String var2) {
      return createProductVersion(0, 0, 0);
   }

   public static long getBuildTime(long var0) {
      return var0;
   }

   public static long getBuildTime(String var0) {
      return getBuildTime(System.currentTimeMillis());
   }
}
