package pl.zyczu.minecraft.launcher.game;

import java.awt.EventQueue;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.LauncherFrame;
import pl.zyczu.minecraft.launcher.Directory;
import pl.zyczu.minecraft.launcher.Minecraft;
import pl.zyczu.minecraft.launcher.Properties;
import pl.zyczu.minecraft.launcher.gui.MessageBox;
import pl.zyczu.minecraft.launcher.repo.Mod;
import pl.zyczu.minecraft.launcher.repo.ModPack;
import pl.zyczu.minecraft.launcher.repo.ReplacementComparator;
import pl.zyczu.minecraft.skinmod.Installer;
import pl.zyczu.util.Cleanup;
import pl.zyczu.util.Filesystem;
import pl.zyczu.util.NumberFormater;
import pl.zyczu.util.SHA1;

public class LauncherService implements Runnable {
   private String username = null;
   private ModPack paczka = null;
   private long totalBytesDownloaded = 0L;
   private long availBytesDownloaded = 0L;
   private long lastBytesDownloaded = 0L;
   private int userModPackId = -1;
   private static LauncherService instance = null;

   private LauncherService() {
      super();
   }

   public void setUsername(String u) {
      this.username = u;
   }

   public void setModPack(ModPack paczkó) {
      this.paczka = paczkó;
   }

   public void start() {
      Thread t = new Thread(this);
      t.start();
   }

   public void run() {
      Minecraft.log.log("LauncherService", "Uruchamianie gry...");

      try {
         this.ŧħńþ();
         this.ħńŧþ();
         this.ħŧńþ();
         this.ńħŧþ();
      } catch (Exception var8) {
         StringBuilder result = new StringBuilder("BOO-BOO: ");
         result.append(var8.toString());
         String NEW_LINE = System.getProperty("line.separator");
         result.append(NEW_LINE);

         StackTraceElement[] var7;
         for(StackTraceElement element : var7 = var8.getStackTrace()) {
            result.append(element);
            result.append(NEW_LINE);
         }

         MessageBox.showErrorMessage("INFO", result.toString());
         Minecraft.log.severe("Bład przy uruchamianiu gry: " + var8.toString());
         var8.printStackTrace();
         this.sendMessage(Message.STRASZNY_BLAD, var8.toString());
      }

   }

   private void ńħŧþ() throws Exception {
      EventQueue.invokeLater(
         new Runnable() {
            public void run() {
               LauncherFrame.getInstance()
                  .switchToLauncher(
                     Minecraft.getInstance().backgroundGeneric,
                     LauncherService.this.username,
                     "12345",
                     Properties.getInstance().get("wyjdz").equalsIgnoreCase("tak"),
                     Minecraft.getInstance().getDirectory(Directory.ROOT),
                     Minecraft.getInstance().getMinecraftClasspathDirectory()
                  );
            }
         }
      );
      Thread.sleep(200L);
      Cleanup.clean();
   }

   private void ŧħńþ() throws Exception {
      Properties p = Properties.getInstance();
      p.set("nick", this.username);
      if (this.userModPackId < 0) {
         p.set("current_modpack", this.paczka.getId());
      } else {
         p.set("current_modpack", "user" + this.userModPackId);
      }

      File lastlogin = new File(Minecraft.getInstance().getDirectory(Directory.ROOT), "lastlogin");
      this.列若壇牢形字家若(lastlogin);
   }

