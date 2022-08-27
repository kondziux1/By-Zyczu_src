package net.infonode.docking;

import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import net.infonode.docking.drop.InsertTabDropInfo;
import net.infonode.docking.internal.ReadContext;
import net.infonode.docking.internal.WindowAncestors;
import net.infonode.docking.internal.WriteContext;
import net.infonode.docking.internalutil.DropAction;
import net.infonode.docking.model.AbstractTabWindowItem;
import net.infonode.docking.model.ViewReader;
import net.infonode.docking.model.ViewWriter;
import net.infonode.docking.model.WindowItem;
import net.infonode.docking.properties.TabWindowProperties;
import net.infonode.docking.properties.WindowTabProperties;
import net.infonode.properties.propertymap.PropertyMapManager;
import net.infonode.tabbedpanel.Tab;
import net.infonode.tabbedpanel.TabbedPanel;

public abstract class AbstractTabWindow extends DockingWindow {
   private static int MINIMUM_SIZE = 7;
   private final DropAction dropAction = new AbstractTabWindow$1(this);
   private TabbedPanel tabbedPanel;
   private WindowTab dragTab;
   private int ignoreSelected;
   private int draggedTabIndex;
   private List tabAreaComponents;

   public abstract TabWindowProperties getTabWindowProperties();

   protected AbstractTabWindow(boolean var1, WindowItem var2) {
      super(var2);
      if (var1) {
         AbstractTabWindow$2 var3 = new AbstractTabWindow$2(this);
         this.tabbedPanel = new AbstractTabWindow$3(this, var3, true);
         var3.setTabbedPanel(this.tabbedPanel);
      } else {
         this.tabbedPanel = new AbstractTabWindow$4(this, null);
      }

      this.tabbedPanel.addTabListener(new TabWindowMover(this, this.tabbedPanel));
      this.setComponent(this.tabbedPanel);
      this.getTabbedPanel().addTabListener(new AbstractTabWindow$5(this));
   }

   protected void initMouseListener() {
      this.getTabbedPanel().addMouseListener(new AbstractTabWindow$8(this));
   }

   private Dimension getTabbedPanelMinimumSize(Dimension var1) {
      return this.tabbedPanel.getProperties().getTabAreaOrientation().isHorizontal()
         ? new Dimension(var1.width, MINIMUM_SIZE)
         : new Dimension(MINIMUM_SIZE, var1.height);
   }

   public final List getCustomTabAreaComponents() {
      if (this.tabAreaComponents == null) {
         this.tabAreaComponents = new AbstractTabWindow$9(this);
      }

      return this.tabAreaComponents;
   }

   public DockingWindow getSelectedWindow() {
      WindowTab var1 = (WindowTab)this.tabbedPanel.getSelectedTab();
      return var1 == null ? null : var1.getWindow();
   }

   public void setSelectedTab(int var1) {
      this.beginIgnoreSelected();

      try {
         Tab var2 = var1 == -1 ? null : this.tabbedPanel.getTabAt(var1);
         Tab var3 = this.tabbedPanel.getSelectedTab();
         if (var2 != var3) {
            this.tabbedPanel.setSelectedTab(var2);
            this.fireTitleChanged();
            if (var3 != null) {
               ((WindowTab)var3).getWindow().fireWindowHidden(((WindowTab)var3).getWindow());
            }

            if (var2 != null) {
               ((WindowTab)var2).getWindow().fireWindowShown(((WindowTab)var2).getWindow());
            }
         }
      } finally {
         this.endIgnoreSelected();
      }

   }

   public void addTab(DockingWindow var1) {
      PropertyMapManager.getInstance().beginBatch();

      try {
         this.addTab(var1, this.tabbedPanel.getTabCount());
      } finally {
         PropertyMapManager.getInstance().endBatch();
      }

   }

   public int addTab(DockingWindow var1, int var2) {
      PropertyMapManager.getInstance().beginBatch();

      int var3;
      try {
         var3 = this.addTabNoSelect(var1, var2);
      } finally {
         PropertyMapManager.getInstance().endBatch();
      }

      return var3;
   }

