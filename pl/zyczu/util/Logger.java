package pl.zyczu.util;

public class Logger {
   public Logger() {
      super();
   }

   public void info(String m) {
      java.lang.System.out.println("[ Info ] " + m);
   }

   public void debug(String m) {
      java.lang.System.out.println("[ Debug ] " + m);
   }

   public void severe(String m) {
      java.lang.System.out.println("[ Straszny Bład ] " + m);
   }

   public void warning(String m) {
      java.lang.System.out.println("[ Warning ] " + m);
   }

   public void log(String a, String m) {
      java.lang.System.out.println("[ " + a + " ] " + m);
   }
}