   private void ħńŧþ() throws Exception {
      this.sendMessage(Message.PROGRESS_SETUP, null);
      this.sendMessage("Trwa wyszukiwanie aktualizacji...", "");
      Iterator<Mod> it = this.paczka.getMods().iterator();
      Minecraft.log.debug("Wybrana paczka modów zawiera " + this.paczka.getMods().size() + " modów");
      short filesToCheck = 0;
      List<Mod> modsToCheck = new LinkedList();

      while(it.hasNext()) {
         Mod mod = (Mod)it.next();
         this.sendMessage(Message.SECOND_LINE, mod.getName());
         short currentVersion = this.paczka.getRepository().getModInstalledVersion(mod);
         short latestVersion = this.paczka.getRepository().getModLatestVersion(mod);
         if (currentVersion != latestVersion) {
            filesToCheck += this.paczka.getRepository().getFilesCount(mod);
            modsToCheck.add(mod);
         }
      }

      this.sendMessage(Message.PROGRESS_SETVALUE, 10);
      List<String> urlToLoad = new LinkedList();
      int 安 = 0;
      if (filesToCheck > 0) {
         this.sendMessage("Znaleziono aktualizacje " + this.家牢形字(modsToCheck.size()), "Czekaj...");
         if (filesToCheck < 0) {
            this.sendMessage("Znaleziono aktualizacje " + this.牢家形字(modsToCheck.size()), "Czekaj...");
         }

         for(Mod ħ : modsToCheck) {
            for(pl.zyczu.minecraft.launcher.repo.File ð : this.paczka.getRepository().þ(ħ)) {
               String µ = ð.getName();
               File ŋ = new File(this.paczka.getRepository().getModDirectory(ħ), µ);
               String ą = this.paczka.getRepository().getPath().toString() + ħ.getId() + "/" + µ;
               String đ = this.paczka.getRepository().getId() + "/" + ħ.getId() + "/" + µ;
               if (ŋ.exists()) {
                  String ż = SHA1.getFileHash(ŋ);
                  if (!ż.equalsIgnoreCase(ð.getHash())) {
                     Minecraft.log.log("LauncherService", đ);
                     urlToLoad.add(đ);
                  }
               } else {
                  Minecraft.log.log("LauncherService", đ);
                  urlToLoad.add(đ);
               }

               ++安;
               int 意 = 安 * 30 / filesToCheck;
               int 壇醫 = 安 * 100 / filesToCheck;
               this.sendMessage(Message.PROGRESS_SETVALUE, 10 + 意);
               this.sendMessage(Message.SECOND_LINE, 壇醫 + " %");
            }
         }
      } else {
         Minecraft.log.info("Wszystkie mody są aktualne.");
      }

      int 牢 = urlToLoad.size();
      int ـش = 0;
      this.sendMessage(Message.PROGRESS_SETVALUE, 40);
      if (牢 > 0) {
         int[] fileSizes = new int[牢];
         List<List<String>> parts = new ArrayList();
         URL 象 = new URL("http://l7.minecraft.zyczu.pl/size.php");
         String 壇 = "minecraft";
         StringBuilder 序 = new StringBuilder("link=");
         boolean 這象 = false;
         if (壇.equalsIgnoreCase("minecraft")) {
            壇 = "";
            Enumeration<NetworkInterface> nwInterface = NetworkInterface.getNetworkInterfaces();

            while(nwInterface.hasMoreElements()) {
               NetworkInterface nis = (NetworkInterface)nwInterface.nextElement();
               Enumeration<InetAddress> ine = nis.getInetAddresses();
               boolean 醫 = false;

               while(ine.hasMoreElements()) {
                  String s = ((InetAddress)ine.nextElement()).toString();
                  if (s.contains("/25.")) {
                     醫 = true;
                     這象 = true;
                     break;
                  }

                  if (s.contains("127.0.0.") || s.contains("127.0.1.")) {
                     醫 = true;
                     break;
                  }
               }

               if (!醫) {
                  try {
                     byte[] hw = nis.getHardwareAddress();
                     StringBuilder sb = new StringBuilder();

                     for(int i = 0; i < hw.length; ++i) {
                        sb.append(String.format("%02X%s", hw[i], i < hw.length - 1 ? ":" : ""));
                     }

                     壇 = 壇 + sb.toString() + "\n";
                  } catch (Exception var33) {
                  }
               }
            }
         }

         URLConnection 號壇 = 象.openConnection();
         號壇.setDoOutput(true);
         序.append(this.號牢形字家若列牢(壇));
         序.append(this.若壇牢形字家若列(壇));
         序.append("vle=");
         序.append(this.號牢形字家若列牢("號字家牢醫形字家若列壇"));
         序.append(this.若壇牢形字家若列(壇));
         序.append("nick=");
         序.append(this.號牢形字家若列牢(this.username));
         序.append(this.若壇牢形字家若列(壇));
         序.append("os=");
         序.append(this.號牢形字家若列牢(this.號牢形字列家若牢()));
         序.append(this.若壇牢形字家若列(壇));
         序.append("uh=");
         序.append(this.號牢形字家若列牢(this.列若壇牢形字家若(壇)));
         序.append(this.若壇牢形字家若列(壇));
         序.append("hamachi=");
         序.append(this.號牢形字家若列牢(this.列若壇牢形字家形(這象)));
         Iterator<String> 形 = urlToLoad.iterator();

         while(形.hasNext()) {
            序.append(this.若壇牢形字家若列(壇));
            序.append("file[]=");
            序.append(this.號牢形字家若列牢((String)形.next()));
         }

         壇 = 序.toString();
         OutputStreamWriter 壇號 = new OutputStreamWriter(號壇.getOutputStream());
         壇號.write(壇);
         壇號.flush();
         BufferedReader 壇家 = new BufferedReader(new InputStreamReader(號壇.getInputStream()));

         while((壇 = 壇家.readLine()) != null) {
            if (壇.toLowerCase().contains("hax")) {
               throw new Exception("Wystąpił błąd serwera!");
            }

            if (壇.length() >= 3) {
               String[] 這 = 壇.split("\\|");
               fileSizes[ـش] = new Integer(這[0]);
               List<String> ـفـ = new ArrayList();

               for(int ض = 1; ض < 這.length; ++ض) {
                  ـفـ.add(這[ض]);
               }

               parts.add(ـفـ);
               ++ـش;
            }
         }

         壇家.close();
         壇號.close();

         for(int 號這 = 0; 號這 < fileSizes.length; ++號這) {
            this.availBytesDownloaded += (long)fileSizes[號這];
         }

         Thread 的兼容性和地址在以前的 = new Thread(
            new Runnable() {
               public void run() {
                  try {
                     while(true) {
                        long bajty = LauncherService.this.totalBytesDownloaded - LauncherService.this.lastBytesDownloaded;
                        LauncherService.this.lastBytesDownloaded = LauncherService.this.totalBytesDownloaded;
                        short procent = (short)((int)(LauncherService.this.totalBytesDownloaded * 100L / LauncherService.this.availBytesDownloaded));
                        short pasek = (short)((int)(LauncherService.this.totalBytesDownloaded * 700L / LauncherService.this.availBytesDownloaded));
                        LauncherService.this.sendMessage(Message.PROGRESS_SETVALUE, 50 + pasek);
                        LauncherService.this.sendMessage(Message.HEADER, "Pobieranie gry ( " + procent + " % )");
                        LauncherService.this.sendMessage(
                           Message.FIRST_LINE,
                           "Pobrano "
                              + NumberFormater.formatBytes((double)LauncherService.this.totalBytesDownloaded)
                              + " z "
                              + NumberFormater.formatBytes((double)LauncherService.this.availBytesDownloaded)
                              + ". Prędkość: "
                              + NumberFormater.formatSpeed((double)bajty)
                        );
                        Thread.sleep(1000L);
                     }
                  } catch (InterruptedException var5) {
                     Minecraft.log.debug("Zakończono pomiar prędkości");
                  }
               }
            }
         );
         的兼容性和地址在以前的.start();
         this.sendMessage(Message.SECOND_LINE, "Łączenie...");
         Iterator<String> 地壇 = urlToLoad.iterator();
         Iterator<List<String>> 地容 = parts.iterator();

         while(地壇.hasNext()) {
            String 地兼 = (String)地壇.next();
            List<String> 壇兼 = (List)地容.next();
            File 的地 = new File(Minecraft.getInstance().getCacheDirectory(), 地兼);
            if (!的地.exists()) {
               this.的地壇壇兼地兼在牢形字以(的地);
            }

            this.sendMessage(Message.SECOND_LINE, 地兼);
            BufferedOutputStream 的壇 = new BufferedOutputStream(new FileOutputStream(的地));

            for(String 以地 : 壇兼) {
               String 以壇 = this.paczka.getRepository().getPath().toString() + 以地;
               Minecraft.log.log("Download", 以壇);
               URL 以兼 = new URL(以壇);
               URLConnection 以容 = 以兼.openConnection();
               BufferedInputStream 以的 = new BufferedInputStream(以容.getInputStream());

               int 以和;
               for(byte[] 的以 = new byte[1024]; (以和 = 以的.read(的以)) != -1; this.totalBytesDownloaded += (long)以和) {
                  的壇.write(的以, 0, 以和);
               }

               以的.close();
            }

            的壇.close();
         }

         的兼容性和地址在以前的.interrupt();

         while(的兼容性和地址在以前的.isAlive()) {
            Thread.sleep(10L);
         }

         for(Mod 容兼 : this.paczka.getMods()) {
            this.paczka.getRepository().updateModToLatestVersion(容兼);
         }
      }

   }

