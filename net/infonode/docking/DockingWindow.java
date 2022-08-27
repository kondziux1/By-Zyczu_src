package net.infonode.docking;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.Icon;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import net.infonode.docking.drag.DockingWindowDragger;
import net.infonode.docking.drop.ChildDropInfo;
import net.infonode.docking.drop.DropFilter;
import net.infonode.docking.drop.SplitDropInfo;
import net.infonode.docking.internal.ReadContext;
import net.infonode.docking.internal.WindowAncestors;
import net.infonode.docking.internal.WriteContext;
import net.infonode.docking.internalutil.DropAction;
import net.infonode.docking.location.LocationDecoder;
import net.infonode.docking.model.TabWindowItem;
import net.infonode.docking.model.ViewWriter;
import net.infonode.docking.model.WindowItem;
import net.infonode.docking.properties.DockingWindowProperties;
import net.infonode.docking.title.DockingWindowTitleProvider;
import net.infonode.docking.title.SimpleDockingWindowTitleProvider;
import net.infonode.docking.util.DockingUtil;
import net.infonode.gui.ComponentUtil;
import net.infonode.gui.EventUtil;
import net.infonode.gui.mouse.MouseButtonListener;
import net.infonode.gui.panel.BasePanel;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapListener;
import net.infonode.properties.propertymap.PropertyMapManager;
import net.infonode.properties.propertymap.PropertyMapTreeListener;
import net.infonode.properties.propertymap.PropertyMapWeakListenerManager;
import net.infonode.util.ArrayUtil;
import net.infonode.util.Direction;

public abstract class DockingWindow extends BasePanel {
   private static int DROP_FLOATING_YOFFSET = 10;
   private DockingWindow windowParent;
   private WindowTab tab;
   private DockingWindow lastFocusedChildWindow;
   private WindowPopupMenuFactory popupMenuFactory;
   private ArrayList mouseButtonListeners;
   private ArrayList listeners;
   private PropertyMapListener propertiesListener = new DockingWindow$1(this);
   private PropertyMapTreeListener propertyObjectTreeListener = new DockingWindow$2(this);
   private static HashSet optimizeWindows = new HashSet();
   private static int optimizeDepth;
   private WindowItem windowItem;
   private WeakReference lastRootWindow = new WeakReference(null);
   private static int updateModelDepth;

   public abstract Icon getIcon();

   public abstract DockingWindow getChildWindow(int var1);

   public abstract int getChildWindowCount();

   protected abstract void doReplace(DockingWindow var1, DockingWindow var2);

   protected abstract void doRemoveWindow(DockingWindow var1);

   protected abstract void update();

   abstract void removeWindowComponent(DockingWindow var1);

   abstract void restoreWindowComponent(DockingWindow var1);

   protected DockingWindow(WindowItem var1) {
      super();
      DockingWindow var2 = var1.getConnectedWindow();
      if (var2 != null) {
         var2.setWindowItem(var1.copy());
      }

      this.windowItem = var1;
      this.windowItem.setConnectedWindow(this);
      this.addMouseListener(new DockingWindow$3(this));
   }

   protected void init() {
      PropertyMapWeakListenerManager.addWeakListener(this.getWindowProperties().getMap(), this.propertiesListener);
      PropertyMapWeakListenerManager.addWeakTreeListener(this.getPropertyObject(), this.propertyObjectTreeListener);
      this.doUpdate();
      this.updateWindowItem(this.getRootWindow());
   }

   private void doUpdate() {
      this.update();
      if (this.tab != null) {
         this.tab.windowTitleChanged();
      }

      if (this.windowParent != null && this.windowParent.getChildWindowCount() == 1) {
         this.windowParent.doUpdate();
      }

   }

   protected void addWindowItem(DockingWindow var1, int var2) {
      boolean var3 = var1.getWindowItem().isRestoreWindow();
      this.windowItem.addWindow(var1.getWindowItem(), var2);
      if (!var3) {
         var1.updateWindowItems();
      }

   }

   protected void updateWindowItem(DockingWindow var1) {
      int var2 = this.getChildWindowIndex(var1);
      int var3 = var2 == 0 ? 0 : this.windowItem.getWindowIndex(this.getChildWindow(var2 - 1).getWindowItem()) + 1;
      this.windowItem.addWindow(var1.getWindowItem(), var3);
   }

   protected final void updateWindowItems() {
      this.windowItem.clearWindows();

      for(int var1 = 0; var1 < this.getChildWindowCount(); ++var1) {
         boolean var2 = this.getChildWindow(var1).windowItem.isRestoreWindow();
         this.windowItem.addWindow(this.getChildWindow(var1).windowItem);
         if (!var2) {
            this.getChildWindow(var1).updateWindowItems();
         }
      }

   }

   public void setPreferredMinimizeDirection(Direction var1) {
      this.windowItem.setLastMinimizedDirection(var1);
   }

   public Direction getPreferredMinimizeDirection() {
      return this.windowItem.getLastMinimizedDirection();
   }

   private ArrayList getMouseButtonListeners() {
      return this.mouseButtonListeners;
   }

   private void setMouseButtonListeners(ArrayList var1) {
      this.mouseButtonListeners = var1;
   }

   private ArrayList getListeners() {
      return this.listeners;
   }

   private void setListeners(ArrayList var1) {
      this.listeners = var1;
   }

   public boolean isUndocked() {
      return this.windowParent != null && this.windowParent.isUndocked();
   }

