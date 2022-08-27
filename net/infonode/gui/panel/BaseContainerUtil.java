package net.infonode.gui.panel;

import java.awt.Color;
import java.awt.Font;

public class BaseContainerUtil {
   public BaseContainerUtil() {
      super();
   }

   public static void setForcedOpaque(BaseContainer var0, boolean var1) {
      var0.setForcedOpaque(var1);
   }

   public static void setOverridedBackground(BaseContainer var0, Color var1) {
      var0.setOverridedBackground(var1);
   }

   public static void setOverridedForeground(BaseContainer var0, Color var1) {
      var0.setOverridedForeround(var1);
   }

   public static void setOverridedFont(BaseContainer var0, Font var1) {
      var0.setOverrideFont(var1);
   }
}