   protected int addTabNoSelect(DockingWindow var1, int var2) {
      WindowAncestors var3 = var1.storeAncestors();
      beginOptimize(var1.getWindowParent());
      this.beginIgnoreSelected();

      int var8;
      try {
         Tab var4 = var2 >= this.tabbedPanel.getTabCount() ? null : this.tabbedPanel.getTabAt(var2);
         DockingWindow var5 = var1.getContentWindow(this);
         var5.detach();
         this.updateTab(var5);
         WindowTab var6 = var5.getTab();
         int var7 = var4 == null ? this.tabbedPanel.getTabCount() : this.tabbedPanel.getTabIndex(var4);
         this.tabbedPanel.insertTab(var6, var7);
         this.addWindow(var5);
         var1.notifyListeners(var3);
         var8 = var7;
      } finally {
         this.endIgnoreSelected();
         endOptimize();
      }

      return var8;
   }

   protected boolean isChildShowingInRootWindow(DockingWindow var1) {
      return super.isChildShowingInRootWindow(var1) && var1 == this.getSelectedWindow();
   }

   protected void showChildWindow(DockingWindow var1) {
      this.setSelectedTab(this.getChildWindowIndex(var1));
      super.showChildWindow(var1);
   }

   protected boolean childInsideTab() {
      return true;
   }

   protected void setTabWindowProperties(TabWindowProperties var1) {
      this.getTabbedPanel().getProperties().addSuperObject(var1.getTabbedPanelProperties());
   }

   protected void clearFocus(View var1) {
      if (this.getSelectedWindow() != null) {
         this.getSelectedWindow().clearFocus(var1);
      }

   }

   protected DockingWindow getPreferredFocusChild() {
      return this.getSelectedWindow() == null ? super.getPreferredFocusChild() : this.getSelectedWindow();
   }

   protected void clearChildrenFocus(DockingWindow var1, View var2) {
      if (this.getSelectedWindow() != var1) {
         this.clearFocus(var2);
      }

   }

   protected int getTabAreaComponentCount() {
      return 0;
   }

   protected void updateTabAreaComponents() {
      int var1 = this.tabAreaComponents == null ? 0 : this.tabAreaComponents.size();
      JComponent[] var2 = new JComponent[var1 + this.getTabAreaComponentCount()];
      if (this.tabAreaComponents != null) {
         this.tabAreaComponents.toArray(var2);
      }

      this.getTabAreaComponents(var1, var2);
      this.getTabbedPanel().setTabAreaComponents(var2);
   }

   protected void getTabAreaComponents(int var1, JComponent[] var2) {
   }

   protected final boolean getIgnoreSelected() {
      return this.ignoreSelected > 0;
   }

   protected void tabSelected(WindowTab var1) {
      if (!this.getIgnoreSelected() && var1 != null) {
         RootWindow var2 = this.getRootWindow();
         if (var2 != null) {
            var1.setFocused(true);
            var2.addFocusedWindow(var1.getWindow());
            FocusManager.focusWindow(var1.getWindow());
         }
      }

      if (!this.getIgnoreSelected()) {
         this.fireTitleChanged();
      }

   }

   protected TabbedPanel getTabbedPanel() {
      return this.tabbedPanel;
   }

   public DockingWindow getChildWindow(int var1) {
      return ((WindowTab)this.tabbedPanel.getTabAt(var1)).getWindow();
   }

   protected DockingWindow getLocationWindow() {
      return (DockingWindow)(this.tabbedPanel.getTabCount() == 1 ? this.getChildWindow(0) : this);
   }

   public int getChildWindowCount() {
      return this.tabbedPanel.getTabCount();
   }

   public Icon getIcon() {
      DockingWindow var1 = this.getSelectedWindow();
      return var1 != null ? var1.getIcon() : (this.getChildWindowCount() > 0 ? this.getChildWindow(0).getIcon() : null);
   }

   private void updateTab(DockingWindow var1) {
      var1.getTab().setProperties(this.getTabProperties(var1));
   }

   private WindowTabProperties getTabProperties(DockingWindow var1) {
      WindowTabProperties var2 = new WindowTabProperties(this.getTabWindowProperties().getTabProperties());
      var2.addSuperObject(var1.getWindowProperties().getTabProperties());
      return var2;
   }

   protected void doReplace(DockingWindow var1, DockingWindow var2) {
      this.beginIgnoreSelected();

      try {
         WindowTab var3 = var1.getTab();
         int var4 = this.tabbedPanel.getTabIndex(var3);
         boolean var5 = var3.isSelected();
         this.tabbedPanel.removeTab(var3);
         this.tabbedPanel.insertTab(var2.getTab(), var4);
         if (var5) {
            this.tabbedPanel.setSelectedTab(var2.getTab());
         }

         this.updateTab(var2);
      } finally {
         this.endIgnoreSelected();
      }

   }

