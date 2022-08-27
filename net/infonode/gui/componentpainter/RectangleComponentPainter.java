package net.infonode.gui.componentpainter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import net.infonode.gui.InsetsUtil;
import net.infonode.gui.colorprovider.ColorProvider;
import net.infonode.gui.colorprovider.FixedColorProvider;
import net.infonode.util.Direction;

public class RectangleComponentPainter extends AbstractComponentPainter {
   private static final long serialVersionUID = 1L;
   private final ColorProvider color;
   private final ColorProvider xorColor;
   private final Insets insets;

   public RectangleComponentPainter(Color var1, int var2) {
      this(new FixedColorProvider(var1), var2);
   }

   public RectangleComponentPainter(Color var1, Color var2, int var3) {
      this(new FixedColorProvider(var1), new FixedColorProvider(var2), var3);
   }

   public RectangleComponentPainter(ColorProvider var1, int var2) {
      this(var1, null, var2);
   }

   public RectangleComponentPainter(ColorProvider var1, ColorProvider var2, int var3) {
      this(var1, var2, new Insets(var3, var3, var3, var3));
   }

   public RectangleComponentPainter(ColorProvider var1, ColorProvider var2, Insets var3) {
      super();
      this.color = var1;
      this.xorColor = var2;
      this.insets = (Insets)var3.clone();
   }

   public void paint(Component var1, Graphics var2, int var3, int var4, int var5, int var6, Direction var7, boolean var8, boolean var9) {
      Color var10 = null;
      var2.setColor(this.color.getColor(var1));
      if (this.xorColor != null) {
         var10 = this.xorColor.getColor(var1);
         if (var10 != null) {
            var2.setXORMode(var10);
         }
      }

      Insets var11 = InsetsUtil.rotate(
         var7,
         new Insets(
            var9 ? this.insets.bottom : this.insets.top,
            var8 ? this.insets.right : this.insets.left,
            var9 ? this.insets.top : this.insets.bottom,
            var8 ? this.insets.left : this.insets.right
         )
      );
      var2.fillRect(var3 + var11.left, var4, var5 - var11.left - var11.right, var11.top);
      var2.fillRect(var3 + var11.left, var4 + var6 - var11.bottom, var5 - var11.left - var11.right, var11.bottom);
      var2.fillRect(var3, var4, var11.left, var6);
      var2.fillRect(var3 + var5 - var11.right, var4, var11.right, var6);
      if (var10 != null) {
         var2.setPaintMode();
      }

   }

   public boolean isOpaque(Component var1) {
      return false;
   }

   public Color getColor(Component var1) {
      return this.color.getColor(var1);
   }
}
