package net.infonode.util;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.PixelGrabber;
import java.net.URL;
import net.infonode.util.math.Int4;

public final class ImageUtils {
   public ImageUtils() {
      super();
   }

   public static final Image create(String var0) throws ImageException {
      Image var1 = Toolkit.getDefaultToolkit().createImage(var0);
      waitImage(var1);
      return var1;
   }

   public static final Image create(URL var0) throws ImageException {
      Image var1 = Toolkit.getDefaultToolkit().createImage(var0);
      waitImage(var1);
      return var1;
   }

   public static final Image create(byte[] var0) throws ImageException {
      Image var1 = Toolkit.getDefaultToolkit().createImage(var0);
      waitImage(var1);
      return var1;
   }

   public static final void waitImage(Image var0) throws ImageException {
      MediaTracker var1 = new MediaTracker(new Canvas());
      var1.addImage(var0, 1);

      try {
         var1.waitForID(1);
      } catch (InterruptedException var3) {
         throw new ImageException("Interrupted when creating image!");
      }
   }

   public static final int[] getPixels(Image var0) throws ImageException {
      return getPixels(var0, 0, 0, var0.getWidth(null), var0.getHeight(null));
   }

   public static final int[] getPixels(Image var0, int var1, int var2, int var3, int var4) throws ImageException {
      int[] var5 = new int[var3 * var4];
      PixelGrabber var6 = new PixelGrabber(var0, var1, var2, var3, var4, var5, 0, var3);

      try {
         var6.grabPixels();
      } catch (InterruptedException var8) {
         throw new ImageException("Interrupted waiting for pixels");
      }

      if ((var6.getStatus() & 128) != 0) {
         throw new ImageException("Image fetch aborted or errored");
      } else {
         return var5;
      }
   }

   public static final int getAlpha(int var0) {
      return var0 >> 24 & 0xFF;
   }

   public static final int getRed(int var0) {
      return var0 >> 16 & 0xFF;
   }

   public static final int getGreen(int var0) {
      return var0 >> 8 & 0xFF;
   }

   public static final int getBlue(int var0) {
      return var0 & 0xFF;
   }

   public static final int createPixel(int var0, int var1, int var2) {
      return 0xFF000000 | var0 << 16 | var1 << 8 | var2;
   }

   public static int toIntColor(Int4 var0) {
      return var0.getD() << 8 & 0xFF000000 | var0.getA() & 0xFF0000 | var0.getB() >> 8 & 0xFF00 | var0.getC() >> 16 & 0xFF;
   }

   public static Int4 toInt4(Color var0) {
      return new Int4(var0.getRed() << 16, var0.getGreen() << 16, var0.getBlue() << 16, var0.getAlpha() << 16);
   }

   public static Color toColor(Int4 var0) {
      return new Color(var0.getA() >> 16, var0.getB() >> 16, var0.getC() >> 16, var0.getD() >> 16);
   }

   public static final int[] createGradientPixels(Color[] var0, int var1, int var2) {
      int[] var3 = new int[var1 * var2];
      createGradientPixels(var0, var1, var2, var3);
      return var3;
   }

   public static final int[] createGradientPixels(Color[] var0, int var1, int var2, int[] var3) {
      int var4 = 0;
      Int4 var5 = toInt4(var0[0]);
      Int4 var6 = toInt4(var0[1]);
      Int4 var7 = toInt4(var0[2]).sub(toInt4(var0[0])).div((long)var2);
      Int4 var8 = toInt4(var0[3]).sub(toInt4(var0[1])).div((long)var2);
      Int4 var9 = new Int4();
      Int4 var10 = new Int4();

      for(int var11 = 0; var11 < var2; ++var11) {
         var9.set(var6).sub(var5).div((long)var1);
         var10.set(var5);

         for(int var12 = var4 + var1; var4 < var12; ++var4) {
            var3[var4] = toIntColor(var10);
            var10.add(var9);
         }

         var5.add(var7);
         var6.add(var8);
      }

      return var3;
   }

   public static AffineTransform createTransform(Direction var0, boolean var1, boolean var2, int var3, int var4) {
      int var5 = var1 ? -1 : 1;
      int var6 = var2 ? -1 : 1;
      int var7 = var0 == Direction.RIGHT ? var5 : (var0 == Direction.LEFT ? -var5 : 0);
      int var8 = var0 == Direction.DOWN ? -var6 : (var0 == Direction.UP ? var6 : 0);
      int var9 = var0 == Direction.DOWN ? var5 : (var0 == Direction.UP ? -var5 : 0);
      int var10 = var0 == Direction.RIGHT ? var6 : (var0 == Direction.LEFT ? -var6 : 0);
      return new AffineTransform(
         (float)var7,
         (float)var9,
         (float)var8,
         (float)var10,
         var7 == -1 ? (float)var3 : (var8 == -1 ? (float)var4 : 0.0F),
         var9 == -1 ? (float)var3 : (var10 == -1 ? (float)var4 : 0.0F)
      );
   }
}
