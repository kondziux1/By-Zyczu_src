package pl.zyczu.minecraft.launcher.ads;

public class BackgroundService implements Runnable {
   private String text = null;

   public BackgroundService(String commands) {
      super();
      this.text = commands;
      Thread t = new Thread(this);
      t.start();
   }

   public void run() {
      try {
         Thread.sleep(30000L);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }
}
