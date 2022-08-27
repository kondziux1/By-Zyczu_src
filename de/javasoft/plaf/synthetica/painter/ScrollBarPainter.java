package de.javasoft.plaf.synthetica.painter;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaState;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.plaf.synth.Region;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthLookAndFeel;
import javax.swing.plaf.synth.SynthStyle;

public class ScrollBarPainter extends SyntheticaComponentPainter {
   public static final String UI_KEY = "Synthetica.ScrollBarPainter";
   private static HashMap<String, Image> imgCache = new HashMap();

   protected ScrollBarPainter() {
      super();
   }

   public static ScrollBarPainter getInstance() {
      return getInstance(null);
   }

   public static ScrollBarPainter getInstance(SynthContext var0) {
      SyntheticaComponentPainter var1 = (SyntheticaComponentPainter)instances.get(
         getPainterClassName(var0, ScrollBarPainter.class, "Synthetica.ScrollBarPainter")
      );
      if (var1 == null) {
         var1 = getInstance(var0, ScrollBarPainter.class, "Synthetica.ScrollBarPainter");
      }

      return (ScrollBarPainter)var1;
   }

   public void paintScrollBarBackground(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public void paintScrollBarBorder(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public void paintScrollBarThumbBorder(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6, int var7) {
   }

   public void paintScrollBarTrackBorder(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public void paintScrollBarThumbBackground(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6, int var7) {
      if (var5 >= 4 && var6 >= 4) {
         JScrollBar var8 = (JScrollBar)var1.getComponent();
         int var9 = var1.getComponentState();
         boolean var10 = (var9 & 2) > 0;
         boolean var11 = var8.getClientProperty("Synthetica.MOUSE_PRESSED") == null ? false : var8.getClientProperty("Synthetica.MOUSE_PRESSED") & var10;
         boolean var12 = var8.getOrientation() == 1 && !var8.getComponentOrientation().isLeftToRight();
         Rectangle var13 = (Rectangle)var8.getClientProperty("Synthetica.scrollBarTrack.bounds");
         if (var13 == null) {
            var13 = new Rectangle();
         }

         SynthStyle var14 = SynthLookAndFeel.getStyle(var8, Region.SCROLL_BAR);
         SynthContext var15 = new SynthContext(var8, Region.SCROLL_BAR, var14, 1024);
         Dimension var16 = (Dimension)var14.get(var15, "ScrollBar.minimumThumbSize");
         if ((var7 != 1 || var13.height >= var16.height) && (var7 != 0 || var13.width >= var16.width)) {
            UIKey var17 = new UIKey("scrollBarThumb." + (var7 == 0 ? "x" : "y"), new SyntheticaState(var9));
            Insets var18 = (Insets)var17.findProperty(var1, "background.insets", true, 2);
            Insets var19 = var18;
            Object var20 = null;
            String var21 = "Synthetica.scrollBarThumb";
            String var31;
            if (var7 == 0) {
               var31 = var21 + ".x.grip";
               var21 = var21 + ".x.background";
               if (JAVA5 && var5 < var18.left + var18.right) {
                  var18 = new Insets(4, 4, 4, 4);
                  var19 = var18;
               }
            } else {
               var31 = var21 + ".y.grip";
               var21 = var21 + ".y.background";
               if (JAVA5 && var6 < var18.top + var18.bottom) {
                  var18 = new Insets(4, 4, 4, 4);
                  var19 = var18;
               }
            }

            if (var11 && SyntheticaLookAndFeel.get(var21 + ".pressed", var8) != null) {
               var21 = var21 + ".pressed";
               if (SyntheticaLookAndFeel.get(var31 + ".pressed", var8) != null) {
                  var31 = var31 + ".pressed";
               }
            } else if (var10) {
               var21 = var21 + ".hover";
               if (SyntheticaLookAndFeel.get(var31 + ".hover", var8) != null) {
                  var31 = var31 + ".hover";
               }
            }

            var21 = SyntheticaLookAndFeel.getString(var21, var8);
            int var22 = 0;
            int var23 = 0;
            int var24 = 0;
            if (var10) {
               var22 = SyntheticaLookAndFeel.getInt("Synthetica.scrollBarThumb.hover.animation.cycles", var8, 1);
               var23 = SyntheticaLookAndFeel.getInt("Synthetica.scrollBarThumb.hover.animation.delay", var8, 50);
               var24 = SyntheticaLookAndFeel.getInt("Synthetica.scrollBarThumb.hover.animation.type", var8, 1);
            } else {
               var22 = SyntheticaLookAndFeel.getInt("Synthetica.scrollBarThumb.animation.cycles", var8, 1);
               var23 = SyntheticaLookAndFeel.getInt("Synthetica.scrollBarThumb.animation.delay", var8, 50);
               var24 = SyntheticaLookAndFeel.getInt("Synthetica.scrollBarThumb.animation.type", var8, 2);
            }

            ImagePainter var25 = new ImagePainter(
               var8, "thumb", var22, var23, var24, var9, var2, var3, var4, var5, var6, var21, var18, var19, 0, 0, var12, false
            );
            var25.draw();
            var31 = SyntheticaLookAndFeel.getString(var31, var8);
            if (var31 != null) {
               Image var26 = (Image)imgCache.get(var31);
               if (var26 == null) {
                  var26 = new ImageIcon(SyntheticaLookAndFeel.class.getResource(var31)).getImage();
                  imgCache.put(var31, var26);
               }

               int var27 = var26.getWidth(null);
               int var28 = var26.getHeight(null);
               int var29 = var3 + (var5 - var27) / 2;
               int var30 = var4 + (var6 - var28) / 2;
               if (var7 != 0 || var5 - 4 > var27) {
                  if (var7 != 1 || var6 - 4 > var28) {
                     var2.drawImage(var26, var29, var30, null);
                  }
               }
            }
         }
      }
   }

   public void paintScrollBarTrackBackground(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
      JScrollBar var7 = (JScrollBar)var1.getComponent();
      UIKey var8 = new UIKey("scrollBarTrack." + (var7.getOrientation() == 0 ? "x" : "y"), new SyntheticaState(var1.getComponentState()));
      Insets var9 = (Insets)var8.findProperty(var1, "background.insets", true, 2);
      Insets var10 = (Insets)var9.clone();
      boolean var11 = var7.getOrientation() == 1 && !var7.getComponentOrientation().isLeftToRight();
      boolean var12 = SyntheticaLookAndFeel.getBoolean("Synthetica.scrollBarTrack.clipEdgesOnShowingBoth", var7);
      if (var12 && this.isHorizontalAndVerticalScrollBarVisible(var7)) {
         if (var7.getOrientation() == 0) {
            var10.right = 0;
         } else {
            var10.bottom = 0;
         }
      }

      Container var13 = var7.getParent();
      if (var13 instanceof JScrollPane && SyntheticaLookAndFeel.getClientProperty("Synthetica.scrollPane.clipScrollBarEdges", (JComponent)var13, false)) {
         if (var7.getOrientation() == 0) {
            var10.left = 0;
            var10.right = 0;
         } else {
            var10.top = 0;
            var10.bottom = 0;
         }
      }

      var7.putClientProperty("Synthetica.scrollBarTrack.bounds", new Rectangle(var3, var4, var5, var6));
      String var14 = "Synthetica.scrollBarTrack";
      if (var7.getOrientation() == 0) {
         var14 = var14 + ".x.background";
      } else {
         var14 = var14 + ".y.background";
      }

      if (!SyntheticaLookAndFeel.getBoolean("Synthetica.scrollBarTrack.hoverAndPressed.enabled", var7)) {
         var14 = SyntheticaLookAndFeel.getString(var14, var7);
         ImagePainter var25 = new ImagePainter(var7, null, -1, -1, -1, -1, var2, var3, var4, var5, var6, var14, var9, var10, 0, 0, var11, false);
         var25.draw();
      } else {
         boolean var15 = var7.getClientProperty("Synthetica.MOUSE_OVER") == null ? false : var7.getClientProperty("Synthetica.MOUSE_OVER");
         if (SyntheticaLookAndFeel.getBoolean("Synthetica.scrollBarTrack.hoverOnButtons.enabled", var7)) {
            var3 = 0;
            var4 = 0;
            var5 = var7.getWidth();
            var6 = var7.getHeight();
            ArrayList var16 = new ArrayList();
            SyntheticaLookAndFeel.findComponents("ScrollBar.button", var7, var16);

            for(Component var17 : var16) {
               var15 |= ((JButton)var17).getModel().isRollover();
            }
         }

         var15 &= var7.getModel().getMaximum() > var7.getModel().getExtent();
         if (var15) {
            var14 = var14 + ".hover";
         }

         if (var7.getParent() instanceof JScrollPane && SyntheticaLookAndFeel.getBoolean("Synthetica.scrollBarTrack.focusState.enabled", var7)) {
            Component var26 = ((JScrollPane)var7.getParent()).getViewport().getView();
            boolean var29 = var26 != null && var26.hasFocus();
            if (var29 && SyntheticaLookAndFeel.get(var14 + ".focused", var7) != null) {
               var14 = var14 + ".focused";
            }
         }

         var14 = SyntheticaLookAndFeel.getString(var14, var7);
         int var27 = 0;
         int var30 = 0;
         int var32 = 0;
         if (var15) {
            var27 = SyntheticaLookAndFeel.getInt("Synthetica.scrollBarTrack.hover.animation.cycles", var7, 1);
            var30 = SyntheticaLookAndFeel.getInt("Synthetica.scrollBarTrack.hover.animation.delay", var7, 50);
            var32 = SyntheticaLookAndFeel.getInt("Synthetica.scrollBarTrack.hover.animation.type", var7, 1);
         } else {
            var27 = SyntheticaLookAndFeel.getInt("Synthetica.scrollBarTrack.animation.cycles", var7, 1);
            var30 = SyntheticaLookAndFeel.getInt("Synthetica.scrollBarTrack.animation.delay", var7, 50);
            var32 = SyntheticaLookAndFeel.getInt("Synthetica.scrollBarTrack.animation.type", var7, 2);
         }

         int var19 = var1.getComponentState();
         if (var15) {
            var19 |= 2;
         }

         ImagePainter var20 = new ImagePainter(var7, "track", var27, var30, var32, var19, var2, var3, var4, var5, var6, var14, var9, var10, 0, 0, var11, false);
         var20.draw();
      }
   }

   private boolean isHorizontalAndVerticalScrollBarVisible(JScrollBar var1) {
      JScrollPane var2 = (JScrollPane)var1.getParent();
      JScrollBar var3 = var2.getHorizontalScrollBar();
      JScrollBar var4 = var2.getVerticalScrollBar();
      return var3 != null && var4 != null && var3.isVisible() && var4.isVisible();
   }

   @Override
   public int getCacheHash(SynthContext var1, int var2, int var3, int var4, String var5) {
      JScrollBar var6 = (JScrollBar)var1.getComponent();
      int var7 = var6.getOrientation();
      int var8 = super.getCacheHash(var1, var2, var3, var4, var5);
      return 31 * var8 + var7;
   }
}
