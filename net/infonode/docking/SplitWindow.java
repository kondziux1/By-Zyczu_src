package net.infonode.docking;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.Icon;
import net.infonode.docking.drop.InteriorDropInfo;
import net.infonode.docking.drop.SplitDropInfo;
import net.infonode.docking.internal.ReadContext;
import net.infonode.docking.internal.WriteContext;
import net.infonode.docking.internalutil.DropAction;
import net.infonode.docking.model.SplitWindowItem;
import net.infonode.docking.model.ViewReader;
import net.infonode.docking.model.ViewWriter;
import net.infonode.docking.properties.SplitWindowProperties;
import net.infonode.gui.ComponentUtil;
import net.infonode.gui.SimpleSplitPane;
import net.infonode.gui.panel.BaseContainerUtil;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.util.Direction;

public class SplitWindow extends DockingWindow {
   private SimpleSplitPane splitPane;
   private DockingWindow leftWindow;
   private DockingWindow rightWindow;

   public SplitWindow(boolean var1) {
      this(var1, null, null);
   }

   public SplitWindow(boolean var1, DockingWindow var2, DockingWindow var3) {
      this(var1, 0.5F, var2, var3);
   }

   public SplitWindow(boolean var1, float var2, DockingWindow var3, DockingWindow var4) {
      this(var1, var2, var3, var4, null);
   }

   protected SplitWindow(boolean var1, float var2, DockingWindow var3, DockingWindow var4, SplitWindowItem var5) {
      super(var5 == null ? new SplitWindowItem() : var5);
      this.splitPane = new SimpleSplitPane(var1);
      BaseContainerUtil.setForcedOpaque(this.splitPane, false);
      this.splitPane.addListener(new SplitWindow$1(this));
      this.setComponent(this.splitPane);
      this.setWindows(var3, var4);
      this.setHorizontal(var1);
      this.setDividerLocation(var2);
      this.splitPane.getDividerPanel().addMouseListener(new SplitWindow$2(this));
      this.init();
   }

   public SplitWindowProperties getSplitWindowProperties() {
      return ((SplitWindowItem)this.getWindowItem()).getSplitWindowProperties();
   }

   public DockingWindow getLeftWindow() {
      return this.leftWindow;
   }

   public DockingWindow getRightWindow() {
      return this.rightWindow;
   }

   public void setDividerLocation(float var1) {
      this.splitPane.setDividerLocation(var1);
   }

   public float getDividerLocation() {
      return this.splitPane.getDividerLocation();
   }

   public void setWindows(DockingWindow var1, DockingWindow var2) {
      if (var1 != this.getLeftWindow() || var2 != this.getRightWindow()) {
         optimizeAfter(null, new SplitWindow$3(this, var1, var2));
      }
   }

   public boolean isHorizontal() {
      return this.splitPane.isHorizontal();
   }

   public void setHorizontal(boolean var1) {
      this.splitPane.setHorizontal(var1);
      ((SplitWindowItem)this.getWindowItem()).setHorizontal(var1);
   }

   protected void update() {
      this.splitPane.setDividerSize(this.getSplitWindowProperties().getDividerSize());
      this.splitPane.setContinuousLayout(this.getSplitWindowProperties().getContinuousLayoutEnabled());
      this.splitPane.setDividerDraggable(this.getSplitWindowProperties().getDividerLocationDragEnabled());
      this.splitPane.setDragIndicatorColor(this.getSplitWindowProperties().getDragIndicatorColor());
   }

   protected void optimizeWindowLayout() {
      DockingWindow var1 = this.getWindowParent();
      if (var1 != null && (this.getRightWindow() == null || this.getLeftWindow() == null)) {
         if (this.getRightWindow() == null && this.getLeftWindow() == null) {
            var1.removeChildWindow(this);
         } else {
            DockingWindow var2 = this.getRightWindow() == null ? this.getLeftWindow() : this.getRightWindow();
            var1.internalReplaceChildWindow(this, var2.getBestFittedWindow(var1));
         }
      }

   }

   public DockingWindow getChildWindow(int var1) {
      return this.getWindows()[var1];
   }

   protected void rootChanged(RootWindow var1, RootWindow var2) {
      super.rootChanged(var1, var2);
      if (var2 != null) {
         this.splitPane.setHeavyWeightDragIndicator(var2.isHeavyweightSupported());
      }

   }

   private DockingWindow[] getWindows() {
      return this.getLeftWindow() == null
         ? (this.getRightWindow() == null ? new DockingWindow[0] : new DockingWindow[]{this.getRightWindow()})
         : (this.getRightWindow() == null ? new DockingWindow[]{this.getLeftWindow()} : new DockingWindow[]{this.getLeftWindow(), this.getRightWindow()});
   }

