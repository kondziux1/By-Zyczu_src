package net.infonode.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import net.infonode.gui.border.BorderUtil;
import net.infonode.util.ColorUtil;

public class UIManagerUtil {
   private UIManagerUtil() {
      super();
   }

   public static Insets getInsets(String var0) {
      return InsetsUtil.copy(UIManager.getInsets(var0));
   }

   public static Insets getInsets(String var0, Insets var1) {
      Insets var2 = getInsets(var0);
      return var2 == null ? var1 : var2;
   }

   public static Insets getInsets(String var0, String var1) {
      Insets var2 = getInsets(var0);
      if (var2 != null) {
         return var2;
      } else {
         var2 = getInsets(var1);
         return var2 == null ? new Insets(0, 0, 0, 0) : var2;
      }
   }

   public static Color getColor(String var0) {
      return ColorUtil.copy(UIManager.getColor(var0));
   }

   public static Color getColor(String var0, String var1) {
      return getColor(var0, var1, Color.BLACK);
   }

   public static Color getColor(String var0, String var1, Color var2) {
      Color var3 = getColor(var0);
      if (var3 != null) {
         return var3;
      } else {
         var3 = getColor(var1);
         return var3 == null ? var2 : var3;
      }
   }

   public static Border getBorder(String var0) {
      return BorderUtil.copy(UIManager.getBorder(var0));
   }

   public static Border getBorder(String var0, String var1) {
      Border var2 = getBorder(var0);
      if (var2 != null) {
         return var2;
      } else {
         var2 = getBorder(var1);
         return var2 == null ? BorderFactory.createEmptyBorder() : var2;
      }
   }

   public static Font getFont(String var0) {
      Font var1 = UIManager.getFont(var0);
      if (var1 == null) {
         var1 = new JLabel().getFont();
      }

      return FontUtil.copy(var1);
   }

   public static Font getFont(String var0, String var1) {
      Font var2 = getFont(var0);
      if (var2 != null) {
         return var2;
      } else {
         var2 = getFont(var1);
         return var2 == null ? new Font("Dialog", 0, 11) : var2;
      }
   }

   public static Color getColor(String var0, Color var1) {
      Color var2 = getColor(var0);
      return var2 == null ? var1 : var2;
   }
}
