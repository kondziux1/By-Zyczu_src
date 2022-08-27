package net.infonode.docking;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import net.infonode.docking.drag.DockingWindowDragSource;
import net.infonode.docking.internal.WriteContext;
import net.infonode.docking.internalutil.ButtonInfo;
import net.infonode.docking.internalutil.CloseButtonInfo;
import net.infonode.docking.internalutil.DockButtonInfo;
import net.infonode.docking.internalutil.InternalDockingUtil;
import net.infonode.docking.internalutil.MaximizeButtonInfo;
import net.infonode.docking.internalutil.MinimizeButtonInfo;
import net.infonode.docking.internalutil.RestoreButtonInfo;
import net.infonode.docking.internalutil.UndockButtonInfo;
import net.infonode.docking.model.TabWindowItem;
import net.infonode.docking.model.ViewWriter;
import net.infonode.docking.properties.TabWindowProperties;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapTreeListener;
import net.infonode.properties.propertymap.PropertyMapWeakListenerManager;
import net.infonode.properties.util.PropertyChangeListener;
import net.infonode.util.ArrayUtil;
import net.infonode.util.Direction;

public class TabWindow extends AbstractTabWindow {
   private static final ButtonInfo[] buttonInfos = new ButtonInfo[]{
      new UndockButtonInfo(TabWindowProperties.UNDOCK_BUTTON_PROPERTIES),
      new DockButtonInfo(TabWindowProperties.DOCK_BUTTON_PROPERTIES),
      new MinimizeButtonInfo(TabWindowProperties.MINIMIZE_BUTTON_PROPERTIES),
      new MaximizeButtonInfo(TabWindowProperties.MAXIMIZE_BUTTON_PROPERTIES),
      new RestoreButtonInfo(TabWindowProperties.RESTORE_BUTTON_PROPERTIES),
      new CloseButtonInfo(TabWindowProperties.CLOSE_BUTTON_PROPERTIES)
   };
   private AbstractButton[] buttons = new AbstractButton[buttonInfos.length];
   private PropertyChangeListener minimumSizePropertiesListener = new TabWindow$1(this);
   private PropertyMapTreeListener buttonFactoryListener = new TabWindow$2(this);

   public TabWindow() {
      this((DockingWindow)null);
   }

   public TabWindow(DockingWindow var1) {
      this(var1 == null ? null : new DockingWindow[]{var1});
   }

   public TabWindow(DockingWindow[] var1) {
      this(var1, null);
   }

   protected TabWindow(DockingWindow[] var1, TabWindowItem var2) {
      super(true, var2 == null ? new TabWindowItem() : var2);
      this.setTabWindowProperties(((TabWindowItem)this.getWindowItem()).getTabWindowProperties());
      PropertyMapWeakListenerManager.addWeakPropertyChangeListener(
         this.getTabWindowProperties().getMap(), TabWindowProperties.RESPECT_CHILD_WINDOW_MINIMUM_SIZE, this.minimumSizePropertiesListener
      );
      new DockingWindowDragSource(this.getTabbedPanel(), new TabWindow$3(this));
      this.initMouseListener();
      this.init();
      this.getTabbedPanel().addTabListener(new TabWindow$4(this));
      if (var1 != null) {
         for(int var3 = 0; var3 < var1.length; ++var3) {
            this.addTab(var1[var3]);
         }
      }

      PropertyMapWeakListenerManager.addWeakTreeListener(this.getTabWindowProperties().getMap(), this.buttonFactoryListener);
   }

   public TabWindowProperties getTabWindowProperties() {
      return ((TabWindowItem)this.getWindowItem()).getTabWindowProperties();
   }

   protected void tabSelected(WindowTab var1) {
      super.tabSelected(var1);
      if (this.getUpdateModel()) {
         ((TabWindowItem)this.getWindowItem())
            .setSelectedItem(var1 == null ? null : this.getWindowItem().getChildWindowContaining(var1.getWindow().getWindowItem()));
      }

   }

