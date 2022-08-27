package net.infonode.docking;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import net.infonode.docking.drop.ChildDropInfo;
import net.infonode.docking.drop.InteriorDropInfo;
import net.infonode.docking.internal.HeavyWeightContainer;
import net.infonode.docking.internal.HeavyWeightDragRectangle;
import net.infonode.docking.internal.ReadContext;
import net.infonode.docking.internal.WriteContext;
import net.infonode.docking.internalutil.DropAction;
import net.infonode.docking.model.RootWindowItem;
import net.infonode.docking.model.WindowItem;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.docking.util.DockingUtil;
import net.infonode.gui.CursorManager;
import net.infonode.gui.DragLabelWindow;
import net.infonode.gui.componentpainter.ComponentPainter;
import net.infonode.gui.componentpainter.RectangleComponentPainter;
import net.infonode.gui.layout.BorderLayout2;
import net.infonode.gui.layout.LayoutUtil;
import net.infonode.gui.layout.StretchLayout;
import net.infonode.gui.panel.SimplePanel;
import net.infonode.gui.shaped.panel.ShapedPanel;
import net.infonode.properties.gui.InternalPropertiesUtil;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapManager;
import net.infonode.util.ArrayUtil;
import net.infonode.util.Direction;
import net.infonode.util.ReadWritable;

public class RootWindow extends DockingWindow implements ReadWritable {
   private static final int SERIALIZE_VERSION = 4;
   private static final int FLOATING_WINDOW_MIN_WIDTH = 400;
   private static final int FLOATING_WINDOW_MIN_HEIGHT = 300;
   private boolean heavyweightSupport = false;
   private final ShapedPanel layeredPane = new RootWindow$1(this);
   private final ShapedPanel windowPanel = new ShapedPanel(new StretchLayout(true, true));
   private final SimplePanel mainPanel = new SimplePanel();
   private JFrame dummyFrame;
   private final ViewSerializer viewSerializer;
   private DockingWindow window;
   private final WindowBar[] windowBars = new WindowBar[Direction.getDirections().length];
   private final ArrayList floatingWindows = new ArrayList();
   private DockingWindow maximizedWindow;
   private View focusedView;
   private ArrayList lastFocusedWindows = new ArrayList(4);
   private ArrayList focusedWindows = new ArrayList(4);
   private final ArrayList views = new ArrayList();
   private boolean cleanUpModel;
   private final Runnable modelCleanUpEvent = new RootWindow$2(this);
   private final JLabel dragTextLabel = new JLabel();
   private Container dragTextContainer;
   private DragLabelWindow dragTextWindow;
   private final Component dragRectangle;
   private JRootPane currentDragRootPane;

   public RootWindow(ViewSerializer var1) {
      this(false, var1);
   }

   public RootWindow(boolean var1, ViewSerializer var2) {
      super(new RootWindowItem());
      this.heavyweightSupport = var1;
      this.dragRectangle = (Component)(var1 ? new HeavyWeightDragRectangle() : new ShapedPanel());
      this.getWindowProperties().addSuperObject(this.getRootWindowProperties().getDockingWindowProperties());
      this.mainPanel.setLayout(new BorderLayout2());
      this.mainPanel.add(this.windowPanel, new Point(1, 1));
      this.createWindowBars();
      this.layeredPane.add(this.mainPanel);
      this.layeredPane.setLayout(new RootWindow.SingleComponentLayout(null));
      this.setComponent(this.layeredPane);
      this.viewSerializer = var2;
      this.dragTextLabel.setOpaque(true);
      if (var1) {
         this.dragTextContainer = new HeavyWeightContainer(this.dragTextLabel, true);
         this.dragTextContainer.validate();
      } else {
         this.dragTextContainer = this.dragTextLabel;
      }

      this.init();
      FocusManager.getInstance();
      this.addTabMouseButtonListener(new RootWindow$3(this));
   }

   public RootWindow(ViewSerializer var1, DockingWindow var2) {
      this(false, var1, var2);
   }

   public RootWindow(boolean var1, ViewSerializer var2, DockingWindow var3) {
      this(var1, var2);
      this.setWindow(var3);
   }

