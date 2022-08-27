package net.minecraft;

public class ZyczuCompat {
   public ZyczuCompat() {
      super();
   }

   public static ClassLoader getClassLoader() {
      return GameUpdater.classLoader;
   }
}
