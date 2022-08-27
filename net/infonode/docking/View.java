package net.infonode.docking;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.HierarchyListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.util.List;
import javax.swing.Icon;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import net.infonode.docking.drag.DockingWindowDragSource;
import net.infonode.docking.drop.InteriorDropInfo;
import net.infonode.docking.internal.ReadContext;
import net.infonode.docking.internal.ViewTitleBar;
import net.infonode.docking.internal.WriteContext;
import net.infonode.docking.internalutil.DropAction;
import net.infonode.docking.model.ViewItem;
import net.infonode.docking.model.ViewWriter;
import net.infonode.docking.properties.ViewProperties;
import net.infonode.docking.properties.ViewTitleBarProperties;
import net.infonode.gui.ComponentUtil;
import net.infonode.gui.panel.SimplePanel;
import net.infonode.properties.base.Property;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapWeakListenerManager;
import net.infonode.properties.util.PropertyChangeListener;
import net.infonode.util.Direction;
import net.infonode.util.StreamUtil;

public class View extends DockingWindow {
   private Component lastFocusedComponent;
   private HierarchyListener focusComponentListener = new View$1(this);
   private SimplePanel contentPanel = new SimplePanel();
   private ViewProperties rootProperties = new ViewProperties();
   private WeakReference lastRootWindow;
   private PropertyChangeListener listener = new View$2(this);
   private PropertyChangeListener titleBarPropertiesListener = new View$3(this);
   private ViewTitleBar titleBar;
   private boolean isfocused = false;
   private List customTitleBarComponents;
   private WindowTab ghostTab;
   private DropAction titleBarDropAction = new View$4(this);

   public View(String var1, Icon var2, Component var3) {
      super(new ViewItem());
      this.rootProperties.setTitle(var1);
      this.rootProperties.setIcon(var2);
      this.getViewProperties().addSuperObject(this.rootProperties);
      super.setComponent(this.contentPanel);
      this.contentPanel.setComponent(var3);
      PropertyMapWeakListenerManager.addWeakPropertyChangeListener(this.getViewProperties().getMap(), ViewProperties.TITLE, this.listener);
      PropertyMapWeakListenerManager.addWeakPropertyChangeListener(this.getViewProperties().getMap(), ViewProperties.ICON, this.listener);
      PropertyMapWeakListenerManager.addWeakPropertyChangeListener(
         this.getViewProperties().getViewTitleBarProperties().getMap(), ViewTitleBarProperties.VISIBLE, this.titleBarPropertiesListener
      );
      PropertyMapWeakListenerManager.addWeakPropertyChangeListener(
         this.getViewProperties().getViewTitleBarProperties().getMap(), ViewTitleBarProperties.CONTENT_TITLE_BAR_GAP, this.titleBarPropertiesListener
      );
      PropertyMapWeakListenerManager.addWeakPropertyChangeListener(
         this.getViewProperties().getViewTitleBarProperties().getMap(), ViewTitleBarProperties.ORIENTATION, this.titleBarPropertiesListener
      );
      this.updateTitleBar(null, null);
      this.init();
   }

   public List getCustomTabComponents() {
      return this.getTab().getCustomTabComponentsList();
   }

   public List getCustomTitleBarComponents() {
      if (this.customTitleBarComponents == null) {
         this.customTitleBarComponents = new View$5(this);
      }

      return this.customTitleBarComponents;
   }

   public Component getComponent() {
      return this.contentPanel.getComponent(0);
   }

   public void setComponent(Component var1) {
      this.contentPanel.setComponent(var1);
   }

   public ViewProperties getViewProperties() {
      return ((ViewItem)this.getWindowItem()).getViewProperties();
   }

   protected void update() {
   }

   public DockingWindow getChildWindow(int var1) {
      return null;
   }

   public int getChildWindowCount() {
      return 0;
   }

   void setLastFocusedComponent(Component var1) {
      if (var1 != this.lastFocusedComponent) {
         if (this.lastFocusedComponent != null) {
            this.lastFocusedComponent.removeHierarchyListener(this.focusComponentListener);
         }

         this.lastFocusedComponent = var1;
         if (this.lastFocusedComponent != null) {
            this.lastFocusedComponent.addHierarchyListener(this.focusComponentListener);
         }
      }

   }