   public void addTabMouseButtonListener(MouseButtonListener var1) {
      if (this.getMouseButtonListeners() == null) {
         this.setMouseButtonListeners(new ArrayList(2));
      }

      this.getMouseButtonListeners().add(var1);
   }

   public void removeTabMouseButtonListener(MouseButtonListener var1) {
      if (this.getMouseButtonListeners() != null && this.getMouseButtonListeners().remove(var1) && this.getMouseButtonListeners().size() == 0) {
         this.setMouseButtonListeners(null);
      }

   }

   void fireTabWindowMouseButtonEvent(MouseEvent var1) {
      this.fireTabWindowMouseButtonEvent(this, EventUtil.convert(var1, this));
   }

   void fireTabWindowMouseButtonEvent(DockingWindow var1, MouseEvent var2) {
      if (this.getMouseButtonListeners() != null) {
         MouseButtonListener[] var3 = (MouseButtonListener[])this.getMouseButtonListeners()
            .toArray(new MouseButtonListener[this.getMouseButtonListeners().size()]);

         for(int var4 = var3.length - 1; var4 >= 0; --var4) {
            var3[var4].mouseButtonEvent(var2);
         }
      }

      if (this.windowParent != null) {
         this.windowParent.fireTabWindowMouseButtonEvent(var1, var2);
      }

   }

   public void addListener(DockingWindowListener var1) {
      if (this.getListeners() == null) {
         this.setListeners(new ArrayList(2));
      }

      this.getListeners().add(var1);
   }

   public void removeListener(DockingWindowListener var1) {
      if (this.getListeners() != null) {
         this.getListeners().remove(var1);
         if (this.getListeners().size() == 0) {
            this.setListeners(null);
         }
      }

   }

   public DockingWindow getWindowParent() {
      return this.windowParent;
   }

   public SplitWindow split(DockingWindow var1, Direction var2, float var3) {
      SplitWindow var4 = new SplitWindow(var2 == Direction.RIGHT || var2 == Direction.LEFT);
      optimizeAfter(var1.getWindowParent(), new DockingWindow$4(this, var4, var2, var1, var3));
      return var4;
   }

   public DockingWindowDragger startDrag(RootWindow var1) {
      return new WindowDragger(this, var1);
   }

   public DockingWindowProperties getWindowProperties() {
      return this.getWindowItem().getDockingWindowProperties();
   }

   public RootWindow getRootWindow() {
      return this.windowParent == null ? null : this.windowParent.getRootWindow();
   }

   public void restoreWithAbort() throws OperationAbortedException {
      this.fireWindowRestoring(this);
      this.restore();
   }

   public void restore() {
      if (this.isMaximized()) {
         this.doRestoreFromMaximize();
      } else if (this.isMinimized() || this.getRootWindow() == null) {
         ArrayList var1 = new ArrayList();
         this.findViews(var1);
         ArrayList var2 = new ArrayList();

         for(int var3 = 0; var3 < var1.size(); ++var3) {
            var2.add(((DockingWindow)var1.get(var3)).getAncestors());
         }

         this.restoreViews(var1);

         for(int var7 = 0; var7 < var1.size(); ++var7) {
            DockingWindow var4 = (DockingWindow)var1.get(var7);
            var4.doFireWindowRestored(var4);
            DockingWindow[] var5 = (DockingWindow[])var2.get(var7);

            for(int var6 = 0; var6 < var5.length; ++var6) {
               var5[var6].doFireWindowRestored(var4);
            }
         }

         this.restoreFocus();
      }

      this.updateButtonVisibility();
   }

   private DockingWindow doRestoreFromMaximize() {
      Object var1 = null;
      if (this.isUndocked()) {
         FloatingWindow var2 = DockingUtil.getFloatingWindowFor(this);
         if (var2 != null) {
            var2.setMaximizedWindow(null);
            var1 = var2;
         }
      } else {
         RootWindow var3 = this.getRootWindow();
         if (var3 != null) {
            var3.setMaximizedWindow(null);
            var1 = var3;
         }
      }

      return (DockingWindow)var1;
   }

   private ArrayList doRestore() {
      ArrayList var1 = new ArrayList();
      this.findViews(var1);
      this.restoreViews(var1);
      return var1;
   }

   public void close() {
      if (this.windowParent != null) {
         DockingWindow[] var1 = this.getAncestors();
         optimizeAfter(this.windowParent, new DockingWindow$5(this));

         for(int var2 = var1.length - 1; var2 >= 0; --var2) {
            var1[var2].fireWindowClosed(this);
         }
      }

   }

   public void closeWithAbort() throws OperationAbortedException {
      this.fireWindowClosing(this);
      this.close();
   }

   public FloatingWindow undock(Point var1) {
      return this.getRootWindow().createFloatingWindow(this, var1);
   }

   public FloatingWindow undockWithAbort(Point var1) throws OperationAbortedException {
      this.fireWindowUndocking(this);
      return this.undock(var1);
   }

   public void dock() {
      if (this.isUndocked()) {
         ArrayList var1 = this.doRestore();
         this.updateButtonVisibility();
         this.fireWindowDocked(var1);
         if (var1.size() > 0 && ((DockingWindow)var1.get(0)).getRootWindow() != null) {
            FocusManager.focusWindow((DockingWindow)var1.get(0));
         }
      }

   }

   public void dockWithAbort() throws OperationAbortedException {
      if (this.isUndocked()) {
         this.fireWindowDocking(this);
         this.dock();
      }

   }

   public int getChildWindowIndex(DockingWindow var1) {
      for(int var2 = 0; var2 < this.getChildWindowCount(); ++var2) {
         if (this.getChildWindow(var2) == var1) {
            return var2;
         }
      }

      return -1;
   }

