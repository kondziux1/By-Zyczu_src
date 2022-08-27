package net.infonode.gui;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.plaf.basic.BasicLabelUI;
import net.infonode.util.Direction;

public class RotatableLabelUI extends BasicLabelUI {
   private static Rectangle paintIconR = new Rectangle();
   private static Rectangle paintTextR = new Rectangle();
   private static Rectangle paintViewR = new Rectangle();
   private Direction direction;
   private boolean mirror;

   public RotatableLabelUI(Direction var1) {
      this(var1, false);
   }

   public RotatableLabelUI(Direction var1, boolean var2) {
      super();
      this.direction = var1;
      this.mirror = var2;
   }

   public Direction getDirection() {
      return this.direction;
   }

   public void setDirection(Direction var1) {
      this.direction = var1;
   }

   public boolean isMirror() {
      return this.mirror;
   }

   public void setMirror(boolean var1) {
      this.mirror = var1;
   }

   public void paint(Graphics var1, JComponent var2) {
      JLabel var3 = (JLabel)var2;
      String var4 = var3.getText();
      Icon var5 = var3.isEnabled() ? var3.getIcon() : var3.getDisabledIcon();
      if (var5 != null || var4 != null) {
         FontMetrics var6 = var1.getFontMetrics();
         Insets var7 = var2.getInsets();
         paintViewR.x = var7.left;
         paintViewR.y = var7.top;
         if (this.direction.isHorizontal()) {
            paintViewR.height = var2.getHeight() - (var7.top + var7.bottom);
            paintViewR.width = var2.getWidth() - (var7.left + var7.right);
         } else {
            paintViewR.height = var2.getWidth() - (var7.top + var7.bottom);
            paintViewR.width = var2.getHeight() - (var7.left + var7.right);
         }

         paintIconR.x = paintIconR.y = paintIconR.width = paintIconR.height = 0;
         paintTextR.x = paintTextR.y = paintTextR.width = paintTextR.height = 0;
         String var8 = this.layoutCL(var3, var6, var4, var5, paintViewR, paintIconR, paintTextR);
         Graphics2D var9 = (Graphics2D)var1;
         AffineTransform var10 = var9.getTransform();
         int var11 = this.mirror ? -1 : 1;
         var9.transform(
            this.direction == Direction.RIGHT
               ? new AffineTransform(1.0F, 0.0F, 0.0F, (float)var11, 0.0F, this.mirror ? (float)var2.getHeight() : 0.0F)
               : (
                  this.direction == Direction.DOWN
                     ? new AffineTransform(0.0F, 1.0F, (float)(-var11), 0.0F, this.mirror ? 0.0F : (float)var2.getWidth(), 0.0F)
                     : (
                        this.direction == Direction.LEFT
                           ? new AffineTransform(-1.0F, 0.0F, 0.0F, (float)(-var11), (float)var2.getWidth(), this.mirror ? 0.0F : (float)var2.getHeight())
                           : new AffineTransform(0.0F, -1.0F, (float)var11, 0.0F, this.mirror ? (float)var2.getWidth() : 0.0F, (float)var2.getHeight())
                     )
               )
         );
         if (var5 != null) {
            var5.paintIcon(var2, var1, paintIconR.x, paintIconR.y);
         }

         if (var4 != null) {
            int var12 = paintTextR.x;
            int var13 = paintTextR.y + var6.getAscent();
            if (var3.isEnabled()) {
               this.paintEnabledText(var3, var1, var8, var12, var13);
            } else {
               this.paintDisabledText(var3, var1, var8, var12, var13);
            }
         }

         var9.setTransform(var10);
      }
   }
}
