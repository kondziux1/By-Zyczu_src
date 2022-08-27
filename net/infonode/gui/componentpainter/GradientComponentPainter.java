package net.infonode.gui.componentpainter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.lang.ref.SoftReference;
import net.infonode.gui.colorprovider.ColorProvider;
import net.infonode.gui.colorprovider.FixedColorProvider;
import net.infonode.util.ColorUtil;
import net.infonode.util.Direction;
import net.infonode.util.ImageUtils;

public class GradientComponentPainter extends AbstractComponentPainter {
   private static final long serialVersionUID = 1L;
   private final ColorProvider[] colorProviders = new ColorProvider[4];
   private transient Color[] colors;
   private final int size = 128;
   private transient SoftReference[] images;
   private transient boolean hasAlpha;

   public GradientComponentPainter(Color var1, Color var2, Color var3, Color var4) {
      this(new FixedColorProvider(var1), new FixedColorProvider(var2), new FixedColorProvider(var3), new FixedColorProvider(var4));
   }

   public GradientComponentPainter(ColorProvider var1, ColorProvider var2, ColorProvider var3, ColorProvider var4) {
      super();
      this.colorProviders[0] = var1;
      this.colorProviders[1] = var2;
      this.colorProviders[2] = var3;
      this.colorProviders[3] = var4;
   }

   public void paint(Component var1, Graphics var2, int var3, int var4, int var5, int var6, Direction var7, boolean var8, boolean var9) {
      this.updateColors(var1);
      if (this.colors[0] != null && this.colors[1] != null && this.colors[2] != null && this.colors[3] != null) {
         if (this.colors[0].equals(this.colors[2]) && this.colors[1].equals(this.colors[3]) && this.colors[0].equals(this.colors[1])) {
            var2.setColor(this.colors[0]);
            var2.fillRect(var3, var4, var5, var6);
         } else {
            int var10 = var7.getValue() + (var8 ? 4 : 0) + (var9 ? 8 : 0);
            SoftReference var11 = this.images[var10];
            Image var12 = var11 == null ? null : (Image)var11.get();
            if (var12 == null) {
               var12 = this.createGradientImage(this.fixColors(var7, var8, var9));
               this.images[var10] = new SoftReference(var12);
            }

            var2.drawImage(var12, var3, var4, var5, var6, null);
         }
      }

   }

   private Color[] fixColors(Direction var1, boolean var2, boolean var3) {
      Color[] var4 = new Color[4];
      if (var2) {
         var4[0] = this.colors[1];
         var4[1] = this.colors[0];
         var4[2] = this.colors[3];
         var4[3] = this.colors[2];
      } else {
         var4[0] = this.colors[0];
         var4[1] = this.colors[1];
         var4[2] = this.colors[2];
         var4[3] = this.colors[3];
      }

      if (var3) {
         Color var5 = var4[2];
         var4[2] = var4[0];
         var4[0] = var5;
         var5 = var4[3];
         var4[3] = var4[1];
         var4[1] = var5;
      }

      if (var1 == Direction.RIGHT) {
         return var4;
      } else {
         if (var1 == Direction.DOWN) {
            Color var7 = var4[0];
            var4[0] = var4[2];
            var4[2] = var4[3];
            var4[3] = var4[1];
            var4[1] = var7;
         } else if (var1 == Direction.LEFT) {
            Color var8 = var4[0];
            var4[0] = var4[3];
            var4[3] = var8;
            var8 = var4[1];
            var4[1] = var4[2];
            var4[2] = var8;
         } else if (var1 == Direction.UP) {
            Color var10 = var4[0];
            var4[0] = var4[1];
            var4[1] = var4[3];
            var4[3] = var4[2];
            var4[2] = var10;
         }

         return var4;
      }
   }

   private Image createGradientImage(Color[] var1) {
      return Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(128, 128, ImageUtils.createGradientPixels(var1, 128, 128), 0, 128));
   }

   private void updateColors(Component var1) {
      if (this.images == null) {
         this.images = new SoftReference[16];
      }

      if (this.colors == null) {
         this.colors = new Color[4];
      }

      for(int var2 = 0; var2 < this.colors.length; ++var2) {
         Color var3 = this.colorProviders[var2].getColor(var1);
         if (var3 != null && !var3.equals(this.colors[var2])) {
            for(int var4 = 0; var4 < this.images.length; ++var4) {
               this.images[var4] = null;
            }
         }

         this.colors[var2] = var3;
         this.hasAlpha |= var3 != null && var3.getAlpha() != 255;
      }

   }

   public boolean isOpaque(Component var1) {
      this.updateColors(var1);
      return !this.hasAlpha;
   }

   public Color getColor(Component var1) {
      this.updateColors(var1);
      return ColorUtil.blend(ColorUtil.blend(this.colors[0], this.colors[1], 0.5), ColorUtil.blend(this.colors[2], this.colors[3], 0.5), 0.5);
   }
}
