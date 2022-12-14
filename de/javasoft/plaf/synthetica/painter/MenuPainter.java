package de.javasoft.plaf.synthetica.painter;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaState;
import de.javasoft.util.java2d.Synthetica2DUtils;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicMenuUI;
import javax.swing.plaf.synth.SynthContext;

public class MenuPainter extends SyntheticaComponentPainter {
   public static final String UI_KEY = "Synthetica.MenuPainter";
   private static Icon topMarkerIcon;
   private static Icon bottomMarkerIcon;

   protected MenuPainter() {
      super();
   }

   public static MenuPainter getInstance() {
      return getInstance(null);
   }

   public static MenuPainter getInstance(SynthContext var0) {
      SyntheticaComponentPainter var1 = (SyntheticaComponentPainter)instances.get(getPainterClassName(var0, MenuPainter.class, "Synthetica.MenuPainter"));
      if (var1 == null) {
         var1 = getInstance(var0, MenuPainter.class, "Synthetica.MenuPainter");
      }

      return (MenuPainter)var1;
   }

   public static void reinitialize() {
      topMarkerIcon = null;
      bottomMarkerIcon = null;
   }

   public void paintMenuBorder(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public void paintMenuItemBorder(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public void paintPopupMenuBorder(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public void paintMenuBarBorder(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public void paintPopupMenuBackground(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
      JPopupMenu var7 = (JPopupMenu)var1.getComponent();
      boolean var8 = SyntheticaLookAndFeel.isToplevelPopupMenu(var7);
      this.paintPopupMenuBackground(var7, var8, var2, var3, var4, var5, var6);
      if (var8) {
         Insets var9 = SyntheticaLookAndFeel.getInsets("Synthetica.popupMenu.toplevel.insets", var7, false);
         this.paintToplevelPopupMenuMarker(var7, var9, var2, var3, var4, var5, var6);

         Component[] var13;
         for(Component var10 : var13 = var7.getComponents()) {
            if (var10 instanceof JComponent && SyntheticaLookAndFeel.getClientProperty("Synthetica.popupMenu.title", (JComponent)var10, false)) {
               Rectangle var14 = var10.getBounds();
               this.paintToplevelPopupMenuTitleBackground(var7, var2, var9.left, var14.y, var5 - var9.left - var9.right, var14.height);
            }
         }
      }

   }

   public void paintPopupMenuBackground(JPopupMenu var1, boolean var2, Graphics var3, int var4, int var5, int var6, int var7) {
      Component var8 = var1.getInvoker();
      boolean var9 = SyntheticaLookAndFeel.get("Synthetica.comboPopup.insets", var1) != null;
      if (!var9 && var1.getName() != null && var1.getName().startsWith("ComboPopup")) {
         if (!SyntheticaLookAndFeel.isWindowOpacityEnabled(null)) {
            Component var24 = SyntheticaLookAndFeel.findComponent(JList.class, var1);
            if (var24 != null) {
               var3.setColor(var24.getBackground());
            } else {
               var3.setColor(var1.getBackground());
            }

            var3.fillRect(var4, var5, var6, var7);
         }

      } else {
         JPanel var10 = (JPanel)var1.getParent();
         String var11 = SyntheticaLookAndFeel.getString("Synthetica.popupMenu.background", var1);
         Insets var12 = SyntheticaLookAndFeel.getInsets("Synthetica.popupMenu.background.insets", var1);
         Insets var13 = var12;
         if (var2) {
            var12 = SyntheticaLookAndFeel.getInsets("Synthetica.popupMenu.toplevel.background.insets", var1, false);
            var13 = var12;
            Boolean var14 = popupIsBelowInvoker(var8, var1);
            if (var14 == null) {
               var14 = false;
            }

            var11 = SyntheticaLookAndFeel.getString("Synthetica.popupMenu.toplevel" + (var14 ? ".belowInvoker.background" : ".aboveInvoker.background"), var1);
            if (var11 == null) {
               var11 = SyntheticaLookAndFeel.getString("Synthetica.popupMenu.toplevel.background", var1);
            }
         }

         BufferedImage var27 = (BufferedImage)var10.getClientProperty("POPUP_BACKGROUND");
         if (var27 != null) {
            if (SyntheticaLookAndFeel.getBoolean("Synthetica.popupMenu.blur.enabled", var1)) {
               if (!var10.getClientProperty("POPUP_LIGHTWEIGHT")) {
                  ImagePainter var15 = new ImagePainter(var27, var3, var4, var5, var6, var7, var12, var13);
                  var15.drawBorder();
               }

               Insets var28 = SyntheticaLookAndFeel.getInsets("Synthetica.popupMenu.blur.insets", var1);
               int var16 = SyntheticaLookAndFeel.getInt("Synthetica.popupMenu.blur.size", var1, 5);
               BufferedImage var17 = Synthetica2DUtils.createBluredImage(var27, var16);
               ImagePainter var18 = new ImagePainter(var17, var3, var4, var5, var6, var7, var28, var28);
               var18.drawCenter();
               String var19 = SyntheticaLookAndFeel.getString("Synthetica.popupMenu.blur.background", var1);
               if (var19 != null && !var2) {
                  var11 = var19;
               }
            } else {
               var3.drawImage(var27, var4, var5, null);
            }
         }

         ImagePainter var29 = new ImagePainter(var3, var4, var5, var6, var7, var11, var12, var13, 0, 0);
         var29.draw();
         var11 = SyntheticaLookAndFeel.getString("Synthetica.popupMenu.iconSeparator", var1);
         if (var11 != null
            && (SyntheticaLookAndFeel.preservePopupIconSpace(var1) || SyntheticaLookAndFeel.popupHasCheckRadio(var1))
            && !SyntheticaLookAndFeel.popupHasCheckRadioWithIcon(var1)) {
            boolean var31 = var1.getComponentOrientation().isLeftToRight();
            Insets var32 = var1.getInsets();
            int var33 = SyntheticaLookAndFeel.getInt("Synthetica.popupMenu.iconSeparator.gap", var1, 24);
            Integer var34 = (Integer)var1.getClientProperty("Synthetica.menuItem.maxIconWidth");
            int var20 = SyntheticaLookAndFeel.getInt("Synthetica.popupMenu.iconSeparator.width", var1, 1);
            if (var34 != null && var34 > 16) {
               var20 += var34 - 16;
            }

            var1.putClientProperty("Synthetica.flipHorizontal", !var31);
            int var21 = var4 + (var31 ? var33 : var6 - var32.right - var33 - var20 + var32.left);
            int var22 = var5 + var32.top;
            int var23 = var7 - var32.top - var32.bottom;
            var12 = SyntheticaLookAndFeel.getInsets("Synthetica.popupMenu.iconSeparator.insets", var1, new Insets(0, 0, 0, 2));
            var29 = new ImagePainter(var1, var3, var21, var22, var20, var23, var11, var12, var12, 0, 0);
            var29.draw();
            var1.putClientProperty("Synthetica.flipHorizontal", false);
         }

      }
   }

   protected void paintToplevelPopupMenuMarker(JPopupMenu var1, Insets var2, Graphics var3, int var4, int var5, int var6, int var7) {
      if (topMarkerIcon == null) {
         topMarkerIcon = SyntheticaLookAndFeel.loadIcon("Synthetica.popupMenu.toplevel.topMarker.image");
      }

      if (bottomMarkerIcon == null) {
         bottomMarkerIcon = SyntheticaLookAndFeel.loadIcon("Synthetica.popupMenu.toplevel.bottomMarker.image");
      }

      Component var8 = var1.getInvoker();
      Boolean var9 = popupIsBelowInvoker(var8, var1);
      Icon var10 = var9 == null ? null : (var9 ? topMarkerIcon : bottomMarkerIcon);
      if (var10 != null) {
         Point var11 = SwingUtilities.convertPoint(var8, var8.getWidth() / 2, var5, var1);
         int var12 = var9 ? var5 : var5 + var7 - var10.getIconHeight();
         var10.paintIcon(var1, var3, var11.x - var10.getIconWidth() / 2, var12);
      }

   }

   protected void paintToplevelPopupMenuTitleBackground(JComponent var1, Graphics var2, int var3, int var4, int var5, int var6) {
      String var7 = SyntheticaLookAndFeel.getString("Synthetica.popupMenu.toplevel.title.background", var1);
      if (var7 != null) {
         Insets var8 = SyntheticaLookAndFeel.getInsets("Synthetica.popupMenu.toplevel.title.background.insets", var1, false);
         ImagePainter var9 = new ImagePainter(var2, var3, var4, var5, var6, var7, var8, var8, 0, 0);
         var9.draw();
      }

   }

   static Boolean popupIsBelowInvoker(Component var0, JPopupMenu var1) {
      Point var2 = var0.getLocation();
      SwingUtilities.convertPointToScreen(var2, var0.getParent());
      Point var3 = new Point(0, var1.getLocation().y);
      SwingUtilities.convertPointToScreen(var3, var1.getParent());
      if (var3.y >= var2.y + var0.getHeight() / 2) {
         return Boolean.TRUE;
      } else {
         return var3.y + var1.getHeight() <= var2.y + var0.getHeight() / 2 ? Boolean.FALSE : null;
      }
   }

   public void paintMenuBackground(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
      Object var7 = null;
      JMenu var8 = (JMenu)var1.getComponent();
      int var9 = var1.getComponentState();
      boolean var10 = (var9 & 8) > 0;
      boolean var11 = (var9 & 512) > 0;
      Boolean var12 = (Boolean)var8.getClientProperty("Synthetica.MOUSE_OVER");
      boolean var13 = var12 == null ? false : var12;
      if (var13) {
         var9 |= 2;
      }

      boolean var14 = (var9 & 2) > 0;
      if (SyntheticaLookAndFeel.getJVMCompatibilityMode() == SyntheticaLookAndFeel.JVMCompatibilityMode.SUN) {
         try {
            Icon var15 = var1.getStyle().getIcon(var1, "Menu.arrowIcon");
            BasicMenuUI var16 = (BasicMenuUI)((JMenu)var1.getComponent()).getUI();
            Class var17 = Class.forName("javax.swing.plaf.basic.BasicMenuItemUI");
            Field var18 = var17.getDeclaredField("arrowIcon");
            var18.setAccessible(true);
            var18.set(var16, var15);
         } catch (Exception var21) {
            throw new RuntimeException(var21);
         }
      }

      String var22 = "Synthetica.menu";
      Object var25 = null;
      Insets var26;
      if (var8.isTopLevelMenu()) {
         var22 = var22 + ".toplevel.background";
         if (var10) {
            var22 = var22 + ".disabled";
         } else if (var11) {
            var22 = var22 + ".selected";
         } else if (var14) {
            var22 = var22 + (SyntheticaLookAndFeel.getString(var22 + ".hover", var8) != null ? ".hover" : ".selected");
         }

         var22 = SyntheticaLookAndFeel.getString(var22, var8);
         var26 = SyntheticaLookAndFeel.getInsets("Synthetica.menu.toplevel.background.insets", var8);
      } else {
         if (var10) {
            var22 = var22 + ".disabled";
         } else if (var14) {
            var22 = var22 + ".hover";
         } else if (var11) {
            if (SyntheticaLookAndFeel.getString(var22 + ".selected", var8) == null) {
               var22 = var22 + ".hover";
            } else {
               var22 = var22 + ".selected";
            }
         }

         var22 = SyntheticaLookAndFeel.getString(var22, var8);
         var26 = SyntheticaLookAndFeel.getInsets("Synthetica.menu.insets", var8);
      }

      if (var22 != null) {
         int var27 = 0;
         int var29 = 0;
         int var19 = 0;
         if (var14) {
            var27 = SyntheticaLookAndFeel.getInt("Synthetica.menu.hover.animation.cycles", var8, 1);
            var29 = SyntheticaLookAndFeel.getInt("Synthetica.menu.hover.animation.delay", var8, 50);
            var19 = SyntheticaLookAndFeel.getInt("Synthetica.menu.hover.animation.type", var8, 1);
         } else {
            var27 = SyntheticaLookAndFeel.getInt("Synthetica.menu.animation.cycles", var8, 1);
            var29 = SyntheticaLookAndFeel.getInt("Synthetica.menu.animation.delay", var8, 50);
            var19 = SyntheticaLookAndFeel.getInt("Synthetica.menu.animation.type", var8, 2);
         }

         ImagePainter var20 = new ImagePainter(var8, var27, var29, var19, var9, var2, var3, var4, var5, var6, var22, var26, var26, 0, 0);
         var20.draw();
      }

   }

   public void paintMenuItemBackground(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
      JComponent var7 = var1.getComponent();
      String var8 = "Synthetica.menuItem";
      int var9 = var1.getComponentState();
      boolean var10 = (var9 & 8) > 0;
      boolean var11 = (var9 & 2) > 0;
      if (var10) {
         var8 = var8 + ".disabled";
      } else if (var11) {
         var8 = var8 + ".hover";
      }

      var8 = SyntheticaLookAndFeel.getString(var8, var7);
      if (var8 != null) {
         Insets var12 = SyntheticaLookAndFeel.getInsets("Synthetica.menuItem.insets", var7);
         int var14 = 0;
         int var15 = 0;
         int var16 = 0;
         if (var11) {
            var14 = SyntheticaLookAndFeel.getInt("Synthetica.menuItem.hover.animation.cycles", var7, 1);
            var15 = SyntheticaLookAndFeel.getInt("Synthetica.menuItem.hover.animation.delay", var7, 50);
            var16 = SyntheticaLookAndFeel.getInt("Synthetica.menuItem.hover.animation.type", var7, 1);
         } else {
            var14 = SyntheticaLookAndFeel.getInt("Synthetica.menuItem.animation.cycles", var7, 1);
            var15 = SyntheticaLookAndFeel.getInt("Synthetica.menuItem.animation.delay", var7, 50);
            var16 = SyntheticaLookAndFeel.getInt("Synthetica.menuItem.animation.type", var7, 2);
         }

         ImagePainter var17 = new ImagePainter(var7, var14, var15, var16, var9, var2, var3, var4, var5, var6, var8, var12, var12, 0, 0);
         var17.draw();
      }

   }

   public void paintRadioButtonMenuItemBackground(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
      this.paintMenuItemBackground(var1, var2, var3, var4, var5, var6);
   }

   public void paintRadioButtonMenuItemBorder(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public void paintCheckBoxMenuItemBackground(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
      this.paintMenuItemBackground(var1, var2, var3, var4, var5, var6);
   }

   public void paintCheckBoxMenuItemBorder(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
   }

   public void paintMenuBarBackground(SynthContext var1, Graphics var2, int var3, int var4, int var5, int var6) {
      this.paintMenuBarBackground(var1.getComponent(), new SyntheticaState(var1.getComponentState()), var2, var3, var4, var5, var6);
   }

   public void paintMenuBarBackground(JComponent var1, SyntheticaState var2, Graphics var3, int var4, int var5, int var6, int var7) {
      Container var8 = var1.getRootPane().getParent();
      boolean var9 = true;
      if (var8 instanceof Window) {
         var9 = ((Window)var8).isActive();
      } else if (var8 instanceof JInternalFrame) {
         var9 = ((JInternalFrame)var8).isSelected();
      }

      String var10 = "Synthetica.menuBar.background";
      if (var9) {
         var10 = var10 + ".active";
      } else {
         var10 = var10 + ".inactive";
      }

      var10 = SyntheticaLookAndFeel.getString(var10, var1);
      if (var10 != null) {
         Insets var11 = SyntheticaLookAndFeel.getInsets("Synthetica.menuBar.background.insets", var1);
         if (var11 == null) {
            var11 = new Insets(0, 0, 0, 0);
         }

         byte var14 = 0;
         if (SyntheticaLookAndFeel.getBoolean("Synthetica.menuBar.background.horizontalTiled", var1)) {
            var14 = 1;
         }

         byte var15 = 0;
         if (SyntheticaLookAndFeel.getBoolean("Synthetica.menuBar.background.verticalTiled", var1)) {
            var15 = 1;
         }

         ImagePainter var16 = new ImagePainter(var3, var4, var5, var6, var7, var10, var11, var11, var14, var15);
         var16.draw();
         var10 = "Synthetica.menuBar.background.light";
         if (var9) {
            var10 = var10 + ".active";
         } else {
            var10 = var10 + ".inactive";
         }

         var10 = SyntheticaLookAndFeel.getString(var10, var1);
         if (var10 != null) {
            var16 = new ImagePainter(var3, var4, var5, var6, var7, var10, var11, var11, 0, var15);
            var16.draw();
         }
      }

   }
}
