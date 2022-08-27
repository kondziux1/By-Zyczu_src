package net.infonode.docking;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;
import net.infonode.docking.drop.ChildDropInfo;
import net.infonode.docking.drop.InteriorDropInfo;
import net.infonode.docking.internal.ReadContext;
import net.infonode.docking.internal.WindowAncestors;
import net.infonode.docking.internal.WriteContext;
import net.infonode.docking.internalutil.DropAction;
import net.infonode.docking.model.FloatingWindowItem;
import net.infonode.docking.model.ViewReader;
import net.infonode.docking.model.ViewWriter;
import net.infonode.docking.properties.DockingWindowProperties;
import net.infonode.docking.properties.FloatingWindowProperties;
import net.infonode.docking.properties.SplitWindowProperties;
import net.infonode.docking.util.DockingUtil;
import net.infonode.gui.layout.StretchLayout;
import net.infonode.gui.panel.SimplePanel;
import net.infonode.gui.shaped.panel.ShapedPanel;
import net.infonode.properties.gui.InternalPropertiesUtil;
import net.infonode.properties.gui.util.ComponentProperties;
import net.infonode.properties.gui.util.ShapedPanelProperties;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapTreeListener;
import net.infonode.properties.propertymap.PropertyMapWeakListenerManager;
import net.infonode.util.Direction;

public class FloatingWindow extends DockingWindow {
   private DockingWindow window;
   private Window dialog;
   private JPanel dragPanel = new SimplePanel();
   private ShapedPanel shapedPanel;
   private DockingWindow maximizedWindow;
   private Runnable titleUpdater;
   private AWTEventListener awtMouseEventListener;
   private PropertyMapTreeListener propertiesListener = new FloatingWindow$1(this);

   FloatingWindow(RootWindow var1) {
      super(new FloatingWindowItem());
      this.getFloatingWindowProperties().addSuperObject(var1.getRootWindowProperties().getFloatingWindowProperties());
      this.setLayout(new StretchLayout(true, true));
      this.shapedPanel = new ShapedPanel();
      this.setComponent(this.shapedPanel);
      Component var2 = var1.getTopLevelComponent();
      this.dialog = (Window)(this.getFloatingWindowProperties().getUseFrame()
         ? new JFrame()
         : (var2 instanceof Frame ? new JDialog((Frame)var2) : new JDialog((Dialog)var2)));
      ((RootPaneContainer)this.dialog).getContentPane().add(this, "Center");
      if (this.dialog instanceof JDialog) {
         ((JDialog)this.dialog).setDefaultCloseOperation(0);
      } else {
         ((JFrame)this.dialog).setDefaultCloseOperation(0);
      }

      this.dialog.addWindowListener(new FloatingWindow$2(this));
      JRootPane var3 = ((RootPaneContainer)this.dialog).getRootPane();
      var3.getLayeredPane().add(this.dragPanel);
      var3.getLayeredPane().setLayer(this.dragPanel, JLayeredPane.DRAG_LAYER);
      this.dragPanel.setVisible(false);
      this.dragPanel.addMouseListener(new FloatingWindow$3(this));
      if (var1.isHeavyweightSupported()) {
         try {
            this.awtMouseEventListener = new FloatingWindow$4(this);
            Toolkit.getDefaultToolkit().addAWTEventListener(this.awtMouseEventListener, 16L);
         } catch (SecurityException var5) {
            this.awtMouseEventListener = null;
         }
      }

      PropertyMapWeakListenerManager.addWeakTreeListener(this.getFloatingWindowProperties().getMap(), this.propertiesListener);
      this.updateFloatingWindow(null);
   }

   FloatingWindow(RootWindow var1, DockingWindow var2, Point var3, Dimension var4) {
      this(var1);
      this.setWindow(var2);
      this.setInternalSize(var4);
      this.dialog.setLocation(var3.x, var3.y);
   }

   public void setWindow(DockingWindow var1) {
      if (this.window != var1) {
         if (this.window == null) {
            WindowAncestors var2 = var1.storeAncestors();
            DockingWindow var3 = this.addWindow(var1);
            this.doReplace(null, var3);
            var1.notifyListeners(var2);
         } else if (var1 == null) {
            this.removeChildWindow(this.window);
            this.window = null;
         } else {
            this.replaceChildWindow(this.window, var1);
         }

      }
   }

