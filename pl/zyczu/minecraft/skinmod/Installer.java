package pl.zyczu.minecraft.skinmod;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import pl.zyczu.util.Filesystem;

public class Installer {
   private static JarOutputStream jos = null;
   private static File base = null;

   public Installer() {
      super();
   }

   public static void recursivePatch(String sd) throws Exception {
      File d = new File(base, sd);
      File[] f = d.listFiles();

      for(int i = 0; i < f.length; ++i) {
         if (!f[i].getName().contains("META-INF")) {
            if (f[i].isDirectory()) {
               jos.putNextEntry(new JarEntry(prepareName(sd + "/" + f[i].getName() + "/")));
               jos.closeEntry();
               recursivePatch(sd + "/" + f[i].getName() + "/");
            } else {
               jos.putNextEntry(new JarEntry(prepareName(sd + "/" + Filesystem.winshitNameToRealName(f[i].getName()))));
               if (f[i].getName().contains(".class")) {
                  InputStream is = new BufferedInputStream(new FileInputStream(f[i]));
                  ByteArrayOutputStream out = new ByteArrayOutputStream();
                  byte[] buffer = new byte[1024];

                  int bufferSize;
                  while((bufferSize = is.read(buffer, 0, buffer.length)) != -1) {
                     out.write(buffer, 0, bufferSize);
                  }

                  byte[] dane = out.toByteArray();
                  is.close();
                  doPatch(dane, Patch.byte_old, Patch.byte_patch);
                  doPatch(dane, Patch.byte_old_2, Patch.byte_patch_2);
                  doPatch(dane, Patch.byte_old_3, Patch.byte_patch_3);
                  doPatch(dane, Patch.byte_old_4, Patch.byte_patch_4);
                  doPatch(dane, Patch.byte_old_5, Patch.byte_patch_5);
                  doPatch(dane, Patch.byte_old_6, Patch.byte_patch_6);
                  jos.write(dane);
                  out.close();
               } else {
                  InputStream is = new BufferedInputStream(new FileInputStream(f[i]));
                  byte[] bufor = new byte[1024];

                  int len;
                  while((len = is.read(bufor)) > 0) {
                     jos.write(bufor, 0, len);
                  }

                  is.close();
               }

               jos.closeEntry();
            }
         }
      }

   }

   public static void patch(File newFile, File tmpdir) throws Exception {
      if (newFile.exists()) {
         newFile.delete();
      }

      FileOutputStream fos = new FileOutputStream(newFile);
      jos = new JarOutputStream(new BufferedOutputStream(fos));
      base = tmpdir;
      recursivePatch("");
      jos.close();
   }

   private static void doPatch(byte[] dane, byte[] byte_old, byte[] byte_patch) {
      for(int i = 0; i < dane.length - byte_old.length; ++i) {
         boolean znal = true;

         for(int j = 0; j < byte_old.length; ++j) {
            if (dane[i + j] != byte_old[j]) {
               znal = false;
               break;
            }
         }

         if (znal) {
            for(int j = 0; j < byte_patch.length; ++j) {
               dane[i + j] = byte_patch[j];
            }
         }
      }

   }

   private static String prepareName(String name) {
      name = name.replace("\\", "/").replace("//", "/");
      if (name.startsWith("/")) {
         name = name.substring(1);
      }

      return name;
   }
}
