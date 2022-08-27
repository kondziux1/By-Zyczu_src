package de.javasoft.plaf.synthetica;

import de.javasoft.util.JavaVersion;
import de.javasoft.util.OS;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;
import java.security.AccessControlException;
import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPopupMenu;
import javax.swing.JToolTip;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;

public class Popup extends javax.swing.Popup {
   public static final String POPUP_BACKGROUND = "POPUP_BACKGROUND";
   public static final String POPUP_LIGHTWEIGHT = "POPUP_LIGHTWEIGHT";
   private Component contents;
   private int x;
   private int y;
   private javax.swing.Popup popup;
   private Container heavyWeightContainer;
   private boolean lightWeight;

   public Popup(Component var1, Component var2, int var3, int var4, javax.swing.Popup var5) {
      super();
      this.contents = var2;
      this.popup = var5;
      this.x = var3;
      this.y = var4;
      Container var6 = var2.getParent();
      ((JComponent)var6).putClientProperty("POPUP_BACKGROUND", null);
      ((JComponent)var6).putClientProperty("POPUP_LIGHTWEIGHT", null);
      if (!SyntheticaLookAndFeel.isWindowOpacityEnabled(null)) {
         ((JComponent)var6).setDoubleBuffered(false);
      }

      for(this.lightWeight = true; var6 != null; var6 = var6.getParent()) {
         if (var6 instanceof JWindow || var6 instanceof Panel || var6 instanceof Window) {
            this.heavyWeightContainer = var6;
            this.lightWeight = false;
            break;
         }
      }

      this.internalFrameCursorBugfix(var1);
      if (this.heavyWeightContainer != null && OS.getCurrentOS() == OS.Mac && !UIManager.getBoolean("Synthetica.popup.osShadow.enabled")) {
         this.heavyWeightContainer.setBackground(new Color(16777216, true));
         this.heavyWeightContainer.setBackground(new Color(0, true));
         if (this.heavyWeightContainer instanceof JWindow) {
            ((JWindow)this.heavyWeightContainer).getRootPane().putClientProperty("apple.awt.draggableWindowBackground", Boolean.FALSE);
         }
      }

   }

   private void internalFrameCursorBugfix(Component var1) {
      if (var1 != null && var1 instanceof JInternalFrame) {
         Container var2 = ((JInternalFrame)var1).getTopLevelAncestor();
         Cursor var3 = Cursor.getPredefinedCursor(0);
         if (var2 instanceof JFrame) {
            ((JFrame)var2).getGlassPane().setCursor(var3);
            ((JFrame)var2).getGlassPane().setVisible(false);
         } else if (var2 instanceof JWindow) {
            ((JWindow)var2).getGlassPane().setCursor(var3);
            ((JWindow)var2).getGlassPane().setVisible(false);
         } else if (var2 instanceof JDialog) {
            ((JDialog)var2).getGlassPane().setCursor(var3);
            ((JDialog)var2).getGlassPane().setVisible(false);
         } else if (var2 instanceof JApplet) {
            ((JApplet)var2).getGlassPane().setCursor(var3);
            ((JApplet)var2).getGlassPane().setVisible(false);
         }
      }

   }

   public void hide() {
      if (this.heavyWeightContainer instanceof Window && SyntheticaLookAndFeel.getBoolean("Synthetica.popupMenu.fade-out.enabled", this.contents)) {
         int var1 = SyntheticaLookAndFeel.getInt("Synthetica.popupMenu.fade-out.delay", this.contents, 25);
         int var2 = SyntheticaLookAndFeel.getInt("Synthetica.popupMenu.fade-out.duration", this.contents, 150);
         Popup.WindowFader var3 = new Popup.WindowFader((Window)this.heavyWeightContainer, this.popup, var1, var2, false, true);
         var3.start();
      } else {
         this.popup.hide();
      }

      Container var4 = this.contents == null ? null : this.contents.getParent();
      if (var4 instanceof JComponent) {
         ((JComponent)var4).putClientProperty("POPUP_BACKGROUND", null);
      }

      if (this.heavyWeightContainer != null) {
         this.heavyWeightContainer = null;
         if (JavaVersion.JAVA5) {
            for(; var4 != null; var4 = var4.getParent()) {
               if (var4 instanceof JFrame) {
                  ((JFrame)var4).update(var4.getGraphics());
               }
            }
         }
      }

      this.contents = null;
      this.popup = null;
   }

   public javax.swing.Popup getDelegate() {
      return this.popup;
   }

