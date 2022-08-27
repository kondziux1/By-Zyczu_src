package net.minecraft;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import pl.zyczu.minecraft.launcher.Minecraft;
import pl.zyczu.minecraft.launcher.game.LauncherPanel;
import pl.zyczu.util.DisabledGlassPane;
import pl.zyczu.util.Winshit;

public class LauncherFrame extends JFrame {
   public static final int VERSION = 12;
   private static final long serialVersionUID = 1L;
   private static LauncherFrame instance = null;
   public DisabledGlassPane glassPane = null;
   protected Launcher launcher = null;

   public static LauncherFrame getInstance() {
      return instance;
   }

   public static void main(String[] args) {
      LauncherFrame frame = new LauncherFrame();
      frame.setVisible(true);
      frame.setTitle("Minecraft Launcher");
      Winshit.win7pinToTaskbar();
      Minecraft.main(args, instance);
   }

   public LauncherFrame() {
      super();
      instance = this;
      this.setDefaultCloseOperation(3);
      Insets insets = this.getInsets();
      this.setSize(854 + insets.left + insets.right, 480 + insets.top + insets.bottom);
      this.setLocationByPlatform(true);

      try {
         this.setIconImage(ImageIO.read(Minecraft.class.getResource("favicon.png")));
      } catch (IOException var3) {
         var3.printStackTrace();
      } catch (IllegalArgumentException var4) {
         var4.printStackTrace();
      }

      this.glassPane = new DisabledGlassPane();
      JRootPane rootPane = SwingUtilities.getRootPane(this);
      rootPane.setGlassPane(this.glassPane);
   }

   public void switchToLauncher(Image par1, String par2, String par3, boolean par4, File par5, File par6) {
      this.launcher = new Launcher();
      this.launcher.init(par1, par2, par3, par4, par5, par6);
      this.remove(LauncherPanel.getInstance());
      this.setLayout(new BorderLayout());
      this.add(this.launcher, "Center");
      this.validate();
      this.launcher.start();
      this.setTitle("Minecraft");
   }

   public void switchPanel(JPanel oldPanel, JPanel newPanel) {
      EventQueue.invokeLater(new LauncherFrame.SwitchPanelRunnable(oldPanel, newPanel));
   }

   public void enableGlassPane() {
      this.glassPane.activate("Czekaj...");
   }

   public void enableGlassPane(String mesydż) {
      this.glassPane.activate(mesydż);
   }

   public void disableGlassPane() {
      this.glassPane.deactivate();
   }

   private class SwitchPanelRunnable implements Runnable {
      private JPanel old;
      private JPanel nju;

      public SwitchPanelRunnable(JPanel a, JPanel b) {
         super();
         this.old = a;
         this.nju = b;
      }

      public void run() {
         boolean updated = true;

         try {
            this.nju.update(this.nju.getGraphics());
         } catch (Exception var3) {
            updated = false;
         }

         LauncherFrame.this.remove(this.old);
         LauncherFrame.this.setLayout(new GridBagLayout());
         LauncherFrame.this.add(this.nju, new GridBagConstraints());
         LauncherFrame.this.pack();
         if (!updated) {
            this.nju.update(this.nju.getGraphics());
         }

      }
   }
}
