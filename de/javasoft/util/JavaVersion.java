package de.javasoft.util;

import java.text.NumberFormat;
import java.text.ParseException;

public class JavaVersion {
   public static final boolean JAVA5 = System.getProperty("java.version").startsWith("1.5.");
   public static final boolean JAVA6 = System.getProperty("java.version").startsWith("1.6.");
   public static final boolean JAVA6U10_OR_ABOVE = isJava6uNOrAbove(10);
   public static final boolean JAVA7 = System.getProperty("java.version").startsWith("1.7.");
   public static final boolean JAVA7U8_OR_ABOVE = isJava7uNOrAbove(8);

   public JavaVersion() {
      super();
   }

   public static boolean isJava6uNOrAbove(int var0) {
      String var1 = System.getProperty("java.version");
      if (JAVA5) {
         return false;
      } else if (var1.equals("1.6.0")) {
         return false;
      } else if (var1.startsWith("1.6.0_")) {
         try {
            int var2 = ((Long)NumberFormat.getIntegerInstance().parse(var1.substring(6))).intValue();
            return var2 >= var0;
         } catch (ParseException var3) {
            return false;
         }
      } else {
         return true;
      }
   }

   public static boolean isJava7uNOrAbove(int var0) {
      String var1 = System.getProperty("java.version");
      if (JAVA5 || JAVA6) {
         return false;
      } else if (var1.equals("1.7.0")) {
         return false;
      } else if (var1.startsWith("1.7.0_")) {
         try {
            int var2 = ((Long)NumberFormat.getIntegerInstance().parse(var1.substring(6))).intValue();
            return var2 >= var0;
         } catch (ParseException var3) {
            return false;
         }
      } else {
         return true;
      }
   }
}
