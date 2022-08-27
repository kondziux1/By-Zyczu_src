package de.javasoft.plaf.synthetica.painter;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.plaf.synth.SynthContext;

public class SplitPanePainter extends SyntheticaComponentPainter {
   public static final String UI_KEY = "Synthetica.SplitPanePainter";
   private static HashMap<String, Image> imgCache = new HashMap();

   protected SplitPanePainter() {
      super();
   }

   public static SplitPanePainter getInstance() {
      return getInstance(null);
   }

   public static SplitPanePainter getInstance(SynthContext var0) {
      SyntheticaComponentPainter var1 = (SyntheticaComponentPainter)instances.get(
         getPainterClassName(var0, SplitPanePainter.class, "Synthetica.SplitPanePainter")
      );
      if (var1 == null) {
         var1 = getInstance(var0, SplitPanePainter.class, "Synthetica.SplitPanePainter");
      }

      return (SplitPanePainter)var1;
   }

   public void paintSplitPaneBackground(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public void paintSplitPaneBorder(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public void paintSplitPaneDividerBackground(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public void paintSplitPaneDragDivider(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6, int var7) {
   }

   public void paintSplitPaneDividerForeground(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6, int var7) {
      JComponent var8 = var1.getComponent();
      Insets var9 = SyntheticaLookAndFeel.getInsets("Synthetica.splitPaneDivider.background.insets", var8, false);
      Object var11 = null;
      String var12 = "Synthetica.splitPaneDivider";
      String var18;
      if (var7 == 0) {
         var18 = var12 + ".x.grip";
         var12 = var12 + ".x.background";
      } else {
         var18 = var12 + ".y.grip";
         var12 = var12 + ".y.background";
      }

      if ((var1.getComponentState() & 2) > 0) {
         var18 = var18 + ".hover";
      }

      var12 = SyntheticaLookAndFeel.getString(var12, var8);
      var18 = SyntheticaLookAndFeel.getString(var18, var8);
      if (var12 != null) {
         ImagePainter var13 = new ImagePainter(var2, var3, var4, var5, var6, var12, var9, var9, 0, 0);
         var13.draw();
      }

      if (var18 != null) {
         Image var22 = (Image)imgCache.get(var18);
         if (var22 == null) {
            var22 = new ImageIcon(SyntheticaLookAndFeel.class.getResource(var18)).getImage();
            imgCache.put(var18, var22);
         }

         int var14 = var22.getWidth(null);
         int var15 = var22.getHeight(null);
         int var16 = var3 + (var5 - var14) / 2;
         int var17 = var4 + (var6 - var15) / 2;
         if (var7 == 1 && var5 - 2 < var14) {
            return;
         }

         if (var7 == 0 && var6 - 2 < var15) {
            return;
         }

         var2.drawImage(var22, var16, var17, null);
      }

   }

   @Override
   public Cacheable.ScaleType getCacheScaleType(String var1) {
      return !var1.equals("paintSplitPaneBorder") && !var1.equals("paintSplitPaneBackground")
         ? super.getCacheScaleType(var1)
         : Cacheable.ScaleType.NINE_SQUARE;
   }

   @Override
   public int getCacheHash(SynthContext var1, int var2, int var3, int var4, String var5) {
      return !var5.equals("paintSplitPaneBorder") && !var5.equals("paintSplitPaneBackground") ? super.getCacheHash(var1, var2, var3, var4, var5) : -1;
   }
}
