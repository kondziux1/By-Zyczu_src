package pl.zyczu.util;

import net.minecraft.LauncherFrame;
import pl.zyczu.minecraft.launcher.LoginPanel;
import pl.zyczu.minecraft.launcher.MessagePanel;
import pl.zyczu.minecraft.launcher.Minecraft;
import pl.zyczu.minecraft.launcher.Properties;
import pl.zyczu.minecraft.launcher.UserModPacks;
import pl.zyczu.minecraft.launcher.gui.MultiPanel;
import pl.zyczu.minecraft.launcher.repo.RepositoryManager;

public class Cleanup implements Runnable {
   public Cleanup() {
      super();
   }

   public static void clean() {
      Thread t = new Thread(new Cleanup());
      t.start();
   }

   public void run() {
      try {
         Thread.sleep(1000L);
         LauncherFrame.getInstance().glassPane = null;
         Minecraft.shutdownInstance();
         LoginPanel.shutdownInstace();
         MessagePanel.shutdownInstance();
         MultiPanel.shutdownInstance();
         Properties.shutdownInstance();
         UserModPacks.shutdownInstance();
         RepositoryManager.shutdownInstance();
      } catch (Exception var2) {
      }

   }
}
