package de.javasoft.plaf.synthetica.painter;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.JPopupMenu.Separator;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.plaf.synth.ColorType;
import javax.swing.plaf.synth.SynthContext;

public class SeparatorPainter extends SyntheticaComponentPainter {
   public static final String UI_KEY = "Synthetica.SeparatorPainter";
   private static HashMap<String, Image> imgCache = new HashMap();

   protected SeparatorPainter() {
      super();
   }

   public static SeparatorPainter getInstance() {
      return getInstance(null);
   }

   public static SeparatorPainter getInstance(SynthContext var0) {
      SyntheticaComponentPainter var1 = (SyntheticaComponentPainter)instances.get(
         getPainterClassName(var0, SeparatorPainter.class, "Synthetica.SeparatorPainter")
      );
      if (var1 == null) {
         var1 = getInstance(var0, SeparatorPainter.class, "Synthetica.SeparatorPainter");
      }

      return (SeparatorPainter)var1;
   }

   public void paintSeparatorBorder(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public void paintSeparatorBackground(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
      JComponent var7 = var1.getComponent();
      if (var7.getParent() instanceof JPopupMenu || var7 instanceof Separator) {
         JPopupMenu var8 = (JPopupMenu)SwingUtilities.getAncestorOfClass(JPopupMenu.class, var7);
         Insets var9 = var8.getInsets();
         String var10 = "Synthetica.popupMenuSeparator";
         var10 = SyntheticaLookAndFeel.getString(var10, var8);
         boolean var11 = SyntheticaLookAndFeel.getBoolean("Synthetica.popupMenu.iconSeparator.clip", var8, true);
         if (var11
            && (SyntheticaLookAndFeel.preservePopupIconSpace(var8) || SyntheticaLookAndFeel.popupHasCheckRadio(var8))
            && !SyntheticaLookAndFeel.popupHasCheckRadioWithIcon(var8)) {
            boolean var12 = var8.getComponentOrientation().isLeftToRight();
            int var13 = SyntheticaLookAndFeel.getInt("Synthetica.popupMenuSeparator.iconSeparatorGap", var8);
            int var14 = SyntheticaLookAndFeel.getInt("Synthetica.popupMenu.iconSeparator.gap", var8, 24);
            Integer var15 = (Integer)var8.getClientProperty("Synthetica.menuItem.maxIconWidth");
            if (var15 != null && var15 > 16) {
               var14 += var15 - 16;
            }

            int var16 = SyntheticaLookAndFeel.getInt("Synthetica.popupMenu.iconSeparator.width", var8, 1);
            int var17 = var14 + var16 + var13 - var9.left;
            var3 += var12 ? var17 : 0;
            var5 -= var17;
         }

         Insets var19 = SyntheticaLookAndFeel.getInsets("Synthetica.popupMenuSeparator.insets", var8, false);
         ImagePainter var20 = new ImagePainter(var2, var3, var4, var5, var6, var10, var19, var19, 0, 0);
         var20.draw();
      }

   }

   public void paintSeparatorForeground(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6, int var7) {
      String var8 = "Synthetica.separator";
      JComponent var9 = var1.getComponent();
      Insets var10 = var1.getStyle().getInsets(var1, null);
      Insets var11 = var9.getInsets();
      var5 -= var11.left + var11.right - var10.left - var10.right;
      var6 -= var11.top + var11.bottom - var10.top - var10.bottom;
      var3 += var11.left - var10.left;
      var4 += var11.top - var10.top;
      boolean var12 = var9 instanceof javax.swing.JToolBar.Separator;
      if (var12) {
         var8 = var8 + ".toolBar";
      } else if (var9.getParent() instanceof JComponent
         && (((JComponent)var9.getParent()).getUIClassID().equals("StatusBarUI") || "StatusBar".equals(SyntheticaLookAndFeel.getStyleName(var9.getParent())))) {
         var8 = var8 + ".statusBar";
      }

      var8 = var8 + ".image";
      Insets var13 = SyntheticaLookAndFeel.getInsets(var8 + ".insets", var9, true);
      if (var7 == 0) {
         var8 = var8 + ".x";
      } else {
         var8 = var8 + ".y";
      }

      var8 = SyntheticaLookAndFeel.getString(var8, var9);
      if (var12) {
         if (var8 == null) {
            return;
         }

         Image var15 = (Image)imgCache.get(var8);
         if (var15 == null) {
            var15 = new ImageIcon(SyntheticaLookAndFeel.class.getResource(var8)).getImage();
            imgCache.put(var8, var15);
         }

         this.adjustToolBarSeparatorSize((javax.swing.JToolBar.Separator)var9, var15.getWidth(null), var15.getHeight(null));
      }

      int var26 = var12 ? 0 : var1.getStyle().get(var1, "Separator.thickness");
      int var16 = var7 != 0 && !var12 ? var26 : var5;
      int var17 = var7 != 1 && !var12 ? var26 : var6;
      if (var8 == null) {
         var2.setColor(var1.getStyle().getColor(var1, ColorType.BACKGROUND));
         var2.fillRect(var3, var4, var16, var17);
      } else {
         ImagePainter var18 = new ImagePainter(var9, 1, 50, 0, 0, var2, var3, var4, var5, var6, var8, var13, var13, 0, 0);
         var18.draw();
      }

   }

   protected void adjustToolBarSeparatorSize(javax.swing.JToolBar.Separator var1, int var2, int var3) {
      if (SyntheticaLookAndFeel.getToolbarSeparatorDimension() == null) {
         int var4 = 0;
         int var5 = 0;

         Component[] var9;
         for(Component var6 : var9 = var1.getParent().getComponents()) {
            if (!(var6 instanceof javax.swing.JToolBar.Separator)) {
               if (var6.getMinimumSize().width > var4) {
                  var4 = var6.getMinimumSize().width;
               }

               if (var6.getMinimumSize().height > var5) {
                  var5 = var6.getMinimumSize().height;
               }
            }
         }

         Object var10 = null;
         DimensionUIResource var11;
         if (var1.getOrientation() == 1) {
            var11 = new DimensionUIResource(var2, var5);
         } else {
            var11 = new DimensionUIResource(var4, var3);
         }

         if (!var11.equals(var1.getSize())) {
            var1.setSeparatorSize(var11);
            var1.revalidate();
         }
      }

   }
}