   public WindowPopupMenuFactory getPopupMenuFactory() {
      return this.popupMenuFactory;
   }

   public void setPopupMenuFactory(WindowPopupMenuFactory var1) {
      this.popupMenuFactory = var1;
   }

   public boolean isMinimized() {
      return this.windowParent != null && this.windowParent.isMinimized();
   }

   public DockingWindow getLastFocusedChildWindow() {
      return this.lastFocusedChildWindow;
   }

   public final void maximize() {
      if (this.isUndocked()) {
         FloatingWindow var1 = DockingUtil.getFloatingWindowFor(this);
         if (var1 != null) {
            var1.setMaximizedWindow(this);
         }
      } else {
         RootWindow var2 = this.getRootWindow();
         if (var2 != null) {
            var2.setMaximizedWindow(this);
         }
      }

      this.updateButtonVisibility();
   }

   public void maximizeWithAbort() throws OperationAbortedException {
      if (!this.isMaximized()) {
         this.fireWindowMaximizing(this);
         this.maximize();
      }

   }

   public boolean isMaximized() {
      DockingWindow var1;
      if (this.isUndocked()) {
         FloatingWindow var2 = DockingUtil.getFloatingWindowFor(this);
         var1 = var2 != null ? var2.getMaximizedWindow() : null;
      } else {
         RootWindow var3 = this.getRootWindow();
         var1 = var3 != null ? var3.getMaximizedWindow() : null;
      }

      return var1 == this;
   }

   public void minimize() {
      this.getOptimizedWindow().doMinimize();
   }

   public void minimize(Direction var1) {
      this.doMinimize(var1);
   }

   public void minimizeWithAbort() throws OperationAbortedException {
      if (!this.isMinimized() && this.getRootWindow().getClosestWindowBar(this) != null) {
         this.fireWindowMinimizing(this);
         this.minimize();
      }

   }

   public void minimizeWithAbort(Direction var1) throws OperationAbortedException {
      if (!this.isMinimized() && this.getRootWindow().getWindowBar(var1) != null) {
         this.fireWindowMinimizing(this);
         this.minimize(var1);
      }

   }

   private void doMinimize() {
      this.doMinimize(
         this.windowItem.getLastMinimizedDirection() != null && this.getRootWindow().getWindowBar(this.windowItem.getLastMinimizedDirection()).isEnabled()
            ? this.windowItem.getLastMinimizedDirection()
            : this.getRootWindow().getClosestWindowBar(this)
      );
   }

   private void doMinimize(Direction var1) {
      DockingWindow var2 = this.getOptimizedWindow();
      if (var1 != null && !var2.isMinimized()) {
         WindowBar var3 = this.getRootWindow().getWindowBar(var1);
         if (var3 != null) {
            var3.addTab(var2);
            this.updateButtonVisibility();
         }

      }
   }

   public boolean isMinimizable() {
      return this.getOptimizedWindow().getWindowProperties().getMinimizeEnabled()
         && !this.isUndocked()
         && this.getRootWindow() != null
         && this.getRootWindow().windowBarEnabled();
   }

   public boolean isMaximizable() {
      return !this.isMinimized() && this.getOptimizedWindow().getWindowProperties().getMaximizeEnabled();
   }

   public boolean isClosable() {
      return this.getOptimizedWindow().getWindowProperties().getCloseEnabled();
   }

   public boolean isRestorable() {
      return this.getOptimizedWindow().getWindowProperties().getRestoreEnabled();
   }

   public boolean isUndockable() {
      return this.getOptimizedWindow().getWindowProperties().getUndockEnabled();
   }

   public boolean isDockable() {
      return this.getOptimizedWindow().getWindowProperties().getDockEnabled();
   }

   public void replaceChildWindow(DockingWindow var1, DockingWindow var2) {
      if (var1 != var2) {
         DockingWindow var3 = this.internalReplaceChildWindow(var1, var2);
         if (this.getUpdateModel()) {
            boolean var4 = var3.getWindowItem().isRestoreWindow();
            var1.windowItem.replaceWith(var3.getWindowItem());
            if (!var4) {
               var3.updateWindowItems();
            }

            this.cleanUpModel();
         }

      }
   }

   protected DockingWindow internalReplaceChildWindow(DockingWindow var1, DockingWindow var2) {
      WindowAncestors var3 = var2.storeAncestors();
      DockingWindow var4 = var2.getContentWindow(this);
      optimizeAfter(var2, new DockingWindow$6(this, var4, var1, var2, var3));
      return var4;
   }

   public String getTitle() {
      DockingWindowTitleProvider var1 = this.getWindowProperties().getTitleProvider();
      return ((DockingWindowTitleProvider)(var1 == null ? SimpleDockingWindowTitleProvider.INSTANCE : var1)).getTitle(this);
   }

   public String toString() {
      return this.getTitle();
   }

   protected WindowAncestors storeAncestors() {
      return new WindowAncestors(this.getAncestors(), this.isMinimized(), this.isUndocked());
   }

   protected void notifyListeners(WindowAncestors var1) {
      if (this.isMinimized() && !var1.isMinimized()) {
         this.fireWindowMinimized(this, var1.getAncestors());
      }

      if (this.isUndocked() && !var1.isUndocked()) {
         this.fireWindowUndocked(this, var1.getAncestors());
      }

      if (!this.isUndocked() && var1.isUndocked()) {
         this.fireWindowDocked(this, var1.getAncestors());
      }

   }

