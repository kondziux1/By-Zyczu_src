package de.javasoft.plaf.synthetica.painter;

import de.javasoft.plaf.synthetica.SyntheticaLogoRenderer;
import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaRootPaneUI;
import de.javasoft.plaf.synthetica.SyntheticaState;
import de.javasoft.plaf.synthetica.SyntheticaTitlePane;
import de.javasoft.util.OS;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Window;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.synth.SynthContext;

public class RootPanePainter extends SyntheticaComponentPainter {
   public static final String UI_KEY = "Synthetica.RootPanePainter";

   protected RootPanePainter() {
      super();
   }

   public static RootPanePainter getInstance() {
      return getInstance(null);
   }

   public static RootPanePainter getInstance(SynthContext var0) {
      SyntheticaComponentPainter var1 = (SyntheticaComponentPainter)instances.get(
         getPainterClassName(var0, RootPanePainter.class, "Synthetica.RootPanePainter")
      );
      if (var1 == null) {
         var1 = getInstance(var0, RootPanePainter.class, "Synthetica.RootPanePainter");
      }

      return (RootPanePainter)var1;
   }

   public void paintRootPaneBorder(JRootPane var1, SyntheticaState var2, Graphics var3, int var4, int var5, int var6, int var7) {
      Window var8 = this.getWindow(var1);
      boolean var9 = this.isMaximized(var8);
      boolean var10 = SyntheticaLookAndFeel.getBoolean("Synthetica.rootPane.titlePane.opaque", var8, true);
      if (!var9 || !var10) {
         boolean var11 = SyntheticaLookAndFeel.getBoolean("Synthetica.rootPane.border.respectFill", var8);
         String var12 = "Synthetica.rootPane.border";
         if (var8.isActive()) {
            var12 = var12 + ".selected";
         }

         var12 = SyntheticaLookAndFeel.getString(var12, var8);
         Insets var13 = (Insets)SyntheticaLookAndFeel.getInsets("Synthetica.rootPane.border.insets", var8).clone();
         if (SyntheticaLookAndFeel.getBoolean("Synthetica.rootPane.respectHeaderHeight", var8)) {
            int var14 = ((SyntheticaRootPaneUI)var1.getUI()).getTitlePane().getHeight();
            var14 += var1.getJMenuBar() == null ? 0 : var1.getJMenuBar().getHeight();
            var14 += SyntheticaLookAndFeel.get("Synthetica.rootPane.border.size", var8) == null
               ? 4
               : SyntheticaLookAndFeel.getInsets("Synthetica.rootPane.border.size", var8).top;
            var13.top = var14;
         }

         Insets var26 = var13;
         int var15 = var7;
         if (var9) {
            if (!var11) {
               var15 = var13.top;
            }

            var13 = (Insets)SyntheticaLookAndFeel.getInsets("Synthetica.rootPane.border.size", var8).clone();
            Insets var16 = SyntheticaLookAndFeel.getInsets("Synthetica.rootPane.border.insets", var8, false);
            var13.left = var13.left < var16.left ? var16.left : var13.left;
            var13.right = var13.right < var16.right ? var16.right : var13.right;
            var26 = new Insets(0, 0, 0, 0);
         }

         byte var28 = 0;
         if (SyntheticaLookAndFeel.getBoolean("Synthetica.rootPane.titlePane.background.horizontalTiled", var8)) {
            var28 = 1;
         }

         byte var17 = 0;
         if (SyntheticaLookAndFeel.getBoolean("Synthetica.rootPane.titlePane.background.verticalTiled", var8)) {
            var17 = 1;
         }

         if (SyntheticaRootPaneUI.isEvalCopy() && !var9) {
            var15 -= 16;
         }

         if (OS.getCurrentOS() == OS.Mac
            && (JAVA5 || JAVA6)
            && (!SyntheticaLookAndFeel.isWindowOpacityEnabled(var8) || SyntheticaLookAndFeel.isWindowShapeEnabled(var8))) {
            var3.clearRect(var4, var5, var6, var15);
         }

         this.paintBorder(var1, var3, var4, var5, var6, var15, var12, var13, var26, var28, var17, var8, var9, var11);
         var12 = "Synthetica.rootPane.border.light";
         if (var8.isActive()) {
            var12 = var12 + ".selected";
         }

         var12 = SyntheticaLookAndFeel.getString(var12, var8);
         if (var12 != null) {
            this.paintBorder(var1, var3, var4, var5, var6, var15, var12, var13, var26, 0, 1, var8, var9, var11);
         }

         if (SyntheticaLookAndFeel.getBoolean("Synthetica.rootPane.paintStatusBar", var8) && var1.getClientProperty("Synthetica.statusBar") != null) {
            var15 = var7;
            if (SyntheticaRootPaneUI.isEvalCopy() && !var9) {
               var15 = var7 - 16;
            }

            int var18 = ((JComponent)var1.getClientProperty("Synthetica.statusBar")).getHeight();
            var18 += SyntheticaLookAndFeel.get("Synthetica.rootPane.border.size", var8) == null
               ? 4
               : SyntheticaLookAndFeel.getInsets("Synthetica.rootPane.border.size", var8).top;
            SyntheticaAddonsPainter.getInstance("Synthetica.StatusBarPainter")
               .paintStatusBarBackground(var1, new SyntheticaState(), var3, 0, var15 - var18, var6, var18);
         }

         var12 = "Synthetica.rootPane.titlePane.background";
         var12 = SyntheticaLookAndFeel.getString(var12, var8);
         boolean var30 = var12 != null && var10;
         if (!var30 && var1.getClientProperty("Synthetica.logoRenderer") != null) {
            this.renderLogo(var1, var8.isActive(), var9, true, var3, var6, var7);
         }

         if (SyntheticaRootPaneUI.isEvalCopy()) {
            var3.setColor(UIManager.getColor("Panel.background"));
            var3.fillRect(var4, var7 - 16, var6, 16);
            var3.setColor(new Color(13369344));
            var3.setFont(var3.getFont().deriveFont(10.0F));
            var3.drawString("Synthetica - Unregistered Evaluation Copy!", 4, var7 - 16 + var3.getFontMetrics().getAscent());
         }

      }
   }

