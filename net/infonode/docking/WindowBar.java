package net.infonode.docking;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import net.infonode.docking.internal.HeavyWeightContainer;
import net.infonode.docking.internal.ReadContext;
import net.infonode.docking.internal.WriteContext;
import net.infonode.docking.internalutil.DropAction;
import net.infonode.docking.model.ViewReader;
import net.infonode.docking.model.ViewWriter;
import net.infonode.docking.model.WindowBarItem;
import net.infonode.docking.properties.TabWindowProperties;
import net.infonode.docking.properties.WindowBarProperties;
import net.infonode.docking.util.DockingUtil;
import net.infonode.gui.panel.BaseContainerUtil;
import net.infonode.gui.panel.ResizablePanel;
import net.infonode.properties.gui.util.ShapedPanelProperties;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapWeakListenerManager;
import net.infonode.properties.util.PropertyChangeListener;
import net.infonode.tabbedpanel.TabbedPanelContentPanel;
import net.infonode.util.Direction;

public class WindowBar extends AbstractTabWindow {
   private RootWindow rootWindow;
   private Direction direction;
   private TabbedPanelContentPanel contentPanel;
   private ResizablePanel edgePanel;
   private HeavyWeightContainer heavyWeightEdgePanel;
   private PropertyChangeListener opaqueListener = new WindowBar$1(this);

   WindowBar(RootWindow var1, Direction var2) {
      super(false, new WindowBarItem());
      this.initMouseListener();
      this.rootWindow = var1;
      this.contentPanel = new TabbedPanelContentPanel(this.getTabbedPanel(), new WindowBar$2(this, this.getTabbedPanel()));
      this.direction = var2;
      WindowBarProperties var3 = new WindowBarProperties();
      var3.getTabWindowProperties().addSuperObject(var1.getRootWindowProperties().getTabWindowProperties());
      ((WindowBarItem)this.getWindowItem()).setWindowBarProperties(new WindowBarProperties(var3));
      this.getWindowBarProperties().addSuperObject(var1.getRootWindowProperties().getWindowBarProperties());
      this.getWindowBarProperties().addSuperObject(WindowBarProperties.createDefault(this.direction));
      this.edgePanel = new ResizablePanel(var1.isHeavyweightSupported(), this.direction.getOpposite(), this.contentPanel);
      this.edgePanel.setPreferredSize(new Dimension(200, 200));
      this.edgePanel.setComponent(this.contentPanel);
      this.edgePanel.setLayeredPane(var1.getLayeredPane());
      this.edgePanel.setInnerArea(var1.getWindowPanel());
      this.updateEdgePanelOpaque();
      PropertyMapWeakListenerManager.addWeakPropertyChangeListener(
         this.contentPanel.getProperties().getShapedPanelProperties().getMap(), ShapedPanelProperties.OPAQUE, this.opaqueListener
      );
      if (var1.isHeavyweightSupported()) {
         this.edgePanel.addComponentListener(new WindowBar$3(this));
      }

      this.getEdgePanel().setVisible(false);
      this.setTabWindowProperties(this.getWindowBarProperties().getTabWindowProperties());
      this.init();
   }

   public TabWindowProperties getTabWindowProperties() {
      return this.getWindowBarProperties().getTabWindowProperties();
   }

   public WindowBarProperties getWindowBarProperties() {
      return ((WindowBarItem)this.getWindowItem()).getWindowBarProperties();
   }

   protected int addTabNoSelect(DockingWindow var1, int var2) {
      var2 = super.addTabNoSelect(var1, var2);
      var1.setLastMinimizedDirection(this.direction);
      return var2;
   }

   public void setContentPanelSize(int var1) {
      this.edgePanel.setPreferredSize(this.direction.isHorizontal() ? new Dimension(var1, 0) : new Dimension(0, var1));
   }

