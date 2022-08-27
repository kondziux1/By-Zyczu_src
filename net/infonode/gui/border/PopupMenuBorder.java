package net.infonode.gui.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.Serializable;
import javax.swing.border.Border;
import net.infonode.gui.GraphicsUtil;

public class PopupMenuBorder implements Border, Serializable {
   private static final long serialVersionUID = 1L;
   private static final Insets INSETS = new Insets(3, 1, 2, 1);
   private Color highlightColor;
   private Color shadowColor;

   public PopupMenuBorder(Color var1, Color var2) {
      super();
      this.highlightColor = var1;
      this.shadowColor = var2;
   }

   public boolean isBorderOpaque() {
      return false;
   }

   public void paintBorder(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      var2.setColor(this.shadowColor);
      var2.drawRect(var3, var4, var5 - 1, var6 - 1);
      var2.setColor(this.highlightColor);
      GraphicsUtil.drawOptimizedLine(var2, var3 + 1, var4 + 1, var3 + var5 - 2, var4 + 1);
      GraphicsUtil.drawOptimizedLine(var2, var3 + 1, var4 + 2, var3 + 1, var4 + 2);
      GraphicsUtil.drawOptimizedLine(var2, var3 + 1, var4 + var6 - 2, var3 + 1, var4 + var6 - 2);
   }

   public Insets getBorderInsets(Component var1) {
      return INSETS;
   }
}
