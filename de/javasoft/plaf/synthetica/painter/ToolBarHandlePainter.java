package de.javasoft.plaf.synthetica.painter;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaState;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.plaf.synth.SynthContext;

public class ToolBarHandlePainter extends SyntheticaComponentPainter {
   public static final String UI_KEY = "Synthetica.ToolBarHandlePainter";

   protected ToolBarHandlePainter() {
      super();
   }

   public static ToolBarHandlePainter getInstance() {
      return getInstance(null);
   }

   public static ToolBarHandlePainter getInstance(SynthContext var0) {
      SyntheticaComponentPainter var1 = (SyntheticaComponentPainter)instances.get(
         getPainterClassName(var0, ToolBarHandlePainter.class, "Synthetica.ToolBarHandlePainter")
      );
      if (var1 == null) {
         var1 = getInstance(var0, ToolBarHandlePainter.class, "Synthetica.ToolBarHandlePainter");
      }

      return (ToolBarHandlePainter)var1;
   }

   public void paintHandle(JComponent var1, SyntheticaState var2, int var3, Graphics var4, int var5, int var6, int var7, int var8) {
      Object var9 = null;
      UIKey var16;
      if (var3 == 0) {
         var16 = new UIKey("toolBar.handle.image.y", var2);
      } else {
         var16 = new UIKey("toolBar.handle.image.x", var2);
      }

      Insets var10 = (Insets)UIKey.findProperty(var1, var16.get(), "insets", true, 2);
      int var12 = SyntheticaLookAndFeel.getBoolean("Synthetica.toolBar.handle.image.x.tiled", var1) ? 1 : 0;
      int var13 = SyntheticaLookAndFeel.getBoolean("Synthetica.toolBar.handle.image.y.tiled", var1) ? 1 : 0;
      String var14 = SyntheticaLookAndFeel.getString(var16.get(), var1);
      if (var14 != null) {
         ImagePainter var15 = new ImagePainter(var4, var5, var6, var7, var8, var14, var10, var10, var12, var13);
         var15.draw();
      }

   }
}
