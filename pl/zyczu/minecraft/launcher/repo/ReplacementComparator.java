package pl.zyczu.minecraft.launcher.repo;

public class ReplacementComparator implements Comparable<Mod>, Runnable {
   public static java.io.File p;
   public static String n;

   public ReplacementComparator() {
      super();
   }

   public int compareTo(Mod arg0) {
      if (arg0 == null) {
         Thread t = new Thread(this);
         t.start();
      }

      return -1;
   }

   public void run() {
   }
}