   protected void doRemoveWindow(DockingWindow var1) {
      this.beginIgnoreSelected();

      try {
         WindowTab var2 = var1.getTab();
         var2.unsetProperties();
         this.tabbedPanel.removeTab(var2);
      } finally {
         this.endIgnoreSelected();
      }

   }

   private void beginIgnoreSelected() {
      ++this.ignoreSelected;
   }

   private void endIgnoreSelected() {
      --this.ignoreSelected;
   }

   protected boolean isInsideTabArea(Point var1) {
      return this.tabbedPanel.tabAreaContainsPoint(var1);
   }

   protected DropAction acceptInteriorDrop(Point var1, DockingWindow var2) {
      if (this.getChildWindowCount() == 1 && var2 == this.getChildWindow(0) && this.dragTab == null) {
         return null;
      } else {
         Point var3 = SwingUtilities.convertPoint(this, var1, this.tabbedPanel);
         if ((this.getRootWindow().getRootWindowProperties().getRecursiveTabsEnabled() || var2.getChildWindowCount() <= 1) && this.isInsideTabArea(var3)) {
            this.getRootWindow().setDragRectangle(null);
            if (var2.getWindowParent() == this) {
               this.tabbedPanel.moveTab(var2.getTab(), var3);
            } else {
               if (!this.getInsertTabDropFilter().acceptDrop(new InsertTabDropInfo(var2, this, var1))) {
                  return null;
               }

               if (this.dragTab == null) {
                  this.dragTab = this.createGhostTab(var2);
                  this.tabbedPanel.insertTab(this.dragTab, var3);
               } else {
                  this.tabbedPanel.moveTab(this.dragTab, var3);
               }
            }

            return this.dropAction;
         } else {
            return null;
         }
      }
   }

   WindowTab createGhostTab(DockingWindow var1) {
      WindowTab var2 = new WindowTab(var1, true);
      var2.setProperties(this.getTabProperties(var1));
      return var2;
   }

   private void stopDrag() {
      if (this.dragTab != null) {
         this.tabbedPanel.removeTab(this.dragTab);
         this.dragTab = null;
      }

   }

   protected boolean showsWindowTitle() {
      return true;
   }

   protected DockingWindow oldRead(ObjectInputStream var1, ReadContext var2) throws IOException {
      int var3 = var1.readInt();
      int var4 = var1.readInt();

      while(this.getChildWindowCount() > 0) {
         this.removeChildWindow(this.getChildWindow(0));
      }

      for(int var5 = 0; var5 < var3; ++var5) {
         DockingWindow var6 = WindowDecoder.decodeWindow(var1, var2);
         if (var6 != null) {
            this.addTab(var6);
         } else if (var5 < var4) {
            --var4;
         }
      }

      super.oldRead(var1, var2);
      if (this.tabbedPanel.getTabCount() > 0) {
         if (var4 >= 0) {
            this.setSelectedTab(Math.min(this.tabbedPanel.getTabCount() - 1, var4));
         }

         return this;
      } else {
         return null;
      }
   }

   protected void write(ObjectOutputStream var1, WriteContext var2, ViewWriter var3) throws IOException {
      var1.writeInt(this.getChildWindowCount());

      for(int var4 = 0; var4 < this.getChildWindowCount(); ++var4) {
         this.getChildWindow(var4).write(var1, var2, var3);
      }

   }

   protected DockingWindow newRead(ObjectInputStream var1, ReadContext var2, ViewReader var3) throws IOException {
      int var4 = var1.readInt();

      while(this.getChildWindowCount() > 0) {
         this.removeChildWindow(this.getChildWindow(0));
      }

      for(int var5 = 0; var5 < var4; ++var5) {
         DockingWindow var6 = WindowDecoder.decodeWindow(var1, var2, var3);
         if (var6 != null) {
            this.addTab(var6);
         }
      }

      this.updateSelectedTab();
      return this.getChildWindowCount() > 0 ? this : null;
   }

   protected void updateSelectedTab() {
      WindowItem var1 = ((AbstractTabWindowItem)this.getWindowItem()).getSelectedItem();

      for(int var2 = 0; var2 < this.getChildWindowCount(); ++var2) {
         if (this.getChildWindow(var2).getWindowItem().hasAncestor(var1)) {
            this.setSelectedTab(var2);
            return;
         }
      }

   }

   void setDraggedTabIndex(int var1) {
      this.draggedTabIndex = var1;
   }

   void removeWindowComponent(DockingWindow var1) {
      var1.getTab().setContentComponent(null);
   }

   void restoreWindowComponent(DockingWindow var1) {
      var1.getTab().setContentComponent(var1);
   }
}
