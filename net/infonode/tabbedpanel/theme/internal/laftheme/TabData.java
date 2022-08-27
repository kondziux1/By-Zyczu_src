package net.infonode.tabbedpanel.theme.internal.laftheme;

import java.awt.Dimension;
import java.util.ArrayList;
import net.infonode.gui.DimensionUtil;
import net.infonode.tabbedpanel.Tab;
import net.infonode.tabbedpanel.TabbedPanel;
import net.infonode.util.Direction;

class TabData {
   private final ArrayList tabList = new ArrayList();
   private final ArrayList visibleTabRects = new ArrayList();
   private TabbedPanel tabbedPanel;
   private Direction areaOrientation;
   private int tabAreaHeight;
   private int tabAreaWidth;
   private int selectedTabPainterIndex;
   private Dimension tpInternalSize;
   private Tab preTab;
   private Tab postTab;

   public TabData() {
      super();
      this.reset();
   }

   public void reset() {
      this.tabList.clear();
      this.visibleTabRects.clear();
      this.tabbedPanel = null;
      this.areaOrientation = null;
      this.tabAreaHeight = 0;
      this.tabAreaWidth = 0;
      this.selectedTabPainterIndex = -1;
      this.tpInternalSize = null;
      this.preTab = null;
      this.postTab = null;
   }

   public ArrayList getTabList() {
      return this.tabList;
   }

   public ArrayList getVisibleTabRects() {
      return this.visibleTabRects;
   }

   public Direction getAreaOrientation() {
      return this.areaOrientation;
   }

   public TabbedPanel getTabbedPanel() {
      return this.tabbedPanel;
   }

   public void initialize(TabbedPanel var1) {
      this.tabbedPanel = var1;
      this.areaOrientation = var1.getProperties().getTabAreaOrientation();
      this.tpInternalSize = DimensionUtil.getInnerDimension(var1.getSize(), var1.getInsets());
   }

   public Dimension getTabbedPanelSize() {
      return this.tpInternalSize;
   }

   public int getTabbedPanelWidth() {
      return this.tpInternalSize.width;
   }

   public int getTabbedPanelHeight() {
      return this.tpInternalSize.height;
   }

   public boolean isHorizontalLayout() {
      return !this.areaOrientation.isHorizontal();
   }

   public int getSelectedTabPainterIndex() {
      return this.selectedTabPainterIndex;
   }

   public void setSelectedTabPainterIndex(int var1) {
      this.selectedTabPainterIndex = var1;
   }

   public int getTabCount() {
      return this.tabList.size();
   }

   public int getTabAreaHeight() {
      return this.tabAreaHeight;
   }

   public void setTabAreaHeight(int var1) {
      this.tabAreaHeight = var1;
   }

   public int getTabAreaWidth() {
      return this.tabAreaWidth;
   }

   public void setTabAreaWidth(int var1) {
      this.tabAreaWidth = var1;
   }

   public Tab getPostTab() {
      return this.postTab;
   }

   public void setPostTab(Tab var1) {
      this.postTab = var1;
   }

   public Tab getPreTab() {
      return this.preTab;
   }

   public void setPreTab(Tab var1) {
      this.preTab = var1;
   }
}
