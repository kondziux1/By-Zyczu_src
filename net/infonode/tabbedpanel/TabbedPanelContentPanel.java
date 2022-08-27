package net.infonode.tabbedpanel;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import net.infonode.gui.ComponentPaintChecker;
import net.infonode.gui.hover.panel.HoverableShapedPanel;
import net.infonode.gui.panel.BaseContainer;
import net.infonode.gui.panel.BaseContainerUtil;
import net.infonode.properties.gui.InternalPropertiesUtil;
import net.infonode.properties.propertymap.PropertyMapTreeListener;
import net.infonode.properties.propertymap.PropertyMapWeakListenerManager;
import net.infonode.util.Direction;

public class TabbedPanelContentPanel extends BaseContainer {
   private final TabbedPanel tabbedPanel;
   private final HoverableShapedPanel shapedPanel;
   private final ComponentPaintChecker repaintChecker;
   private final PropertyMapTreeListener propertiesListener = new TabbedPanelContentPanel$1(this);

   public TabbedPanelContentPanel(TabbedPanel var1, JComponent var2) {
      super(new BorderLayout());
      this.tabbedPanel = var1;
      this.shapedPanel = new TabbedPanelContentPanel$2(this, new BorderLayout(), var1.getProperties().getContentPanelProperties().getHoverListener(), var1);
      this.repaintChecker = new ComponentPaintChecker(this.shapedPanel);
      this.shapedPanel.add(var2, "Center");
      this.add(this.shapedPanel, "Center");
      this.update();
      PropertyMapWeakListenerManager.addWeakTreeListener(var1.getProperties().getMap(), this.propertiesListener);
      var1.getDraggableComponentBox().addListener(new TabbedPanelContentPanel$3(this));
      var1.addTabListener(new TabbedPanelContentPanel$4(this));
   }

   public TabbedPanel getTabbedPanel() {
      return this.tabbedPanel;
   }

   public TabbedPanelContentPanelProperties getProperties() {
      return this.tabbedPanel.getProperties().getContentPanelProperties();
   }

   private void update() {
      this.getProperties().getComponentProperties().applyTo(this.shapedPanel);
      InternalPropertiesUtil.applyTo(
         this.getProperties().getShapedPanelProperties(), this.shapedPanel, this.tabbedPanel.getProperties().getTabAreaOrientation().getNextCW()
      );
      BaseContainerUtil.setForcedOpaque(this, this.getProperties().getShapedPanelProperties().getOpaque());
   }

   private void repaintBorder() {
      if (this.repaintChecker.isPaintingOk()) {
         Direction var2 = this.tabbedPanel.getProperties().getTabAreaOrientation();
         Rectangle var1;
         if (var2 == Direction.UP) {
            var1 = new Rectangle(0, 0, this.shapedPanel.getWidth(), this.shapedPanel.getInsets().top);
         } else if (var2 == Direction.LEFT) {
            var1 = new Rectangle(0, 0, this.shapedPanel.getInsets().left, this.shapedPanel.getHeight());
         } else if (var2 == Direction.DOWN) {
            var1 = new Rectangle(
               0, this.shapedPanel.getHeight() - this.shapedPanel.getInsets().bottom - 1, this.shapedPanel.getWidth(), this.shapedPanel.getHeight()
            );
         } else {
            var1 = new Rectangle(
               this.shapedPanel.getWidth() - this.shapedPanel.getInsets().right - 1, 0, this.shapedPanel.getWidth(), this.shapedPanel.getHeight()
            );
         }

         this.shapedPanel.repaint(var1);
      }

   }

   private void doProcessMouseEvent(MouseEvent var1) {
      this.processMouseEvent(var1);
   }

   private void doProcessMouseMotionEvent(MouseEvent var1) {
      this.processMouseMotionEvent(var1);
   }
}