   public int getContentPanelSize() {
      Dimension var1 = this.edgePanel.getPreferredSize();
      return this.direction.isHorizontal() ? var1.width : var1.height;
   }

   public Direction getDirection() {
      return this.direction;
   }

   public RootWindow getRootWindow() {
      return this.rootWindow;
   }

   protected void showChildWindow(DockingWindow var1) {
      int var2 = this.getChildWindowIndex(var1);
      if (var2 != -1) {
         this.setSelectedTab(var2);
      }

      super.showChildWindow(var1);
   }

   Component getEdgePanel() {
      if (!this.rootWindow.isHeavyweightSupported()) {
         return this.edgePanel;
      } else {
         if (this.heavyWeightEdgePanel == null) {
            this.heavyWeightEdgePanel = new WindowBar$4(this, this.edgePanel);
            this.heavyWeightEdgePanel.setVisible(false);
         }

         return this.heavyWeightEdgePanel;
      }
   }

   protected void update() {
      this.edgePanel.setResizeWidth(this.getWindowBarProperties().getContentPanelEdgeResizeDistance());
      this.edgePanel.setContinuousLayout(this.getWindowBarProperties().getContinuousLayoutEnabled());
      this.edgePanel.setDragIndicatorColor(this.getWindowBarProperties().getDragIndicatorColor());
      this.getWindowBarProperties().getComponentProperties().applyTo(this, this.direction.getNextCW());
   }

   private void updateEdgePanelOpaque() {
      if (this.edgePanel != null) {
         BaseContainerUtil.setForcedOpaque(
            this.edgePanel, this.rootWindow.isHeavyweightSupported() || this.contentPanel.getProperties().getShapedPanelProperties().getOpaque()
         );
      }

   }

   public Dimension getPreferredSize() {
      if (this.isEnabled()) {
         Dimension var1 = super.getPreferredSize();
         int var2 = this.getWindowBarProperties().getMinimumWidth();
         return new Dimension(Math.max(var2, var1.width), Math.max(var2, var1.height));
      } else {
         return new Dimension(0, 0);
      }
   }

   protected void tabSelected(WindowTab var1) {
      this.getEdgePanel().setVisible(var1 != null);
      super.tabSelected(var1);
   }

   protected boolean isInsideTabArea(Point var1) {
      return true;
   }

   protected void clearFocus(View var1) {
      super.clearFocus(var1);
      if (var1 != null && !DockingUtil.isAncestor(this, var1)) {
         this.getTabbedPanel().setSelectedTab(null);
      }

   }

   public boolean isMinimized() {
      return true;
   }

   protected boolean acceptsSplitWith(DockingWindow var1) {
      return false;
   }

   DropAction acceptDrop(Point var1, DockingWindow var2) {
      return this.isEnabled() ? super.acceptDrop(var1, var2) : null;
   }

   protected PropertyMap getPropertyObject() {
      return this.getWindowBarProperties().getMap();
   }

   protected PropertyMap createPropertyObject() {
      return new WindowBarProperties().getMap();
   }

   protected void write(ObjectOutputStream var1, WriteContext var2, ViewWriter var3) throws IOException {
      var1.writeInt(this.getContentPanelSize());
      var1.writeBoolean(this.isEnabled());
      this.getWindowItem().writeSettings(var1, var2);
      super.write(var1, var2, var3);
   }

   protected DockingWindow newRead(ObjectInputStream var1, ReadContext var2, ViewReader var3) throws IOException {
      this.setContentPanelSize(var1.readInt());
      this.setEnabled(var1.readBoolean());
      this.getWindowItem().readSettings(var1, var2);
      super.newRead(var1, var2, var3);
      return this;
   }

   protected DockingWindow oldRead(ObjectInputStream var1, ReadContext var2) throws IOException {
      super.oldRead(var1, var2);
      this.setContentPanelSize(var1.readInt());
      this.setEnabled(var1.readBoolean());
      return this;
   }
}
