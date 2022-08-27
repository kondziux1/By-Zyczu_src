package net.infonode.gui.laf;

import java.awt.Color;
import net.infonode.util.ColorUtil;

public class InfoNodeLookAndFeelThemes {
   private InfoNodeLookAndFeelThemes() {
      super();
   }

   public static InfoNodeLookAndFeelTheme getDarkBlueGreenTheme() {
      float var0 = 0.61F;
      float var1 = 0.2F;
      return new InfoNodeLookAndFeelTheme(
         "Dark Blue Green Theme",
         Color.getHSBColor(var0, var1, 0.5F),
         Color.getHSBColor(var0, var1 + 0.1F, 0.9F),
         Color.getHSBColor(0.12F, 0.04F, 0.5F),
         Color.WHITE,
         new Color(0, 170, 0),
         Color.WHITE,
         0.8
      );
   }

   public static InfoNodeLookAndFeelTheme getGrayTheme() {
      float var0 = 0.61F;
      float var1 = 0.4F;
      return new InfoNodeLookAndFeelTheme(
         "Gray Theme",
         new Color(220, 220, 220),
         Color.getHSBColor(var0, var1 + 0.3F, 1.0F),
         InfoNodeLookAndFeelTheme.DEFAULT_BACKGROUND_COLOR,
         InfoNodeLookAndFeelTheme.DEFAULT_TEXT_COLOR,
         Color.getHSBColor(var0, var1, 1.0F),
         Color.BLACK
      );
   }

   public static InfoNodeLookAndFeelTheme getBlueIceTheme() {
      float var0 = 0.61F;
      float var1 = 0.4F;
      Color var2 = new Color(197, 206, 218);
      InfoNodeLookAndFeelTheme var3 = new InfoNodeLookAndFeelTheme(
         "Blue Ice Theme",
         var2,
         Color.getHSBColor(var0, var1 + 0.3F, 1.0F),
         InfoNodeLookAndFeelTheme.DEFAULT_BACKGROUND_COLOR,
         InfoNodeLookAndFeelTheme.DEFAULT_TEXT_COLOR,
         Color.getHSBColor(var0, var1 - 0.15F, 0.9F),
         Color.BLACK,
         0.3
      );
      var3.setPrimaryControlColor(ColorUtil.mult(var2, 0.9));
      return var3;
   }

   public static InfoNodeLookAndFeelTheme getSoftGrayTheme() {
      InfoNodeLookAndFeelTheme var0 = new InfoNodeLookAndFeelTheme(
         "Soft Gray Theme",
         new Color(230, 230, 233),
         new Color(212, 220, 236),
         new Color(255, 255, 255),
         new Color(0, 0, 0),
         new Color(76, 113, 188),
         new Color(255, 255, 255),
         0.2
      );
      var0.setActiveInternalFrameTitleBackgroundColor(new Color(76, 113, 188));
      var0.setActiveInternalFrameTitleForegroundColor(Color.WHITE);
      var0.setActiveInternalFrameTitleGradientColor(new Color(80, 135, 248));
      var0.setInactiveInternalFrameTitleBackgroundColor(new Color(220, 220, 222));
      var0.setInactiveInternalFrameTitleForegroundColor(Color.BLACK);
      var0.setInactiveInternalFrameTitleGradientColor(new Color(240, 240, 243));
      return var0;
   }
}