   Component getFocusComponent() {
      this.checkLastFocusedComponent();
      return this.lastFocusedComponent;
   }

   public boolean isFocusCycleRoot() {
      return true;
   }

   public void restoreFocus() {
      this.makeVisible();
      this.checkLastFocusedComponent();
      if (this.lastFocusedComponent == null) {
         ComponentUtil.smartRequestFocus(this.contentPanel);
      } else {
         this.lastFocusedComponent.requestFocusInWindow();
      }

   }

   public Icon getIcon() {
      return this.getViewProperties().getIcon();
   }

   protected void doReplace(DockingWindow var1, DockingWindow var2) {
      throw new RuntimeException(
         (class$net$infonode$docking$View == null ? (class$net$infonode$docking$View = class$("net.infonode.docking.View")) : class$net$infonode$docking$View)
            + ".replaceChildWindow called!"
      );
   }

   protected void doRemoveWindow(DockingWindow var1) {
      throw new RuntimeException(
         (class$net$infonode$docking$View == null ? (class$net$infonode$docking$View = class$("net.infonode.docking.View")) : class$net$infonode$docking$View)
            + ".removeChildWindow called!"
      );
   }

   protected void write(ObjectOutputStream var1, WriteContext var2) throws IOException {
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      ObjectOutputStream var4 = new ObjectOutputStream(var3);

      try {
         var2.getViewSerializer().writeView(this, var4);
         this.getWindowItem().writeSettings(var4, var2);
      } finally {
         var4.close();
      }

      var1.writeInt(var3.size());
      var3.writeTo(var1);
   }

   static View read(ObjectInputStream var0, ReadContext var1) throws IOException {
      int var2 = var0.readInt();
      byte[] var3 = new byte[var2];
      StreamUtil.readAll(var0, var3);
      ObjectInputStream var4 = new ObjectInputStream(new ByteArrayInputStream(var3));
      View var5 = var1.getViewSerializer().readView(var4);
      if (var5 != null) {
         var5.getWindowItem().readSettings(var4, var1);
      }

      return var5;
   }

   protected DropAction doAcceptDrop(Point var1, DockingWindow var2) {
      if (this.getWindowParent() instanceof TabWindow
         && this.titleBar != null
         && this.titleBar.contains(SwingUtilities.convertPoint(this, var1, this.titleBar))) {
         return this.acceptInteriorDrop(var1, var2);
      } else {
         return this.getWindowParent() instanceof TabWindow && this.getWindowParent().getChildWindowCount() == 1 ? null : super.doAcceptDrop(var1, var2);
      }
   }

   protected DropAction acceptInteriorDrop(Point var1, DockingWindow var2) {
      if (this.getWindowParent() instanceof TabWindow && this.titleBar != null && var2.getWindowParent() != this.getWindowParent()) {
         Point var3 = SwingUtilities.convertPoint(this, var1, this.titleBar);
         if (this.titleBar.contains(var3)) {
            if (!this.getInteriorDropFilter().acceptDrop(new InteriorDropInfo(var2, this, var1))) {
               return null;
            }

            this.addGhostTab(var2);
            Object var4 = this.getWindowParent() instanceof TabWindow ? this.getWindowParent() : this;
            this.getRootWindow()
               .setDragRectangle(
                  SwingUtilities.convertRectangle(
                     (Component)var4, new Rectangle(0, 0, ((Component)var4).getWidth(), ((Component)var4).getHeight()), this.getRootWindow()
                  )
               );
            return this.titleBarDropAction;
         }
      }

      return null;
   }

   private void addGhostTab(DockingWindow var1) {
      if (this.ghostTab == null) {
         this.ghostTab = ((AbstractTabWindow)this.getWindowParent()).createGhostTab(var1);
         ((AbstractTabWindow)this.getWindowParent()).getTabbedPanel().addTab(this.ghostTab);
         ((AbstractTabWindow)this.getWindowParent()).getTabbedPanel().scrollTabToVisibleArea(this.ghostTab);
      }

   }

   public String toString() {
      return this.getTitle();
   }

