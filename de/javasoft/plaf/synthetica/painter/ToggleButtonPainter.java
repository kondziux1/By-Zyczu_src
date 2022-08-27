package de.javasoft.plaf.synthetica.painter;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaState;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.plaf.synth.SynthContext;

public class ToggleButtonPainter extends SyntheticaComponentPainter {
   public static final String UI_KEY = "Synthetica.ToggleButtonPainter";

   protected ToggleButtonPainter() {
      super();
   }

   public static ToggleButtonPainter getInstance() {
      return getInstance(null);
   }

   public static ToggleButtonPainter getInstance(SynthContext var0) {
      SyntheticaComponentPainter var1 = (SyntheticaComponentPainter)instances.get(
         getPainterClassName(var0, ToggleButtonPainter.class, "Synthetica.ToggleButtonPainter")
      );
      if (var1 == null) {
         var1 = getInstance(var0, ToggleButtonPainter.class, "Synthetica.ToggleButtonPainter");
      }

      return (ToggleButtonPainter)var1;
   }

   public void paintToggleButtonBorder(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public void paintToggleButtonBackground(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
      if (this.paintCheck(var1)) {
         AbstractButton var7 = (AbstractButton)var1.getComponent();
         int var8 = var1.getComponentState();
         if ((var8 & 4) > 0 && (var8 & 2) > 0) {
            var8 ^= 2;
         }

         String var9 = var7.getClientProperty("JButton.segmentPosition") == null ? "" : "." + var7.getClientProperty("JButton.segmentPosition");
         if ((var7.isBorderPainted() || var7.getText() == null || var7.getText().length() == 0) && SyntheticaLookAndFeel.isOpaque(var7)) {
            this.paintToggleButtonBackground(var7, new SyntheticaState(var8), var9, var2, var3, var4, var5, var6);
         }

         if (var7.hasFocus() && var7.isFocusPainted()) {
            FocusPainter.paintFocus("focus.toggleButton" + var9, var1, var2, var3, var4, var5, var6);
         }

      }
   }

   public void paintToggleButtonBackground(JComponent var1, SyntheticaState var2, String var3, Graphics var4, int var5, int var6, int var7, int var8) {
      boolean var9 = this.isToolBarButton(var1);
      boolean var10 = var3 != null && var3.length() > 0;
      if (var3 != null && var3.length() > 0 && !var3.startsWith(".")) {
         var3 = "." + var3;
      }

      Container var11 = var1.getParent();
      Insets var12 = SyntheticaLookAndFeel.getInsets("Synthetica.toggleButton.border.insets", var1);
      String var14 = var9 && var10 ? "Synthetica.toolBar.toggleButton" : "Synthetica.toggleButton";
      if (var11 != null && var11.getParent() instanceof JToolBar && "ButtonBar".equals(SyntheticaLookAndFeel.getStyleName(var11))) {
         String var15 = "Synthetica.toolBar.buttonBar.toggleButton.border" + var3;
         if (var2.isSet(SyntheticaState.State.PRESSED)) {
            var15 = var15 + ".pressed";
         } else {
            if (var2.isSet(SyntheticaState.State.SELECTED)) {
               var15 = var15 + ".selected";
            }

            if (var2.isSet(SyntheticaState.State.HOVER)) {
               var15 = var15 + ".hover";
            }
         }

         if (SyntheticaLookAndFeel.get(var15, var1) != null) {
            var14 = "Synthetica.toolBar.buttonBar.toggleButton";
         }
      }

      var14 = var14 + ".border";
      var14 = var14 + var3;
      if (var2.isSet(SyntheticaState.State.PRESSED) && SyntheticaLookAndFeel.get(var14 + ".pressed", var1) != null) {
         var14 = var14 + ".pressed";
      } else if (var2.isSet(SyntheticaState.State.SELECTED)) {
         var14 = var14 + ".selected";
      }

      if (!var1.isEnabled()) {
         var14 = var14 + ".disabled";
      }

      if (var10) {
         if (var2.isSet(SyntheticaState.State.HOVER)) {
            var14 = var14 + ".hover";
         }
      } else if (var9
         && var2.isSet(SyntheticaState.State.HOVER)
         && !var2.isSet(SyntheticaState.State.SELECTED)
         && !SyntheticaLookAndFeel.getBoolean("Synthetica.toolBar.buttons.paintBorder", var1)) {
         var14 = "Synthetica.toolBar.button.border.hover";
      } else if (var9 && var2.isSet(SyntheticaState.State.PRESSED)) {
         var14 = "Synthetica.toolBar.button.border.pressed";
      } else if (var9 && !var2.isSet(SyntheticaState.State.SELECTED) && !SyntheticaLookAndFeel.getBoolean("Synthetica.toolBar.buttons.paintBorder", var1)) {
         var14 = "Synthetica.toolBar.button.border";
      } else if (var9
         && var2.isSet(SyntheticaState.State.SELECTED)
         && !var2.isSet(SyntheticaState.State.HOVER)
         && SyntheticaLookAndFeel.getString("Synthetica.toolBar.toggleButton.border.selected", var1) != null) {
         var14 = "Synthetica.toolBar.toggleButton.border.selected";
      } else if (var9
         && var2.isSet(SyntheticaState.State.SELECTED)
         && var2.isSet(SyntheticaState.State.HOVER)
         && SyntheticaLookAndFeel.getString("Synthetica.toolBar.toggleButton.border.selectedAndHover", var1) != null) {
         var14 = "Synthetica.toolBar.toggleButton.border.selectedAndHover";
      } else if (var2.isSet(SyntheticaState.State.HOVER)) {
         var14 = var14 + ".hover";
      }

      if (var1.hasFocus() && SyntheticaLookAndFeel.get(var14 + ".focused", var1) != null) {
         var14 = var14 + ".focused";
      }

      var14 = SyntheticaLookAndFeel.getString(var14, var1);
      if (var14 != null) {
         int var22 = 0;
         int var16 = 0;
         int var17 = 0;
         if (var2.isSet(SyntheticaState.State.HOVER)) {
            var22 = SyntheticaLookAndFeel.getInt("Synthetica.toggleButton.hover.animation.cycles", var1, 1);
            var16 = SyntheticaLookAndFeel.getInt("Synthetica.toggleButton.hover.animation.delay", var1, 50);
            var17 = SyntheticaLookAndFeel.getInt("Synthetica.toggleButton.hover.animation.type", var1, 1);
         } else {
            var22 = SyntheticaLookAndFeel.getInt("Synthetica.toggleButton.animation.cycles", var1, 1);
            var16 = SyntheticaLookAndFeel.getInt("Synthetica.toggleButton.animation.delay", var1, 50);
            var17 = SyntheticaLookAndFeel.getInt("Synthetica.toggleButton.animation.type", var1, 2);
         }

         ImagePainter var18 = new ImagePainter(var1, var22, var16, var17, var2.getState(), var4, var5, var6, var7, var8, var14, var12, var12, 0, 0);
         var18.draw();
      }
   }

   protected boolean paintCheck(SynthContext var1) {
      AbstractButton var2 = (AbstractButton)var1.getComponent();
      int var3 = var1.getComponentState();
      boolean var4 = (var3 & 2) > 0;
      boolean var5 = (var3 & 512) > 0;
      boolean var6 = (var3 & 4) > 0;
      boolean var7 = this.isToolBarButton(var2);
      if (!var7
         || SyntheticaLookAndFeel.getBoolean("Synthetica.toolBar.buttons.paintBorder", var2)
         || SyntheticaLookAndFeel.get("Synthetica.toolBar.toggleButton.border", var2) != null
         || var4
         || var5
         || var6 && SyntheticaLookAndFeel.getBoolean("Synthetica.toolBar.button.pressed.paintBorder", var2)) {
         if (!var2.isBorderPainted() && var2.getText() != null && var2.getText().length() != 0) {
            if (!var2.isBorderPainted()) {
               return false;
            }
         } else {
            if (!var2.isBorderPainted() && !var4 && !var5) {
               return false;
            }

            if (!var2.isContentAreaFilled()) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean isToolBarButton(JComponent var1) {
      Boolean var2 = (Boolean)var1.getClientProperty("Synthetica.useToolBarStyle");
      return var2 != null && !var2 ? false : var1.getParent() instanceof JToolBar;
   }

   @Override
   public int getCacheHash(SynthContext var1, int var2, int var3, int var4, String var5) {
      AbstractButton var6 = (AbstractButton)var1.getComponent();
      String var7 = (String)var6.getClientProperty("JButton.segmentPosition");
      Border var8 = var6.getBorder();
      int var9 = super.getCacheHash(var1, var2, var3, var4, var5);
      var9 = var7 == null ? var9 : 31 * var9 + var7.hashCode();
      var9 = var8 == null ? var9 : 31 * var9 + (var6.isBorderPainted() ? 0 : 1);
      var9 = 31 * var9 + (var6.isContentAreaFilled() ? 0 : 1);
      return 31 * var9 + (var6.getParent() instanceof JToolBar ? 0 : 1);
   }
}
