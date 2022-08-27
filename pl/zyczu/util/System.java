package pl.zyczu.util;

public class System {
   public System() {
      super();
   }

   public static String getOperatingSystemName() {
      String os = java.lang.System.getProperty("os.name").toLowerCase();
      if (os.contains("win")) {
         return "windows";
      } else if (os.contains("linux")) {
         return "linux";
      } else if (os.contains("unix")) {
         return "linux";
      } else if (os.contains("macos")) {
         return "macosx";
      } else {
         return os.contains("solaris") ? "solaris" : "lindows";
      }
   }
}