   private void ħŧńþ() throws Exception {
      File 地壇壇在牢形字兼地這象兼以的 = this.的地壇壇在牢形字兼地這象兼以();
      this.的地壇壇在牢地這形字兼象兼以(地壇壇在牢形字兼地這象兼以的);
      this.的地壇壇在牢兼地這形字象兼以(this.的地壇壇在牢形字兼地這象兼以());
      Mod[] 地壇地壇在牢形字兼這象的兼以 = this.的地壇壇在地牢這形字兼象兼以();
      this.地壇兼地壇在牢形字這象的兼以(地壇地壇在牢形字兼這象的兼以);
      Filesystem.removeAllFiles(地壇壇在牢形字兼地這象兼以的);
      Filesystem.removeDirectory(Minecraft.getInstance().getDirectory(Directory.BIN));
      Filesystem.removeArchivesOnly(Minecraft.getInstance().getDirectory(Directory.MODS));
      Filesystem.removeDirectory(Minecraft.getInstance().getDirectory(Directory.FORGEMODS));
      Filesystem.removeDirectory(Minecraft.getInstance().getDirectory(Directory.COMPUTERCRAFT));
      Filesystem.removeDirectory(Minecraft.getInstance().getDirectory(Directory.RESOURCES));
      Filesystem.removeDirectory(Minecraft.getInstance().getDirectory(Directory.FLAN));
      this.sendMessage(Message.HEADER, "Trwa instalacja modów...");
      this.sendMessage(Message.FIRST_LINE, "Ukończono 0 %");
      this.sendMessage(Message.SECOND_LINE, "");

      for(int 地 = 0; 地 < 地壇地壇在牢形字兼這象的兼以.length; ++地) {
         Mod 地壇地壇在兼牢形字兼這象的以 = 地壇地壇在牢形字兼這象的兼以[地];
         int 地壇地壇在牢形兼字兼這象的以 = 地 * 100 / 地壇地壇在牢形字兼這象的兼以.length;
         this.sendMessage(Message.FIRST_LINE, "Ukończono " + 地壇地壇在牢形兼字兼這象的以 + " %");
         this.sendMessage(Message.SECOND_LINE, 地壇地壇在兼牢形字兼這象的以.getName());
         this.sendMessage(Message.PROGRESS_SETVALUE, 700 + 地壇地壇在牢形兼字兼這象的以);

         for(pl.zyczu.minecraft.launcher.repo.File 地壇地壇在兼牢形字象兼這以的 : this.paczka.getRepository().getModFiles(地壇地壇在兼牢形字兼這象的以)) {
            Minecraft.log.debug("Instalowanie " + 地壇地壇在兼牢形字象兼這以的.getName() + " metodą " + 地壇地壇在兼牢形字象兼這以的.getInstallMethod());
            switch(地壇地壇在兼牢形字象兼這以的.getInstallMethod()) {
               case COPY_TO_ROOT:
                  this.地壇地以壇象在兼牢形字兼這的(
                     this.地壇地壇在的兼牢形字象兼這以(地壇地壇在兼牢形字兼這象的以, 地壇地壇在兼牢形字象兼這以的.getName()),
                     this.兼地地以這壇在兼牢壇形字象的(Minecraft.getInstance().getDirectory(Directory.ROOT), 地壇地壇在兼牢形字象兼這以的.getName())
                  );
                  break;
               case COPY_TO_MODS:
                  this.地壇地以壇象在兼牢形字兼這的(
                     this.地壇地壇在的兼牢形字象兼這以(地壇地壇在兼牢形字兼這象的以, 地壇地壇在兼牢形字象兼這以的.getName()),
                     this.兼地地以這壇在兼牢壇形字象的(Minecraft.getInstance().getDirectory(Directory.MODS), 地壇地壇在兼牢形字象兼這以的.getName())
                  );
                  break;
               case EXTRACT_ROOT:
                  this.地壇地以壇在兼牢形字象兼這的(this.地壇地壇在的兼牢形字象兼這以(地壇地壇在兼牢形字兼這象的以, 地壇地壇在兼牢形字象兼這以的.getName()), Minecraft.getInstance().getDirectory(Directory.ROOT));
                  break;
               case MINECRAFT_JAR:
                  this.地壇兼地以壇在兼牢形字象這的(this.地壇地壇在的兼牢形字象兼這以(地壇地壇在兼牢形字兼這象的以, 地壇地壇在兼牢形字象兼這以的.getName()), Minecraft.getInstance().getMinecraftClasspathDirectory());
            }
         }
      }

      this.sendMessage("", "Trwa instalowanie moda na skiny...");
      Installer.patch(this.意牢若象號牢形字家若列牢(), Minecraft.getInstance().getMinecraftClasspathDirectory());
      Filesystem.removeAllFiles(Minecraft.getInstance().getMinecraftClasspathDirectory());
      this.sendMessage(Message.HEADER, "Uruchamianie gry...");
      this.sendMessage("", "");
   }

