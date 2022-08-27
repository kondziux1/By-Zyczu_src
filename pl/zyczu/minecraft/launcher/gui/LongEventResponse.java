package pl.zyczu.minecraft.launcher.gui;

import java.awt.EventQueue;
import net.minecraft.LauncherFrame;

public class LongEventResponse {
   private static Thread backgroundTask = null;

   public LongEventResponse() {
      super();
   }

   public static void execute(Runnable kodDoUruchomienia) {
      LauncherFrame.getInstance().enableGlassPane();
      backgroundTask = new Thread(kodDoUruchomienia);
      backgroundTask.start();
      Thread t = new Thread(new Runnable() {
         public void run() {
            while(LongEventResponse.backgroundTask.isAlive()) {
               try {
                  Thread.sleep(6L);
               } catch (InterruptedException var2) {
               }
            }

            EventQueue.invokeLater(new Runnable() {
               public void run() {
                  LauncherFrame.getInstance().disableGlassPane();
               }
            });
         }
      });
      t.start();
   }
}