   void setRootWindow(RootWindow var1) {
      if (var1 != null) {
         RootWindow var2 = this.lastRootWindow == null ? null : (RootWindow)this.lastRootWindow.get();
         if (var2 != var1) {
            if (var2 != null) {
               var2.removeView(this);
            }

            this.lastRootWindow = new WeakReference(var1);
            var1.addView(this);
         }
      }
   }

   protected void setFocused(boolean var1) {
      super.setFocused(var1);
      if (this.isfocused != var1) {
         this.isfocused = var1;
         if (var1) {
            this.getViewProperties()
               .getViewTitleBarProperties()
               .getNormalProperties()
               .addSuperObject(this.getViewProperties().getViewTitleBarProperties().getFocusedProperties());
         } else {
            this.getViewProperties()
               .getViewTitleBarProperties()
               .getNormalProperties()
               .removeSuperObject(this.getViewProperties().getViewTitleBarProperties().getFocusedProperties());
         }
      }

   }

   protected void rootChanged(RootWindow var1, RootWindow var2) {
      super.rootChanged(var1, var2);
      this.setRootWindow(var2);
      if (var1 != this.getRootWindow()) {
         if (var1 != null) {
            this.rootProperties.removeSuperObject(var1.getRootWindowProperties().getViewProperties());
         }

         if (this.getRootWindow() != null) {
            this.rootProperties.addSuperObject(this.getRootWindow().getRootWindowProperties().getViewProperties());
         }
      }

   }

   protected PropertyMap getPropertyObject() {
      return this.getViewProperties().getMap();
   }

   protected PropertyMap createPropertyObject() {
      return new ViewProperties().getMap();
   }

   protected boolean needsTitleWindow() {
      return this.getViewProperties().getAlwaysShowTitle();
   }

   private void checkLastFocusedComponent() {
      if (this.lastFocusedComponent != null && !SwingUtilities.isDescendingFrom(this.lastFocusedComponent, this)) {
         this.lastFocusedComponent.removeHierarchyListener(this.focusComponentListener);
         this.lastFocusedComponent = null;
      }

   }

   void removeWindowComponent(DockingWindow var1) {
   }

   void restoreWindowComponent(DockingWindow var1) {
   }

   private void updateTitleBar(Property var1, Object var2) {
      boolean var3 = var2 == null;
      ViewTitleBarProperties var4 = this.getViewProperties().getViewTitleBarProperties();
      if (var3 || var1 == ViewTitleBarProperties.VISIBLE) {
         if (var4.getVisible()) {
            if (this.titleBar == null) {
               this.titleBar = new ViewTitleBar(this);
               new DockingWindowDragSource(this.titleBar, new View$6(this));
               this.titleBar.addMouseListener(new View$7(this));
               if (this.customTitleBarComponents != null) {
                  this.titleBar.updateCustomBarComponents(this.customTitleBarComponents);
               }

               var3 = true;
            }
         } else if (this.titleBar != null) {
            this.remove(this.titleBar);
            this.titleBar.dispose();
            this.titleBar = null;
            var3 = true;
         }
      }

      if ((var3 || var1 == ViewTitleBarProperties.ORIENTATION) && this.titleBar != null) {
         this.remove(this.titleBar);
         this.add(this.titleBar, ComponentUtil.getBorderLayoutOrientation(var4.getOrientation()));
         var3 = true;
      }

      if (var3 || var1 == ViewTitleBarProperties.CONTENT_TITLE_BAR_GAP) {
         if (this.titleBar != null) {
            Direction var5 = var4.getOrientation();
            int var6 = var4.getContentTitleBarGap();
            this.contentPanel
               .setBorder(
                  new EmptyBorder(
                     var5 == Direction.UP ? var6 : 0, var5 == Direction.LEFT ? var6 : 0, var5 == Direction.DOWN ? var6 : 0, var5 == Direction.RIGHT ? var6 : 0
                  )
               );
         } else {
            this.contentPanel.setBorder(null);
         }
      }

   }

   protected void updateButtonVisibility() {
      super.updateButtonVisibility();
      if (this.titleBar != null) {
         this.titleBar.updateViewButtons(null);
      }

   }

   protected void write(ObjectOutputStream var1, WriteContext var2, ViewWriter var3) throws IOException {
      var1.writeInt(1);
      var3.writeView(this, var1, var2);
   }
}
