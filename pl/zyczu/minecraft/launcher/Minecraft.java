package pl.zyczu.minecraft.launcher;

import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.minecraft.LauncherFrame;
import pl.zyczu.util.Logger;

public class Minecraft {
   public static LauncherFrame frame = null;
   public static Logger log = null;
   private Properties prop = null;
   public Image backgroundGeneric = null;
   public Font font = null;
   public Image settingsIcon = null;
   public Image closeIcon = null;
   protected static final String version = "3.3.3";
   public static final int ZYCZU_LAUNCHER_VERSION = 10;
   public static final String polecaneHTML = "http://l7.minecraft.zyczu.pl/polecane.html";
   public static Thread serviceThread = null;
   private static LoadingPanel loadingPanel = null;
   private File minecraftClasspathDirectory = null;
   private File cacheDirectory = null;
   private File gameDirectory = null;
   private static File workingDirectory = null;
   private static Minecraft instance;

   public Minecraft() {
      super();
   }

   public static void main(String[] args, LauncherFrame f) {
      frame = f;
      log = new Logger();
      System.out.println("Minecraft Launcher by Zyczu v. 3.3.3");
      System.out.println("http://minecraft.zyczu.pl/\n");
      loadingPanel = new LoadingPanel("3.3.3");
      frame.setLayout(new GridBagLayout());
      frame.add(loadingPanel, new GridBagConstraints());
      frame.pack();
      instance = new Minecraft();
      instance.setup();
   }

   public File getMinecraftClasspathDirectory() {
      if (this.minecraftClasspathDirectory == null) {
         File f = new File(getWorkingDirectory(), "tmp");
         if (!f.exists()) {
            f.mkdirs();
         }

         this.minecraftClasspathDirectory = f;
      }

      synchronized(this.minecraftClasspathDirectory) {
         return this.minecraftClasspathDirectory;
      }
   }

   public File getCacheDirectory() {
      if (this.cacheDirectory == null) {
         File f = new File(getWorkingDirectory(), "cache");
         if (!f.exists()) {
            f.mkdirs();
         }

         this.cacheDirectory = f;
      }

      return this.cacheDirectory;
   }

   public File getDirectory(Directory d) {
      File f = null;
      switch(d) {
         case ROOT:
            f = this.getGameDirectory();
            break;
         case BIN:
            f = new File(this.getGameDirectory(), "bin");
         case NATIVES:
         case TEXTUREPACKS:
         case SAVES:
         case STATS:
         case HEROBRINE:
         case SPOUTCRAFT:
         case PUREBDCRAFT:
         case GENERIC_CRAFT:
         case GENERIC_LINK:
         case EXTERNAL_LINK:
         default:
            break;
         case MODS:
            f = new File(this.getGameDirectory(), "mods");
            break;
         case RESOURCES:
            f = new File(this.getGameDirectory(), "resources");
            break;
         case FORGEMODS:
            f = new File(this.getGameDirectory(), "coremods");
            break;
         case COMPUTERCRAFT:
            f = new File(this.getGameDirectory(), "mods/ComputerCraft");
            break;
         case FLAN:
            f = new File(this.getGameDirectory(), "Flan");
      }

      if (!f.exists()) {
         f.mkdirs();
      }

      return f;
   }

   private File getGameDirectory() {
      if (this.gameDirectory == null) {
         File f = this.registerNewApplicationName("minecraft");
         this.gameDirectory = f;
      }

      return this.gameDirectory;
   }

   private File registerNewApplicationName(String applicationName) {
      File returnValue = null;
      String osName = System.getProperty("os.name").toLowerCase();
      String userHome = System.getProperty("user.home", ".");
      if (osName.contains("win")) {
         String applicationData = System.getenv("APPDATA");
         if (applicationData != null) {
            returnValue = new File(applicationData, "." + applicationName + '/');
         } else {
            returnValue = new File(userHome, '.' + applicationName + '/');
         }
      } else if (osName.contains("mac")) {
         returnValue = new File(userHome, "Library/Application Support/" + applicationName);
      } else if (!osName.contains("linux") && !osName.contains("unix")) {
         returnValue = new File(userHome, applicationName + '/');
      } else {
         returnValue = new File(userHome, '.' + applicationName + '/');
      }

      if (!returnValue.exists() && !returnValue.mkdirs()) {
         log.severe("Nie można utworzyć folderu dla minecrafta!");
         System.exit(1);
      }

      return returnValue;
   }

