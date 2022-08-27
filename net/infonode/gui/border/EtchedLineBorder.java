package net.infonode.gui.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.Serializable;
import javax.swing.border.Border;
import net.infonode.gui.ComponentUtil;
import net.infonode.gui.GraphicsUtil;
import net.infonode.util.ColorUtil;

public class EtchedLineBorder implements Border, Serializable {
   private static final long serialVersionUID = 1L;
   private boolean drawTop;
   private boolean drawBottom;
   private boolean drawLeft;
   private boolean drawRight;
   private Insets insets;
   private Color highlightColor;
   private Color shadowColor;

   public EtchedLineBorder() {
      this(true, true, true, true);
   }

   public EtchedLineBorder(boolean var1, boolean var2, boolean var3, boolean var4) {
      this(var1, var2, var3, var4, null, null);
   }

   public EtchedLineBorder(boolean var1, boolean var2, boolean var3, boolean var4, Color var5, Color var6) {
      super();
      this.drawBottom = var3;
      this.drawLeft = var2;
      this.drawRight = var4;
      this.drawTop = var1;
      this.insets = new Insets(var1 ? 2 : 0, var2 ? 2 : 0, var3 ? 2 : 0, var4 ? 2 : 0);
      this.highlightColor = var5;
      this.shadowColor = var6;
   }

   public Insets getBorderInsets(Component var1) {
      return this.insets;
   }

   public boolean isBorderOpaque() {
      return false;
   }

   public void paintBorder(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      Color var7 = this.highlightColor == null ? ColorUtil.mult(ComponentUtil.getBackgroundColor(var1), 1.7) : this.highlightColor;
      Color var8 = this.shadowColor == null ? ColorUtil.mult(ComponentUtil.getBackgroundColor(var1), 0.5) : this.shadowColor;
      var2.setColor(var7);
      if (this.drawTop) {
         GraphicsUtil.drawOptimizedLine(var2, var3, var4 + 1, var3 + var5 - 1, var4 + 1);
      }

      if (this.drawLeft) {
         GraphicsUtil.drawOptimizedLine(var2, var3 + 1, var4, var3 + 1, var4 + var6 - 1);
      }

      var2.setColor(var8);
      if (this.drawBottom) {
         GraphicsUtil.drawOptimizedLine(var2, var3, var4 + var6 - 2, var3 + var5 - 1, var4 + var6 - 2);
      }

      if (this.drawRight) {
         GraphicsUtil.drawOptimizedLine(var2, var3 + var5 - 2, var4, var3 + var5 - 2, var4 + var6 - 1);
      }

      var2.setColor(var7);
      if (this.drawBottom) {
         GraphicsUtil.drawOptimizedLine(var2, var3, var4 + var6 - 1, var3 + var5 - 1, var4 + var6 - 1);
      }

      if (this.drawRight) {
         GraphicsUtil.drawOptimizedLine(var2, var3 + var5 - 1, var4, var3 + var5 - 1, var4 + var6 - 1);
      }

      var2.setColor(var8);
      if (this.drawTop) {
         GraphicsUtil.drawOptimizedLine(var2, var3, var4, var3 + var5 - 1, var4);
      }

      if (this.drawLeft) {
         GraphicsUtil.drawOptimizedLine(var2, var3, var4, var3, var4 + var6 - 1);
      }

      var2.setColor(ComponentUtil.getBackgroundColor(var1));
      if (this.drawTop && this.drawRight) {
         GraphicsUtil.drawOptimizedLine(var2, var3 + var5 - 2, var4 + 1, var3 + var5 - 1, var4);
      }

      if (this.drawBottom && this.drawLeft) {
         GraphicsUtil.drawOptimizedLine(var2, var3, var4 + var6 - 1, var3 + 1, var4 + var6 - 2);
      }

   }
}
