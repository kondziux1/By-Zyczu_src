package de.javasoft.util.java2d;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class DropShadow {
   private boolean highQuality = true;
   private BufferedImage shadow = null;
   private BufferedImage originalImage = null;
   private float angle = 45.0F;
   private int distance = -5;
   private int shadowSize = 5;
   private float shadowOpacity = 0.8F;
   private Color shadowColor = new Color(0);
   private int distance_x = 0;
   private int distance_y = 0;

   protected DropShadow() {
      super();
      this.computeShadowPosition();
   }

   public DropShadow(BufferedImage var1) {
      this();
      this.setImage(var1);
   }

   public void setImage(BufferedImage var1) {
      this.originalImage = var1;
      this.refreshShadow();
   }

   public BufferedImage getImage() {
      return this.originalImage;
   }

   public boolean getHighQuality() {
      return this.highQuality;
   }

   public void setQuality(boolean var1) {
      this.highQuality = var1;
      this.refreshShadow();
   }

   public float getAngle() {
      return this.angle;
   }

   public void setAngle(float var1) {
      this.angle = var1;
      this.computeShadowPosition();
   }

   public int getDistance() {
      return this.distance;
   }

   public void setDistance(int var1) {
      this.distance = var1;
      this.computeShadowPosition();
   }

   public Color getShadowColor() {
      return this.shadowColor;
   }

   public void setShadowColor(Color var1) {
      this.shadowColor = var1;
      this.refreshShadow();
   }

   public float getShadowOpacity() {
      return this.shadowOpacity;
   }

   public void setShadowOpacity(float var1) {
      this.shadowOpacity = var1;
      this.refreshShadow();
   }

   public int getShadowSize() {
      return this.shadowSize;
   }

   public void setShadowSize(int var1) {
      this.shadowSize = var1;
      this.refreshShadow();
   }

   private void refreshShadow() {
      if (this.originalImage != null) {
         this.shadow = this.createDropShadow(this.originalImage);
      }

   }

   private void computeShadowPosition() {
      double var1 = Math.toRadians((double)this.angle);
      this.distance_x = (int)(Math.cos(var1) * (double)this.distance);
      this.distance_y = (int)(Math.sin(var1) * (double)this.distance);
   }

   private BufferedImage createDropShadow(BufferedImage var1) {
      BufferedImage var2 = new BufferedImage(var1.getWidth() + this.shadowSize * 2, var1.getHeight() + this.shadowSize * 2, 2);
      Graphics2D var3 = var2.createGraphics();
      var3.drawImage(var1, null, this.shadowSize, this.shadowSize);
      var3.dispose();
      if (this.highQuality) {
         BufferedImage var4 = this.createShadowMask(var2);
         Synthetica2DUtils.createBlurOp(this.shadowSize).filter(var4, var2);
      } else {
         this.applyShadow(var2);
      }

      return var2;
   }

   private void applyShadow(BufferedImage var1) {
      int var2 = var1.getWidth();
      int var3 = var1.getHeight();
      int var4 = this.shadowSize - 1 >> 1;
      int var5 = this.shadowSize - var4;
      int var6 = var4;
      int var7 = var2 - var5;
      int var8 = var4;
      int var9 = var3 - var5;
      int var10 = this.shadowColor.getRGB() & 16777215;
      int[] var11 = new int[this.shadowSize];
      int var12 = 0;
      int[] var14 = ((DataBufferInt)var1.getRaster().getDataBuffer()).getData();
      int var15 = var5 * var2;
      float var16 = this.shadowOpacity / (float)this.shadowSize;
      int var17 = 0;

      for(int var18 = 0; var17 < var3; var18 = ++var17 * var2) {
         int var13 = 0;
         var12 = 0;

         for(int var19 = 0; var19 < this.shadowSize; ++var18) {
            int var20 = var14[var18] >>> 24;
            var11[var19] = var20;
            var13 += var20;
            ++var19;
         }

         var18 -= var5;

         for(int var30 = var6; var30 < var7; ++var18) {
            int var33 = (int)((float)var13 * var16);
            var14[var18] = var33 << 24 | var10;
            var13 -= var11[var12];
            var33 = var14[var18 + var5] >>> 24;
            var11[var12] = var33;
            var13 += var33;
            if (++var12 >= this.shadowSize) {
               var12 -= this.shadowSize;
            }

            ++var30;
         }
      }

      var17 = 0;

      for(int var28 = 0; var17 < var2; var28 = ++var17) {
         int var24 = 0;
         var12 = 0;

         for(int var31 = 0; var31 < this.shadowSize; var28 += var2) {
            int var35 = var14[var28] >>> 24;
            var11[var31] = var35;
            var24 += var35;
            ++var31;
         }

         var28 -= var15;

         for(int var32 = var8; var32 < var9; var28 += var2) {
            int var36 = (int)((float)var24 * var16);
            var14[var28] = var36 << 24 | var10;
            var24 -= var11[var12];
            var36 = var14[var28 + var15] >>> 24;
            var11[var12] = var36;
            var24 += var36;
            if (++var12 >= this.shadowSize) {
               var12 -= this.shadowSize;
            }

            ++var32;
         }
      }

   }

   private BufferedImage createShadowMask(BufferedImage var1) {
      BufferedImage var2 = new BufferedImage(var1.getWidth(), var1.getHeight(), 2);
      Graphics2D var3 = var2.createGraphics();
      var3.drawImage(var1, 0, 0, null);
      var3.setComposite(AlphaComposite.getInstance(5, this.shadowOpacity));
      var3.setColor(this.shadowColor);
      var3.fillRect(0, 0, var1.getWidth(), var1.getHeight());
      var3.dispose();
      return var2;
   }

   public void paintShadow(Graphics var1, int var2, int var3) {
      if (this.shadow != null) {
         var1.drawImage(this.shadow, var2 + this.distance_x, var3 + this.distance_y, null);
      }

   }

   public void paint(Graphics var1, int var2, int var3) {
      this.paint(var1, var2, var3, true);
   }

   public void paint(Graphics var1, int var2, int var3, boolean var4) {
      if (var4) {
         this.paintShadow(var1, var2, var3);
      }

      if (this.originalImage != null) {
         var1.drawImage(this.originalImage, var2, var3, null);
      }

   }
}