   public View getFocusedView() {
      return this.focusedView;
   }

   void addFocusedWindow(DockingWindow var1) {
      for(int var2 = 0; var2 < this.lastFocusedWindows.size(); ++var2) {
         if (((WeakReference)this.lastFocusedWindows.get(var2)).get() == var1) {
            return;
         }
      }

      this.lastFocusedWindows.add(new WeakReference(var1));
   }

   void setFocusedView(View var1) {
      if (var1 != this.focusedView) {
         View var2 = this.focusedView;
         this.focusedView = var1;

         for(Object var3 = var1; var3 != null; var3 = ((DockingWindow)var3).getWindowParent()) {
            this.focusedWindows.add(new WeakReference(var3));

            for(int var4 = 0; var4 < this.lastFocusedWindows.size(); ++var4) {
               if (((WeakReference)this.lastFocusedWindows.get(var4)).get() == var3) {
                  this.lastFocusedWindows.remove(var4);
                  break;
               }
            }
         }

         for(int var6 = 0; var6 < this.lastFocusedWindows.size(); ++var6) {
            DockingWindow var8 = (DockingWindow)((WeakReference)this.lastFocusedWindows.get(var6)).get();
            if (var8 != null) {
               var8.setFocused(false);
            }
         }

         ArrayList var7 = this.lastFocusedWindows;
         this.lastFocusedWindows = this.focusedWindows;
         this.focusedWindows = var7;

         for(int var9 = 0; var9 < this.lastFocusedWindows.size(); ++var9) {
            DockingWindow var5 = (DockingWindow)((WeakReference)this.lastFocusedWindows.get(var9)).get();
            if (var5 != null) {
               var5.setFocused(true);
            }
         }

         if (var1 != null) {
            var1.childGainedFocus(null, var1);
         } else {
            this.clearFocus(null);
         }

         for(int var10 = 0; var10 < this.focusedWindows.size(); ++var10) {
            DockingWindow var12 = (DockingWindow)((WeakReference)this.focusedWindows.get(var10)).get();
            if (var12 != null) {
               var12.fireViewFocusChanged(var2, this.focusedView);
            }
         }

         for(int var11 = 0; var11 < this.lastFocusedWindows.size(); ++var11) {
            DockingWindow var13 = (DockingWindow)((WeakReference)this.lastFocusedWindows.get(var11)).get();
            if (var13 != null) {
               var13.fireViewFocusChanged(var2, this.focusedView);
            }
         }

         this.focusedWindows.clear();
      }
   }

   public RootWindowProperties getRootWindowProperties() {
      return ((RootWindowItem)this.getWindowItem()).getRootWindowProperties();
   }

   public Direction getClosestWindowBar(DockingWindow var1) {
      Point var2 = SwingUtilities.convertPoint(var1.getParent(), var1.getLocation(), this);
      int[] var3 = new int[]{
         this.getWindowBar(Direction.UP).isEnabled() ? var2.y + var1.getHeight() : Integer.MAX_VALUE,
         this.getWindowBar(Direction.DOWN).isEnabled() ? this.getHeight() - var2.y : Integer.MAX_VALUE,
         this.getWindowBar(Direction.LEFT).isEnabled() ? var2.x + var1.getWidth() : Integer.MAX_VALUE,
         this.getWindowBar(Direction.RIGHT).isEnabled() ? this.getWidth() - var2.x : Integer.MAX_VALUE
      };
      Direction var4 = (new Direction[]{Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT})[ArrayUtil.findSmallest(var3)];
      return this.getWindowBar(var4).isEnabled() ? var4 : null;
   }

   public WindowBar getWindowBar(Direction var1) {
      return this.windowBars[var1.getValue()];
   }

