package de.javasoft.util;

public enum OS {
   Windows,
   Mac,
   Linux,
   FreeBSD,
   Solaris,
   Unknown;

   private static final String OS_NAME = System.getProperty("os.name");
   private static final String OS_VERSION = System.getProperty("os.version");

   private OS() {
   }

   public static OS getCurrentOS() {
      if (OS_NAME.startsWith("Windows")) {
         return Windows;
      } else if (OS_NAME.startsWith("Mac")) {
         return Mac;
      } else if (OS_NAME.startsWith("Linux") || OS_NAME.toUpperCase().startsWith("LINUX")) {
         return Linux;
      } else if (OS_NAME.startsWith("FreeBSD") || OS_NAME.toUpperCase().startsWith("FREEBSD")) {
         return FreeBSD;
      } else {
         return OS_NAME.startsWith("Solaris") ? Solaris : Unknown;
      }
   }

   public static String getName() {
      return OS_NAME;
   }

   public static String getVersion() {
      return OS_VERSION;
   }
}