   public DockingWindow getWindow() {
      return this.window;
   }

   public void setMaximizedWindow(DockingWindow var1) {
      if (var1 != this.maximizedWindow) {
         if (!(var1 instanceof FloatingWindow) && (var1 == null || DockingUtil.getFloatingWindowFor(var1) == this)) {
            this.internalSetMaximizedWindow(var1);
         }
      }
   }

   public DockingWindow getMaximizedWindow() {
      return this.maximizedWindow;
   }

   public FloatingWindowProperties getFloatingWindowProperties() {
      return ((FloatingWindowItem)this.getWindowItem()).getFloatingWindowProperties();
   }

   public DockingWindowProperties getWindowProperties() {
      return super.getWindowProperties();
   }

   public void minimize() {
   }

   public void minimize(Direction var1) {
   }

   public boolean isDockable() {
      return false;
   }

   public boolean isMaximizable() {
      return false;
   }

   public boolean isMinimizable() {
      return false;
   }

   public boolean isRestorable() {
      return false;
   }

   public boolean isUndockable() {
      return false;
   }

   public void close() {
      PropertyMapWeakListenerManager.removeWeakTreeListener(this.getFloatingWindowProperties().getMap(), this.propertiesListener);
      RootWindow var1 = this.getRootWindow();
      super.close();
      this.dialog.dispose();
      if (var1 != null) {
         var1.removeFloatingWindow(this);
      }

      try {
         if (this.awtMouseEventListener != null) {
            Toolkit.getDefaultToolkit().removeAWTEventListener(this.awtMouseEventListener);
         }
      } catch (SecurityException var3) {
      }

   }

   public Icon getIcon() {
      return this.window == null ? null : this.window.getIcon();
   }

   public DockingWindow getChildWindow(int var1) {
      return this.window;
   }

   public int getChildWindowCount() {
      return this.window == null ? 0 : 1;
   }

   public boolean isUndocked() {
      return true;
   }

   void startDrag() {
      JRootPane var1 = ((RootPaneContainer)this.dialog).getRootPane();
      this.dragPanel.setBounds(0, 0, var1.getWidth(), var1.getHeight());
      this.dragPanel.setVisible(true);
   }

   void stopDrag() {
      this.dragPanel.setVisible(false);
   }

   JPanel getDragPanel() {
      return this.dragPanel;
   }

   boolean windowContainsPoint(Point var1) {
      return this.getTopLevelAncestor().contains(SwingUtilities.convertPoint(this, var1, this.getTopLevelAncestor()));
   }

   private void internalSetMaximizedWindow(DockingWindow var1) {
      if (var1 != this.maximizedWindow) {
         DockingWindow var2 = null;
         if (this.maximizedWindow != null) {
            DockingWindow var3 = this.maximizedWindow;
            this.maximizedWindow = null;
            if (var3.getWindowParent() != null) {
               var3.getWindowParent().restoreWindowComponent(var3);
            }

            if (var3 != this.window) {
               this.shapedPanel.remove(var3);
            }

            var2 = var3;
            this.fireWindowRestored(var3);
         }

         this.maximizedWindow = var1;
         if (this.maximizedWindow != null) {
            if (this.maximizedWindow.getWindowParent() != null) {
               this.maximizedWindow.getWindowParent().removeWindowComponent(this.maximizedWindow);
            }

            if (this.maximizedWindow != this.window) {
               this.shapedPanel.add(this.maximizedWindow);
               if (this.window != null) {
                  this.window.setVisible(false);
               }
            }

            this.maximizedWindow.setVisible(true);
            var2 = this.maximizedWindow;
            this.fireWindowMaximized(this.maximizedWindow);
         } else if (this.window != null) {
            this.window.setVisible(true);
         }

         if (var2 != null) {
            FocusManager.focusWindow(var2);
         }

      }
   }

   protected void doReplace(DockingWindow var1, DockingWindow var2) {
      if (var1 == this.window) {
         if (this.window != null) {
            this.shapedPanel.remove(this.window);
            this.window.setVisible(true);
         }

         this.window = var2;
         if (this.window != null) {
            if (this.maximizedWindow != null) {
               this.window.setVisible(false);
            }

            this.shapedPanel.add(this.window);
            this.doUpdateTitle();
            this.shapedPanel.revalidate();
         }
      }

      this.updateButtonVisibility();
   }

