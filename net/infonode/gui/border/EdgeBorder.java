package net.infonode.gui.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.Serializable;
import javax.swing.border.Border;
import net.infonode.gui.ComponentUtil;
import net.infonode.gui.GraphicsUtil;
import net.infonode.gui.colorprovider.ColorProvider;
import net.infonode.gui.colorprovider.FixedColorProvider;
import net.infonode.util.ColorUtil;

public class EdgeBorder implements Border, Serializable {
   private static final long serialVersionUID = 1L;
   private ColorProvider topLeftColor;
   private ColorProvider bottomRightColor;
   private boolean drawTop;
   private boolean drawBottom;
   private boolean drawLeft;
   private boolean drawRight;
   private Insets insets;

   public EdgeBorder() {
      this(true, true, true, true);
   }

   public EdgeBorder(boolean var1, boolean var2, boolean var3, boolean var4) {
      this(null, var1, var2, var3, var4);
   }

   public EdgeBorder(Color var1, boolean var2, boolean var3, boolean var4, boolean var5) {
      super();
      FixedColorProvider var6 = var1 == null ? null : new FixedColorProvider(var1);
      this.init(var6, var6, var2, var3, var4, var5);
   }

   public EdgeBorder(ColorProvider var1) {
      super();
      this.init(var1, var1, true, true, true, true);
   }

   public EdgeBorder(ColorProvider var1, ColorProvider var2, boolean var3, boolean var4, boolean var5, boolean var6) {
      super();
      this.init(var1, var2, var3, var4, var5, var6);
   }

   private void init(ColorProvider var1, ColorProvider var2, boolean var3, boolean var4, boolean var5, boolean var6) {
      this.topLeftColor = var1;
      this.bottomRightColor = var2;
      this.drawTop = var3;
      this.drawBottom = var4;
      this.drawLeft = var5;
      this.drawRight = var6;
      this.insets = new Insets(var3 ? 1 : 0, var5 ? 1 : 0, var4 ? 1 : 0, var6 ? 1 : 0);
   }

   public void paintBorder(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      Color var7 = this.getColor(this.topLeftColor, var1);
      Color var8 = this.getColor(this.bottomRightColor, var1);
      if (var7 != null && var8 != null) {
         var2.setColor(var7);
         if (this.drawTop) {
            GraphicsUtil.drawOptimizedLine(var2, var3, var4, var3 + var5 - 1, var4);
         }

         if (this.drawLeft) {
            GraphicsUtil.drawOptimizedLine(var2, var3, var4, var3, var4 + var6 - 1);
         }

         var2.setColor(var8);
         if (this.drawRight) {
            GraphicsUtil.drawOptimizedLine(var2, var3 + var5 - 1, var4, var3 + var5 - 1, var4 + var6 - 1);
         }

         if (this.drawBottom) {
            GraphicsUtil.drawOptimizedLine(var2, var3, var4 + var6 - 1, var3 + var5 - 1, var4 + var6 - 1);
         }
      }

   }

   public Insets getBorderInsets(Component var1) {
      return this.insets;
   }

   public boolean isBorderOpaque() {
      return false;
   }

   private Color getColor(ColorProvider var1, Component var2) {
      Color var3 = var1 != null ? var1.getColor() : null;
      if (var3 == null) {
         Color var4 = ComponentUtil.getBackgroundColor(var2);
         return var4 == null ? null : ColorUtil.mult(var4, 0.7F);
      } else {
         return var3;
      }
   }
}
