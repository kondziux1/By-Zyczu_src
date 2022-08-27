package net.infonode.gui.shaped.border;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Shape;

public class AbstractShapedBorderWrapper extends AbstractShapedBorder {
   private static final long serialVersionUID = 1L;
   private ShapedBorder border;

   protected AbstractShapedBorderWrapper(ShapedBorder var1) {
      super();
      this.border = var1;
   }

   public Shape getShape(Component var1, int var2, int var3, int var4, int var5) {
      return this.border.getShape(var1, var2, var3, var4, var5);
   }

   public Insets getBorderInsets(Component var1) {
      return this.border.getBorderInsets(var1);
   }

   public void paintBorder(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      this.border.paintBorder(var1, var2, var3, var4, var5, var6);
   }

   public boolean isBorderOpaque() {
      return this.border.isBorderOpaque();
   }
}
