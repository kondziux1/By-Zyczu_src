package net.infonode.gui;

import java.awt.Toolkit;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class DynamicUIManager {
   private static final DynamicUIManager instance = new DynamicUIManager();
   private ArrayList listeners = new ArrayList(2);
   private ArrayList prioritizedListeners = new ArrayList(2);
   private String[] properties = new String[]{
      "win.3d.backgroundColor",
      "win.3d.highlightColor",
      "win.3d.lightColor",
      "win.3d.shadowColor",
      "win.frame.activeCaptionColor",
      "win.frame.activeCaptionGradientColor",
      "win.frame.captionTextColor",
      "win.frame.activeBorderColor",
      "win.mdi.backgroundColor",
      "win.desktop.backgroundColor",
      "win.frame.inactiveCaptionColor",
      "win.frame.inactiveCaptionGradientColor",
      "win.frame.inactiveCaptionTextColor",
      "win.frame.inactiveBorderColor",
      "win.menu.backgroundColor",
      "win.menu.textColor",
      "win.frame.textColor?????",
      "win.item.highlightColor",
      "win.item.highlightTextColor",
      "win.tooltip.backgroundColor",
      "win.tooltip.textColor",
      "win.frame.backgroundColor",
      "win.frame.textColor",
      "win.item.hotTrackedColor"
   };
   private Toolkit currentToolkit;
   private boolean propertyChangePending;

   public static DynamicUIManager getInstance() {
      return instance;
   }

   private DynamicUIManager() {
      super();
      DynamicUIManager$1 var1 = new DynamicUIManager$1(this);
      UIManager.addPropertyChangeListener(new DynamicUIManager$2(this, var1));
      UIManager.getDefaults().addPropertyChangeListener(new DynamicUIManager$3(this));
      this.setupPropertyListener(var1);
   }

   private void setupPropertyListener(PropertyChangeListener var1) {
      if (this.currentToolkit != null) {
         for(int var2 = 0; var2 < this.properties.length; ++var2) {
            this.currentToolkit.removePropertyChangeListener(this.properties[var2], var1);
         }
      }

      this.currentToolkit = Toolkit.getDefaultToolkit();

      for(int var3 = 0; var3 < this.properties.length; ++var3) {
         this.currentToolkit.addPropertyChangeListener(this.properties[var3], var1);
      }

   }

   public void addListener(DynamicUIManagerListener var1) {
      this.listeners.add(var1);
   }

   public void removeListener(DynamicUIManagerListener var1) {
      this.listeners.remove(var1);
   }

   public void addPrioritizedListener(DynamicUIManagerListener var1) {
      this.prioritizedListeners.add(var1);
   }

   public void removePrioritizedListener(DynamicUIManagerListener var1) {
      this.prioritizedListeners.remove(var1);
   }

   private void fireLookAndFeelChanging() {
      Object[] var1 = this.prioritizedListeners.toArray();
      Object[] var2 = this.listeners.toArray();

      for(int var3 = 0; var3 < var1.length; ++var3) {
         ((DynamicUIManagerListener)var1[var3]).lookAndFeelChanging();
      }

      for(int var4 = 0; var4 < var2.length; ++var4) {
         ((DynamicUIManagerListener)var2[var4]).lookAndFeelChanging();
      }

   }

   private void fireLookAndFeelChanged() {
      Object[] var1 = this.prioritizedListeners.toArray();
      Object[] var2 = this.listeners.toArray();

      for(int var3 = 0; var3 < var1.length; ++var3) {
         ((DynamicUIManagerListener)var1[var3]).lookAndFeelChanged();
      }

      for(int var4 = 0; var4 < var2.length; ++var4) {
         ((DynamicUIManagerListener)var2[var4]).lookAndFeelChanged();
      }

   }

   private void handlePropertyChanges() {
      if (!this.propertyChangePending) {
         this.propertyChangePending = true;
         SwingUtilities.invokeLater(new DynamicUIManager$4(this));
         this.firePropertyChanging();
      }

   }

   private void firePropertyChanging() {
      Object[] var1 = this.prioritizedListeners.toArray();
      Object[] var2 = this.listeners.toArray();

      for(int var3 = 0; var3 < var1.length; ++var3) {
         ((DynamicUIManagerListener)var1[var3]).propertiesChanging();
      }

      for(int var4 = 0; var4 < var2.length; ++var4) {
         ((DynamicUIManagerListener)var2[var4]).propertiesChanging();
      }

   }

   private void firePropertyChanged() {
      Object[] var1 = this.prioritizedListeners.toArray();
      Object[] var2 = this.listeners.toArray();

      for(int var3 = 0; var3 < var1.length; ++var3) {
         ((DynamicUIManagerListener)var1[var3]).propertiesChanged();
      }

      for(int var4 = 0; var4 < var2.length; ++var4) {
         ((DynamicUIManagerListener)var2[var4]).propertiesChanged();
      }

   }
}
