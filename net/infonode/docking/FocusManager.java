package net.infonode.docking;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.beans.PropertyChangeListener;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

class FocusManager {
   private static final FocusManager INSTANCE = new FocusManager();
   private int ignoreFocusChanges;
   private Timer focusTimer = new Timer(20, new FocusManager$1(this));
   private boolean focusUpdateTriggered;
   private ArrayList lastFocusedWindows = new ArrayList();
   private Component focusedComponent;
   private PropertyChangeListener focusListener = new FocusManager$2(this);

   private FocusManager() {
      super();
      KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener("focusOwner", this.focusListener);
      this.updateFocus();
   }

   static FocusManager getInstance() {
      return INSTANCE;
   }

   private void updateFocus() {
      this.focusedComponent = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
      ArrayList var1 = this.lastFocusedWindows;
      this.lastFocusedWindows = new ArrayList();
      this.updateWindows(this.focusedComponent, this.focusedComponent, var1);

      for(int var2 = 0; var2 < var1.size(); ++var2) {
         RootWindow var3 = (RootWindow)((Reference)var1.get(var2)).get();
         if (var3 != null) {
            var3.setFocusedView(null);
         }
      }

   }

   void pinFocus(Runnable var1) {
      ++this.ignoreFocusChanges;
      Component var2 = this.focusedComponent;

      try {
         var1.run();
      } finally {
         if (--this.ignoreFocusChanges == 0 && var2 != null) {
            var2.requestFocusInWindow();
            SwingUtilities.invokeLater(new FocusManager$3(this, var2));
         }

      }

   }

   void startIgnoreFocusChanges() {
      ++this.ignoreFocusChanges;
   }

   void stopIgnoreFocusChanges() {
      if (--this.ignoreFocusChanges == 0) {
         this.updateFocus();
      }

   }

   static void focusWindow(DockingWindow var0) {
      if (var0 != null) {
         var0.restoreFocus();
         SwingUtilities.invokeLater(new FocusManager$5(var0));
      }
   }

   private static View getViewContaining(Component var0) {
      return var0 == null ? null : (var0 instanceof View ? (View)var0 : getViewContaining(var0.getParent()));
   }

   private void updateWindows(Component var1, Component var2, ArrayList var3) {
      label23:
      while(true) {
         View var4 = getViewContaining((Component)var2);
         if (var4 != null) {
            var4.setLastFocusedComponent(var1);
            RootWindow var5 = var4.getRootWindow();
            if (var5 != null) {
               var5.setFocusedView(var4);
               this.lastFocusedWindows.add(new WeakReference(var5));
               var2 = var5;
               int var6 = 0;

               while(true) {
                  if (var6 >= var3.size()) {
                     continue label23;
                  }

                  if (((Reference)var3.get(var6)).get() == var2) {
                     var3.remove(var6);
                  }

                  ++var6;
               }
            }
         }

         return;
      }
   }
}
