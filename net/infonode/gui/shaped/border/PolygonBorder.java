package net.infonode.gui.shaped.border;

import java.awt.Component;
import java.awt.Polygon;
import net.infonode.gui.colorprovider.ColorProvider;

public class PolygonBorder extends AbstractPolygonBorder {
   private static final long serialVersionUID = 1L;
   private int[] coords;
   private float[] widthFactors;
   private float[] heightFactors;

   public PolygonBorder(ColorProvider var1, int[] var2, float[] var3, float[] var4) {
      super(var1);
      this.coords = var2;
      this.widthFactors = var3;
      this.heightFactors = var4;
   }

   public PolygonBorder(ColorProvider var1, ColorProvider var2, int[] var3, float[] var4, float[] var5) {
      super(var1, var2);
      this.coords = var3;
      this.widthFactors = var4;
      this.heightFactors = var5;
   }

   public PolygonBorder(ColorProvider var1, ColorProvider var2, ColorProvider var3, ColorProvider var4, int[] var5, float[] var6, float[] var7) {
      super(var1, var2, var3, var4);
      this.coords = var5;
      this.widthFactors = var6;
      this.heightFactors = var7;
   }

   protected Polygon createPolygon(Component var1, int var2, int var3) {
      int[] var4 = new int[this.coords.length / 2];
      int[] var5 = new int[this.coords.length / 2];
      int var6 = 0;

      for(int var7 = 0; var7 < var4.length; ++var7) {
         var4[var7] = (int)(this.widthFactors[var6] * (float)var2) + (int)(this.heightFactors[var6] * (float)var3) + this.coords[var6];
         var5[var7] = (int)(this.widthFactors[++var6] * (float)var2) + (int)(this.heightFactors[var6] * (float)var3) + this.coords[var6];
         ++var6;
      }

      return new Polygon(var4, var5, var4.length);
   }
}
