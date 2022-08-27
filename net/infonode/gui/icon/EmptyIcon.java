package net.infonode.gui.icon;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

public class EmptyIcon implements Icon {
   public static final EmptyIcon INSTANCE = new EmptyIcon();

   private EmptyIcon() {
      super();
   }

   public void paintIcon(Component var1, Graphics var2, int var3, int var4) {
   }

   public int getIconWidth() {
      return 0;
   }

   public int getIconHeight() {
      return 0;
   }
}
