package pl.zyczu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import pl.zyczu.minecraft.launcher.Minecraft;

public class Filesystem {
   public Filesystem() {
      super();
   }

   private static void rmdir(File f) {
      if (f.isDirectory()) {
         File[] var4;
         for(File c : var4 = f.listFiles()) {
            rmdir(c);
         }
      }

      if (!f.delete()) {
         Minecraft.log.warning("Nie mogę usunąć: " + f.getAbsolutePath());
      }

   }

   private static void rmdira(File f) {
      if (f.isDirectory()) {
         File[] var4;
         for(File c : var4 = f.listFiles()) {
            rmdir(c);
         }
      }

   }

   private static void rmmoda(File f) {
      if (f.isDirectory()) {
         File[] var4;
         for(File c : var4 = f.listFiles()) {
            if (c.isFile()) {
               String d = c.getName().toLowerCase();
               if (d.contains(".jar") || d.contains(".zip")) {
                  c.delete();
               }
            }
         }
      }

   }

   public static void removeAllFiles(File f) {
      rmdira(f);
   }

   public static void removeDirectory(File directory) {
      if (directory.exists()) {
         rmdir(directory);
      }

   }

   public static void removeArchivesOnly(File directory) {
      if (directory.exists()) {
         rmmoda(directory);
      }

   }

   public static void copyFile(File plik, File cel) throws IOException {
      try {
         InputStream inStream = new FileInputStream(plik);
         OutputStream outStream = new FileOutputStream(cel);
         byte[] buffer = new byte[1024];

         int length;
         while((length = inStream.read(buffer)) > 0) {
            outStream.write(buffer, 0, length);
         }

         inStream.close();
         outStream.close();
      } catch (Exception var6) {
         var6.printStackTrace();
         throw new IOException("Nie można skopiować pliku " + plik.getName());
      }
   }

   public static void extractJar(File jar, File destdir) throws Exception {
      byte[] buf = new byte[1024];
      if (!destdir.exists()) {
         destdir.mkdirs();
      }

      JarInputStream zis = new JarInputStream(new FileInputStream(jar));
      JarEntry ze = null;

      while((ze = zis.getNextJarEntry()) != null) {
         if (!ze.isDirectory()) {
            File newFile = new File(destdir, ze.getName());
            new File(newFile.getParent()).mkdirs();
            FileOutputStream fos = new FileOutputStream(newFile);

            int len;
            while((len = zis.read(buf)) > 0) {
               fos.write(buf, 0, len);
            }

            fos.close();
         }
      }

      zis.closeEntry();
      zis.close();
   }

   public static void extractZip(File zip, File destdir) throws Exception {
      byte[] buf = new byte[1024];
      if (!destdir.exists()) {
         destdir.mkdirs();
      }

      ZipInputStream zis = new ZipInputStream(new FileInputStream(zip));
      ZipEntry ze = null;

      while((ze = zis.getNextEntry()) != null) {
         if (!ze.isDirectory()) {
            File newFile = new File(destdir, ze.getName());
            new File(newFile.getParent()).mkdirs();
            FileOutputStream fos = new FileOutputStream(newFile);

            int len;
            while((len = zis.read(buf)) > 0) {
               fos.write(buf, 0, len);
            }

            fos.close();
         }
      }

      zis.closeEntry();
      zis.close();
   }

   private static String realNameToWinshitName(String realName) {
      String comment = "Pozwala zapisać na dysku pliki aux.class i pozostałe zabronione nazwy plików pod łinszytem";
      realName = realName.replace("\\", "/");
      String[] sp = realName.split("/");
      StringBuilder rez = new StringBuilder();

      for(int i = 0; i < sp.length - 1; ++i) {
         rez.append(sp[i]).append("/");
      }

      rez.append("______");
      rez.append(sp[sp.length - 1]);
      return rez.toString();
   }

   public static void extractJarWithWinshitFix(File jar, File destdir) throws Exception {
      byte[] buf = new byte[1024];
      if (!destdir.exists()) {
         destdir.mkdirs();
      }

      JarInputStream zis = new JarInputStream(new FileInputStream(jar));
      JarEntry ze = null;

      while((ze = zis.getNextJarEntry()) != null) {
         if (!ze.isDirectory()) {
            File newFile = new File(destdir, realNameToWinshitName(ze.getName()));
            new File(newFile.getParent()).mkdirs();
            FileOutputStream fos = new FileOutputStream(newFile);

            int len;
            while((len = zis.read(buf)) > 0) {
               fos.write(buf, 0, len);
            }

            fos.close();
         }
      }

      zis.closeEntry();
      zis.close();
   }

   public static void extractZipWithWinshitFix(File zip, File destdir) throws Exception {
      byte[] buf = new byte[1024];
      if (!destdir.exists()) {
         destdir.mkdirs();
      }

      ZipInputStream zis = new ZipInputStream(new FileInputStream(zip));
      ZipEntry ze = null;

      while((ze = zis.getNextEntry()) != null) {
         if (!ze.isDirectory()) {
            File newFile = new File(destdir, realNameToWinshitName(ze.getName()));
            new File(newFile.getParent()).mkdirs();
            FileOutputStream fos = new FileOutputStream(newFile);

            int len;
            while((len = zis.read(buf)) > 0) {
               fos.write(buf, 0, len);
            }

            fos.close();
         }
      }

      zis.closeEntry();
      zis.close();
   }

   public static String winshitNameToRealName(String name) {
      return name.replace("______", "");
   }
}