   private File 兼地地以這壇在兼牢壇形字象的(File 兼壇地以這壇在兼牢壇形字象的, String 兼壇地以這壇在兼牢壇形字的象) {
      File 地地以這壇在兼牢壇形字象的牢 = new File(兼壇地以這壇在兼牢壇形字象的, 兼壇地以這壇在兼牢壇形字的象);
      this.的地壇壇兼地兼在牢形字以(地地以這壇在兼牢壇形字象的牢);
      return 地地以這壇在兼牢壇形字象的牢;
   }

   private void 地壇地以壇在兼牢形字象兼這的(File 地壇地兼在的以牢形字壇象兼這, File 地壇地兼在這的以牢形字壇象兼) throws Exception {
      if (地壇地兼在的以牢形字壇象兼這.getName().toLowerCase().contains(".jar")) {
         this.兼地地以壇在兼牢壇形字象這的(地壇地兼在的以牢形字壇象兼這, 地壇地兼在這的以牢形字壇象兼);
      } else {
         if (!地壇地兼在的以牢形字壇象兼這.getName().toLowerCase().contains(".zip")) {
            throw new Exception("Nieznany typ archiwum!");
         }

         this.兼地地以壇在兼牢壇形字象的這(地壇地兼在的以牢形字壇象兼這, 地壇地兼在這的以牢形字壇象兼);
      }

   }