   public void show() {
      final boolean var1 = this.contents instanceof JPopupMenu && SyntheticaLookAndFeel.getBoolean("Synthetica.popupMenu.blur.enabled", this.contents);
      if (var1 && this.heavyWeightContainer == null) {
         this.heavyWeightContainer = this.contents.getParent();
      }

      if (this.heavyWeightContainer != null && SyntheticaLookAndFeel.getBoolean("Synthetica.popupRobot.enabled", this.contents.getParent(), true)) {
         SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               Popup.this.showPopup(var1);
            }
         });
      } else {
         this.popup.show();
      }
   }

   private void showPopup(boolean var1) {
      if (this.heavyWeightContainer != null) {
         if (JavaVersion.JAVA7U8_OR_ABOVE && OS.getCurrentOS() == OS.Mac && this.contents instanceof JToolTip) {
            SyntheticaLookAndFeel.setChildrenOpaque(this.heavyWeightContainer, false);
         }

         boolean var2 = SyntheticaLookAndFeel.getBoolean("Synthetica.popupRobot.enabled", this.contents.getParent(), true);
         if (var2 && (OS.getCurrentOS() != OS.Mac && (SyntheticaLookAndFeel.isWindowOpacityEnabled(null) || var1) || OS.getCurrentOS() == OS.Mac && var1)) {
            ((JComponent)this.contents.getParent()).putClientProperty("POPUP_BACKGROUND", this.snapshot());
            ((JComponent)this.contents.getParent()).putClientProperty("POPUP_LIGHTWEIGHT", this.lightWeight);
         }

         if (this.heavyWeightContainer instanceof Window && SyntheticaLookAndFeel.getBoolean("Synthetica.popupMenu.fade-in.enabled", this.contents)) {
            int var3 = SyntheticaLookAndFeel.getInt("Synthetica.popupMenu.fade-in.delay", this.contents, 25);
            int var4 = SyntheticaLookAndFeel.getInt("Synthetica.popupMenu.fade-in.duration", this.contents, 200);
            Popup.WindowFader var5 = new Popup.WindowFader((Window)this.heavyWeightContainer, null, var3, var4, true, false);
            var5.start();
         }

         this.popup.show();
         if (this.heavyWeightContainer instanceof Window && !SyntheticaLookAndFeel.isWindowOpacityEnabled(null) && !var1) {
            SyntheticaLookAndFeel.setWindowOpaque((Window)this.heavyWeightContainer, false);
         }

      }
   }

   private BufferedImage snapshot() {
      BufferedImage var1 = null;

      try {
         Robot var2 = new Robot();
         Dimension var3 = this.heavyWeightContainer.getPreferredSize();
         if (var3.width > 0 && var3.height > 0) {
            Rectangle var4 = new Rectangle(this.x, this.y, var3.width, var3.height);
            var1 = var2.createScreenCapture(var4);
         }
      } catch (AccessControlException var5) {
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      return var1;
   }

   private static class WindowFader extends Timer {
      private static ActionListener listener = new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            Popup.WindowFader var2 = (Popup.WindowFader)var1.getSource();
            float var3 = (float)var2.counter / var2.repeats;
            this.setWindowOpacity(var2.window, var2.fadein ? var3 : 1.0F - var3);
            if ((float)var2.counter == var2.repeats) {
               var2.counter = 0;
               var2.stop();
               if (var2.hidePopup) {
                  var2.popup.hide();
                  this.setWindowOpacity(var2.window, 1.0F);
               }

               var2.popup = null;
               var2.window = null;
            } else {
               var2.counter = var2.counter + 1;
            }

         }

         private void setWindowOpacity(Window var1, float var2) {
            if (JavaVersion.JAVA6U10_OR_ABOVE) {
               if (JavaVersion.JAVA6U10_OR_ABOVE && !JavaVersion.JAVA7) {
                  try {
                     Class var7 = Class.forName("com.sun.awt.AWTUtilities");
                     Method var8 = var7.getMethod("setWindowOpacity", Window.class, Float.TYPE);
                     var8.invoke(null, var1, var2);
                  } catch (Exception var6) {
                     var6.printStackTrace();
                  }
               } else {
                  try {
                     Class var3 = Class.forName("java.awt.Window");
                     Method var4 = var3.getMethod("setOpacity", Float.TYPE);
                     var4.invoke(var1, var2);
                  } catch (Exception var5) {
                     var5.printStackTrace();
                  }
               }
            }

         }
      };
      private Window window;
      private javax.swing.Popup popup;
      private boolean fadein;
      private boolean hidePopup;
      private float repeats;
      private int counter;

      public WindowFader(Window var1, javax.swing.Popup var2, int var3, int var4, boolean var5, boolean var6) {
         super(0, listener);
         this.window = var1;
         this.popup = var2;
         this.setDelay(var3);
         this.repeats = (float)(var4 / var3);
         this.fadein = var5;
         this.hidePopup = var6;
      }
   }
}