   public static File getWorkingDirectory() {
      if (workingDirectory == null) {
         String osName = System.getProperty("os.name").toLowerCase();
         String userHome = System.getProperty("user.home", ".");
         String applicationName = "minecraftzyczu";
         if (osName.contains("win")) {
            String applicationData = System.getenv("APPDATA");
            if (applicationData != null) {
               workingDirectory = new File(applicationData, "." + applicationName + '/');
            } else {
               workingDirectory = new File(userHome, '.' + applicationName + '/');
            }
         } else if (osName.contains("mac")) {
            workingDirectory = new File(userHome, "Library/Application Support/" + applicationName);
         } else if (!osName.contains("linux") && !osName.contains("unix")) {
            workingDirectory = new File(userHome, applicationName + '/');
         } else {
            workingDirectory = new File(userHome, '.' + applicationName + '/');
         }

         if (!workingDirectory.exists() && !workingDirectory.mkdirs()) {
            log.severe("Nie można utworzyć folderu dla minecrafta!");
            System.exit(1);
         }

         File f1 = new File(workingDirectory, "repo");
         if (!f1.exists() && !f1.mkdir()) {
            log.severe("Nie można utworzyć folderu dla repozytorium!");
            System.exit(1);
         }

         log.log("Launcher", "Katalog główny: " + workingDirectory);
      }

      return workingDirectory;
   }

   public Font getFont(int rozm) {
      return this.font.deriveFont((float)rozm);
   }

   public Properties getProperties() {
      return this.prop;
   }

   public static void switchPanel(JPanel oldPanel, JPanel newPanel) {
      frame.switchPanel(oldPanel, newPanel);
   }

   public void setup() {
      Properties p = Properties.getInstance();
      this.prop = p;
      int ram = p.getNumber("ram");
      double minram = 0.87 * (double)ram;
      double maxram = 1.12 * (double)ram;
      long heapSizeMegs = Runtime.getRuntime().maxMemory() / 1024L / 1024L;
      log.log("OutOfMemoryFix", "Przydzielono " + heapSizeMegs + "MB ramu, oczekiwano " + ram + "MB");
      File test = new File(getWorkingDirectory(), "crash.tmp");
      if ((double)heapSizeMegs < minram || (double)heapSizeMegs > maxram) {
         if (test.exists()) {
            test.delete();
            JOptionPane.showMessageDialog(
               frame,
               "Wygląda na to, że nie udało się uruchomić launchera z zadaną ilością RAMu.\n\nZostanie przywrócona domyślna ilość RAMu.\n\nGra może się crashować (Out of Memory)!"
            );
            p.set("ram", heapSizeMegs);
            this.fork((int)heapSizeMegs);
         } else {
            try {
               test.createNewFile();
               this.fork(ram);
            } catch (IOException var22) {
               log.log("OutOfMemoryFix", "Nie udało się wgrać fixa na OutOfMemory! Gra będzie się crashować!");
            }
         }
      }

      test.delete();
      pl.zyczu.minecraft.launcher.ads.NetworkService.getInstance();
      this.startService();

      try {
         this.backgroundGeneric = ImageIO.read(Minecraft.class.getResource("nowetlo.png"));
      } catch (IOException var21) {
         log.warning("Nie udało się załadować tła!");
         var21.printStackTrace();
      }

      try {
         this.settingsIcon = ImageIO.read(Minecraft.class.getResource("package_settings.png"));
      } catch (IOException var20) {
         log.warning("Nie udało się załadować ikonki ustawień!");
         var20.printStackTrace();
      }

      try {
         this.closeIcon = ImageIO.read(Minecraft.class.getResource("zamknij.png"));
      } catch (IOException var19) {
         log.warning("Nie udało się załadować ikonki zamykania Własne Mody!");
         var19.printStackTrace();
      }

      try {
         InputStream is = Minecraft.class.getResourceAsStream("minecraftzyczu.ttf");
         this.font = Font.createFont(0, is);
         GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(this.font);
      } catch (IOException var17) {
         log.warning("Nie udało się załadować czcionki minecraft!");
         var17.printStackTrace();
      } catch (FontFormatException var18) {
         log.warning("Niepoprawny plik czcionki minecraft!");
         var18.printStackTrace();
      }

      try {
         UIManager.setLookAndFeel(new SyntheticaAluOxideLookAndFeel());
      } catch (UnsupportedLookAndFeelException var15) {
         log.warning("Nie można załadować wyglądu!");
         var15.printStackTrace();
      } catch (ParseException var16) {
         log.warning("Nie można załadować wyglądu!");
         var16.printStackTrace();
      }

      log.debug("Załadowano grafikę");
      log.warning("Service status: " + NetworkService.status);
      if (NetworkService.status != ServiceStatus.READY) {
         MessagePanel mp = MessagePanel.getInstance();
         mp.setTitle("Trwa aktualizacja");
         mp.setFirst("Repozytorium z modami jest właśnie aktualizowane...");
         mp.setVisible(true);
         frame.remove(loadingPanel);
         frame.add(mp, new GridBagConstraints());
         frame.pack();
         int licznik = 100;
         boolean przyc = false;

         while(true) {
            if (przyc && NetworkService.status != ServiceStatus.SYNCING && NetworkService.status != ServiceStatus.CONNECTING) {
               mp.removeButton();
               przyc = false;
            }

            if (NetworkService.status == ServiceStatus.READY) {
               mp.cleanup();
               mp.setFirst("Czekaj...");
               LoginPanel panel = new LoginPanel();
               frame.remove(mp);
               frame.add(panel, new GridBagConstraints());
               frame.pack();
               break;
            }

            if (NetworkService.status == ServiceStatus.ERROR) {
               mp.cleanup();
               mp.setTitle("Brak wymaganych plików!");
               mp.setFirst("Podczas pierwszego uruchomienia launchera wymagane jest pobranie plików z sieci.");
               mp.setSecond("Proszę sprawdzić połączenie internetowe i uruchomić ponownie aplikację.");
               mp.setButton("OK", new ActionListener() {
                  public void actionPerformed(ActionEvent arg0) {
                     System.exit(0);
                  }
               });
               return;
            }

            if (licznik > 0) {
               --licznik;
            } else if (licznik == 0) {
               if (NetworkService.status != ServiceStatus.CONNECTING && NetworkService.status != ServiceStatus.SYNCING) {
                  licznik = -1;
               } else {
                  log.debug("Pojawia się przycisk");
                  przyc = true;
                  licznik = -1;
                  mp.setButton("Pomiń aktualizację", new ActionListener() {
                     public void actionPerformed(ActionEvent arg0) {
                        Minecraft.log.debug("Pomijanie aktualizacji");
                        NetworkService.subThread.stop();
                        String[] spr = new String[]{"modpacks.xml", "cat.xml", "files.xml", "packages.xml", "repo.xml"};

                        for(int i = 0; i < spr.length; ++i) {
                           File plik = new File(Minecraft.workingDirectory, "repo/" + spr[i]);
                           if (!plik.exists() || plik.length() < 10L) {
                              NetworkService.status = ServiceStatus.ERROR;
                              break;
                           }
                        }

                     }
                  });
               }
            }

            switch(NetworkService.status) {
               case CONNECTING:
                  mp.setSecond("Łączenie...");
                  break;
               case SYNCING:
                  mp.setSecond("Synchronizacja...");
                  break;
               case DOWNLOADING:
                  if (NetworkService.statusFile == null) {
                     mp.setSecond("Pobieranie ...");
                  } else {
                     mp.setSecond("Pobieranie " + NetworkService.statusFile + " ...");
                  }
               case DLOAD_REPO:
               case DLOAD_FILES:
               case DLOAD_MODPACKS:
               case SAVING:
               case READY:
               case ERROR:
               default:
                  break;
               case INTERPRETING:
                  mp.setSecond("Analizowanie...");
                  break;
               case FIRSTRUN_INIT:
                  mp.setSecond("Przenoszenie ustawień ze starego launchera...");
                  break;
               case UPDATE_ZYCZU:
                  mp.setSecond("Aktualizacja ze starej wersji launchera by Zyczu...");
                  break;
               case UPDATE_MINECRAFTSP_EXE:
                  mp.setSecond("Aktualizacja z MinecraftSP.exe ...");
            }

            try {
               Thread.sleep(20L);
            } catch (InterruptedException var14) {
               var14.printStackTrace();
            }
         }
      } else {
         LoginPanel panel = new LoginPanel();
         frame.remove(loadingPanel);
         frame.add(panel, new GridBagConstraints());
         frame.pack();
      }

   }

