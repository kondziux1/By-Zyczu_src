package pl.zyczu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1 {
   public SHA1() {
      super();
   }

   private static byte[] createFileHash(File filename, String method) {
      try {
         try {
            InputStream fis = new FileInputStream(filename);
            byte[] buffer = new byte[1024];
            MessageDigest complete = MessageDigest.getInstance(method);
            int numRead = 0;

            while(numRead != -1) {
               numRead = fis.read(buffer);
               if (numRead > 0) {
                  complete.update(buffer, 0, numRead);
               }
            }

            fis.close();
            return complete.digest();
         } catch (NoSuchAlgorithmException var6) {
            return null;
         }
      } catch (IOException var7) {
         return null;
      }
   }

   private static byte[] createHash(String text, String method) {
      try {
         byte[] b = text.getBytes();
         MessageDigest algorithm = MessageDigest.getInstance(method);
         algorithm.reset();
         algorithm.update(b);
         return algorithm.digest();
      } catch (NoSuchAlgorithmException var5) {
         return null;
      }
   }

   public static String getFileHash(String filename) {
      return getFileHash(new File(filename));
   }

   public static String getFileHash(File filename) {
      try {
         byte[] b = createFileHash(filename, "SHA-1");
         return asHex(b);
      } catch (Exception var2) {
         return null;
      }
   }

   public static String getHash(String text) {
      try {
         byte[] b = createHash(text, "SHA-1");
         return asHex(b);
      } catch (Exception var2) {
         return null;
      }
   }

   private static String asHex(byte[] b) {
      String result = "";

      for(int i = 0; i < b.length; ++i) {
         result = result + Integer.toString((b[i] & 255) + 256, 16).substring(1);
      }

      return result;
   }
}