   protected void doRemoveWindow(DockingWindow var1) {
      if (var1 != null) {
         this.shapedPanel.remove(var1);
         this.window.setVisible(true);
         this.window = null;
         this.shapedPanel.repaint();
      }

   }

   protected void afterWindowRemoved(DockingWindow var1) {
      if (this.getFloatingWindowProperties().getAutoCloseEnabled()) {
         this.close();
      }

   }

   private void doUpdateTitle() {
      if (this.titleUpdater == null) {
         this.titleUpdater = new FloatingWindow$5(this);
         SwingUtilities.invokeLater(this.titleUpdater);
      }

   }

   protected boolean acceptsSplitWith(DockingWindow var1) {
      return false;
   }

   protected DropAction doAcceptDrop(Point var1, DockingWindow var2) {
      DockingWindow var3 = this.maximizedWindow != null ? this.maximizedWindow : this.window;
      if (var3 != null) {
         Point var4 = SwingUtilities.convertPoint(this, var1, var3);
         if (var3.contains(var4)) {
            return this.getChildDropFilter().acceptDrop(new ChildDropInfo(var2, this, var1, var3)) ? var3.acceptDrop(var4, var2) : null;
         }
      }

      return super.doAcceptDrop(var1, var2);
   }

   protected DropAction acceptInteriorDrop(Point var1, DockingWindow var2) {
      if (this.window != null) {
         return null;
      } else {
         this.getRootWindow().setDragRectangle(null);
         return this.getInteriorDropFilter().acceptDrop(new InteriorDropInfo(var2, this, var1)) ? new FloatingWindow$6(this) : null;
      }
   }

   protected void update() {
   }

   void removeWindowComponent(DockingWindow var1) {
   }

   void restoreWindowComponent(DockingWindow var1) {
   }

   protected void showChildWindow(DockingWindow var1) {
      if (this.maximizedWindow != null && var1 == this.window) {
         this.setMaximizedWindow(null);
      }

      super.showChildWindow(var1);
   }

   protected PropertyMap getPropertyObject() {
      return new SplitWindowProperties().getMap();
   }

   protected PropertyMap createPropertyObject() {
      return new SplitWindowProperties().getMap();
   }

   private void updateFloatingWindow(Map var1) {
      FloatingWindowProperties var2 = this.getFloatingWindowProperties();
      ComponentProperties var3 = var1 != null && var1.get(var2.getComponentProperties().getMap()) == null ? null : var2.getComponentProperties();
      ShapedPanelProperties var4 = var1 != null && var1.get(var2.getShapedPanelProperties().getMap()) == null ? null : var2.getShapedPanelProperties();
      if (var3 != null) {
         var3.applyTo(this.shapedPanel);
      }

      if (var4 != null) {
         InternalPropertiesUtil.applyTo(var4, this.shapedPanel);
      }

   }

   protected void fireTitleChanged() {
      super.fireTitleChanged();
      this.doUpdateTitle();
   }

   private void setInternalSize(Dimension var1) {
      ((RootPaneContainer)this.dialog).getRootPane().setPreferredSize(var1);
      this.dialog.pack();
      ((RootPaneContainer)this.dialog).getRootPane().setPreferredSize(null);
   }

   protected DockingWindow read(ObjectInputStream var1, ReadContext var2, ViewReader var3) throws IOException {
      this.dialog.setSize(new Dimension(var1.readInt(), var1.readInt()));
      this.dialog.setLocation(var1.readInt(), var1.readInt());
      this.dialog.setVisible(var1.readBoolean());
      this.getWindowItem().readSettings(var1, var2);
      if (var1.readBoolean()) {
         this.setWindow(WindowDecoder.decodeWindow(var1, var2, var3));
      }

      return this;
   }

   protected void write(ObjectOutputStream var1, WriteContext var2, ViewWriter var3) throws IOException {
      var1.writeInt(this.dialog.getWidth());
      var1.writeInt(this.dialog.getHeight());
      var1.writeInt(this.dialog.getX());
      var1.writeInt(this.dialog.getY());
      var1.writeBoolean(this.dialog.isVisible());
      this.getWindowItem().writeSettings(var1, var2);
      var1.writeBoolean(this.window != null);
      if (this.window != null) {
         this.window.write(var1, var2, var3);
      }

   }
}
