package net.infonode.gui.icon;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.Icon;

public class IconUtil {
   public static final Icon SMALL_ICON = new IconUtil$1();

   private IconUtil() {
      super();
   }

   public static Icon copy(Icon var0) {
      return new IconUtil$2(var0);
   }

   public static Icon getIcon(Object var0) {
      return var0 == null
         ? null
         : (
            var0 instanceof AbstractButton
               ? ((AbstractButton)var0).getIcon()
               : (var0 instanceof Action ? (Icon)((Action)var0).getValue("SmallIcon") : (var0 instanceof IconProvider ? ((IconProvider)var0).getIcon() : null))
         );
   }

   public static int getIconWidth(Object var0) {
      Icon var1 = getIcon(var0);
      return var1 == null ? 0 : var1.getIconWidth();
   }

   public static int getIconHeight(Object var0) {
      Icon var1 = getIcon(var0);
      return var1 == null ? 0 : var1.getIconHeight();
   }

   public static int getMaxIconWidth(Object[] var0) {
      int var1 = 0;

      for(int var2 = 0; var2 < var0.length; ++var2) {
         int var3 = getIconWidth(var0[var2]);
         if (var3 > var1) {
            var1 = var3;
         }
      }

      return var1;
   }
}