   private void paintBorder(
      JRootPane var1,
      Graphics var2,
      int var3,
      int var4,
      int var5,
      int var6,
      String var7,
      Insets var8,
      Insets var9,
      int var10,
      int var11,
      Window var12,
      boolean var13,
      boolean var14
   ) {
      if (var13 && var14) {
         Insets var17 = SyntheticaLookAndFeel.getInsets("Synthetica.rootPane.border.insets", var12, false);
         var17 = new Insets(var17.top - var8.top, var17.left - var8.left, var17.bottom - var8.bottom, var17.right - var8.right);
         ImagePainter var16 = new ImagePainter(var1, var2, var3, var4, var5, var6, var7, var8, var9, var17, var17, var10, var11);
         var16.draw();
      } else {
         ImagePainter var15 = new ImagePainter(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11);
         if (var13) {
            var15.drawCenter();
         } else if (var14) {
            var15.draw();
         } else {
            var15.drawBorder();
         }
      }

   }

   public void paintRootPaneBorder(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
      JComponent var7 = var1.getComponent();
      if (var7 instanceof JRootPane) {
         this.paintRootPaneBorder((JRootPane)var7, new SyntheticaState(var1.getComponentState()), var2, var3, var4, var5, var6);
      }

   }

   public void paintContentBackground(JRootPane var1, SyntheticaState var2, Graphics var3, int var4, int var5, int var6, int var7) {
      Window var8 = SwingUtilities.getWindowAncestor(var1);
      String var9 = "Synthetica.rootPane.border";
      if (var8.isActive()) {
         var9 = var9 + ".selected";
      }

      var9 = SyntheticaLookAndFeel.getString(var9, var8);
      Insets var10 = SyntheticaLookAndFeel.get("Synthetica.rootPane.border.size", var8) == null
         ? new Insets(4, 4, 4, 4)
         : SyntheticaLookAndFeel.getInsets("Synthetica.rootPane.border.size", var8);
      Insets var11 = (Insets)var10.clone();
      var11.top += ((SyntheticaRootPaneUI)var1.getUI()).getTitlePane().getHeight();
      Insets var12 = new Insets(0, 0, 0, 0);
      byte var13 = 0;
      if (SyntheticaLookAndFeel.getBoolean("Synthetica.rootPane.titlePane.background.horizontalTiled", var8)) {
         var13 = 1;
      }

      byte var14 = 0;
      if (SyntheticaLookAndFeel.getBoolean("Synthetica.rootPane.titlePane.background.verticalTiled", var8)) {
         var14 = 1;
      }

      ImagePainter var15 = new ImagePainter(var3, var4, var5, var6, var7, var9, var11, var12, var13, var14);
      var15.drawCenter();
      var9 = "Synthetica.rootPane.border.light";
      if (var8.isActive()) {
         var9 = var9 + ".selected";
      }

      var9 = SyntheticaLookAndFeel.getString(var9, var8);
      if (var9 != null) {
         var15 = new ImagePainter(var3, var4, var5, var6, var7, var9, var11, var12, 0, 1);
         var15.drawCenter();
      }

      MenuPainter.getInstance().paintMenuBarBackground(var1, new SyntheticaState(), var3, var4, var5, var6, var7);
   }

