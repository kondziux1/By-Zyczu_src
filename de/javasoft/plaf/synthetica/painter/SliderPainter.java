package de.javasoft.plaf.synthetica.painter;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaState;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.plaf.synth.SynthContext;

public class SliderPainter extends SyntheticaComponentPainter {
   public static final String UI_KEY = "Synthetica.SliderPainter";

   protected SliderPainter() {
      super();
   }

   public static SliderPainter getInstance() {
      return getInstance(null);
   }

   public static SliderPainter getInstance(SynthContext var0) {
      SyntheticaComponentPainter var1 = (SyntheticaComponentPainter)instances.get(getPainterClassName(var0, SliderPainter.class, "Synthetica.SliderPainter"));
      if (var1 == null) {
         var1 = getInstance(var0, SliderPainter.class, "Synthetica.SliderPainter");
      }

      return (SliderPainter)var1;
   }

   public void paintSliderBorder(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
      JComponent var7 = var1.getComponent();
      if (var7.hasFocus()) {
         FocusPainter.paintFocus("focus.slider", var1, var2, var3, var4, var5, var6);
      }

   }

   public void paintSliderBackground(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public void paintSliderTrackBorder(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public void paintSliderTrackBackground(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
      JSlider var7 = (JSlider)var1.getComponent();
      SyntheticaPainterState var8 = new SyntheticaPainterState(var1);
      this.paintSliderTrack(var7, var8, var7.getOrientation(), var2, var3, var4, var5, var6);
      var8 = new SyntheticaPainterState(var1, 0, true);
      this.paintSliderTrack(
         var7, var8, var7.getOrientation(), var7.getValue(), var7.getMinimum(), var7.getMaximum(), var7.getInverted(), var2, var3, var4, var5, var6
      );
   }

   public void paintSliderTrack(JComponent var1, SyntheticaState var2, int var3, Graphics var4, int var5, int var6, int var7, int var8) {
      UIKey var9 = new UIKey("slider.track", var2, -1, -1, var3);
      Insets var10 = (Insets)UIKey.findProperty(var1, var9.get(), "image.insets", true, 1);
      String var12 = SyntheticaLookAndFeel.getString(var9.get("image"), var1);
      ImagePainter var13 = new ImagePainter(var1, var4, var5, var6, var7, var8, var12, var10, var10, 0, 0);
      var13.draw();
   }

   public void paintSliderTrack(
      JComponent var1, SyntheticaState var2, int var3, int var4, int var5, int var6, boolean var7, Graphics var8, int var9, int var10, int var11, int var12
   ) {
      int var13 = SyntheticaLookAndFeel.getInt("Synthetica.slider.thumb.width", var1, 10);
      String var14 = "slider.trackMark";
      UIKey var15 = new UIKey(var14, var2, -1, -1, var3);
      String var16 = SyntheticaLookAndFeel.getString(var15.get("image"), var1);
      if (var16 == null) {
         var14 = "slider.track";
         var15 = new UIKey(var14, var2, -1, -1, var3);
         var16 = SyntheticaLookAndFeel.getString(var15.get("image"), var1);
      }

      if (var16 != null) {
         Insets var17 = (Insets)UIKey.findProperty(var1, var15.get(), "image.insets", true, 1);
         Insets var18 = (Insets)var17.clone();
         var15 = new UIKey(var14, var2);
         int var19 = SyntheticaLookAndFeel.getInt(var15.get("animation.cycles"), var1, 1);
         int var20 = SyntheticaLookAndFeel.getInt(var15.get("animation.delay"), var1, 50);
         int var21 = SyntheticaLookAndFeel.getInt(var15.get("animation.type"), var1, 2);
         if (var2.isSet(SyntheticaState.State.HOVER) || var2.isSet(SyntheticaState.State.PRESSED)) {
            var21 = SyntheticaLookAndFeel.getInt(var15.get("animation.type"), var1, 1);
         }

         int var22 = var4 - var5;
         int var23 = var6 == var5 ? var22 : var6 - var5;
         if (var23 == 0) {
            var23 = 1;
         }

         if (var3 == 0) {
            var11 -= var13;
            if (!var7 ^ !var1.getComponentOrientation().isLeftToRight()) {
               var11 = var11 * var22 / var23 + var13 / 2;
               var18.right = 0;
            } else {
               int var24 = var11 * var22 / var23 + var13 / 2;
               var9 += var11 - var24 + var13;
               var11 = var24;
               var18.left = 0;
            }
         } else {
            var12 -= var13;
            if (!var7) {
               int var28 = var12 * var22 / var23 + var13 / 2;
               var10 += var12 - var28 + var13;
               var12 = var28;
               var18.top = 0;
            } else {
               var12 = var12 * var22 / var23 + var13 / 2;
               var18.bottom = 0;
            }
         }

         ImagePainter var29 = new ImagePainter(var1, "track", var19, var20, var21, var2.getState(), var8, var9, var10, var11, var12, var16, var17, var18, 0, 0);
         var29.draw();
      }

   }

   public void paintSliderThumbBorder(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6, int var7) {
   }

   public void paintSliderThumbBackground(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6, int var7) {
      JSlider var8 = (JSlider)var1.getComponent();
      SyntheticaPainterState var9 = new SyntheticaPainterState(var1);
      this.paintSliderThumb(var8, var9, var7, var2, var3, var4, var5, var6);
   }

   public void paintSliderThumb(JComponent var1, SyntheticaState var2, int var3, Graphics var4, int var5, int var6, int var7, int var8) {
      UIKey var9 = new UIKey("slider.thumb", var2, -1, -1, var3);
      Insets var10 = (Insets)UIKey.findProperty(var1, var9.get(), "image.insets", true, 1);
      String var12 = SyntheticaLookAndFeel.getString(var9.get("image"), var1);
      if (var2.isSet(SyntheticaState.State.PRESSED) && var12 == null) {
         var2.setState(SyntheticaState.State.HOVER.toInt());
         var9 = new UIKey("slider.thumb", var2, -1, -1, var3);
         var12 = SyntheticaLookAndFeel.getString(var9.get("image"), var1);
      }

      if (var12 != null) {
         var9 = new UIKey("slider.thumb", var2);
         int var13 = SyntheticaLookAndFeel.getInt(var9.get("animation.cycles"), var1, 1);
         int var14 = SyntheticaLookAndFeel.getInt(var9.get("animation.delay"), var1, 50);
         int var15 = SyntheticaLookAndFeel.getInt(var9.get("animation.type"), var1, 2);
         if (var2.isSet(SyntheticaState.State.HOVER) || var2.isSet(SyntheticaState.State.PRESSED)) {
            var15 = SyntheticaLookAndFeel.getInt(var9.get("animation.type"), var1, 1);
         }

         ImagePainter var16 = new ImagePainter(var1, "thumb", var13, var14, var15, var2.getState(), var4, var5, var6, var7, var8, var12, var10, var10, 0, 0);
         var16.draw();
      }

      if (var1.hasFocus() || var2.isSet(SyntheticaState.State.FOCUSED)) {
         String var19 = null;
         if (SyntheticaLookAndFeel.get("Synthetica.focus.slider.thumb.x", var1) != null && ((JSlider)var1).getOrientation() == 0) {
            var19 = "focus.slider.thumb.x";
         } else if (SyntheticaLookAndFeel.get("Synthetica.focus.slider.thumb.y", var1) != null && ((JSlider)var1).getOrientation() == 1) {
            var19 = "focus.slider.thumb.y";
         } else if (SyntheticaLookAndFeel.get("Synthetica.focus.slider.thumb", var1) != null) {
            var19 = "focus.slider.thumb";
         }

         if (var19 != null) {
            FocusPainter.paintFocus(var19, var1, var2.getState(), "", var4, var5, var6, var7, var8);
         }
      }

   }

   @Override
   public int getCacheHash(SynthContext var1, int var2, int var3, int var4, String var5) {
      if (var5.equals("paintSliderBackground")) {
         return -1;
      } else {
         JSlider var6 = (JSlider)var1.getComponent();
         int var7 = var6.getOrientation();
         int var8 = super.getCacheHash(var1, var2, var3, var4, var5);
         return 31 * var8 + var7;
      }
   }
}
