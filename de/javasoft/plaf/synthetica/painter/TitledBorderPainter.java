package de.javasoft.plaf.synthetica.painter;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

public class TitledBorderPainter extends SyntheticaComponentPainter {
   public static final String UI_KEY = "Synthetica.TitledBorderPainter";

   protected TitledBorderPainter() {
      super();
   }

   public static TitledBorderPainter getInstance() {
      SyntheticaComponentPainter var0 = (SyntheticaComponentPainter)instances.get(
         getPainterClassName(null, TitledBorderPainter.class, "Synthetica.TitledBorderPainter")
      );
      if (var0 == null) {
         var0 = getInstance(null, TitledBorderPainter.class, "Synthetica.TitledBorderPainter");
      }

      return (TitledBorderPainter)var0;
   }

   public void paintBorder(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      String var7 = "Synthetica.titledBorder";
      var7 = SyntheticaLookAndFeel.getString(var7, var1);
      Insets var8 = SyntheticaLookAndFeel.getInsets("Synthetica.titledBorder.image.insets", var1);
      if (var8 == null) {
         var8 = SyntheticaLookAndFeel.getInsets("Synthetica.titledBorder.insets", var1);
      }

      ImagePainter var10 = new ImagePainter(var2, var3, var4, var5, var6, var7, var8, var8, 0, 0);
      var10.drawBorder();
   }
}