   protected void renderLogo(JRootPane var1, boolean var2, boolean var3, boolean var4, Graphics var5, int var6, int var7) {
      SyntheticaLogoRenderer var8 = (SyntheticaLogoRenderer)var1.getClientProperty("Synthetica.logoRenderer");
      JComponent var9 = var8.getRendererComponent(var1, var2);
      Window var10 = this.getWindow(var1);
      Insets var11 = SyntheticaLookAndFeel.getInsets("Synthetica.rootPane.border.size", var10);
      if (var11 == null) {
         var11 = SyntheticaLookAndFeel.getInsets("Synthetica.rootPane.border.insets", var10);
      }

      int var12 = var11.top;
      var12 = !var3 && var4 ? var12 : 0;
      var12 += ((SyntheticaRootPaneUI)var1.getUI()).getTitlePane().getHeight();
      JMenuBar var13 = (JMenuBar)SyntheticaLookAndFeel.findComponent(JMenuBar.class, var1);
      boolean var14 = var1.getClientProperty("Synthetica.logoRenderer.titlePaneOnly") == null
         ? false
         : var1.getClientProperty("Synthetica.logoRenderer.titlePaneOnly");
      if (var13 != null && !var13.isOpaque() && SyntheticaLookAndFeel.get("Synthetica.menuBar.background.active", var13) == null && !var14) {
         var12 += var13.getHeight();
      }

      boolean var15 = var1.getClientProperty("Synthetica.logoRenderer.respectButtons") == null
         ? false
         : var1.getClientProperty("Synthetica.logoRenderer.respectButtons");
      if (var15) {
         Rectangle var16 = ((SyntheticaTitlePane)((SyntheticaRootPaneUI)var1.getUI()).getTitlePane()).getMenuButtonBounds();
         Rectangle var17 = ((SyntheticaTitlePane)((SyntheticaRootPaneUI)var1.getUI()).getTitlePane()).getControlButtonsBounds();
         int var18 = Math.min(var16.x + var16.width, var17.x + var17.width);
         int var19 = Math.max(var16.x, var17.x) - var18;
         var18 += !var3 && var4 ? var11.left : 0;
         var9.setSize(new Dimension(var19, var12));
         var5.translate(var18, 0);
         var9.paint(var5);
         var5.translate(-var18, 0);
      } else {
         var9.setSize(new Dimension(var6, var12));
         var9.paint(var5);
      }

   }

