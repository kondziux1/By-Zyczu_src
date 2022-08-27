package pl.zyczu.minecraft.launcher;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.swing.JOptionPane;
import pl.zyczu.minecraft.launcher.repo.RepositoryManager;
import pl.zyczu.util.Filesystem;
import pl.zyczu.util.SHA1;

public class NetworkService implements Runnable {
   private Minecraft mc;
   public static ServiceStatus status = ServiceStatus.UNKNOWN;
   public static String statusFile = null;
   public static Thread subThread = null;

   public NetworkService(Minecraft m) {
      super();
      this.mc = m;
   }

   public void run() {
      try {
         subThread = new Thread(new Runnable() {
            public void run() {
               try {
                  LinkedList<String> doPobrania = new LinkedList();
                  NetworkService.status = ServiceStatus.CONNECTING;
                  Minecraft.log.log("NetworkService", "Trwa aktualizowanie repozytoriów");
                  URL alt = new URL("http://l7.minecraft.zyczu.pl/update.php?version=10");
                  URLConnection conn = alt.openConnection();
                  NetworkService.status = ServiceStatus.SYNCING;
                  BufferedReader flrd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                  boolean ok = false;

                  String line;
                  while((line = flrd.readLine()) != null) {
                     if (line.contains("LISONHAX_OUTDATED_LAUNCHER")) {
                        JOptionPane.showMessageDialog(null, "Masz starą wersję launchera! Ściągnij najnowszy launcher ze strony: minecraft.zyczu.pl");
                        System.exit(0);
                     } else if (line.contains("OKOKOK")) {
                        ok = true;
                     } else if (line.length() > 2) {
                        if (ok) {
                           try {
                              String[] n = line.split("\\|");
                              File f = new File(Minecraft.getWorkingDirectory(), "repo/" + n[0]);
                              if (!f.exists() || !SHA1.getFileHash(f).equals(n[1])) {
                                 doPobrania.add(n[0]);
                              }
                           } catch (Exception var15) {
                              JOptionPane.showMessageDialog(null, "Bład serwera: " + line);
                              System.exit(1);
                           }
                        } else {
                           JOptionPane.showMessageDialog(null, "Bład serwera: " + line);
                           System.exit(0);
                        }
                     }
                  }

                  flrd.close();
                  if (doPobrania.size() > 0) {
                     NetworkService.status = ServiceStatus.DOWNLOADING;

                     for(String pliczek : doPobrania) {
                        Minecraft.log.log("Download", "Pobieranie pliku " + pliczek);
                        NetworkService.statusFile = pliczek;
                        URL url = new URL("http://l7.minecraft.zyczu.pl/" + pliczek);
                        URLConnection cn = url.openConnection();
                        InputStream fb = cn.getInputStream();
                        OutputStream out = new FileOutputStream(new File(Minecraft.getWorkingDirectory(), "repo/" + pliczek));
                        byte[] buf = new byte[1024];
                        int przeczytano = -1;

                        while((przeczytano = fb.read(buf)) != -1) {
                           out.write(buf, 0, przeczytano);
                        }

                        fb.close();
                        out.close();
                     }
                  } else {
                     Minecraft.log.log("NetworkService", "Repozytoria są aktualne");
                  }
               } catch (Exception var16) {
                  Minecraft.log.severe("Straszny błąd przy aktualizacji!");
                  var16.printStackTrace();
               }

            }
         });
         subThread.start();

         while(subThread.isAlive()) {
            Thread.sleep(20L);
         }

         status = ServiceStatus.INTERPRETING;
         long start = System.currentTimeMillis();
         RepositoryManager repositoryManager = RepositoryManager.getInstance();
         repositoryManager.setup(Minecraft.getWorkingDirectory());
         long stop = System.currentTimeMillis();
         Minecraft.log.log("Debug", "Analiza repozytorium zajęła " + (stop - start) + " ms");
         if (Properties.getInstance().get("firstrun", "true").equalsIgnoreCase("true")) {
            status = ServiceStatus.FIRSTRUN_INIT;
            Properties.getInstance().set("firstrun", "false");
            File minecraftLauncherByZyczu = new File(Minecraft.getWorkingDirectory().getParent(), ".zyczulauncher");
            File minecraftSP_exe = new File(Minecraft.getWorkingDirectory().getParent(), ".minecraft");
            if (minecraftLauncherByZyczu.exists()) {
               status = ServiceStatus.UPDATE_ZYCZU;
               Thread.sleep(500L);

               try {
                  File oldConfig = new File(minecraftLauncherByZyczu, "default.properties");
                  java.util.Properties legacyProperties = new java.util.Properties();
                  legacyProperties.load(new BufferedInputStream(new FileInputStream(oldConfig)));
                  if (!legacyProperties.getProperty("quitbtn", "tak").equalsIgnoreCase("tak")) {
                     Properties.getInstance().set("wyjdz", "false");
                  }

                  java.util.Properties var22 = null;
               } catch (Exception var18) {
                  Minecraft.log.warning("Nie udało się przywrócić ustawień przycisku Wyjdź z Gry!");
               }

               try {
                  String password = "passwordfile";
                  Random random = new Random(43287234L);
                  byte[] salt = new byte[8];
                  random.nextBytes(salt);
                  PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, 5);
                  SecretKey pbeKey = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(new PBEKeySpec(password.toCharArray()));
                  Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
                  cipher.init(2, pbeKey, pbeParamSpec);
                  File lastLogin = new File(minecraftLauncherByZyczu, "lastlogin");
                  DataInputStream dis;
                  if (cipher != null) {
                     dis = new DataInputStream(new CipherInputStream(new FileInputStream(lastLogin), cipher));
                  } else {
                     dis = new DataInputStream(new FileInputStream(lastLogin));
                  }

                  Properties.getInstance().set("nick", dis.readUTF());
                  dis.close();
               } catch (Exception var17) {
                  Minecraft.log.warning("Nie udało się przywrócić nicku z MinecraftLauncherByZyczu_v3_2_0.jar");
               }

               Filesystem.removeDirectory(minecraftLauncherByZyczu);
            } else if (minecraftSP_exe.exists()) {
               status = ServiceStatus.UPDATE_MINECRAFTSP_EXE;
               Thread.sleep(500L);

               try {
                  String password = "passwordfile";
                  Random random = new Random(43287234L);
                  byte[] salt = new byte[8];
                  random.nextBytes(salt);
                  PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, 5);
                  SecretKey pbeKey = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(new PBEKeySpec(password.toCharArray()));
                  Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
                  cipher.init(2, pbeKey, pbeParamSpec);
                  File lastLogin = new File(minecraftSP_exe, "lastlogin");
                  DataInputStream dis;
                  if (cipher != null) {
                     dis = new DataInputStream(new CipherInputStream(new FileInputStream(lastLogin), cipher));
                  } else {
                     dis = new DataInputStream(new FileInputStream(lastLogin));
                  }

                  Properties.getInstance().set("nick", dis.readUTF());
                  dis.close();
                  lastLogin.delete();
               } catch (Exception var16) {
                  Minecraft.log.warning("Nie udało się przywrócić nicku z MinecraftSP.exe");
               }
            }
         }

         status = ServiceStatus.READY;
      } catch (Exception var19) {
         Minecraft.log.log("NetworkService", "Wystąpił bład " + var19.toString());
         var19.printStackTrace();
      }

   }
}
