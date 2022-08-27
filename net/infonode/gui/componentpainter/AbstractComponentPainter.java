package net.infonode.gui.componentpainter;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import net.infonode.util.Direction;
import net.infonode.util.ImageUtils;

public abstract class AbstractComponentPainter implements ComponentPainter, Serializable {
   private static final long serialVersionUID = 1L;

   protected AbstractComponentPainter() {
      super();
   }

   public void paint(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      this.paint(var1, var2, var3, var4, var5, var6, Direction.RIGHT, false, false);
   }

   public void paint(Component var1, Graphics var2, int var3, int var4, int var5, int var6, Direction var7, boolean var8, boolean var9) {
      if (var7 == Direction.RIGHT && !var8 && !var9) {
         this.paint(var1, var2, var3, var4, var5, var6);
      } else {
         Graphics2D var10 = (Graphics2D)var2;
         AffineTransform var11 = var10.getTransform();

         try {
            int var12 = var7.isHorizontal() ? var5 : var6;
            int var13 = var7.isHorizontal() ? var6 : var5;
            AffineTransform var14 = ImageUtils.createTransform(var7, var8, var9, var12, var13);
            var10.translate(var3, var4);
            var10.transform(var14);
            this.paint(var1, var2, 0, 0, var12, var13);
         } finally {
            var10.setTransform(var11);
         }
      }

   }

   public boolean isOpaque(Component var1) {
      return true;
   }
}
