package net.infonode.gui.menu;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;
import javax.swing.JPopupMenu.Separator;
import net.infonode.gui.icon.IconUtil;

public class MenuUtil {
   private MenuUtil() {
      super();
   }

   public static void optimizeSeparators(JPopupMenu var0) {
      boolean var1 = true;

      boolean var3;
      for(int var2 = 0; var2 < var0.getComponentCount(); var1 = var3) {
         if (var0.getComponent(var2).isVisible() && var0.getComponent(var2) instanceof JMenu) {
            optimizeSeparators(((JMenu)var0.getComponent(var2)).getPopupMenu());
         }

         var3 = var0.getComponent(var2) instanceof Separator;
         if (var1 && var3) {
            var0.remove(var2);
         } else {
            ++var2;
         }
      }

      if (var0.getComponentCount() > 0 && var0.getComponent(var0.getComponentCount() - 1) instanceof Separator) {
         var0.remove(var0.getComponentCount() - 1);
      }

   }

   public static void align(MenuElement var0) {
      MenuElement[] var1 = var0.getSubElements();
      int var2 = IconUtil.getMaxIconWidth(var1);

      for(int var3 = 0; var3 < var1.length; ++var3) {
         if (var1[var3] instanceof AbstractButton) {
            AbstractButton var4 = (AbstractButton)var1[var3];
            Icon var5 = var4.getIcon();
            var4.setIcon(new MenuUtil$1(var5, var2));
         }

         align(var1[var3]);
      }

   }
}
