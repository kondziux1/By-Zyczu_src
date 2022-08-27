package de.javasoft.plaf.synthetica;

import de.javasoft.util.OS;
import java.awt.Component;
import java.awt.KeyEventPostProcessor;
import java.awt.Window;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRootPane;
import javax.swing.MenuElement;
import javax.swing.MenuSelectionManager;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.basic.ComboPopup;

class AltKeyEventProcessor implements KeyEventPostProcessor {
   private static boolean altKeyPressed = false;
   private static boolean menuActivated = false;

   AltKeyEventProcessor() {
      super();
   }

   public boolean postProcessKeyEvent(KeyEvent var1) {
      if (var1.isConsumed()) {
         return false;
      } else {
         int var2 = var1.getModifiers();
         if (var1.getKeyCode() == 18 && var2 != 10 & var2 != 32) {
            Component var3 = var1.getComponent();
            Window var4 = SwingUtilities.getWindowAncestor(var3);
            JRootPane var5 = SwingUtilities.getRootPane(var3);
            JMenuBar var6 = var5 != null ? var5.getJMenuBar() : null;
            if (var6 == null && var4 instanceof JFrame) {
               var6 = ((JFrame)var4).getJMenuBar();
            }

            if (var1.getID() == 401) {
               if (!altKeyPressed) {
                  this.altPressed(var1, var6);
               }

               altKeyPressed = true;
               return true;
            }

            if (var1.getID() == 402) {
               if (altKeyPressed) {
                  this.altReleased(var1, var6);
               }

               altKeyPressed = false;
            }
         } else {
            altKeyPressed = false;
         }

         return false;
      }
   }

   private void altPressed(KeyEvent var1, JMenuBar var2) {
      boolean var3 = UIManager.getBoolean("Synthetica.forcedAltKeyEventConsumption");
      MenuSelectionManager var4 = MenuSelectionManager.defaultManager();
      MenuElement[] var5 = var4.getSelectedPath();
      if (var5.length > 0) {
         if (!(var5[0] instanceof ComboPopup)) {
            var4.clearSelectedPath();
         }

         menuActivated = false;
         if (!var3) {
            var1.consume();
         }
      } else {
         menuActivated = true;
         if (var2 != null && var2.getMenu(0) != null && !var3) {
            var1.consume();
         }
      }

      if (var3) {
         var1.consume();
      }

      if (var2 != null && menuActivated) {
         var2.repaint();
      }

   }

   public static void setMenuActive(boolean var0) {
      menuActivated = var0;
   }

   public static boolean isMenuActive() {
      return menuActivated;
   }

   public static boolean showMnemonicsOnAltKeyOnly() {
      return OS.getCurrentOS() == OS.Windows && SyntheticaLookAndFeel.getBoolean("Synthetica.showMnemonicsOnAltKeyOnly", null, true)
         ? true
         : SyntheticaLookAndFeel.getBoolean("Synthetica.forceShowMnemonicsOnAltKeyOnly", null);
   }

   private void altReleased(KeyEvent var1, JMenuBar var2) {
      MenuSelectionManager var3 = MenuSelectionManager.defaultManager();
      if (var3.getSelectedPath().length == 0 && menuActivated) {
         JMenu var4 = var2 != null ? var2.getMenu(0) : null;
         if (var4 != null) {
            MenuElement[] var5 = new MenuElement[]{var2, var4};
            var3.setSelectedPath(var5);
         }
      }

   }
}
