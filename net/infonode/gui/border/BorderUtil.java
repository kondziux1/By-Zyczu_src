package net.infonode.gui.border;

import java.awt.Component;
import java.awt.Insets;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import net.infonode.gui.InsetsUtil;

public class BorderUtil {
   private BorderUtil() {
      super();
   }

   public static Insets getInsetsOutside(Component var0, Border var1, Border var2) {
      Insets var3 = new Insets(0, 0, 0, 0);
      getInsetsOutside(var0, var1, var2, var3);
      return var3;
   }

   private static boolean getInsetsOutside(Component var0, Border var1, Border var2, Insets var3) {
      if (var1 == null) {
         return false;
      } else if (var1 == var2) {
         return true;
      } else if (!(var1 instanceof CompoundBorder)) {
         InsetsUtil.addTo(var3, var1.getBorderInsets(var0));
         return false;
      } else {
         CompoundBorder var4 = (CompoundBorder)var1;
         return getInsetsOutside(var0, var4.getOutsideBorder(), var2, var3) || getInsetsOutside(var0, var4.getInsideBorder(), var2, var3);
      }
   }

   public static Border copy(Border var0) {
      return new BorderUtil$1(var0);
   }
}
