package net.infonode.tabbedpanel.theme.internal.laftheme;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

public class SizeIcon implements Icon {
   public static SizeIcon EMPTY = new SizeIcon(0, 0);
   private int width;
   private int height;
   private boolean swap;

   public SizeIcon(int var1, int var2) {
      this(var1, var2, false);
   }

   public SizeIcon(int var1, int var2, boolean var3) {
      super();
      this.width = var1;
      this.height = var2;
      this.swap = var3;
   }

   public void paintIcon(Component var1, Graphics var2, int var3, int var4) {
   }

   public int getIconWidth() {
      return this.swap ? this.height : this.width;
   }

   public int getIconHeight() {
      return this.swap ? this.width : this.height;
   }
}