   protected boolean isShowingInRootWindow() {
      return this.windowParent != null && this.windowParent.isChildShowingInRootWindow(this);
   }

   protected boolean isChildShowingInRootWindow(DockingWindow var1) {
      return this.isShowingInRootWindow();
   }

   public void makeVisible() {
      this.showChildWindow(null);
   }

   public void restoreFocus() {
      if (this.lastFocusedChildWindow != null) {
         this.lastFocusedChildWindow.restoreFocus();
      } else {
         DockingWindow var1 = this.getPreferredFocusChild();
         if (var1 != null) {
            var1.restoreFocus();
         } else {
            ComponentUtil.smartRequestFocus(this);
         }
      }

   }

   protected DockingWindow getPreferredFocusChild() {
      return this.getChildWindowCount() > 0 ? this.getChildWindow(0) : null;
   }

   protected DockingWindow getOptimizedWindow() {
      return this;
   }

   protected DockingWindow getBestFittedWindow(DockingWindow var1) {
      return this;
   }

   protected void internalClose() {
      optimizeAfter(this.windowParent, new DockingWindow$7(this));
   }

   protected void showChildWindow(DockingWindow var1) {
      if (this.windowParent != null && !this.isMaximized()) {
         this.windowParent.showChildWindow(this);
      }

   }

   protected boolean insideTab() {
      return this.windowParent == null ? false : this.windowParent.childInsideTab();
   }

   protected boolean childInsideTab() {
      return this.windowParent == null ? false : this.windowParent.childInsideTab();
   }

   protected DockingWindow[] getAncestors() {
      DockingWindow var1 = this;

      int var2;
      for(var2 = 0; var1 != null; ++var2) {
         var1 = var1.getWindowParent();
      }

      DockingWindow[] var3 = new DockingWindow[var2];

      for(DockingWindow var4 = this; var4 != null; var4 = var4.getWindowParent()) {
         --var2;
         var3[var2] = var4;
      }

      return var3;
   }

   private void fireWindowRemoved(DockingWindow var1, DockingWindow var2) {
      if (this.getListeners() != null) {
         DockingWindowListener[] var3 = (DockingWindowListener[])this.getListeners().toArray(new DockingWindowListener[this.getListeners().size()]);

         for(int var4 = 0; var4 < var3.length; ++var4) {
            var3[var4].windowRemoved(var1, var2);
         }
      }

      if (this.windowParent != null) {
         this.windowParent.fireWindowRemoved(var1, var2);
      }

   }

   protected void fireWindowShown(DockingWindow var1) {
      if (this.getListeners() != null) {
         DockingWindowListener[] var2 = (DockingWindowListener[])this.getListeners().toArray(new DockingWindowListener[this.getListeners().size()]);

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var2[var3].windowShown(var1);
         }
      }

