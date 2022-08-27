package net.infonode.docking.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.internal.ReadContext;
import net.infonode.docking.internal.WriteContext;
import net.infonode.docking.properties.DockingWindowProperties;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapUtil;
import net.infonode.util.Direction;

public abstract class WindowItem {
   public static final DockingWindowProperties emptyProperties = new DockingWindowProperties();
   private WindowItem parent;
   private WeakReference connectedWindow = new WeakReference(null);
   private final ArrayList windows = new ArrayList();
   private DockingWindowProperties dockingWindowProperties;
   private DockingWindowProperties parentProperties = emptyProperties;
   private Direction lastMinimizedDirection;

   protected abstract DockingWindow createWindow(ViewReader var1, ArrayList var2);

   public abstract WindowItem copy();

   protected WindowItem() {
      super();
      this.dockingWindowProperties = new DockingWindowProperties(emptyProperties);
   }

   protected WindowItem(WindowItem var1) {
      super();
      this.dockingWindowProperties = new DockingWindowProperties(var1.getDockingWindowProperties().getMap().copy(true, true));
      this.dockingWindowProperties.getMap().replaceSuperMap(var1.getParentDockingWindowProperties().getMap(), emptyProperties.getMap());
      this.lastMinimizedDirection = var1.getLastMinimizedDirection();
   }

   public boolean isRestoreWindow() {
      return this.parent != null && this.parent.isRestoreWindow();
   }

   public void addWindow(WindowItem var1) {
      if (var1.parent != this) {
         this.addWindow(var1, this.windows.size());
      }

   }

   public void addWindow(WindowItem var1, int var2) {
      var2 = var2 == -1 ? this.windows.size() : var2;
      if (var1.parent == this) {
         int var3 = this.windows.indexOf(var1);
         if (var3 != var2) {
            this.windows.remove(var3);
            this.windows.add(var3 < var2 ? var2 - 1 : var2, var1);
         }
      } else {
         var1.setParent(this);
         this.windows.add(var2, var1);
      }

   }

   public void removeWindow(WindowItem var1) {
      if (this.windows.remove(var1)) {
         var1.parent = null;
      }

   }

   public void removeWindowRefs(DockingWindow var1) {
      if (this.connectedWindow.get() == var1) {
         this.connectedWindow = new WeakReference(null);
      }

      for(int var2 = 0; var2 < this.getWindowCount(); ++var2) {
         this.getWindow(var2).removeWindowRefs(var1);
      }

   }

   public void replaceWith(WindowItem var1) {
      if (var1 != this && this.parent != null) {
         var1.setParent(this.parent);
         int var2 = this.parent.windows.indexOf(this);
         this.parent.windows.set(var2, var1);
         this.parent = null;
      }
   }

   public int getWindowIndex(WindowItem var1) {
      return this.windows.indexOf(var1);
   }

   private void setParent(WindowItem var1) {
      if (this.parent != var1) {
         if (this.parent != null) {
            this.parent.removeWindow(this);
         }

         this.parent = var1;
      }
   }

   public final int getWindowCount() {
      return this.windows.size();
   }

   public final WindowItem getWindow(int var1) {
      return (WindowItem)this.windows.get(var1);
   }

   public WindowItem getParent() {
      return this.parent;
   }

   public void setConnectedWindow(DockingWindow var1) {
      this.connectedWindow = new WeakReference(var1);
   }

   public DockingWindow getConnectedWindow() {
      return (DockingWindow)this.connectedWindow.get();
   }

   public RootWindowItem getRootItem() {
      return this.parent == null ? null : this.parent.getRootItem();
   }

   public DockingWindow getVisibleDockingWindow() {
      DockingWindow var1 = this.getConnectedWindow();
      if (var1 != null && var1.getRootWindow() != null && !var1.isMinimized() && !var1.isUndocked()) {
         return var1;
      } else {
         for(int var2 = 0; var2 < this.getWindowCount(); ++var2) {
            WindowItem var3 = this.getWindow(var2);
            var1 = var3.getVisibleDockingWindow();
            if (var1 != null) {
               return var1;
            }
         }

         return null;
      }
   }

   public DockingWindow getInsideDockingWindow() {
      if (this.getParent() == null) {
         return null;
      } else {
         DockingWindow var1 = this.getParent().getConnectedWindow();
         return var1 != null ? var1 : this.getParent().getInsideDockingWindow();
      }
   }

   public void removeAll() {
      while(this.getWindowCount() > 0) {
         this.removeWindow(this.getWindow(0));
      }

   }

   public boolean cleanUp() {
      for(int var1 = this.getWindowCount() - 1; var1 >= 0; --var1) {
         if (this.getWindow(var1).cleanUp()) {
            this.windows.remove(var1);
         }
      }

      return this.getWindowCount() == 0 && this.getConnectedWindow() == null;
   }

