package net.infonode.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class StreamUtil {
   public StreamUtil() {
      super();
   }

   public static final void readAll(InputStream var0, byte[] var1, int var2, int var3) throws IOException {
      while(var3 > 0) {
         int var4 = var0.read(var1, var2, var3);
         if (var4 == -1) {
            throw new IOException("End of stream reached!");
         }

         var2 += var4;
         var3 -= var4;
      }

   }

   public static final byte[] readAll(InputStream var0) throws IOException {
      byte[] var1 = new byte[var0.available()];
      int var2 = 0;

      while(var2 < var1.length) {
         var2 += var0.read(var1, var2, var1.length - var2);
      }

      var0.close();
      return var1;
   }

   public static final byte[] writeObject(Object var0) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      ObjectOutputStream var2 = new ObjectOutputStream(var1);
      var2.writeObject(var0);
      var2.close();
      return ArrayUtil.part(var1.toByteArray(), 0, var1.size());
   }

   public static final Object readObject(byte[] var0) throws IOException, ClassNotFoundException {
      return new ObjectInputStream(new ByteArrayInputStream(var0)).readObject();
   }

   public static byte[] write(Writable var0) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      ObjectOutputStream var2 = new ObjectOutputStream(var1);
      var0.write(var2);
      var2.close();
      return var1.toByteArray();
   }

   public static void read(byte[] var0, Readable var1) throws IOException {
      var1.read(new ObjectInputStream(new ByteArrayInputStream(var0)));
   }

   public static void readAll(InputStream var0, byte[] var1) throws IOException {
      readAll(var0, var1, 0, var1.length);
   }

   public static void write(InputStream var0, OutputStream var1, int var2) throws IOException {
      int var4;
      for(byte[] var3 = new byte[10000]; var2 > 0; var2 -= var4) {
         var4 = var0.read(var3, 0, Math.min(var3.length, var2));
         var1.write(var3, 0, var4);
      }

   }
}
