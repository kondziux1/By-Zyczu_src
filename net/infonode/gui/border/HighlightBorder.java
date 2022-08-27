package net.infonode.gui.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.Serializable;
import javax.swing.border.Border;
import net.infonode.gui.GraphicsUtil;
import net.infonode.gui.InsetsUtil;
import net.infonode.gui.colorprovider.BackgroundPainterColorProvider;
import net.infonode.gui.colorprovider.ColorMultiplier;
import net.infonode.gui.colorprovider.ColorProvider;
import net.infonode.gui.colorprovider.ColorProviderUtil;
import net.infonode.util.Direction;

public class HighlightBorder implements Border, Serializable {
   private static final long serialVersionUID = 1L;
   private static final Insets INSETS = new Insets(1, 1, 0, 0);
   private boolean lowered;
   private boolean pressed;
   private ColorProvider colorProvider;

   public HighlightBorder() {
      this(false);
   }

   public HighlightBorder(boolean var1) {
      this(var1, null);
   }

   public HighlightBorder(boolean var1, Color var2) {
      this(var1, false, var2);
   }

   public HighlightBorder(boolean var1, boolean var2, Color var3) {
      this(var1, var2, ColorProviderUtil.getColorProvider(var3, new ColorMultiplier(BackgroundPainterColorProvider.INSTANCE, var1 ? 0.7 : 1.7)));
   }

   public HighlightBorder(boolean var1, boolean var2, ColorProvider var3) {
      super();
      this.lowered = var1;
      this.pressed = var2;
      this.colorProvider = var3;
   }

   public Insets getBorderInsets(Component var1) {
      return this.pressed ? InsetsUtil.rotate(Direction.LEFT, INSETS) : INSETS;
   }

   public boolean isBorderOpaque() {
      return false;
   }

   public void paintBorder(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      var2.setColor(this.colorProvider.getColor(var1));
      if (this.pressed) {
         GraphicsUtil.drawOptimizedLine(var2, var3 + (this.lowered ? 0 : 1), var4 + var6 - 1, var3 + var5 - 1, var4 + var6 - 1);
         GraphicsUtil.drawOptimizedLine(var2, var3 + var5 - 1, var4 + (this.lowered ? 0 : 1), var3 + var5 - 1, var4 + var6 - 2);
      } else {
         GraphicsUtil.drawOptimizedLine(var2, var3, var4, var3 + var5 - (this.lowered ? 1 : 2), var4);
         GraphicsUtil.drawOptimizedLine(var2, var3, var4, var3, var4 + var6 - (this.lowered ? 1 : 2));
      }

   }
}