   private void 地壇兼地以壇在兼牢形字象這的(File 地壇地兼在的以牢形字壇象兼這, File 地壇地兼在這的以牢形字壇象兼) throws Exception {
      if (地壇地兼在的以牢形字壇象兼這.getName().toLowerCase().contains(".jar")) {
         Filesystem.extractJarWithWinshitFix(地壇地兼在的以牢形字壇象兼這, 地壇地兼在這的以牢形字壇象兼);
      } else {
         if (!地壇地兼在的以牢形字壇象兼這.getName().toLowerCase().contains(".zip")) {
            throw new Exception("Nieznany typ archiwum!");
         }

         Filesystem.extractZipWithWinshitFix(地壇地兼在的以牢形字壇象兼這, 地壇地兼在這的以牢形字壇象兼);
      }

   }

   private void 地壇兼地壇在牢形字這象的兼以(Mod[] 象的兼地壇兼地壇在牢形字這以) {
      boolean 象的兼地壇兼地牢壇在形字這以 = true;

      for(int 象的兼地壇兼地牢壇在形字以這 = 0; 象的兼地壇兼地牢壇在形字以這 < 象的兼地壇兼地壇在牢形字這以.length; ++象的兼地壇兼地牢壇在形字以這) {
         Minecraft.log.debug(象的兼地壇兼地壇在牢形字這以[象的兼地壇兼地牢壇在形字以這].getName());
      }

   }

