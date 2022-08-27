package net.infonode.docking.internal;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;

public class HeavyWeightContainer extends Panel {
   private Image bufferImage;
   private boolean doubleBuffer = false;

   public HeavyWeightContainer(Component var1) {
      this(var1, false);
   }

   public HeavyWeightContainer(Component var1, boolean var2) {
      super(new BorderLayout());
      this.doubleBuffer = var2;
      this.add(var1, "Center");
   }

   public void invalidate() {
      super.invalidate();
      this.bufferImage = null;
   }

   public void update(Graphics var1) {
      if (this.doubleBuffer) {
         this.paint(var1);
      } else {
         super.update(var1);
      }

   }

   public boolean isDoubleBuffered() {
      return this.doubleBuffer;
   }

   public void paint(Graphics var1) {
      if (this.doubleBuffer) {
         if (this.bufferImage == null) {
            this.bufferImage = this.createImage(this.getWidth(), this.getHeight());
         }

         Graphics var2 = this.bufferImage.getGraphics();
         var2.setColor(this.getBackground());
         var2.fillRect(0, 0, this.getWidth(), this.getHeight());
         super.paint(var2);
         var1.drawImage(this.bufferImage, 0, 0, null);
         var2.dispose();
      } else {
         super.paint(var1);
      }

   }
}