   public DockingWindow getFirstChildWindow() {
      for(int var1 = 0; var1 < this.getWindowCount(); ++var1) {
         DockingWindow var2 = this.getWindow(var1).getFirstWindow();
         if (var2 != null) {
            return var2;
         }
      }

      return null;
   }

   public DockingWindow getFirstWindow() {
      DockingWindow var1 = this.getConnectedWindow();
      return var1 != null ? var1 : this.getFirstChildWindow();
   }

   public WindowItem getChildWindowContaining(WindowItem var1) {
      while(var1.getParent() != this) {
         var1 = var1.getParent();
         if (var1 == null) {
            return null;
         }
      }

      return var1;
   }

   public boolean hasAncestor(WindowItem var1) {
      return this == var1 || this.parent != null && this.parent.hasAncestor(var1);
   }

   public WindowItem getTopItem() {
      return this.parent == null ? this : this.parent.getTopItem();
   }

   public DockingWindowProperties getDockingWindowProperties() {
      if (this.dockingWindowProperties == null) {
         this.dockingWindowProperties = new DockingWindowProperties(emptyProperties);
         this.parentProperties = emptyProperties;
      }

      return this.dockingWindowProperties;
   }

   public DockingWindowProperties getParentDockingWindowProperties() {
      return this.parentProperties == null ? emptyProperties : this.parentProperties;
   }

   public void setParentDockingWindowProperties(DockingWindowProperties var1) {
      this.dockingWindowProperties.getMap().replaceSuperMap(this.parentProperties.getMap(), var1.getMap());
      this.parentProperties = var1;
   }

   public Direction getLastMinimizedDirection() {
      return this.lastMinimizedDirection;
   }

   public void setLastMinimizedDirection(Direction var1) {
      this.lastMinimizedDirection = var1;
   }

   public void writeSettings(ObjectOutputStream var1, WriteContext var2) throws IOException {
      var1.writeInt(this.getLastMinimizedDirection() == null ? -1 : this.getLastMinimizedDirection().getValue());
      if (var2.getWritePropertiesEnabled()) {
         this.dockingWindowProperties.getMap().write(var1, true);
         this.getPropertyObject().write(var1, true);
      }

   }

   public void readSettings(ObjectInputStream var1, ReadContext var2) throws IOException {
      if (var2.getVersion() > 1) {
         int var3 = var1.readInt();
         this.setLastMinimizedDirection(var3 == -1 ? null : Direction.getDirections()[var3]);
      }

      if (var2.isPropertyValuesAvailable()) {
         if (var2.getReadPropertiesEnabled()) {
            this.dockingWindowProperties.getMap().read(var1);
            this.getPropertyObject().read(var1);
         } else {
            PropertyMapUtil.skipMap(var1);
            PropertyMapUtil.skipMap(var1);
         }
      }

   }

   public void write(ObjectOutputStream var1, WriteContext var2, ViewWriter var3) throws IOException {
      var1.writeInt(this.getWindowCount());

      for(int var4 = 0; var4 < this.getWindowCount(); ++var4) {
         this.getWindow(var4).write(var1, var2, var3);
      }

      DockingWindow var5 = this.getConnectedWindow();
      this.writeSettings(var1, var2);
      var1.writeBoolean(var5 != null && !var5.isMinimized() && !var5.isUndocked() && var5.getRootWindow() != null);
   }

   public DockingWindow read(ObjectInputStream var1, ReadContext var2, ViewReader var3) throws IOException {
      ArrayList var4 = this.readChildWindows(var1, var2, var3);
      this.readSettings(var1, var2);
      return var1.readBoolean() ? this.createWindow(var3, var4) : (var4.size() > 0 ? (DockingWindow)var4.get(0) : null);
   }

   public ArrayList readChildWindows(ObjectInputStream var1, ReadContext var2, ViewReader var3) throws IOException {
      int var4 = var1.readInt();
      this.removeAll();
      ArrayList var5 = new ArrayList();

      for(int var6 = 0; var6 < var4; ++var6) {
         WindowItem var7 = WindowItemDecoder.decodeWindowItem(var1, var2, var3);
         this.addWindow(var7);
         DockingWindow var8 = var7.read(var1, var2, var3);
         if (var8 != null) {
            var5.add(var8);
         }
      }

      return var5;
   }

   protected PropertyMap getPropertyObject() {
      return null;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      DockingWindow var2 = this.getConnectedWindow();
      var1.append(var2 + ":\n");

      for(int var3 = 0; var3 < this.windows.size(); ++var3) {
         var1.append("  " + this.windows.get(var3).toString());
      }

      return var1.toString();
   }

   public void clearWindows() {
      this.removeAll();
   }
}