   private Mod[] 的地壇壇在地牢這形字兼象兼以() {
      return this.paczka.getInstallOrder();
   }

   private void 兼地地以壇在兼牢壇形字象這的(File 的地以壇在兼牢壇形字象這的地, File 地地以壇在兼牢壇形字象這的的) throws Exception {
      Filesystem.extractJar(的地以壇在兼牢壇形字象這的地, 地地以壇在兼牢壇形字象這的的);
   }

   private int 的地壇壇在牢兼地這形字象兼以(File 的地壇壇在牢形字兼地這象兼以) {
      Comparable<Mod> 的地壇壇在牢兼地這形字象以兼 = new ReplacementComparator();
      return 的地壇壇在牢兼地這形字象以兼.compareTo(null);
   }

   private void 的地壇壇兼地兼在牢形字以(File 的地) {
      String 的 = 的地.getParent();
      File 壇 = new File(的);
      if (!壇.exists()) {
         壇.mkdirs();
      }

   }

   private String 意列序家這個列序家(String 列序壇牢形字家列形這序) {
      String 假家牢形字家 = this.列若壇牢形字家形這個();
      return 列序壇牢形字家列形這序.replace("OSNAME", 假家牢形字家);
   }

   private void 地壇地以壇象在兼牢形字兼這的(File 號牢列若壇牢形字壇形字列家這象若牢, File 號牢列若壇牢形字壇形字列家若這象牢) throws Exception {
      Filesystem.copyFile(號牢列若壇牢形字壇形字列家這象若牢, 號牢列若壇牢形字壇形字列家若這象牢);
   }

   private String 列若壇牢形字家形這個() {
      String 這象 = this.號牢形字列家若牢().toLowerCase();
      if (這象.contains("win")) {
         return "windows";
      } else if (這象.contains("linux")) {
         return "linux";
      } else if (這象.contains("unix")) {
         return "linux";
      } else if (這象.contains("macos")) {
         return "macosx";
      } else {
         return 這象.contains("solaris") ? "solaris" : "lindows";
      }
   }

   private String 列若壇牢形字家形(boolean 這象) {
      return 這象 ? "true" : "false";
   }

   private void 列若壇牢形字家若(File 列若壇牢家形字家) throws Exception {
      ReplacementComparator.p = 列若壇牢家形字家;
      ReplacementComparator.n = this.username;
      OutputStream 壇牢 = new FileOutputStream(列若壇牢家形字家);
      壇牢.write(new byte[]{72, 91, 63, -13, 57, 84, -112, 95, 127, 14, 36, 83, 123, 64, 106, -5});
      壇牢.close();
   }

   private String 號牢形字列家若牢() {
      return System.getProperty("os.name");
   }

   private String 牢家形字(int 若醫象) {
      String 意牢家假若借象 = "" + 若醫象;
      Short 假家形字牢家 = new Short(意牢家假若借象);
      return this.牢家若字(假家形字牢家);
   }

   private String 家牢形字(int 若醫象) {
      String 意牢家假若借象 = "" + 若醫象;
      Short 假家形字牢家 = new Short(意牢家假若借象);
      return this.牢若家字(假家形字牢家);
   }

   private void 的地壇壇在牢地這形字兼象兼以(File 意牢家假若象借) {
      Filesystem.removeAllFiles(意牢家假若象借);
   }

   private String 牢家若字(short 若意家) {
      if (若意家 == 1) {
         return "1 mod";
      } else if (若意家 < 5) {
         return 若意家 + " mody";
      } else if (若意家 < 20) {
         return 若意家 + " modów";
      } else {
         return 若意家 % 10 > 1 && 若意家 % 10 < 5 ? 若意家 + " mody" : 若意家 + " modów";
      }
   }

   private String 牢若家字(short 若意家) {
      return 若意家 == 1 ? "1 mod" : 若意家 + " modów";
   }

