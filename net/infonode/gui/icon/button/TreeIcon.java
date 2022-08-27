package net.infonode.gui.icon.button;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;
import net.infonode.gui.GraphicsUtil;

public class TreeIcon implements Icon {
   public static final int PLUS = 1;
   public static final int MINUS = 2;
   private int type;
   private int width;
   private int height;
   private Color color;
   private Color bgColor;
   private boolean border = true;

   public TreeIcon(int var1, int var2, int var3, boolean var4, Color var5, Color var6) {
      super();
      this.type = var1;
      this.width = var2;
      this.height = var3;
      this.border = var4;
      this.color = var5;
      this.bgColor = var6;
   }

   public TreeIcon(int var1, int var2, int var3) {
      this(var1, var2, var3, true, Color.BLACK, null);
   }

   public void paintIcon(Component var1, Graphics var2, int var3, int var4) {
      if (this.bgColor != null) {
         var2.setColor(this.bgColor);
         var2.fillRect(var3 + 1, var4 + 1, this.width - 2, this.height - 2);
      }

      var2.setColor(this.color);
      if (this.border) {
         var2.drawRect(var3 + 1, var4 + 1, this.width - 2, this.height - 2);
      }

      GraphicsUtil.drawOptimizedLine(var2, var3 + 3, var4 + this.height / 2, var3 + this.width - 3, var4 + this.height / 2);
      if (this.type == 1) {
         GraphicsUtil.drawOptimizedLine(var2, var3 + this.width / 2, var4 + 3, var3 + this.width / 2, var4 + this.height - 3);
      }

   }

   public int getIconWidth() {
      return this.width;
   }

   public int getIconHeight() {
      return this.height;
   }
}