   public int getChildWindowCount() {
      return this.getWindows().length;
   }

   public Icon getIcon() {
      return this.getLeftWindow() == null ? (this.getRightWindow() == null ? null : this.getRightWindow().getIcon()) : this.getLeftWindow().getIcon();
   }

   protected void doReplace(DockingWindow var1, DockingWindow var2) {
      if (this.getLeftWindow() == var1) {
         this.leftWindow = var2;
         this.splitPane.setLeftComponent(var2);
      } else {
         this.rightWindow = var2;
         this.splitPane.setRightComponent(var2);
      }

      ComponentUtil.validate(this.splitPane);
   }

   protected void doRemoveWindow(DockingWindow var1) {
      if (var1 == this.getLeftWindow()) {
         this.leftWindow = null;
         this.splitPane.setLeftComponent(null);
      } else {
         this.rightWindow = null;
         this.splitPane.setRightComponent(null);
      }

   }

   protected DockingWindow oldRead(ObjectInputStream var1, ReadContext var2) throws IOException {
      this.splitPane.setHorizontal(var1.readBoolean());
      this.splitPane.setDividerLocation(var1.readFloat());
      DockingWindow var3 = WindowDecoder.decodeWindow(var1, var2);
      DockingWindow var4 = WindowDecoder.decodeWindow(var1, var2);
      super.oldRead(var1, var2);
      if (var3 != null && var4 != null) {
         this.setWindows(var3, var4);
         return this;
      } else {
         return var3 != null ? var3 : (var4 != null ? var4 : null);
      }
   }

   protected void updateWindowItem(RootWindow var1) {
      super.updateWindowItem(var1);
      ((SplitWindowItem)this.getWindowItem())
         .setParentSplitWindowProperties(var1 == null ? SplitWindowItem.emptyProperties : var1.getRootWindowProperties().getSplitWindowProperties());
   }

   protected PropertyMap getPropertyObject() {
      return this.getSplitWindowProperties().getMap();
   }

   protected PropertyMap createPropertyObject() {
      return new SplitWindowProperties().getMap();
   }

   void removeWindowComponent(DockingWindow var1) {
      if (var1 == this.getLeftWindow()) {
         this.splitPane.setLeftComponent(null);
      } else {
         this.splitPane.setRightComponent(null);
      }

   }

   void restoreWindowComponent(DockingWindow var1) {
      if (var1 == this.getLeftWindow()) {
         this.splitPane.setLeftComponent(this.leftWindow);
      } else {
         this.splitPane.setRightComponent(this.rightWindow);
      }

   }

   protected int getChildEdgeDepth(DockingWindow var1, Direction var2) {
      return (var1 == this.leftWindow ? var2 : var2.getOpposite()) == (this.isHorizontal() ? Direction.RIGHT : Direction.DOWN)
         ? 0
         : super.getChildEdgeDepth(var1, var2);
   }

   protected DropAction doAcceptDrop(Point var1, DockingWindow var2) {
      DropAction var3 = this.acceptChildDrop(var1, var2);
      if (var3 != null) {
         return var3;
      } else {
         float var4 = this.isHorizontal() ? (float)var1.y / (float)this.getHeight() : (float)var1.x / (float)this.getWidth();
         if (var4 <= 0.33F) {
            Direction var6 = this.isHorizontal() ? Direction.UP : Direction.LEFT;
            return this.getSplitDropFilter().acceptDrop(new SplitDropInfo(var2, this, var1, var6)) ? this.split(var2, var6) : null;
         } else if (var4 >= 0.66F) {
            Direction var5 = this.isHorizontal() ? Direction.DOWN : Direction.RIGHT;
            return this.getSplitDropFilter().acceptDrop(new SplitDropInfo(var2, this, var1, var5)) ? this.split(var2, var5) : null;
         } else {
            return this.getInteriorDropFilter().acceptDrop(new InteriorDropInfo(var2, this, var1)) ? this.createTabWindow(var2) : null;
         }
      }
   }

   protected void write(ObjectOutputStream var1, WriteContext var2, ViewWriter var3) throws IOException {
      var1.writeInt(2);
      var3.writeWindowItem(this.getWindowItem(), var1, var2);
      this.getLeftWindow().write(var1, var2, var3);
      this.getRightWindow().write(var1, var2, var3);
   }

   protected DockingWindow newRead(ObjectInputStream var1, ReadContext var2, ViewReader var3) throws IOException {
      DockingWindow var4 = WindowDecoder.decodeWindow(var1, var2, var3);
      DockingWindow var5 = WindowDecoder.decodeWindow(var1, var2, var3);
      if (var4 != null && var5 != null) {
         this.setWindows(var4, var5);
         return this;
      } else {
         return var4 != null ? var4 : (var5 != null ? var5 : null);
      }
   }
}
