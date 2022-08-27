package de.javasoft.plaf.synthetica.painter;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaState;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.plaf.synth.SynthContext;

public class ToolBarPainter extends SyntheticaComponentPainter {
   public static final String UI_KEY = "Synthetica.ToolBarPainter";

   protected ToolBarPainter() {
      super();
   }

   public static ToolBarPainter getInstance() {
      return getInstance(null);
   }

   public static ToolBarPainter getInstance(SynthContext var0) {
      SyntheticaComponentPainter var1 = (SyntheticaComponentPainter)instances.get(getPainterClassName(var0, ToolBarPainter.class, "Synthetica.ToolBarPainter"));
      if (var1 == null) {
         var1 = getInstance(var0, ToolBarPainter.class, "Synthetica.ToolBarPainter");
      }

      return (ToolBarPainter)var1;
   }

   public void paintToolBarBorder(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public void paintToolBarBackground(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
      this.paintToolBarBackground(var1.getComponent(), new SyntheticaState(var1.getComponentState()), var2, var3, var4, var5, var6);
   }

   public void paintToolBarBackground(JComponent var1, SyntheticaState var2, Graphics var3, int var4, int var5, int var6, int var7) {
      String var8 = SyntheticaLookAndFeel.getString("Synthetica.toolBar.background", var1);
      if (var8 != null && SyntheticaLookAndFeel.isOpaque(var1)) {
         Insets var9 = SyntheticaLookAndFeel.getInsets("Synthetica.toolBar.background.insets", var1);
         byte var11 = 0;
         if (SyntheticaLookAndFeel.getBoolean("Synthetica.toolBar.background.horizontalTiled", var1)) {
            var11 = 1;
         }

         byte var12 = 0;
         if (SyntheticaLookAndFeel.getBoolean("Synthetica.toolBar.background.verticalTiled", var1)) {
            var12 = 1;
         }

         ImagePainter var13 = new ImagePainter(var3, var4, var5, var6, var7, var8, var9, var9, var11, var12);
         var13.draw();
         var8 = "Synthetica.toolBar.background.light";
         var8 = SyntheticaLookAndFeel.getString(var8, var1);
         if (var8 != null) {
            var13 = new ImagePainter(var3, var4, var5, var6, var7, var8, var9, var9, 0, 0);
            var13.draw();
         }

         var8 = "Synthetica.toolBar.background.light2";
         var8 = SyntheticaLookAndFeel.getString(var8, var1);
         if (var8 != null) {
            var13 = new ImagePainter(var3, var4, var5, var6, var7, var8, var9, var9, 0, 0);
            var13.draw();
         }
      }

   }

   public void paintToolBarContentBackground(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public void paintToolBarContentBorder(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }
}