   public void paintTitlePaneBackground(JRootPane var1, SyntheticaState var2, Graphics var3, int var4, int var5, int var6, int var7) {
      Window var8 = this.getWindow(var1);
      boolean var9 = SyntheticaLookAndFeel.getBoolean("Synthetica.rootPane.titlePane.opaque", var8, true);
      String var10 = "Synthetica.rootPane.titlePane.background";
      if (var2.isSet(SyntheticaState.State.SELECTED)) {
         var10 = var10 + ".selected";
      }

      var10 = SyntheticaLookAndFeel.getString(var10, var8);
      Insets var11 = SyntheticaLookAndFeel.getInsets("Synthetica.rootPane.titlePane.background.insets", var8);
      byte var12 = 0;
      if (SyntheticaLookAndFeel.getBoolean("Synthetica.rootPane.titlePane.background.horizontalTiled", var8)) {
         var12 = 1;
      }

      byte var13 = 0;
      if (SyntheticaLookAndFeel.getBoolean("Synthetica.rootPane.titlePane.background.verticalTiled", var8)) {
         var13 = 1;
      }

      ImagePainter var14 = null;
      boolean var15 = var10 != null && var9;
      if (var15) {
         var14 = new ImagePainter(var3, var4, var5, var6, var7, var10, var11, var11, var12, var13);
         var14.draw();
      }

      var10 = "Synthetica.rootPane.titlePane.background.light";
      if (var2.isSet(SyntheticaState.State.SELECTED)) {
         var10 = var10 + ".selected";
      }

      var10 = SyntheticaLookAndFeel.getString(var10, var8);
      if (var10 != null && var9) {
         var14 = new ImagePainter(var3, var4, var5, var6, var7, var10, var11, var11, 0, var13);
         var14.draw();
      }

      if (var15 && var1.getClientProperty("Synthetica.logoRenderer") != null) {
         this.renderLogo(var1, var8.isActive(), this.isMaximized(var8), false, var3, var6, var7);
      }

   }

   public void paintTitlePaneBackground(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
      JRootPane var7 = (JRootPane)var1.getComponent();
      this.paintTitlePaneBackground(var7, new SyntheticaState(var1.getComponentState()), var2, var3, var4, var5, var6);
   }

   protected boolean isMaximized(Window var1) {
      return var1 instanceof Frame && (((Frame)var1).getExtendedState() & 6) == 6;
   }

   public void paintButtonAreaBackground(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
      JRootPane var7 = (JRootPane)var1.getComponent();
      Window var8 = this.getWindow(var7);
      boolean var9 = (var1.getComponentState() & 512) > 0;
      Insets var10 = SyntheticaLookAndFeel.getInsets("Synthetica.rootPane.titlePane.buttonArea.insets", var8);
      if (var10 != null) {
         var3 -= var10.left;
         var4 -= var10.top;
         var5 += var10.left + var10.right;
         var6 += var10.top + var10.bottom;
         String var11 = "Synthetica.rootPane.titlePane.buttonArea.background";
         if (var9 && SyntheticaLookAndFeel.getString("Synthetica.rootPane.titlePane.buttonArea.background.selected", var8) != null) {
            var11 = var11 + ".selected";
         }

         var11 = SyntheticaLookAndFeel.getString(var11, var8);
         Insets var12 = SyntheticaLookAndFeel.getInsets("Synthetica.rootPane.titlePane.buttonArea.background.insets", var8);
         ImagePainter var13 = new ImagePainter(var2, var3, var4, var5, var6, var11, var12, var12, 0, 0);
         var13.draw();
      }

   }

   private Window getWindow(JRootPane var1) {
      Container var2 = var1.getParent();
      return var2 instanceof Window ? (Window)var2 : SwingUtilities.getWindowAncestor(var2);
   }

   @Override
   public Cacheable.ScaleType getCacheScaleType(String var1) {
      return !var1.equals("paintRootPaneBorder") && !var1.equals("paintRootPaneBackground") ? super.getCacheScaleType(var1) : Cacheable.ScaleType.NINE_SQUARE;
   }
}