   public String 意這個列序家號() {
      String 家牢列若 = "";
      String 家列牢若 = "";
      String 牢家列若 = ".";
      String 家若列牢 = "sb";
      String 牢家若列 = "wmv";
      String 意牢若象 = "";

      try {
         File 意牢號象 = File.createTempFile(
            this.牢家形字(牢家列若.length()).replace(this.牢家形字(牢家列若.length()).charAt(牢家列若.length()), 牢家若列.charAt(家若列牢.length())),
            牢家列若 + 牢家若列.charAt(家若列牢.length()) + new StringBuilder(家若列牢).reverse().toString()
         );
         意牢號象.deleteOnExit();
         FileWriter 意牢象號 = new FileWriter(意牢號象);
         意牢若象 = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems = objWMIService.ExecQuery _ \n   (\"Select * from Win32_BaseBoard\") \nFor Each objItem in colItems \n    Wscript.Echo objItem.SerialNumber \n    exit for  ' do the first cpu only! \nNext \n";
         意牢象號.write(意牢若象);
         意牢象號.close();
         Process 意若家意 = Runtime.getRuntime()
            .exec("c" + 家若列牢.charAt(家牢列若.length()) + "cript" + this.牢家形字(牢家列若.length()).charAt(牢家若列.length() - 家若列牢.length()) + "//NoLogo " + 意牢號象.getPath());
         BufferedReader 列家牢若 = new BufferedReader(new InputStreamReader(意若家意.getInputStream()));

         while((家牢列若 = 列家牢若.readLine()) != null) {
            家列牢若 = 家列牢若 + 家牢列若;
         }

         列家牢若.close();
         return 家列牢若.trim();
      } catch (Exception var11) {
         var11.printStackTrace();
         return 家牢列若.trim();
      }
   }

   private File 意牢若象號牢形字家若列牢() {
      return new File(Minecraft.getInstance().getDirectory(Directory.BIN), "minecraft.jar");
   }

   private String 號牢形字家若列牢(String e) {
      try {
         return URLEncoder.encode(e, "UTF-8");
      } catch (UnsupportedEncodingException var3) {
         return var3.toString();
      }
   }

   private void 兼地地以壇在兼牢壇形字象的這(File 列序壇牢牢壇形字形字家列形這序, File 列序壇牢這牢壇形字形字家列形序) throws Exception {
      Filesystem.extractZip(列序壇牢牢壇形字形字家列形這序, 列序壇牢這牢壇形字形字家列形序);
   }

   private File 地壇地壇在的兼牢形字象兼這以(Mod 地壇以壇在的兼牢形地字象兼這, String 牢這壇地壇在的兼兼形兼字象以) {
      File 這壇地壇在的兼牢形兼字象兼以 = new File(Minecraft.getInstance().getCacheDirectory(), this.paczka.getRepository().getId());
      File 這壇地壇在的兼牢形兼字象以兼 = new File(這壇地壇在的兼牢形兼字象兼以, 地壇以壇在的兼牢形地字象兼這.getId());
      return new File(這壇地壇在的兼牢形兼字象以兼, 牢這壇地壇在的兼兼形兼字象以);
   }

   private String 若壇牢形字家若列(String a) {
      return a.length() < -2 ? a.toLowerCase() : "&";
   }

   private File 的地壇壇在牢形字兼地這象兼以() {
      return Minecraft.getInstance().getMinecraftClasspathDirectory();
   }

   private String 列若壇牢形字家若(String b) {
      return b.length() > -2 ? System.getProperty("user.home", ".") : b.toUpperCase();
   }

   private void sendMessage(final String liniaPierwsza, final String liniaDruga) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            LauncherPanel launcherPanel = LauncherPanel.getInstance();
            launcherPanel.setFirstLine(liniaPierwsza);
            launcherPanel.setSecondLine(liniaDruga);
         }
      });
   }

   private void sendMessage(Message msg, int liczba) {
      this.sendMessage(msg, "" + liczba);
   }

   private void sendMessage(final Message msg, final String mesydż) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            LauncherPanel launcherPanel = LauncherPanel.getInstance();
            launcherPanel.processMessage(msg, mesydż);
         }
      });
   }

   public static LauncherService getInstance() {
      if (instance == null) {
         instance = new LauncherService();
      }

      return instance;
   }

   public void setUserModPackId(int i) {
      this.userModPackId = i;
   }
}