   private void startService() {
      serviceThread = new Thread(new NetworkService(this));
      serviceThread.setName("ZyczuLauncherNetworkService");
      serviceThread.start();
   }

   public void fork(int pamiec_w_mb) {
      log.log("OutOfMemoryFix", "Uruchamianie nowego procesu z " + pamiec_w_mb + "MB");
      ArrayList params = new ArrayList();

      String pathToJar;
      try {
         pathToJar = Minecraft.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
      } catch (URISyntaxException var8) {
         log.severe("Nie można pobrać adresu URI pliku JAR!");
         return;
      }

      params.add("javaw");
      params.add("-Xms" + new Integer(pamiec_w_mb / 2).toString() + "m");
      params.add("-Xmx" + new Integer(pamiec_w_mb).toString() + "m");
      params.add("-classpath");
      params.add(pathToJar);
      params.add("net.minecraft.LauncherFrame");

      try {
         ProcessBuilder pb = new ProcessBuilder(params);
         Process process = pb.start();
         if (process == null) {
            throw new IOException("!");
         }

         System.exit(0);
      } catch (IOException var7) {
      }

      try {
         params.set(0, "java");
         ProcessBuilder pb = new ProcessBuilder(params);
         Process process = pb.start();
         if (process == null) {
            throw new IOException("!");
         }

         System.exit(0);
      } catch (IOException var6) {
      }

   }

   public static Minecraft getInstance() {
      return instance;
   }

   private void shutdownNow() {
      this.prop = null;
      this.backgroundGeneric = null;
      this.font = null;
      this.settingsIcon = null;
   }

   public static void shutdownInstance() {
      if (instance != null) {
         instance.shutdownNow();
      }

      frame = null;
      log = null;
      serviceThread = null;
      loadingPanel = null;
      instance = null;
   }
}
