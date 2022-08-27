package de.javasoft.plaf.synthetica.painter;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaState;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.synth.SynthContext;

public class PanelPainter extends SyntheticaComponentPainter {
   public static final String UI_KEY = "Synthetica.PanelPainter";

   protected PanelPainter() {
      super();
   }

   public static PanelPainter getInstance() {
      return getInstance(null);
   }

   public static PanelPainter getInstance(SynthContext var0) {
      SyntheticaComponentPainter var1 = (SyntheticaComponentPainter)instances.get(getPainterClassName(var0, PanelPainter.class, "Synthetica.PanelPainter"));
      if (var1 == null) {
         var1 = getInstance(var0, PanelPainter.class, "Synthetica.PanelPainter");
      }

      return (PanelPainter)var1;
   }

   public void paintPanelBorder(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public void paintPanelBackground(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
      this.paintPanelBackground(var1.getComponent(), new SyntheticaState(), var2, var3, var4, var5, var6);
   }

   public void paintPanelBackground(JComponent var1, SyntheticaState var2, Graphics var3, int var4, int var5, int var6, int var7) {
      Color var8 = var1.getBackground();
      if (var1.isOpaque() || SyntheticaLookAndFeel.getBoolean("Synthetica.panel.background.opaque", var1, false)) {
         String var9 = SyntheticaLookAndFeel.getString("Synthetica.panel.background.image", var1);
         boolean var10 = SyntheticaLookAndFeel.getBoolean("Synthetica.panel.background.image.enabled", var1, true);
         if ((!var10 || var9 == null) && var8 != null && !(var8 instanceof ColorUIResource)) {
            var3.setColor(var8);
            var3.fillRect(var4, var5, var6, var7);
         } else if (var10 && var9 != null && (var8 == null || var8 != null && var8 instanceof ColorUIResource)) {
            int var11 = SyntheticaLookAndFeel.getBoolean("Synthetica.panel.background.horizontalTiled", var1, false) ? 1 : 0;
            int var12 = SyntheticaLookAndFeel.getBoolean("Synthetica.panel.background.verticalTiled", var1, false) ? 1 : 0;
            Insets var13 = SyntheticaLookAndFeel.getInsets("Synthetica.panel.background.image.insets", var1, false);
            String var15 = SyntheticaLookAndFeel.getString("Synthetica.panel.background.image.origin", var1);
            if (var15 != null && var15.equals("PANEL")) {
               ImagePainter var26 = new ImagePainter(var3, var4, var5, var6, var7, var9, var13, var13, var11, var12);
               var26.draw();
            } else {
               Object var16 = var15 != null && var15.equals("CONTENT_PANE") ? var1.getRootPane().getContentPane() : var1.getRootPane();
               if (var16 == null) {
                  return;
               }

               Rectangle var17 = ((Container)var16).getBounds();
               var17.width = Math.max(var17.width, SyntheticaLookAndFeel.getInt("Synthetica.panel.minimumBackgroundWidth", var1, var17.width));
               var17.height = Math.max(var17.height, SyntheticaLookAndFeel.getInt("Synthetica.panel.minimumBackgroundHeight", var1, var17.height));
               boolean var18 = SyntheticaLookAndFeel.getClientProperty("Synthetica.panel.paintViewportAware", var1, true);
               Object var19 = var18 ? this.findRelevantParent(var1) : var1;
               Rectangle var20 = SwingUtilities.convertRectangle((Component)var19, new Rectangle(var4, var5, var6, var7), (Component)var16);
               if (var19 instanceof JViewport) {
                  JViewport var21 = (JViewport)var19;
                  Point var22 = SwingUtilities.convertPoint(var1, new Point(), var21);
                  Point var23 = var21.getViewPosition();
                  var20.x += var22.x + var23.x;
                  var20.y += var22.y + var23.y;
                  Point var24 = SwingUtilities.convertPoint(var21, new Point(), (Component)var16);
                  Dimension var25 = var21.getViewSize();
                  var17.width = var25.width + var24.x;
                  var17.height = var25.height + var24.y;
               }

               ImagePainter var27 = new ImagePainter(var3, var4 - var20.x, var5 - var20.y, var17.width, var17.height, var9, var13, var13, var11, var12);
               var27.draw();
            }
         }
      }

   }

   private Component findRelevantParent(Component var1) {
      Container var2 = null;

      for(Container var3 = var1.getParent(); var3 != null; var3 = var3.getParent()) {
         if (var3 instanceof JViewport) {
            var2 = var3;
         }
      }

      return (Component)(var2 == null ? var1 : var2);
   }

   @Override
   public Cacheable.ScaleType getCacheScaleType(String var1) {
      return Cacheable.ScaleType.NINE_SQUARE;
   }

   @Override
   public int getCacheHash(SynthContext var1, int var2, int var3, int var4, String var5) {
      return -1;
   }
}