      if (this.windowParent != null) {
         this.windowParent.fireWindowShown(var1);
      }

   }

   protected void fireViewFocusChanged(View var1, View var2) {
      if (this.getListeners() != null) {
         DockingWindowListener[] var3 = (DockingWindowListener[])this.getListeners().toArray(new DockingWindowListener[this.getListeners().size()]);

         for(int var4 = 0; var4 < var3.length; ++var4) {
            var3[var4].viewFocusChanged(var1, var2);
         }
      }

   }

   protected void fireWindowHidden(DockingWindow var1) {
      if (this.getListeners() != null) {
         DockingWindowListener[] var2 = (DockingWindowListener[])this.getListeners().toArray(new DockingWindowListener[this.getListeners().size()]);

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var2[var3].windowHidden(var1);
         }
      }

      if (this.windowParent != null) {
         this.windowParent.fireWindowHidden(var1);
      }

   }

   private void fireWindowAdded(DockingWindow var1, DockingWindow var2) {
      if (this.getListeners() != null) {
         DockingWindowListener[] var3 = (DockingWindowListener[])this.getListeners().toArray(new DockingWindowListener[this.getListeners().size()]);

         for(int var4 = 0; var4 < var3.length; ++var4) {
            var3[var4].windowAdded(var1, var2);
         }
      }

      if (this.windowParent != null) {
         this.windowParent.fireWindowAdded(var1, var2);
      }

   }

   private void fireWindowClosing(DockingWindow var1) throws OperationAbortedException {
      if (this.getListeners() != null) {
         DockingWindowListener[] var2 = (DockingWindowListener[])this.getListeners().toArray(new DockingWindowListener[this.getListeners().size()]);

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var2[var3].windowClosing(var1);
         }
      }

      if (this.windowParent != null) {
         this.windowParent.fireWindowClosing(var1);
      }

   }

   private void fireWindowClosed(DockingWindow var1) {
      if (this.getListeners() != null) {
         DockingWindowListener[] var2 = (DockingWindowListener[])this.getListeners().toArray(new DockingWindowListener[this.getListeners().size()]);

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var2[var3].windowClosed(var1);
         }
      }

   }

   void fireWindowUndocking(DockingWindow var1) throws OperationAbortedException {
      if (this.getListeners() != null) {
         DockingWindowListener[] var2 = (DockingWindowListener[])this.getListeners().toArray(new DockingWindowListener[this.getListeners().size()]);

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var2[var3].windowUndocking(var1);
         }
      }

      if (this.windowParent != null) {
         this.windowParent.fireWindowUndocking(var1);
      }

   }

   void fireWindowUndocked(DockingWindow var1, DockingWindow[] var2) {
      this.doFireWindowUndocked(var1);

      for(int var3 = var2.length - 1; var3 >= 0; --var3) {
         var2[var3].doFireWindowUndocked(this);
      }

   }

   private void doFireWindowUndocked(DockingWindow var1) {
      if (this.getListeners() != null) {
         DockingWindowListener[] var2 = (DockingWindowListener[])this.getListeners().toArray(new DockingWindowListener[this.getListeners().size()]);

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var2[var3].windowUndocked(var1);
         }
      }

   }

   void fireWindowMinimizing(DockingWindow var1) throws OperationAbortedException {
      if (this.getListeners() != null) {
         DockingWindowListener[] var2 = (DockingWindowListener[])this.getListeners().toArray(new DockingWindowListener[this.getListeners().size()]);

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var2[var3].windowMinimizing(var1);
         }
      }

      if (this.windowParent != null) {
         this.windowParent.fireWindowMinimizing(var1);
      }

   }

   void fireWindowMaximizing(DockingWindow var1) throws OperationAbortedException {
      if (this.getListeners() != null) {
         DockingWindowListener[] var2 = (DockingWindowListener[])this.getListeners().toArray(new DockingWindowListener[this.getListeners().size()]);

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var2[var3].windowMaximizing(var1);
         }
      }

      if (this.windowParent != null) {
         this.windowParent.fireWindowMaximizing(var1);
      }

   }

   void fireWindowRestoring(DockingWindow var1) throws OperationAbortedException {
      if (this.getListeners() != null) {
         DockingWindowListener[] var2 = (DockingWindowListener[])this.getListeners().toArray(new DockingWindowListener[this.getListeners().size()]);

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var2[var3].windowRestoring(var1);
         }
      }

      if (this.windowParent != null) {
         this.windowParent.fireWindowRestoring(var1);
      }

   }

   void fireWindowDocking(DockingWindow var1) throws OperationAbortedException {
      if (this.getListeners() != null) {
         DockingWindowListener[] var2 = (DockingWindowListener[])this.getListeners().toArray(new DockingWindowListener[this.getListeners().size()]);

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var2[var3].windowDocking(var1);
         }
      }

      if (this.windowParent != null) {
         this.windowParent.fireWindowDocking(var1);
      }

   }

   void fireWindowDocked(DockingWindow var1, DockingWindow[] var2) {
      this.doFireWindowDocked(var1);

      for(int var3 = var2.length - 1; var3 >= 0; --var3) {
         var2[var3].doFireWindowDocked(this);
      }

   }

   void fireWindowDocked(ArrayList var1) {
      for(int var2 = 0; var2 < var1.size(); ++var2) {
         DockingWindow var3 = (DockingWindow)var1.get(var2);
         var3.doFireWindowDocked(var3);
         DockingWindow[] var4 = var3.getAncestors();

         for(int var5 = 0; var5 < var4.length; ++var5) {
            var4[var5].doFireWindowDocked(var3);
         }
      }

   }

   private void doFireWindowDocked(DockingWindow var1) {
      if (this.getListeners() != null) {
         DockingWindowListener[] var2 = (DockingWindowListener[])this.getListeners().toArray(new DockingWindowListener[this.getListeners().size()]);

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var2[var3].windowDocked(var1);
         }
      }

   }

   private void doFireWindowRestored(DockingWindow var1) {
      if (this.getListeners() != null) {
         DockingWindowListener[] var2 = (DockingWindowListener[])this.getListeners().toArray(new DockingWindowListener[this.getListeners().size()]);

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var2[var3].windowRestored(var1);
         }
      }

   }

   void fireWindowMaximized(DockingWindow var1) {
      if (this.getListeners() != null) {
         DockingWindowListener[] var2 = (DockingWindowListener[])this.getListeners().toArray(new DockingWindowListener[this.getListeners().size()]);

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var2[var3].windowMaximized(var1);
         }
      }

      if (this.windowParent != null) {
         this.windowParent.fireWindowMaximized(var1);
      }

   }

   void fireWindowMinimized(DockingWindow var1, DockingWindow[] var2) {
      this.doFireWindowMinimized(var1);

      for(int var3 = var2.length - 1; var3 >= 0; --var3) {
         var2[var3].doFireWindowMinimized(var1);
      }

   }

   private void doFireWindowMinimized(DockingWindow var1) {
      if (this.getListeners() != null) {
         DockingWindowListener[] var2 = (DockingWindowListener[])this.getListeners().toArray(new DockingWindowListener[this.getListeners().size()]);

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var2[var3].windowMinimized(var1);
         }
      }

   }

   void fireWindowRestored(DockingWindow var1) {
      if (this.getListeners() != null) {
         DockingWindowListener[] var2 = (DockingWindowListener[])this.getListeners().toArray(new DockingWindowListener[this.getListeners().size()]);

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var2[var3].windowRestored(var1);
         }
      }

      if (this.windowParent != null) {
         this.windowParent.fireWindowRestored(var1);
      }

   }

   protected void setLastMinimizedDirection(Direction var1) {
      this.windowItem.setLastMinimizedDirection(var1);
   }

   protected void clearChildrenFocus(DockingWindow var1, View var2) {
      for(int var3 = 0; var3 < this.getChildWindowCount(); ++var3) {
         if (var1 != this.getChildWindow(var3)) {
            this.getChildWindow(var3).clearFocus(var2);
         }
      }

   }

   void childGainedFocus(DockingWindow var1, View var2) {
      if (var1 != null) {
         this.lastFocusedChildWindow = var1;
      }

      this.clearChildrenFocus(var1, var2);
      if (this.windowParent != null) {
         this.windowParent.childGainedFocus(this, var2);
      }

   }

   WindowTab getTab() {
      if (this.tab == null) {
         this.tab = new WindowTab(this, false);
      }

      return this.tab;
   }

   protected void childRemoved(DockingWindow var1) {
      if (this.lastFocusedChildWindow == var1) {
         this.lastFocusedChildWindow = null;
      }

   }

   protected void updateButtonVisibility() {
      if (this.tab != null) {
         this.tab.updateTabButtons(null);
      }

      for(int var1 = 0; var1 < this.getChildWindowCount(); ++var1) {
         this.getChildWindow(var1).updateButtonVisibility();
      }

   }

   protected final void readLocations(ObjectInputStream var1, RootWindow var2, int var3) throws IOException {
      if (var3 < 3) {
         LocationDecoder.decode(var1, var2);
      }

      if (var3 > 1) {
         int var4 = var1.readInt();
         this.lastFocusedChildWindow = var4 == -1 ? null : this.getChildWindow(var4);
      }

      for(int var5 = 0; var5 < this.getChildWindowCount(); ++var5) {
         this.getChildWindow(var5).readLocations(var1, var2, var3);
      }

   }

   protected void writeLocations(ObjectOutputStream var1) throws IOException {
      var1.writeInt(this.lastFocusedChildWindow == null ? -1 : this.getChildWindowIndex(this.lastFocusedChildWindow));

      for(int var2 = 0; var2 < this.getChildWindowCount(); ++var2) {
         this.getChildWindow(var2).writeLocations(var1);
      }

   }

   protected static void beginOptimize(DockingWindow var0) {
      ++optimizeDepth;
      if (var0 != null) {
         optimizeWindows.add(var0);
      }

      PropertyMapManager.getInstance().beginBatch();
   }

   protected static void endOptimize() {
      PropertyMapManager.getInstance().endBatch();
      if (--optimizeDepth == 0) {
         while(optimizeWindows.size() > 0) {
            HashSet var0 = optimizeWindows;
            optimizeWindows = new HashSet();

            for(DockingWindow var2 : var0) {
               var2.optimizeWindowLayout();
            }
         }
      }

   }

   protected static void optimizeAfter(DockingWindow var0, Runnable var1) {
      FocusManager.getInstance().pinFocus(new DockingWindow$8(var0, var1));
   }

   protected boolean needsTitleWindow() {
      return false;
   }

   protected boolean showsWindowTitle() {
      return false;
   }

   protected void optimizeWindowLayout() {
   }

   protected DockingWindow getLocationWindow() {
      return this;
   }

   protected void fireTitleChanged() {
      if (this.tab != null) {
         this.tab.windowTitleChanged();
      }

      if (this.windowParent != null) {
         this.windowParent.fireTitleChanged();
      }

   }

   protected DockingWindow getContentWindow(DockingWindow var1) {
      return (DockingWindow)(this.needsTitleWindow() && !var1.showsWindowTitle() ? new TabWindow(this) : this);
   }

   protected final void removeChildWindow(DockingWindow var1) {
      optimizeAfter(var1.getWindowParent(), new DockingWindow$9(this, var1));
   }

   protected final void removeWindow(DockingWindow var1) {
      var1.setWindowParent(null);
      if (this.getUpdateModel()) {
         this.windowItem.removeWindow(this.windowItem.getChildWindowContaining(var1.getWindowItem()));
         this.cleanUpModel();
      }

   }

   protected final void detach() {
      DockingWindow var1 = this.getWindowParent();
      if (var1 != null) {
         var1.removeChildWindow(this);
      }

   }

   protected final DockingWindow addWindow(DockingWindow var1) {
      if (var1 == null) {
         return null;
      } else {
         DockingWindow var2 = var1.getContentWindow(this);
         var2.detach();
         var2.setWindowParent(this);
         this.fireTitleChanged();
         var2.fireWindowAdded(this, var2);
         if (var2.isShowingInRootWindow()) {
            this.fireWindowShown(var2);
         }

         return var2;
      }
   }

   protected void rootChanged(RootWindow var1, RootWindow var2) {
      if (var2 != null) {
         this.lastRootWindow = new WeakReference(var2);
      }

      for(int var3 = 0; var3 < this.getChildWindowCount(); ++var3) {
         if (this.getChildWindow(var3) != null) {
            this.getChildWindow(var3).rootChanged(var1, var2);
         }
      }

      this.updateWindowItem(var2);
   }

   protected void clearFocus(View var1) {
      for(int var2 = 0; var2 < this.getChildWindowCount(); ++var2) {
         this.getChildWindow(var2).clearFocus(var1);
      }

   }

   private void setWindowParent(DockingWindow var1) {
      if (var1 != this.windowParent) {
         RootWindow var2 = this.getRootWindow();
         if (this.windowParent != null) {
            if (this.isMaximized()) {
               if (this.isUndocked()) {
                  DockingUtil.getFloatingWindowFor(this).setMaximizedWindow(null);
               } else {
                  this.getRootWindow().setMaximizedWindow(null);
               }
            }

            this.windowParent.childRemoved(this);
            this.clearFocus(null);
            if (this.tab != null) {
               this.tab.setContentComponent(this);
            }
         }

         this.windowParent = var1;
         RootWindow var3 = this.getRootWindow();
         if (var2 != var3) {
            this.rootChanged(var2, var3);
         }

      }
   }

   private Direction getSplitDirection(Point var1) {
      double[] var2 = new double[]{
         var1.getX() / (double)this.getWidth(),
         ((double)this.getWidth() - var1.getX()) / (double)this.getWidth(),
         var1.getY() / (double)this.getHeight(),
         ((double)this.getHeight() - var1.getY()) / (double)this.getHeight()
      };
      int var3 = ArrayUtil.findSmallest(var2);
      return var3 == 0 ? Direction.LEFT : (var3 == 1 ? Direction.RIGHT : (var3 == 2 ? Direction.UP : Direction.DOWN));
   }

   private int getEdgeDistance(Point var1, Direction var2) {
      return var2 == Direction.RIGHT
         ? this.getWidth() - var1.x
         : (var2 == Direction.DOWN ? this.getHeight() - var1.y : (var2 == Direction.LEFT ? var1.x : var1.y));
   }

   DropAction acceptDrop(Point var1, DockingWindow var2) {
      DropAction var3 = null;
      FloatingWindow var4 = DockingUtil.getFloatingWindowFor(var2);
      FloatingWindow var5 = DockingUtil.getFloatingWindowFor(this);
      if (this.getRootWindow() != var2.getRootWindow()
         || (var2.getWindowProperties().getDockEnabled() || var4 == null || var5 != null) && (var2.getWindowProperties().getUndockEnabled() || var4 == var5)) {
         var3 = this.isShowing()
               && this.contains(var1)
               && !this.hasParent(var2)
               && (this.getRootWindow().getRootWindowProperties().getRecursiveTabsEnabled() || !this.insideTab())
            ? this.doAcceptDrop(var1, var2)
            : null;
      }

      return var3;
   }

   DropAction getDefaultDropAction() {
      return new DockingWindow$10(this);
   }

   protected boolean acceptsSplitWith(DockingWindow var1) {
      return var1 != this;
   }

   protected DropAction doAcceptDrop(Point var1, DockingWindow var2) {
      DropAction var3 = this.acceptSplitDrop(var1, var2, this.getRootWindow().getRootWindowProperties().getEdgeSplitDistance());
      if (var3 != null) {
         return var3;
      } else {
         var3 = this.acceptChildDrop(var1, var2);
         if (var3 != null) {
            return var3;
         } else {
            var3 = this.acceptInteriorDrop(var1, var2);
            return var3 != null ? var3 : this.acceptSplitDrop(var1, var2, -1);
         }
      }
   }

   protected DropAction acceptSplitDrop(Point var1, DockingWindow var2, int var3) {
      if (!this.acceptsSplitWith(var2)) {
         return null;
      } else {
         Direction var4 = this.getSplitDirection(var1);
         int var5 = this.getEdgeDistance(var1, var4);
         if (var3 != -1 && var5 > var3 * this.getEdgeDepth(var4)) {
            return null;
         } else {
            return this.getSplitDropFilter().acceptDrop(new SplitDropInfo(var2, this, var1, var4)) ? this.split(var2, var4) : null;
         }
      }
   }

   protected DropAction split(DockingWindow var1, Direction var2) {
      int var3 = var2 != Direction.LEFT && var2 != Direction.RIGHT ? this.getWidth() : this.getWidth() / 3;
      int var4 = var2 != Direction.DOWN && var2 != Direction.UP ? this.getHeight() : this.getHeight() / 3;
      int var5 = var2 == Direction.RIGHT ? this.getWidth() - var3 : 0;
      int var6 = var2 == Direction.DOWN ? this.getHeight() - var4 : 0;
      Rectangle var7 = new Rectangle(var5, var6, var3, var4);
      this.getRootWindow().setDragRectangle(SwingUtilities.convertRectangle(this, var7, this.getRootWindow()));
      return new DockingWindow$11(this, var2);
   }

   protected void beforeDrop(DockingWindow var1) throws OperationAbortedException {
      if (!this.isMinimized() && var1.isMinimized()) {
         this.fireWindowMinimizing(this);
      }

      if (!this.isUndocked() && var1.isUndocked()) {
         this.fireWindowUndocking(this);
      }

   }

   protected DropAction createTabWindow(DockingWindow var1) {
      this.getRootWindow().setDragRectangle(SwingUtilities.convertRectangle(this.getParent(), this.getBounds(), this.getRootWindow()));
      return new DockingWindow$12(this);
   }

   protected DropAction acceptInteriorDrop(Point var1, DockingWindow var2) {
      return null;
   }

   protected boolean hasParent(DockingWindow var1) {
      return var1 == this || this.getWindowParent() != null && this.getWindowParent().hasParent(var1);
   }

   protected DockingWindow oldRead(ObjectInputStream var1, ReadContext var2) throws IOException {
      this.windowItem.readSettings(var1, var2);
      return this;
   }

   protected abstract PropertyMap getPropertyObject();

   protected abstract PropertyMap createPropertyObject();

   void showPopupMenu(MouseEvent var1) {
      if (!var1.isConsumed()) {
         DockingWindow var2 = this;

         while(var2.getPopupMenuFactory() == null) {
            var2 = var2.getWindowParent();
            if (var2 == null) {
               return;
            }
         }

         JPopupMenu var3 = var2.getPopupMenuFactory().createPopupMenu(this);
         if (var3 != null && var3.getComponentCount() > 0) {
            var3.show(var1.getComponent(), var1.getX(), var1.getY());
         }

      }
   }

   protected void setFocused(boolean var1) {
      if (this.tab != null) {
         this.tab.setFocused(var1);
      }

   }

   protected int getEdgeDepth(Direction var1) {
      return 1 + (this.windowParent == null ? 0 : this.windowParent.getChildEdgeDepth(this, var1));
   }

   protected int getChildEdgeDepth(DockingWindow var1, Direction var2) {
      return this.windowParent == null ? 0 : this.windowParent.getChildEdgeDepth(this, var2);
   }

   protected DropAction acceptChildDrop(Point var1, DockingWindow var2) {
      for(int var3 = 0; var3 < this.getChildWindowCount(); ++var3) {
         DockingWindow var4 = this.getChildWindow(var3);
         Point var5 = SwingUtilities.convertPoint(this, var1, var4);
         if (this.getChildDropFilter().acceptDrop(new ChildDropInfo(var2, this, var1, var4))) {
            DropAction var6 = var4.acceptDrop(var5, var2);
            if (var6 != null) {
               return var6;
            }
         }
      }

      return null;
   }

   protected WindowItem getWindowItem() {
      return this.windowItem;
   }

   protected boolean getUpdateModel() {
      return updateModelDepth == 0 && this.windowItem.isRestoreWindow();
   }

   private void findViews(ArrayList var1) {
      if (this instanceof View) {
         var1.add(this);
      }

      for(int var2 = 0; var2 < this.getChildWindowCount(); ++var2) {
         this.getChildWindow(var2).findViews(var1);
      }

   }

   private void restoreViews(ArrayList var1) {
      for(int var2 = 0; var2 < var1.size(); ++var2) {
         ((DockingWindow)var1.get(var2)).restoreItem();
      }

   }

   protected static void beginUpdateModel() {
      ++updateModelDepth;
   }

   protected static void endUpdateModel() {
      --updateModelDepth;
   }

   private void restoreItem() {
      beginUpdateModel();

      try {
         if (this.windowItem != null) {
            for(WindowItem var1 = this.windowItem; var1.getParent() != null; var1 = var1.getParent()) {
               DockingWindow var2 = var1.getParent().getConnectedWindow();
               if (var2 != null && var2.getRootWindow() != null && !var2.isMinimized() && !var2.isUndocked()) {
                  if (var2 instanceof TabWindow) {
                     insertTab((TabWindow)var2, this);
                  } else if (var2 instanceof RootWindow) {
                     DockingWindow var11 = this.getContainer(var1.getParent(), this.windowItem);
                     ((RootWindow)var2).setWindow(var11);
                     return;
                  }

                  return;
               }

               DockingWindow var3 = null;

               for(int var4 = 0; var4 < var1.getParent().getWindowCount(); ++var4) {
                  WindowItem var5 = var1.getParent().getWindow(var4);
                  if (var5 != var1) {
                     var3 = var5.getVisibleDockingWindow();
                     if (var3 != null) {
                        break;
                     }
                  }
               }

               if (var3 != null) {
                  optimizeAfter(var3.getWindowParent(), new DockingWindow$14(this, var1, var3));
                  return;
               }
            }
         }

         RootWindow var9 = (RootWindow)this.lastRootWindow.get();
         if (var9 != null) {
            WindowItem var10 = this.getWindowItem().getTopItem();
            optimizeAfter(null, new DockingWindow$15(this, var9, var10));
         }

      } finally {
         endUpdateModel();
      }
   }

   private static void insertTab(TabWindow var0, DockingWindow var1) {
      int var2 = 0;
      WindowItem var3 = var0.getWindowItem();
      WindowItem var4 = var3.getChildWindowContaining(var1.getWindowItem());

      for(int var5 = 0; var5 < var3.getWindowCount(); ++var5) {
         WindowItem var6 = var3.getWindow(var5);
         if (var6 == var4) {
            break;
         }

         DockingWindow var7 = var6.getVisibleDockingWindow();
         if (var7 != null) {
            ++var2;
         }
      }

      var0.addTabNoSelect(var1, var2);
      var0.updateSelectedTab();
   }

   private DockingWindow getContainer(WindowItem var1, WindowItem var2) {
      if (!this.needsTitleWindow()) {
         return this;
      } else {
         while(var2 != var1) {
            if (var2 instanceof TabWindowItem) {
               TabWindow var3 = new TabWindow(null, (TabWindowItem)var2);
               var3.addTabNoSelect(this, 0);
               return var3;
            }

            var2 = var2.getParent();
         }

         TabWindow var4 = new TabWindow();
         var4.addTabNoSelect(this, 0);
         var2.replaceWith(var4.getWindowItem());
         var4.getWindowItem().addWindow(var2);
         return var4;
      }
   }

   private void setWindowItem(WindowItem var1) {
      this.windowItem = var1;
      var1.setConnectedWindow(this);
      this.updateWindowItem(this.getRootWindow());
   }

   protected void updateWindowItem(RootWindow var1) {
      this.windowItem
         .setParentDockingWindowProperties(var1 == null ? WindowItem.emptyProperties : var1.getRootWindowProperties().getDockingWindowProperties());
   }

   protected void afterWindowRemoved(DockingWindow var1) {
   }

   protected void write(ObjectOutputStream var1, WriteContext var2, ViewWriter var3) throws IOException {
   }

   protected void cleanUpModel() {
      if (this.windowParent != null) {
         this.windowParent.cleanUpModel();
      }

   }

   DropFilter getSplitDropFilter() {
      return this.getWindowProperties().getDropFilterProperties().getSplitDropFilter();
   }

   DropFilter getChildDropFilter() {
      return this.getWindowProperties().getDropFilterProperties().getChildDropFilter();
   }

   DropFilter getInteriorDropFilter() {
      return this.getWindowProperties().getDropFilterProperties().getInteriorDropFilter();
   }

   DropFilter getInsertTabDropFilter() {
      return this.getWindowProperties().getDropFilterProperties().getInsertTabDropFilter();
   }
}