   public void setWindow(DockingWindow var1) {
      if (this.window != var1) {
         if (this.window == null) {
            DockingWindow var2 = this.addWindow(var1);
            this.doReplace(null, var2);
            if (this.getUpdateModel() && var2.getWindowItem().getRootItem() != this.getWindowItem()) {
               this.getWindowItem().removeAll();
               this.addWindowItem(var2, -1);
            }
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

   public FloatingWindow createFloatingWindow(Point var1, Dimension var2, DockingWindow var3) {
      FloatingWindow var4 = new FloatingWindow(this, var3, var1, var2);
      this.floatingWindows.add(var4);
      this.addWindow(var4);
      return var4;
   }

   FloatingWindow createFloatingWindow() {
      FloatingWindow var1 = new FloatingWindow(this);
      this.floatingWindows.add(var1);
      this.addWindow(var1);
      return var1;
   }

   FloatingWindow createFloatingWindow(DockingWindow var1, Point var2) {
      Dimension var3 = var1.getSize();
      if (var3.width <= 0 || var3.height <= 0) {
         Dimension var4 = var1.getPreferredSize();
         var3 = new Dimension(Math.max(400, var4.width), Math.max(300, var4.height));
      }

      FloatingWindow var5 = this.createFloatingWindow(var2, var3, var1);
      var5.getTopLevelAncestor().setVisible(true);
      return var5;
   }

   void removeFloatingWindow(FloatingWindow var1) {
      this.floatingWindows.remove(var1);
      this.removeWindow(var1);
   }

   public ViewSerializer getViewSerializer() {
      return this.viewSerializer;
   }

   public DockingWindow getChildWindow(int var1) {
      return (DockingWindow)(var1 < 4
         ? this.windowBars[var1]
         : (
            this.window != null
               ? (var1 == 4 ? this.window : (DockingWindow)this.floatingWindows.get(var1 - 5))
               : (DockingWindow)this.floatingWindows.get(var1 - 4)
         ));
   }

   public int getChildWindowCount() {
      return 4 + (this.window == null ? 0 : 1) + this.floatingWindows.size();
   }

   public Icon getIcon() {
      return null;
   }

   public void write(ObjectOutputStream var1) throws IOException {
      this.write(var1, true);
   }

   public void write(ObjectOutputStream var1, boolean var2) throws IOException {
      this.cleanUpModel();
      var1.writeInt(4);
      var1.writeBoolean(var2);
      WriteContext var3 = new WriteContext(var2, this.getViewSerializer());
      ArrayList var4 = new ArrayList();

      for(int var5 = 0; var5 < this.views.size(); ++var5) {
         View var6 = (View)((WeakReference)this.views.get(var5)).get();
         if (var6 != null) {
            var4.add(var6);
         }
      }

      writeViews(var4, var1, var3);
      RootWindow$4 var7 = new RootWindow$4(this, var4);
      this.getWindowItem().write(var1, var3, var7);

      for(int var8 = 0; var8 < 4; ++var8) {
         this.windowBars[var8].write(var1, var3, var7);
      }

      var1.writeInt(this.floatingWindows.size());

      for(int var9 = 0; var9 < this.floatingWindows.size(); ++var9) {
         ((FloatingWindow)this.floatingWindows.get(var9)).write(var1, var3, var7);
      }

      this.writeLocations(var1);
      if (this.maximizedWindow != null) {
         this.writeMaximized(this.maximizedWindow, var1);
      }

      var1.writeInt(-1);
   }

   private void writeWindowItemIndex(WindowItem var1, ObjectOutputStream var2) throws IOException {
      if (var1.getParent() != null) {
         this.writeWindowItemIndex(var1.getParent(), var2);
         int var3 = var1.getParent().getWindowIndex(var1);
         var2.writeInt(var3);
      }
   }

   private void writeMaximized(DockingWindow var1, ObjectOutputStream var2) throws IOException {
      DockingWindow var3 = var1.getWindowParent();
      if (var3 != null) {
         this.writeMaximized(var3, var2);
         var2.writeInt(var3.getChildWindowIndex(var1));
      }

   }

   private static void writeViews(ArrayList var0, ObjectOutputStream var1, WriteContext var2) throws IOException {
      var1.writeInt(var0.size());

      for(int var3 = 0; var3 < var0.size(); ++var3) {
         ((View)var0.get(var3)).write(var1, var2);
      }

   }

   public void read(ObjectInputStream var1) throws IOException {
      this.read(var1, true);
   }

   private void oldInternalRead(ObjectInputStream var1, ReadContext var2) throws IOException {
      this.setWindow(var1.readBoolean() ? WindowDecoder.decodeWindow(var1, var2) : null);

      for(int var3 = 0; var3 < 4; ++var3) {
         var1.readInt();
         this.windowBars[var3].oldRead(var1, var2);
      }

      super.oldRead(var1, var2);
      this.readLocations(var1, this, var2.getVersion());
      if (var2.getVersion() > 1) {
         int var6 = var1.readInt();

         for(int var4 = 0; var4 < var6; ++var4) {
            View var5 = (View)WindowDecoder.decodeWindow(var1, var2);
            var5.setRootWindow(this);
            var5.readLocations(var1, this, var2.getVersion());
         }
      }

   }

   private void newInternalRead(ObjectInputStream var1, ReadContext var2) throws IOException {
      beginUpdateModel();

      try {
         int var3 = var1.readInt();
         View[] var4 = new View[var3];

         for(int var5 = 0; var5 < var3; ++var5) {
            var4[var5] = View.read(var1, var2);
            if (var4[var5] != null) {
               var4[var5].setRootWindow(this);
            }
         }

         RootWindow$5 var12 = new RootWindow$5(this, var4);
         this.setWindow(this.getWindowItem().read(var1, var2, var12));

         for(int var6 = 0; var6 < 4; ++var6) {
            this.windowBars[var6].newRead(var1, var2, var12);
         }

         if (var2.getVersion() >= 4) {
            int var13 = var1.readInt();

            for(int var7 = 0; var7 < var13; ++var7) {
               FloatingWindow var8 = this.createFloatingWindow();
               var8.read(var1, var2, var12);
            }
         }

         this.readLocations(var1, this, var2.getVersion());
      } finally {
         endUpdateModel();
      }

   }

   public void read(ObjectInputStream var1, boolean var2) throws IOException {
      FocusManager.getInstance().startIgnoreFocusChanges();
      PropertyMapManager.getInstance().beginBatch();

      try {
         this.setWindow(null);

         while(this.floatingWindows.size() > 0) {
            ((FloatingWindow)this.floatingWindows.get(0)).close();
         }

         int var3 = var1.readInt();
         if (var3 > 4) {
            throw new IOException("Can't read serialized data because it was written by a later version of InfoNode Docking Windows!");
         }

         ReadContext var4 = new ReadContext(this, var3, var1.readBoolean(), var2);
         if (var4.getVersion() < 3) {
            this.oldInternalRead(var1, var4);
         } else {
            this.newInternalRead(var1, var4);
         }

         if (var3 > 1) {
            this.readMaximized(var1);
         }

         FocusManager.focusWindow(this);
      } finally {
         PropertyMapManager.getInstance().endBatch();
         SwingUtilities.invokeLater(new RootWindow$6(this));
      }

   }

   private void readMaximized(ObjectInputStream var1) throws IOException {
      int var2;
      Object var3;
      for(var3 = this; (var2 = var1.readInt()) != -1; var3 = ((DockingWindow)var3).getChildWindow(var2)) {
         if (var2 >= ((DockingWindow)var3).getChildWindowCount()) {
            while(var1.readInt() != -1) {
            }

            return;
         }
      }

      if (var3 != this) {
         this.setMaximizedWindow((DockingWindow)var3);
      }

   }

   public DockingWindow getMaximizedWindow() {
      return this.maximizedWindow;
   }

   public void setMaximizedWindow(DockingWindow var1) {
      if (var1 == null || !var1.isMinimized() && !var1.isUndocked()) {
         this.internalSetMaximizedWindow(var1);
      }
   }

   void addView(View var1) {
      int var2 = this.views.size();

      for(int var3 = 0; var3 < this.views.size(); ++var3) {
         View var4 = (View)((WeakReference)this.views.get(var3)).get();
         if (var4 == var1) {
            return;
         }

         if (var4 == null) {
            var2 = var3;
         }
      }

      this.views.add(var2, new WeakReference(var1));
   }

   public void removeView(View var1) {
      if (var1.getRootWindow() != this) {
         if (this.focusedView == var1) {
            this.focusedView = null;
         }

         this.removeWeak(var1, this.views);
         this.removeWeak(var1, this.lastFocusedWindows);
         this.removeWeak(var1, this.focusedWindows);
         this.getWindowItem().removeWindowRefs(var1);
      }
   }

   private void removeWeak(Object var1, List var2) {
      for(int var3 = 0; var3 < var2.size(); ++var3) {
         Object var4 = ((WeakReference)var2.get(var3)).get();
         if (var4 == var1) {
            var2.remove(var3);
            return;
         }
      }

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
               this.windowPanel.remove(var3);
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
               this.windowPanel.add(this.maximizedWindow);
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

   private void createWindowBars() {
      Direction[] var1 = Direction.getDirections();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         this.windowBars[var2] = new WindowBar(this, var1[var2]);
         this.windowBars[var2].setEnabled(false);
         this.addWindow(this.windowBars[var2]);
         this.layeredPane.add(this.windowBars[var2].getEdgePanel());
         this.mainPanel
            .add(
               this.windowBars[var2],
               new Point(
                  var1[var2] == Direction.LEFT ? 0 : (var1[var2] == Direction.RIGHT ? 2 : 1),
                  var1[var2] == Direction.UP ? 0 : (var1[var2] == Direction.DOWN ? 2 : 1)
               )
            );
         this.windowBars[var2].addPropertyChangeListener("enabled", new RootWindow$7(this));
      }

   }

   JComponent getLayeredPane() {
      return this.layeredPane;
   }

   JComponent getWindowPanel() {
      return this.windowPanel;
   }

   protected void showChildWindow(DockingWindow var1) {
      if (this.maximizedWindow != null && var1 == this.window) {
         this.setMaximizedWindow(null);
      }

      super.showChildWindow(var1);
   }

   protected void update() {
      RootWindowProperties var1 = this.getRootWindowProperties();
      var1.getComponentProperties().applyTo(this.layeredPane);
      InternalPropertiesUtil.applyTo(var1.getShapedPanelProperties(), this.layeredPane);
      var1.getWindowAreaProperties().applyTo(this.windowPanel);
      InternalPropertiesUtil.applyTo(var1.getWindowAreaShapedPanelProperties(), this.windowPanel);
      var1.getDragLabelProperties().applyTo(this.dragTextLabel);
      if (!this.heavyweightSupport) {
         ShapedPanel var2 = (ShapedPanel)this.dragRectangle;
         InternalPropertiesUtil.applyTo(var1.getDragRectangleShapedPanelProperties(), var2);
         if (var2.getComponentPainter() == null) {
            var2.setComponentPainter(new RectangleComponentPainter(Color.BLACK, var1.getDragRectangleBorderWidth()));
         }
      } else {
         HeavyWeightDragRectangle var5 = (HeavyWeightDragRectangle)this.dragRectangle;
         ComponentPainter var3 = var1.getDragRectangleShapedPanelProperties().getComponentPainter();
         Color var4 = var3 != null ? var3.getColor(this) : null;
         var5.setColor(var4 != null ? var4 : Color.BLACK);
         var5.setBorderWidth(var1.getDragRectangleBorderWidth());
      }

   }

   void internalStartDrag(JComponent var1) {
      this.currentDragRootPane = this.getRootPane();
      FloatingWindow var2 = DockingUtil.getFloatingWindowFor((DockingWindow)var1);

      for(int var3 = 0; var3 < this.floatingWindows.size(); ++var3) {
         FloatingWindow var4 = (FloatingWindow)this.floatingWindows.get(var3);
         var4.startDrag();
         if (this.dummyFrame != null && var4 != var2) {
            ((JDialog)var4.getTopLevelAncestor()).toFront();
         }
      }

      if (this.dummyFrame != null && var2 != null) {
         ((JDialog)var2.getTopLevelAncestor()).toFront();
      }

   }

   void stopDrag() {
      if (this.dragTextContainer.getParent() != null) {
         if (!this.heavyweightSupport) {
            this.dragTextContainer
               .getParent()
               .repaint(this.dragTextContainer.getX(), this.dragTextContainer.getY(), this.dragTextContainer.getWidth(), this.dragTextContainer.getHeight());
         }

         this.dragTextContainer.getParent().remove(this.dragTextContainer);
      }

      if (this.dragTextWindow != null) {
         this.dragTextWindow.setVisible(false);
         this.dragTextWindow.dispose();
         this.dragTextWindow = null;
      }

      if (this.dragRectangle.getParent() != null) {
         if (!this.heavyweightSupport) {
            this.dragRectangle
               .getParent()
               .repaint(this.dragRectangle.getX(), this.dragRectangle.getY(), this.dragRectangle.getWidth(), this.dragRectangle.getHeight());
         }

         this.dragRectangle.getParent().remove(this.dragRectangle);
      }

      CursorManager.resetGlobalCursor(this.getCurrentDragRootPane());
      this.currentDragRootPane = null;

      for(int var1 = 0; var1 < this.floatingWindows.size(); ++var1) {
         ((FloatingWindow)this.floatingWindows.get(var1)).stopDrag();
      }

   }

   boolean floatingWindowsContainPoint(Point var1) {
      for(int var2 = 0; var2 < this.floatingWindows.size(); ++var2) {
         FloatingWindow var3 = (FloatingWindow)this.floatingWindows.get(var2);
         if (var3.isShowing() && var3.windowContainsPoint(SwingUtilities.convertPoint(this.getRootPane(), var1, var3))) {
            return true;
         }
      }

      return false;
   }

   void setCurrentDragRootPane(JRootPane var1) {
      CursorManager.resetGlobalCursor(this.getCurrentDragRootPane());
      this.currentDragRootPane = var1;
   }

   JRootPane getCurrentDragRootPane() {
      return this.currentDragRootPane == null ? this.getRootPane() : this.currentDragRootPane;
   }

   Component getTopLevelComponent() {
      Container var1 = this.getTopLevelAncestor();
      if (!(var1 instanceof JFrame) && !(var1 instanceof JDialog)) {
         if (this.dummyFrame == null) {
            this.dummyFrame = new JFrame("");
            this.dummyFrame.setDefaultCloseOperation(2);
         }

         return this.dummyFrame;
      } else {
         return var1;
      }
   }

   boolean isHeavyweightSupported() {
      return this.heavyweightSupport;
   }

   private static boolean reparent(Container var0, Component var1) {
      if (var1.getParent() != var0) {
         if (var1.getParent() != null) {
            var1.getParent().remove(var1);
         }

         var0.add(var1);
         return true;
      } else {
         return false;
      }
   }

   void setDragCursor(Cursor var1) {
      CursorManager.setGlobalCursor(this.getCurrentDragRootPane(), var1);
      if (this.dragTextWindow != null) {
         this.dragTextWindow.setCursor(var1);
      }

   }

   void setDragText(Point var1, String var2) {
      if (var1 != null) {
         JRootPane var3 = this.getCurrentDragRootPane();
         if (reparent(var3.getLayeredPane(), this.dragTextContainer)) {
            var3.getLayeredPane().setLayer(this.dragTextContainer, JLayeredPane.DRAG_LAYER + (this.heavyweightSupport ? -1 : 1));
         }

         this.dragTextLabel.setText(var2);
         this.dragTextContainer.setSize(this.dragTextContainer.getPreferredSize());
         Point var4 = SwingUtilities.convertPoint(var3, var1, this.dragTextContainer.getParent());
         int var5 = this.dragTextLabel.getInsets().bottom;
         this.dragTextContainer
            .setLocation(
               (int)(var4.getX() - (double)(this.dragTextContainer.getWidth() / 2)),
               (int)(var4.getY() - (double)this.dragTextContainer.getHeight() + (double)var5)
            );
         Point var6 = SwingUtilities.convertPoint(var3, var1, this.getRootPane());
         if (!this.getRootPane().contains(var6) && !this.floatingWindowsContainPoint(var6)) {
            this.dragTextContainer.setVisible(false);
            if (this.dragTextWindow == null) {
               Component var7 = this.getTopLevelComponent();
               if (var7 instanceof Frame) {
                  this.dragTextWindow = new DragLabelWindow((Frame)var7);
               } else if (var7 instanceof Dialog) {
                  this.dragTextWindow = new DragLabelWindow((Dialog)var7);
               }

               if (this.dragTextWindow != null) {
                  this.dragTextWindow.setFocusableWindowState(false);
                  this.dragTextWindow.setFocusable(false);
                  this.getRootWindowProperties().getDragLabelProperties().applyTo(this.dragTextWindow.getLabel());
               }
            }

            if (this.dragTextWindow != null) {
               this.dragTextWindow.getLabel().setText(var2);
               SwingUtilities.convertPointToScreen(var1, var3);
               this.dragTextWindow.setLocation(var1.x - this.dragTextContainer.getWidth() / 2, var1.y - this.dragTextContainer.getHeight() + var5);
               this.dragTextWindow.setVisible(true);
            }
         } else {
            this.dragTextContainer.setVisible(true);
            if (this.heavyweightSupport) {
               this.dragTextContainer.repaint();
            }

            if (this.dragTextWindow != null) {
               this.dragTextWindow.setVisible(false);
            }
         }
      } else if (this.dragTextContainer.getParent() != null) {
         this.dragTextContainer.setVisible(false);
         if (this.dragTextWindow != null) {
            this.dragTextWindow.setVisible(false);
         }
      }

   }

   void setDragRectangle(Rectangle var1) {
      if (var1 != null) {
         if (reparent(this.getCurrentDragRootPane().getLayeredPane(), this.dragRectangle)) {
            this.getCurrentDragRootPane().getLayeredPane().setLayer(this.dragRectangle, JLayeredPane.DRAG_LAYER + (this.heavyweightSupport ? -1 : 0));
         }

         this.dragRectangle.setBounds(SwingUtilities.convertRectangle(this, var1, this.dragRectangle.getParent()));
         this.dragRectangle.setVisible(true);
      } else if (this.dragRectangle.getParent() != null) {
         this.dragRectangle.setVisible(false);
      }

   }

   protected void doReplace(DockingWindow var1, DockingWindow var2) {
      if (var1 == this.window) {
         if (this.window != null) {
            this.windowPanel.remove(this.window);
            this.window.setVisible(true);
         }

         this.window = var2;
         if (this.window != null) {
            if (this.maximizedWindow != null) {
               this.window.setVisible(false);
            }

            this.windowPanel.add(this.window);
            this.windowPanel.revalidate();
         }
      }

   }

   protected void doRemoveWindow(DockingWindow var1) {
      if (var1 == this.window) {
         this.windowPanel.remove(var1);
         this.window.setVisible(true);
         this.window = null;
      }

      this.repaint();
   }

   public RootWindow getRootWindow() {
      return this;
   }

   protected boolean acceptsSplitWith(DockingWindow var1) {
      return false;
   }

   protected DropAction doAcceptDrop(Point var1, DockingWindow var2) {
      if (this.maximizedWindow != null) {
         Point var3 = SwingUtilities.convertPoint(this, var1, this.maximizedWindow);
         if (this.maximizedWindow.contains(var3) && this.getChildDropFilter().acceptDrop(new ChildDropInfo(var2, this, var1, this.maximizedWindow))) {
            DropAction var4 = this.maximizedWindow.acceptDrop(var3, var2);
            if (var4 != null) {
               return var4;
            }
         }
      }

      return super.doAcceptDrop(var1, var2);
   }

   protected DropAction acceptInteriorDrop(Point var1, DockingWindow var2) {
      if (this.window != null) {
         return null;
      } else {
         return this.getInteriorDropFilter().acceptDrop(new InteriorDropInfo(var2, this, var1)) ? new RootWindow$8(this) : null;
      }
   }

   protected PropertyMap getPropertyObject() {
      return this.getRootWindowProperties().getMap();
   }

   protected PropertyMap createPropertyObject() {
      return new RootWindowProperties().getMap();
   }

   boolean windowBarEnabled() {
      for(int var1 = 0; var1 < this.windowBars.length; ++var1) {
         if (this.windowBars[var1].isEnabled()) {
            return true;
         }
      }

      return false;
   }

   void removeWindowComponent(DockingWindow var1) {
   }

   void restoreWindowComponent(DockingWindow var1) {
   }

   protected void cleanUpModel() {
      if (!this.cleanUpModel) {
         this.cleanUpModel = true;
         SwingUtilities.invokeLater(this.modelCleanUpEvent);
      }

   }

   protected boolean isShowingInRootWindow() {
      return true;
   }

   public void updateUI() {
      super.updateUI();
      if (this.floatingWindows != null) {
         for(int var1 = 0; var1 < this.floatingWindows.size(); ++var1) {
            SwingUtilities.updateComponentTreeUI(((JComponent)this.floatingWindows.get(var1)).getTopLevelAncestor());
         }

         for(int var3 = 0; var3 < this.views.size(); ++var3) {
            View var2 = (View)((WeakReference)this.views.get(var3)).get();
            if (var2 != null && var2.getWindowParent() == null) {
               SwingUtilities.updateComponentTreeUI(var2);
            }
         }
      }

   }

   protected void paintComponent(Graphics var1) {
      super.paintComponent(var1);
   }

   private class SingleComponentLayout implements LayoutManager {
      private SingleComponentLayout() {
         super();
      }

      public void addLayoutComponent(String var1, Component var2) {
      }

      public void layoutContainer(Container var1) {
         Dimension var2 = LayoutUtil.getInteriorSize(var1);
         Insets var3 = var1.getInsets();
         RootWindow.this.mainPanel.setBounds(var3.left, var3.top, var2.width, var2.height);
         int var4 = RootWindow.this.windowBars[Direction.LEFT.getValue()].getPreferredSize().width;
         int var5 = RootWindow.this.windowBars[Direction.RIGHT.getValue()].getPreferredSize().width;
         int var6 = RootWindow.this.windowBars[Direction.UP.getValue()].getPreferredSize().height;
         int var7 = RootWindow.this.windowBars[Direction.DOWN.getValue()].getPreferredSize().height;
         Direction[] var8 = Direction.getDirections();

         for(int var9 = 0; var9 < RootWindow.this.windowBars.length; ++var9) {
            Component var10 = RootWindow.this.windowBars[var9].getEdgePanel();
            if (var10.isVisible()) {
               Direction var11 = var8[var9];
               int var12 = var2.width - var4 - var5;
               int var13 = var2.height - var6 - var7;
               if (var11 == Direction.RIGHT) {
                  int var14 = var1.getWidth() - var3.right - var5 + RootWindow.this.windowBars[var11.getValue()].getInsets().left;
                  int var15 = Math.min(var10.getPreferredSize().width, var12 + RootWindow.this.windowBars[var11.getValue()].getInsets().left);
                  var10.setBounds(var14 - var15, var3.top + var6, var15, var13);
               } else if (var11 == Direction.LEFT) {
                  int var16 = var3.left + var4 - RootWindow.this.windowBars[var11.getValue()].getInsets().right;
                  int var19 = Math.min(var10.getPreferredSize().width, var12 + RootWindow.this.windowBars[var11.getValue()].getInsets().right);
                  var10.setBounds(var16, var3.top + var6, var19, var13);
               } else if (var11 == Direction.DOWN) {
                  int var17 = var1.getHeight() - var3.bottom - var7 + RootWindow.this.windowBars[var11.getValue()].getInsets().top;
                  int var20 = Math.min(var10.getPreferredSize().height, var13 + RootWindow.this.windowBars[var11.getValue()].getInsets().top);
                  var10.setBounds(var3.left + var4, var17 - var20, var12, var20);
               } else {
                  int var18 = var3.top + var6 - RootWindow.this.windowBars[var11.getValue()].getInsets().bottom;
                  int var21 = Math.min(var10.getPreferredSize().height, var13 + RootWindow.this.windowBars[var11.getValue()].getInsets().bottom);
                  var10.setBounds(var3.left + var4, var18, var12, var21);
               }
            }
         }

      }

      public Dimension minimumLayoutSize(Container var1) {
         return LayoutUtil.add(RootWindow.this.mainPanel.getMinimumSize(), var1.getInsets());
      }

      public Dimension preferredLayoutSize(Container var1) {
         return LayoutUtil.add(RootWindow.this.mainPanel.getPreferredSize(), var1.getInsets());
      }

      public void removeLayoutComponent(Component var1) {
      }
   }
}
