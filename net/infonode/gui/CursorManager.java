package net.infonode.gui;

import java.awt.Cursor;
import java.util.WeakHashMap;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JRootPane;

public class CursorManager {
   private static boolean enabled = true;
   private static WeakHashMap windowPanels = new WeakHashMap();

   private CursorManager() {
      super();
   }

   public static void setGlobalCursor(JRootPane var0, Cursor var1) {
      if (var0 != null) {
         CursorManager.RootCursorInfo var2 = (CursorManager.RootCursorInfo)windowPanels.get(var0);
         if (var2 == null) {
            var2 = new CursorManager.RootCursorInfo(new CursorManager$1());
            windowPanels.put(var0, var2);
            var0.getLayeredPane().add(var2.getComponent());
            var0.getLayeredPane().setLayer(var2.getComponent(), JLayeredPane.DRAG_LAYER + 10);
            var2.getComponent().setBounds(0, 0, var0.getWidth(), var0.getHeight());
            var0.getLayeredPane().addComponentListener(new CursorManager$2(var0));
         }

         if (!var2.isCursorSet()) {
            var2.setCursor(var1);
            var2.pushCursor(var0.isCursorSet() ? var0.getCursor() : null);
         }

         if (enabled) {
            var0.setCursor(var1);
            var2.getComponent().setVisible(true);
         }

      }
   }

   public static Cursor getCurrentGlobalCursor(JRootPane var0) {
      if (var0 == null) {
         return Cursor.getDefaultCursor();
      } else {
         CursorManager.RootCursorInfo var1 = (CursorManager.RootCursorInfo)windowPanels.get(var0);
         return var1 != null && var1.isCursorSet() ? var1.getCursor() : Cursor.getDefaultCursor();
      }
   }

   public static void resetGlobalCursor(JRootPane var0) {
      if (var0 != null) {
         CursorManager.RootCursorInfo var1 = (CursorManager.RootCursorInfo)windowPanels.get(var0);
         if (var1 != null && var1.isCursorSet()) {
            var0.setCursor(var1.popCursor());
            var1.getComponent().setVisible(false);
         }

      }
   }

   public static void setEnabled(boolean var0) {
      enabled = var0;
   }

   public static boolean isEnabled() {
      return enabled;
   }

   public static JComponent getCursorLayerComponent(JRootPane var0) {
      if (var0 == null) {
         return null;
      } else {
         CursorManager.RootCursorInfo var1 = (CursorManager.RootCursorInfo)windowPanels.get(var0);
         return var1 == null ? null : var1.getComponent();
      }
   }

   public static boolean isGlobalCursorSet(JRootPane var0) {
      if (var0 == null) {
         return false;
      } else {
         CursorManager.RootCursorInfo var1 = (CursorManager.RootCursorInfo)windowPanels.get(var0);
         return var1 != null && var1.isCursorSet();
      }
   }

   private static class RootCursorInfo {
      private Cursor savedCursor;
      private Cursor cursor;
      private JComponent panel;
      private boolean cursorSet = false;

      RootCursorInfo(JComponent var1) {
         super();
         this.panel = var1;
      }

      public JComponent getComponent() {
         return this.panel;
      }

      public void pushCursor(Cursor var1) {
         if (this.savedCursor == null) {
            this.savedCursor = var1;
         }

         this.cursorSet = true;
      }

      public Cursor popCursor() {
         Cursor var1 = this.savedCursor;
         this.savedCursor = null;
         this.cursorSet = false;
         return var1;
      }

      public boolean isCursorSet() {
         return this.cursorSet;
      }

      public Cursor getCursor() {
         return this.cursor;
      }

      public void setCursor(Cursor var1) {
         this.cursor = var1;
      }
   }
}