   protected void update() {
   }

   protected void updateButtonVisibility() {
      this.doUpdateButtonVisibility(null);
   }

   private void doUpdateButtonVisibility(Map var1) {
      if (InternalDockingUtil.updateButtons(buttonInfos, this.buttons, null, this, this.getTabWindowProperties().getMap(), var1)) {
         this.updateTabAreaComponents();
      }

      super.updateButtonVisibility();
   }

   protected int getTabAreaComponentCount() {
      return ArrayUtil.countNotNull(this.buttons);
   }

   protected void getTabAreaComponents(int var1, JComponent[] var2) {
      for(int var3 = 0; var3 < this.buttons.length; ++var3) {
         if (this.buttons[var3] != null) {
            var2[var1++] = this.buttons[var3];
         }
      }

   }

   protected void optimizeWindowLayout() {
      if (this.getWindowParent() != null) {
         if (this.getTabbedPanel().getTabCount() == 0) {
            this.internalClose();
         } else if (this.getTabbedPanel().getTabCount() == 1 && (this.getWindowParent().showsWindowTitle() || !this.getChildWindow(0).needsTitleWindow())) {
            this.getWindowParent().internalReplaceChildWindow(this, this.getChildWindow(0).getBestFittedWindow(this.getWindowParent()));
         }

      }
   }

   public int addTab(DockingWindow var1, int var2) {
      int var3 = super.addTab(var1, var2);
      this.setSelectedTab(var3);
      return var3;
   }

   protected int addTabNoSelect(DockingWindow var1, int var2) {
      DockingWindow var3 = var2 == this.getChildWindowCount() ? null : this.getChildWindow(var2);
      int var4 = super.addTabNoSelect(var1, var2);
      if (this.getUpdateModel()) {
         this.addWindowItem(var1, var3 == null ? -1 : this.getWindowItem().getWindowIndex(this.getWindowItem().getChildWindowContaining(var3.getWindowItem())));
      }

      return var4;
   }

   protected void updateWindowItem(RootWindow var1) {
      super.updateWindowItem(var1);
      ((TabWindowItem)this.getWindowItem())
         .setParentTabWindowProperties(var1 == null ? TabWindowItem.emptyProperties : var1.getRootWindowProperties().getTabWindowProperties());
   }

   protected PropertyMap getPropertyObject() {
      return this.getTabWindowProperties().getMap();
   }

   protected PropertyMap createPropertyObject() {
      return new TabWindowProperties().getMap();
   }

   protected int getEdgeDepth(Direction var1) {
      return var1 == this.getTabbedPanel().getProperties().getTabAreaOrientation() ? 1 : super.getEdgeDepth(var1);
   }

   protected int getChildEdgeDepth(DockingWindow var1, Direction var2) {
      return var2 == this.getTabbedPanel().getProperties().getTabAreaOrientation() ? 0 : 1 + super.getChildEdgeDepth(var1, var2);
   }

   protected DockingWindow getOptimizedWindow() {
      return this.getChildWindowCount() == 1 ? this.getChildWindow(0).getOptimizedWindow() : super.getOptimizedWindow();
   }

   protected boolean acceptsSplitWith(DockingWindow var1) {
      return super.acceptsSplitWith(var1) && (this.getChildWindowCount() != 1 || this.getChildWindow(0) != var1);
   }

   protected DockingWindow getBestFittedWindow(DockingWindow var1) {
      return (DockingWindow)(this.getChildWindowCount() != 1 || this.getChildWindow(0).needsTitleWindow() && !var1.showsWindowTitle()
         ? this
         : this.getChildWindow(0).getBestFittedWindow(var1));
   }

   protected void write(ObjectOutputStream var1, WriteContext var2, ViewWriter var3) throws IOException {
      var1.writeInt(3);
      var3.writeWindowItem(this.getWindowItem(), var1, var2);
      super.write(var1, var2, var3);
   }
}
