package net.infonode.tabbedpanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import net.infonode.gui.ComponentPaintChecker;
import net.infonode.gui.ComponentUtil;
import net.infonode.gui.DimensionUtil;
import net.infonode.gui.EventUtil;
import net.infonode.gui.ScrollButtonBox;
import net.infonode.gui.draggable.DraggableComponent;
import net.infonode.gui.draggable.DraggableComponentBox;
import net.infonode.gui.draggable.DraggableComponentBoxListener;
import net.infonode.gui.hover.HoverListener;
import net.infonode.gui.hover.panel.HoverableShapedPanel;
import net.infonode.gui.layout.DirectionLayout;
import net.infonode.gui.panel.BaseContainerUtil;
import net.infonode.gui.shaped.panel.ShapedPanel;
import net.infonode.properties.gui.InternalPropertiesUtil;
import net.infonode.properties.gui.util.ButtonProperties;
import net.infonode.properties.gui.util.ComponentProperties;
import net.infonode.properties.gui.util.ShapedPanelProperties;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapTreeListener;
import net.infonode.properties.propertymap.PropertyMapWeakListenerManager;
import net.infonode.tabbedpanel.internal.ShadowPainter;
import net.infonode.tabbedpanel.internal.TabDropDownList;
import net.infonode.tabbedpanel.internal.TabbedHoverUtil;
import net.infonode.util.Direction;
import net.infonode.util.ValueChange;

public class TabbedPanel extends JPanel {
   private int shadowSize = 4;
   private ComponentPaintChecker shadowRepaintChecker;
   private TabDropDownList dropDownList;
   private JComponent contentPanel;
   private JComponent[] tabAreaComponents;
   private Direction tabAreaOrientation;
   private TabDropDownListVisiblePolicy listVisiblePolicy = TabDropDownListVisiblePolicy.NEVER;
   private TabLayoutPolicy listTabLayoutPolicy = TabLayoutPolicy.SCROLLING;
   private DraggableComponentBox draggableComponentBox = new DraggableComponentBox(TabbedUIDefaults.getButtonIconSize(), false);
   private ArrayList listeners;
   private TabbedPanelProperties properties = new TabbedPanelProperties(TabbedPanelProperties.getDefaultProperties());
   private Tab highlightedTab;
   private boolean settingHighlighted;
   private boolean mouseEntered = false;
   private boolean removingSelected = false;
   private TabAreaVisiblePolicy areaVisiblePolicy = TabAreaVisiblePolicy.ALWAYS;
   private TabbedPanel.ShadowPanel componentsPanel = new TabbedPanel.ShadowPanel();
   private ScrollButtonBox scrollButtonBox;
   private GridBagConstraints constraints = new GridBagConstraints();
   private GridBagLayout tabAreaLayoutManager = new TabbedPanel$1(this);
   private HoverableShapedPanel tabAreaContainer = new TabbedPanel$2(
      this, this.tabAreaLayoutManager, this.properties.getTabAreaProperties().getHoverListener()
   );
   private HoverableShapedPanel tabAreaComponentsPanel = new TabbedPanel$3(
      this, new DirectionLayout(), this.properties.getTabAreaComponentsProperties().getHoverListener()
   );
   private DraggableComponentBoxListener draggableComponentBoxListener = new TabbedPanel$4(this);
   private PropertyMapTreeListener propertyChangedListener = new TabbedPanel$5(this);

   private void updatePropertiesForTabAreaComponentsButtons(Map var1) {
      TabbedPanelButtonProperties var2 = this.properties.getButtonProperties();
      Map var3 = (Map)var1.get(var2.getTabDropDownListButtonProperties().getMap());
      if (var3 != null && this.dropDownList != null) {
         AbstractButton var4 = this.dropDownList.getButton();
         if (var3.keySet().contains(ButtonProperties.FACTORY)) {
            var4 = this.properties.getButtonProperties().getTabDropDownListButtonProperties().getFactory().createButton(this);
            this.dropDownList.setButton(var4);
         }

         this.properties.getButtonProperties().getTabDropDownListButtonProperties().applyTo(var4);
      }

      if (this.scrollButtonBox != null) {
         AbstractButton[] var8 = new AbstractButton[]{
            this.scrollButtonBox.getUpButton(),
            this.scrollButtonBox.getDownButton(),
            this.scrollButtonBox.getLeftButton(),
            this.scrollButtonBox.getRightButton()
         };
         ButtonProperties[] var5 = new ButtonProperties[]{
            var2.getScrollUpButtonProperties(),
            var2.getScrollDownButtonProperties(),
            var2.getScrollLeftButtonProperties(),
            var2.getScrollRightButtonProperties()
         };

         for(int var6 = 0; var6 < var8.length; ++var6) {
            var3 = (Map)var1.get(var5[var6].getMap());
            if (var3 != null) {
               if (var3.keySet().contains(ButtonProperties.FACTORY)) {
                  var8[var6] = var5[var6].getFactory().createButton(this);
               }

               var5[var6].applyTo(var8[var6]);
            }
         }

         this.scrollButtonBox.setButtons(var8[0], var8[1], var8[2], var8[3]);
      }

   }

   private void updateAllDefaultValues() {
      this.updateAllTabsProperties();
      this.draggableComponentBox.setScrollEnabled(this.properties.getTabLayoutPolicy() == TabLayoutPolicy.SCROLLING);
      this.updateTabDropDownList();
      this.draggableComponentBox.setScrollOffset(this.properties.getTabScrollingOffset());
      this.draggableComponentBox.setEnsureSelectedVisible(this.properties.getEnsureSelectedTabVisible());
      this.tabAreaOrientation = this.properties.getTabAreaOrientation();
      this.updatePropertiesForTabAreaLayoutConstraints();
      this.componentsPanel.add(this.tabAreaContainer, ComponentUtil.getBorderLayoutOrientation(this.tabAreaOrientation));
      this.componentsPanel.revalidate();
      this.draggableComponentBox.setComponentSpacing(this.properties.getTabSpacing());
      this.draggableComponentBox.setDepthSortOrder(this.properties.getTabDepthOrderPolicy() == TabDepthOrderPolicy.DESCENDING);
      this.draggableComponentBox.setAutoSelect(this.properties.getAutoSelectTab());
      this.shadowSize = this.properties.getShadowSize();
      this.componentsPanel
         .setBorder(this.contentPanel != null && this.properties.getShadowEnabled() ? new EmptyBorder(0, 0, this.shadowSize, this.shadowSize) : null);
      this.componentsPanel.setHoverListener(this.properties.getHoverListener());
      this.tabAreaContainer.setHoverListener(this.properties.getTabAreaProperties().getHoverListener());
      ShapedPanelProperties var1 = this.properties.getTabAreaProperties().getShapedPanelProperties();
      this.properties.getTabAreaProperties().getComponentProperties().applyTo(this.tabAreaContainer);
      this.updateIntelligentInsets(this.tabAreaContainer, this.properties.getTabAreaProperties().getComponentProperties());
      this.updateShapedPanelProperties(this.tabAreaContainer, this.properties.getTabAreaProperties().getShapedPanelProperties());
      this.tabAreaComponentsPanel.setHoverListener(this.properties.getTabAreaComponentsProperties().getHoverListener());
      this.updatePropertiesForTabAreaLayoutConstraints();
      var1 = this.properties.getTabAreaComponentsProperties().getShapedPanelProperties();
      this.properties.getTabAreaComponentsProperties().getComponentProperties().applyTo(this.tabAreaComponentsPanel);
      this.updateIntelligentInsets(this.tabAreaComponentsPanel, this.properties.getTabAreaComponentsProperties().getComponentProperties());
      this.updateShapedPanelProperties(this.tabAreaComponentsPanel, var1);
      this.tabAreaComponentsPanel
         .setHorizontalFlip(
            this.tabAreaOrientation != Direction.DOWN && this.tabAreaOrientation != Direction.LEFT ? var1.getHorizontalFlip() : !var1.getHorizontalFlip()
         );
      this.tabAreaComponentsPanel.setVerticalFlip(var1.getVerticalFlip());
      this.updatePanelOpaque();
   }

   private void updateProperties(Map var1) {
      Map var2 = this.getMap(var1, this.properties.getMap());
      if (var2 != null) {
         Set var3 = var2.keySet();
         if (var3.contains(TabbedPanelProperties.TAB_REORDER_ENABLED)
            || var2.keySet().contains(TabbedPanelProperties.ABORT_DRAG_KEY)
            || var2.keySet().contains(TabbedPanelProperties.TAB_SELECT_TRIGGER)) {
            this.updateAllTabsProperties();
         }

         if (var3.contains(TabbedPanelProperties.TAB_LAYOUT_POLICY) && this.getTabCount() > 1) {
            this.draggableComponentBox
               .setScrollEnabled((TabLayoutPolicy)((ValueChange)var2.get(TabbedPanelProperties.TAB_LAYOUT_POLICY)).getNewValue() == TabLayoutPolicy.SCROLLING);
         }

         if (var3.contains(TabbedPanelProperties.TAB_DROP_DOWN_LIST_VISIBLE_POLICY)) {
            this.updateTabDropDownList();
         }

         if (var3.contains(TabbedPanelProperties.TAB_SCROLLING_OFFSET)) {
            this.draggableComponentBox.setScrollOffset(((ValueChange)var2.get(TabbedPanelProperties.TAB_SCROLLING_OFFSET)).getNewValue());
         }

         if (var3.contains(TabbedPanelProperties.ENSURE_SELECTED_VISIBLE)) {
            this.draggableComponentBox.setEnsureSelectedVisible(((ValueChange)var2.get(TabbedPanelProperties.ENSURE_SELECTED_VISIBLE)).getNewValue());
         }

         if (var3.contains(TabbedPanelProperties.TAB_AREA_ORIENTATION)) {
            this.tabAreaOrientation = (Direction)((ValueChange)var2.get(TabbedPanelProperties.TAB_AREA_ORIENTATION)).getNewValue();
            this.updatePropertiesForTabAreaLayoutConstraints();
            this.componentsPanel.remove(this.tabAreaContainer);
            this.componentsPanel.add(this.tabAreaContainer, ComponentUtil.getBorderLayoutOrientation(this.tabAreaOrientation));
            this.componentsPanel.revalidate();
            this.properties.getTabAreaComponentsProperties().getComponentProperties().applyTo(this.tabAreaComponentsPanel);
            this.updateIntelligentInsets(this.tabAreaContainer, this.properties.getTabAreaProperties().getComponentProperties());
            this.tabAreaComponentsPanel.setDirection(this.tabAreaOrientation.getNextCW());
            this.updateIntelligentInsets(this.tabAreaComponentsPanel, this.properties.getTabAreaComponentsProperties().getComponentProperties());
         }

         if (var3.contains(TabbedPanelProperties.TAB_SPACING)) {
            this.draggableComponentBox.setComponentSpacing(((ValueChange)var2.get(TabbedPanelProperties.TAB_SPACING)).getNewValue());
         }

         if (var3.contains(TabbedPanelProperties.TAB_DEPTH_ORDER)) {
            this.draggableComponentBox
               .setDepthSortOrder(
                  (TabDepthOrderPolicy)((ValueChange)var2.get(TabbedPanelProperties.TAB_DEPTH_ORDER)).getNewValue() == TabDepthOrderPolicy.DESCENDING
               );
         }

         if (var3.contains(TabbedPanelProperties.AUTO_SELECT_TAB)) {
            this.draggableComponentBox.setAutoSelect(((ValueChange)var2.get(TabbedPanelProperties.AUTO_SELECT_TAB)).getNewValue());
         }

         if (var3.contains(TabbedPanelProperties.SHADOW_ENABLED)
            || var2.keySet().contains(TabbedPanelProperties.SHADOW_STRENGTH)
            || var2.keySet().contains(TabbedPanelProperties.SHADOW_COLOR)
            || var2.keySet().contains(TabbedPanelProperties.SHADOW_BLEND_AREA_SIZE)
            || var2.keySet().contains(TabbedPanelProperties.SHADOW_SIZE)
            || var2.keySet().contains(TabbedPanelProperties.PAINT_TAB_AREA_SHADOW)) {
            this.shadowSize = this.properties.getShadowSize();
            this.componentsPanel
               .setBorder(this.contentPanel != null && this.properties.getShadowEnabled() ? new EmptyBorder(0, 0, this.shadowSize, this.shadowSize) : null);
         }

         if (var3.contains(TabbedPanelProperties.HOVER_LISTENER)) {
            this.componentsPanel.setHoverListener((HoverListener)((ValueChange)var2.get(TabbedPanelProperties.HOVER_LISTENER)).getNewValue());
         }
      }

      this.updatePanelOpaque();
   }

   private void updatePropertiesForTabArea(Map var1) {
      Map var2 = this.getMap(var1, this.properties.getTabAreaProperties().getMap());
      if (var2 != null) {
         if (var2.keySet().contains(TabAreaProperties.HOVER_LISTENER)) {
            this.tabAreaContainer.setHoverListener((HoverListener)((ValueChange)var2.get(TabAreaProperties.HOVER_LISTENER)).getNewValue());
         }

         this.areaVisiblePolicy = this.getProperties().getTabAreaProperties().getTabAreaVisiblePolicy();
         this.updateTabAreaVisibility();
      }

      var2 = this.getMap(var1, this.properties.getTabAreaProperties().getComponentProperties().getMap());
      Map var3 = this.getMap(var1, this.properties.getTabAreaProperties().getShapedPanelProperties().getMap());
      if (var2 != null || var3 != null) {
         this.properties.getTabAreaProperties().getComponentProperties().applyTo(this.tabAreaContainer);
         this.updateIntelligentInsets(this.tabAreaContainer, this.properties.getTabAreaProperties().getComponentProperties());
         this.updateShapedPanelProperties(this.tabAreaContainer, this.properties.getTabAreaProperties().getShapedPanelProperties());
         this.repaint();
      }

   }

   private void updateIntelligentInsets(JComponent var1, ComponentProperties var2) {
      Direction var3 = this.properties.getTabAreaOrientation();
      Insets var4 = var2.getInsets();
      if (var4 != null) {
         if (var3 == Direction.RIGHT) {
            var4 = new Insets(var4.left, var4.bottom, var4.right, var4.top);
         } else if (var3 == Direction.DOWN) {
            var4 = new Insets(var4.bottom, var4.left, var4.top, var4.right);
         } else if (var3 == Direction.LEFT) {
            var4 = new Insets(var4.left, var4.top, var4.right, var4.bottom);
         }

         Border var5 = var2.getBorder();
         var1.setBorder((Border)(var5 != null ? new CompoundBorder(var5, new EmptyBorder(var4)) : new EmptyBorder(var4)));
      }

   }

   private void updatePropertiesForTabAreaComponentsArea(Map var1) {
      Map var2 = this.getMap(var1, this.properties.getTabAreaComponentsProperties().getMap());
      if (var2 != null) {
         if (var2.keySet().contains(TabAreaComponentsProperties.HOVER_LISTENER)) {
            this.tabAreaComponentsPanel.setHoverListener((HoverListener)((ValueChange)var2.get(TabAreaComponentsProperties.HOVER_LISTENER)).getNewValue());
         }

         if (var2.keySet().contains(TabAreaComponentsProperties.STRETCH_ENABLED)) {
            this.updatePropertiesForTabAreaLayoutConstraints();
         }
      }

      var2 = this.getMap(var1, this.properties.getTabAreaComponentsProperties().getComponentProperties().getMap());
      Map var3 = this.getMap(var1, this.properties.getTabAreaComponentsProperties().getShapedPanelProperties().getMap());
      if (var2 != null || var3 != null) {
         ShapedPanelProperties var4 = this.properties.getTabAreaComponentsProperties().getShapedPanelProperties();
         this.properties.getTabAreaComponentsProperties().getComponentProperties().applyTo(this.tabAreaComponentsPanel);
         this.updateIntelligentInsets(this.tabAreaComponentsPanel, this.properties.getTabAreaComponentsProperties().getComponentProperties());
         this.updateShapedPanelProperties(this.tabAreaComponentsPanel, var4);
         this.tabAreaComponentsPanel
            .setHorizontalFlip(
               this.tabAreaOrientation != Direction.DOWN && this.tabAreaOrientation != Direction.LEFT ? var4.getHorizontalFlip() : !var4.getHorizontalFlip()
            );
         this.tabAreaComponentsPanel.setVerticalFlip(var4.getVerticalFlip());
      }

   }

   private void updatePropertiesForTabAreaLayoutConstraints() {
      boolean var1 = this.properties.getTabAreaComponentsProperties().getStretchEnabled();
      if (this.tabAreaOrientation == Direction.UP) {
         this.setTabAreaLayoutConstraints(this.draggableComponentBox, 0, 0, 2, 1.0, 1.0, 15);
         this.setTabAreaLayoutConstraints(this.tabAreaComponentsPanel, 1, 0, var1 ? 3 : 0, 0.0, 1.0, 15);
         this.updateTabAreaComponentsPanel(Direction.RIGHT, 0, 1);
      } else if (this.tabAreaOrientation == Direction.DOWN) {
         this.setTabAreaLayoutConstraints(this.draggableComponentBox, 0, 0, 2, 1.0, 1.0, 11);
         this.setTabAreaLayoutConstraints(this.tabAreaComponentsPanel, 1, 0, var1 ? 3 : 0, 0.0, 0.0, 11);
         this.updateTabAreaComponentsPanel(Direction.RIGHT, 0, 0);
      } else if (this.tabAreaOrientation == Direction.LEFT) {
         this.setTabAreaLayoutConstraints(this.draggableComponentBox, 0, 0, 3, 1.0, 1.0, 13);
         this.setTabAreaLayoutConstraints(this.tabAreaComponentsPanel, 0, 1, var1 ? 2 : 0, 0.0, 0.0, 13);
         this.updateTabAreaComponentsPanel(Direction.DOWN, 0, 0);
      } else {
         this.setTabAreaLayoutConstraints(this.draggableComponentBox, 0, 0, 3, 1.0, 1.0, 17);
         this.setTabAreaLayoutConstraints(this.tabAreaComponentsPanel, 0, 1, var1 ? 2 : 0, 0.0, 0.0, 17);
         this.updateTabAreaComponentsPanel(Direction.DOWN, 0, 1);
      }

      this.draggableComponentBox.setComponentDirection(this.tabAreaOrientation);
   }

   private Map getMap(Map var1, PropertyMap var2) {
      return var1 != null ? (Map)var1.get(var2) : null;
   }

   private void updateTabAreaVisibility() {
      if (this.areaVisiblePolicy == TabAreaVisiblePolicy.ALWAYS) {
         this.tabAreaContainer.setVisible(true);
      } else if (this.areaVisiblePolicy == TabAreaVisiblePolicy.NEVER) {
         this.tabAreaContainer.setVisible(false);
      } else if (this.areaVisiblePolicy == TabAreaVisiblePolicy.MORE_THAN_ONE_TAB) {
         this.tabAreaContainer.setVisible(this.getTabCount() > 1);
      } else if (this.areaVisiblePolicy == TabAreaVisiblePolicy.TABS_EXIST) {
         this.tabAreaContainer.setVisible(this.getTabCount() > 0);
      }

      if (!this.tabAreaContainer.isVisible()) {
         this.tabAreaContainer.setSize(0, 0);
      }

   }

   public TabbedPanel() {
      super();
      this.initialize(new TabbedPanelContentPanel(this, new TabContentPanel(this)));
   }

   public TabbedPanel(JComponent var1) {
      this(var1, false);
   }

   public TabbedPanel(JComponent var1, boolean var2) {
      super();
      if (var2) {
         this.initialize(new TabbedPanelContentPanel(this, var1));
      } else {
         this.initialize(var1);
      }

   }

   public boolean tabAreaContainsPoint(Point var1) {
      return !this.tabAreaContainer.isVisible() ? false : this.tabAreaContainer.contains(SwingUtilities.convertPoint(this, var1, this.tabAreaContainer));
   }

   public boolean contentAreaContainsPoint(Point var1) {
      return this.contentPanel != null ? this.contentPanel.contains(SwingUtilities.convertPoint(this, var1, this.contentPanel)) : false;
   }

   public boolean isTabAreaVisible() {
      return this.tabAreaContainer.isVisible();
   }

   public void addTab(Tab var1) {
      this.doInsertTab(var1, null, -1);
   }

   public void insertTab(Tab var1, int var2) {
      this.doInsertTab(var1, null, var2);
   }

   public void insertTab(Tab var1, Point var2) {
      this.doInsertTab(var1, var2, -1);
   }

   public void removeTab(Tab var1) {
      if (var1 != null && var1.getTabbedPanel() == this) {
         if (this.getSelectedTab() != var1) {
            var1.setTabbedPanel(null);
         } else {
            this.removingSelected = true;
         }

         this.draggableComponentBox.removeDraggableComponent(var1.getDraggableComponent());
      }

      this.checkIfOnlyOneTab(false);
   }

   public void moveTab(Tab var1, Point var2) {
      this.draggableComponentBox.dragDraggableComponent(var1.getDraggableComponent(), SwingUtilities.convertPoint(this, var2, this.draggableComponentBox));
   }

   public void setSelectedTab(Tab var1) {
      if (this.getSelectedTab() != var1) {
         if (var1 != null) {
            if (var1.isEnabled() && this.getTabIndex(var1) > -1) {
               if (var1.getDraggableComponent() == this.draggableComponentBox.getSelectedDraggableComponent()) {
                  this.setHighlightedTab(var1);
               } else {
                  var1.setSelected(true);
               }
            }
         } else {
            this.draggableComponentBox.selectDraggableComponent(null);
         }

      }
   }

   public Tab getSelectedTab() {
      return this.findTab(this.draggableComponentBox.getSelectedDraggableComponent());
   }

   public void setHighlightedTab(Tab var1) {
      if (!this.settingHighlighted) {
         this.settingHighlighted = true;
         Tab var2 = this.highlightedTab;
         Tab var3 = null;
         if (var2 != var1) {
            this.draggableComponentBox.setTopComponent(var1 != null ? var1.getDraggableComponent() : null);
         }

         if (var1 != null) {
            if (this.getTabIndex(var1) > -1) {
               this.highlightedTab = var1;
               if (var2 != null && var2 != var1) {
                  var2.setHighlighted(false);
               }

               if (var2 != var1) {
                  if (var1.isEnabled()) {
                     var1.setHighlighted(true);
                  } else {
                     var1.setHighlighted(false);
                     this.highlightedTab = null;
                  }
               }

               if (var1.isEnabled() && var1 != var2) {
                  var3 = var1;
               }

               if (var2 != var1) {
                  this.fireHighlightedEvent(var3, var2);
               }
            }
         } else if (var2 != null) {
            this.highlightedTab = null;
            var2.setHighlighted(false);
            this.fireHighlightedEvent(null, var2);
         }

         this.updateShadow();
         this.settingHighlighted = false;
      }

   }

   public Tab getHighlightedTab() {
      return this.highlightedTab;
   }

   public int getTabCount() {
      return this.draggableComponentBox.getDraggableComponentCount();
   }

   public Tab getTabAt(int var1) {
      DraggableComponent var2 = this.draggableComponentBox.getDraggableComponentAt(var1);
      return var2 == null ? null : (Tab)var2.getComponent();
   }

   public int getTabIndex(Tab var1) {
      return var1 == null ? -1 : this.draggableComponentBox.getDraggableComponentIndex(var1.getDraggableComponent());
   }

   public void scrollTabToVisibleArea(Tab var1) {
      if (var1.getTabbedPanel() == this) {
         this.draggableComponentBox.scrollToVisible(var1.getDraggableComponent());
      }

   }

   public void setTabAreaComponents(JComponent[] var1) {
      if (this.tabAreaComponents != null) {
         for(int var2 = 0; var2 < this.tabAreaComponents.length; ++var2) {
            this.tabAreaComponentsPanel.remove(this.tabAreaComponents[var2]);
         }
      }

      this.tabAreaComponents = var1 == null ? null : (JComponent[])var1.clone();
      if (var1 != null) {
         for(int var3 = 0; var3 < var1.length; ++var3) {
            this.tabAreaComponentsPanel.add(var1[var3]);
         }
      }

      this.setTabAreaComponentsButtonsVisible();
      this.tabAreaComponentsPanel.revalidate();
   }

   public boolean isTabAreaComponentsVisible() {
      return this.tabAreaComponentsPanel.isVisible();
   }

   public JComponent[] getTabAreaComponents() {
      return this.tabAreaComponents == null ? null : (JComponent[])this.tabAreaComponents.clone();
   }

   public void addTabListener(TabListener var1) {
      if (this.listeners == null) {
         this.listeners = new ArrayList(2);
      }

      this.listeners.add(var1);
   }

   public void removeTabListener(TabListener var1) {
      if (this.listeners != null) {
         this.listeners.remove(var1);
         if (this.listeners.size() == 0) {
            this.listeners = null;
         }
      }

   }

   public TabbedPanelProperties getProperties() {
      return this.properties;
   }

   public boolean hasContentArea() {
      return this.contentPanel != null;
   }

   DraggableComponentBox getDraggableComponentBox() {
      return this.draggableComponentBox;
   }

   private void initialize(JComponent var1) {
      this.setLayout(new BorderLayout());
      this.shadowRepaintChecker = new ComponentPaintChecker(this);
      this.setOpaque(false);
      this.draggableComponentBox.setOuterParentArea(this.tabAreaContainer);
      this.tabAreaContainer.add(this.tabAreaComponentsPanel);
      this.tabAreaContainer.add(this.draggableComponentBox);
      this.contentPanel = var1;
      this.draggableComponentBox.addListener(this.draggableComponentBoxListener);
      if (var1 != null) {
         this.componentsPanel.add(var1, "Center");
      }

      this.add(this.componentsPanel, "Center");
      this.updateAllDefaultValues();
      PropertyMapWeakListenerManager.addWeakTreeListener(this.properties.getMap(), this.propertyChangedListener);
   }

   private void updateTabDropDownList() {
      TabDropDownListVisiblePolicy var1 = this.properties.getTabDropDownListVisiblePolicy();
      TabLayoutPolicy var2 = this.properties.getTabLayoutPolicy();
      if (var1 != this.listVisiblePolicy || var2 != this.listTabLayoutPolicy) {
         if (this.dropDownList != null) {
            this.tabAreaComponentsPanel.remove(this.dropDownList);
            this.dropDownList.dispose();
            this.dropDownList = null;
         }

         if (var1 == TabDropDownListVisiblePolicy.MORE_THAN_ONE_TAB
            || var1 == TabDropDownListVisiblePolicy.TABS_NOT_VISIBLE && var2 == TabLayoutPolicy.SCROLLING) {
            this.dropDownList = new TabDropDownList(
               this,
               this.properties
                  .getButtonProperties()
                  .getTabDropDownListButtonProperties()
                  .applyTo(this.properties.getButtonProperties().getTabDropDownListButtonProperties().getFactory().createButton(this))
            );
            this.tabAreaComponentsPanel.add(this.dropDownList, this.scrollButtonBox == null ? 0 : 1);
            if (var1 == TabDropDownListVisiblePolicy.TABS_NOT_VISIBLE) {
               this.dropDownList.setVisible(false);
            }
         }
      }

      this.listVisiblePolicy = var1;
      this.listTabLayoutPolicy = var2;
      if (this.dropDownList != null
         && !this.draggableComponentBox.isScrollEnabled()
         && this.listVisiblePolicy == TabDropDownListVisiblePolicy.TABS_NOT_VISIBLE) {
         this.dropDownList.setVisible(false);
      }

      this.tabAreaComponentsPanel.revalidate();
   }

   private void updateAllTabsProperties() {
      Component[] var1 = this.draggableComponentBox.getBoxComponents();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         this.updateTabProperties((Tab)var1[var2]);
      }

   }

   private void updateTabProperties(Tab var1) {
      var1.getDraggableComponent().setAbortDragKeyCode(this.properties.getAbortDragKey());
      var1.getDraggableComponent().setReorderEnabled(this.properties.getTabReorderEnabled());
      var1.getDraggableComponent().setSelectOnMousePress(this.properties.getTabSelectTrigger() == TabSelectTrigger.MOUSE_PRESS);
   }

   private void updateTabAreaComponentsPanel(Direction var1, int var2, int var3) {
      ((DirectionLayout)this.tabAreaComponentsPanel.getLayout()).setDirection(var1);
   }

   private void updateShapedPanelProperties(ShapedPanel var1, ShapedPanelProperties var2) {
      InternalPropertiesUtil.applyTo(var2, var1, this.properties.getTabAreaOrientation().getNextCW());
   }

   private void setTabAreaLayoutConstraints(JComponent var1, int var2, int var3, int var4, double var5, double var7, int var9) {
      this.constraints.gridx = var2;
      this.constraints.gridy = var3;
      this.constraints.fill = var4;
      this.constraints.weightx = var5;
      this.constraints.weighty = var7;
      this.constraints.anchor = var9;
      this.tabAreaLayoutManager.setConstraints(var1, this.constraints);
   }

   private void doInsertTab(Tab var1, Point var2, int var3) {
      if (var1 != null && !this.draggableComponentBox.containsDraggableComponent(var1.getDraggableComponent())) {
         var1.setTabbedPanel(this);
         if (var2 != null) {
            this.draggableComponentBox
               .insertDraggableComponent(var1.getDraggableComponent(), SwingUtilities.convertPoint(this, var2, this.draggableComponentBox));
         } else {
            this.draggableComponentBox.insertDraggableComponent(var1.getDraggableComponent(), var3);
         }

         this.updateTabProperties(var1);
         this.checkIfOnlyOneTab(true);
      }

   }

   private Tab findTab(DraggableComponent var1) {
      return var1 == null ? null : (Tab)var1.getComponent();
   }

   private void checkIfOnlyOneTab(boolean var1) {
      if (this.getTabCount() == 1) {
         this.draggableComponentBox.setScrollEnabled(false);
         this.updateScrollButtons();
      } else if (var1 && this.getTabCount() == 2) {
         this.draggableComponentBox.setScrollEnabled(this.properties.getTabLayoutPolicy() == TabLayoutPolicy.SCROLLING);
         this.updateScrollButtons();
         this.updateTabDropDownList();
      }

   }

   private void setTabAreaComponentsButtonsVisible() {
      if (this.scrollButtonBox != null) {
         boolean var1 = false;
         if (!this.tabAreaOrientation.isHorizontal()) {
            var1 = this.draggableComponentBox.getInnerSize().getWidth() > (double)this.calcScrollWidth();
         } else {
            var1 = this.draggableComponentBox.getInnerSize().getHeight() > (double)this.calcScrollHeight();
         }

         this.scrollButtonBox.setVisible(var1);
         if (this.dropDownList != null && this.listVisiblePolicy == TabDropDownListVisiblePolicy.TABS_NOT_VISIBLE) {
            this.dropDownList.setVisible(var1);
         }

         if (!var1) {
            this.scrollButtonBox.setButton1Enabled(false);
            this.scrollButtonBox.setButton2Enabled(true);
         }
      } else if (this.dropDownList != null && this.listVisiblePolicy == TabDropDownListVisiblePolicy.TABS_NOT_VISIBLE) {
         this.dropDownList.setVisible(false);
      }

      this.tabAreaComponentsPanel.setVisible(ComponentUtil.hasVisibleChildren(this.tabAreaComponentsPanel));
   }

   private int calcScrollWidth() {
      Insets var1 = this.tabAreaComponentsPanel.getInsets();
      boolean var2 = this.dropDownList != null && this.listVisiblePolicy == TabDropDownListVisiblePolicy.TABS_NOT_VISIBLE;
      boolean var3 = var2
         ? ComponentUtil.isOnlyVisibleComponents(new Component[]{this.scrollButtonBox, this.dropDownList})
         : ComponentUtil.isOnlyVisibleComponent(this.scrollButtonBox);
      int var4 = this.tabAreaComponentsPanel.isVisible() && var3 ? var1.left + var1.right : 0;
      int var5 = this.tabAreaComponentsPanel.isVisible()
         ? (int)this.tabAreaComponentsPanel.getPreferredSize().getWidth()
            - var4
            - (this.scrollButtonBox.isVisible() ? this.scrollButtonBox.getWidth() + (var2 ? this.dropDownList.getWidth() : 0) : 0)
         : 0;
      Insets var6 = this.tabAreaContainer.getInsets();
      return this.tabAreaContainer.getWidth() - var5 - var6.left - var6.right;
   }

   private int calcScrollHeight() {
      Insets var1 = this.tabAreaComponentsPanel.getInsets();
      boolean var2 = this.listVisiblePolicy == TabDropDownListVisiblePolicy.TABS_NOT_VISIBLE;
      boolean var3 = var2
         ? ComponentUtil.isOnlyVisibleComponents(new Component[]{this.scrollButtonBox, this.dropDownList})
         : ComponentUtil.isOnlyVisibleComponent(this.scrollButtonBox);
      int var4 = this.tabAreaComponentsPanel.isVisible() && var3 ? var1.top + var1.bottom : 0;
      int var5 = this.tabAreaComponentsPanel.isVisible()
         ? (int)this.tabAreaComponentsPanel.getPreferredSize().getHeight()
            - var4
            - (this.scrollButtonBox.isVisible() ? this.scrollButtonBox.getHeight() + (var2 ? this.dropDownList.getHeight() : 0) : 0)
         : 0;
      Insets var6 = this.tabAreaContainer.getInsets();
      return this.tabAreaContainer.getHeight() - var5 - var6.top - var6.bottom;
   }

   private void updateScrollButtons() {
      ScrollButtonBox var1 = this.scrollButtonBox;
      this.scrollButtonBox = this.draggableComponentBox.getScrollButtonBox();
      if (var1 != this.scrollButtonBox) {
         if (var1 != null) {
            this.tabAreaComponentsPanel.remove(var1);
         }

         if (this.scrollButtonBox != null) {
            this.scrollButtonBox
               .setButtons(
                  this.properties
                     .getButtonProperties()
                     .getScrollUpButtonProperties()
                     .applyTo(this.properties.getButtonProperties().getScrollUpButtonProperties().getFactory().createButton(this)),
                  this.properties
                     .getButtonProperties()
                     .getScrollDownButtonProperties()
                     .applyTo(this.properties.getButtonProperties().getScrollDownButtonProperties().getFactory().createButton(this)),
                  this.properties
                     .getButtonProperties()
                     .getScrollLeftButtonProperties()
                     .applyTo(this.properties.getButtonProperties().getScrollLeftButtonProperties().getFactory().createButton(this)),
                  this.properties
                     .getButtonProperties()
                     .getScrollRightButtonProperties()
                     .applyTo(this.properties.getButtonProperties().getScrollRightButtonProperties().getFactory().createButton(this))
               );
            this.scrollButtonBox.setVisible(false);
            this.tabAreaComponentsPanel.add(this.scrollButtonBox, 0);
         }

         this.tabAreaComponentsPanel.revalidate();
      }

   }

   private void fireTabMoved(Tab var1) {
      if (this.listeners != null) {
         TabEvent var2 = new TabEvent(this, var1);
         Object[] var3 = this.listeners.toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((TabListener)var3[var4]).tabMoved(var2);
         }
      }

   }

   private void fireDraggedEvent(Tab var1, MouseEvent var2) {
      if (this.listeners != null) {
         TabDragEvent var3 = new TabDragEvent(this, EventUtil.convert(var2, var1));
         Object[] var4 = this.listeners.toArray();

         for(int var5 = 0; var5 < var4.length; ++var5) {
            ((TabListener)var4[var5]).tabDragged(var3);
         }
      }

   }

   private void fireDroppedEvent(Tab var1, MouseEvent var2) {
      if (this.listeners != null) {
         TabDragEvent var3 = new TabDragEvent(this, EventUtil.convert(var2, var1));
         Object[] var4 = this.listeners.toArray();

         for(int var5 = 0; var5 < var4.length; ++var5) {
            ((TabListener)var4[var5]).tabDropped(var3);
         }
      }

   }

   private void fireNotDroppedEvent(Tab var1) {
      if (this.listeners != null) {
         TabEvent var2 = new TabEvent(this, var1);
         Object[] var3 = this.listeners.toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((TabListener)var3[var4]).tabDragAborted(var2);
         }
      }

   }

   private void fireSelectedEvent(Tab var1, Tab var2) {
      if (this.listeners != null) {
         TabStateChangedEvent var3 = new TabStateChangedEvent(this, this, var2, var2, var1);
         Object[] var4 = this.listeners.toArray();

         for(int var5 = 0; var5 < var4.length; ++var5) {
            ((TabListener)var4[var5]).tabDeselected(var3);
         }

         var3 = new TabStateChangedEvent(this, this, var1, var2, var1);
         var4 = this.listeners.toArray();

         for(int var8 = 0; var8 < var4.length; ++var8) {
            ((TabListener)var4[var8]).tabSelected(var3);
         }
      }

   }

   private void fireHighlightedEvent(Tab var1, Tab var2) {
      if (this.listeners != null) {
         TabStateChangedEvent var3 = new TabStateChangedEvent(this, this, var2, var2, var1);
         Object[] var4 = this.listeners.toArray();

         for(int var5 = 0; var5 < var4.length; ++var5) {
            ((TabListener)var4[var5]).tabDehighlighted(var3);
         }

         var3 = new TabStateChangedEvent(this, this, var1, var2, var1);
         var4 = this.listeners.toArray();

         for(int var8 = 0; var8 < var4.length; ++var8) {
            ((TabListener)var4[var8]).tabHighlighted(var3);
         }
      }

   }

   private void fireAddedEvent(Tab var1) {
      if (this.listeners != null) {
         TabEvent var2 = new TabEvent(this, var1);
         Object[] var3 = this.listeners.toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((TabListener)var3[var4]).tabAdded(var2);
         }
      }

   }

   private void fireRemovedEvent(Tab var1) {
      if (this.listeners != null) {
         TabRemovedEvent var2 = new TabRemovedEvent(this, var1, this);
         Object[] var3 = this.listeners.toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((TabListener)var3[var4]).tabRemoved(var2);
         }
      }

   }

   protected void processMouseEvent(MouseEvent var1) {
      if (var1.getID() == 504) {
         if (!this.mouseEntered) {
            this.mouseEntered = true;
            super.processMouseEvent(var1);
         }
      } else if (var1.getID() == 505) {
         if (!this.contains(var1.getPoint())) {
            this.mouseEntered = false;
            super.processMouseEvent(var1);
         }
      } else {
         super.processMouseEvent(var1);
      }

   }

   void doProcessMouseEvent(MouseEvent var1) {
      this.processMouseEvent(SwingUtilities.convertMouseEvent((Component)var1.getSource(), var1, this));
   }

   void doProcessMouseMotionEvent(MouseEvent var1) {
      this.processMouseMotionEvent(SwingUtilities.convertMouseEvent((Component)var1.getSource(), var1, this));
   }

   private void updateShadow() {
      if (this.shadowRepaintChecker.isPaintingOk() && this.contentPanel != null && this.properties.getShadowEnabled()) {
         Point var1 = SwingUtilities.convertPoint(this.tabAreaContainer, new Point(0, 0), this);
         this.repaint(var1.x, var1.y, this.tabAreaContainer.getWidth() + this.shadowSize, this.tabAreaContainer.getHeight() + this.shadowSize);
      }

   }

   private void updatePanelOpaque() {
      if (this.properties.getShadowEnabled()
         || !this.properties.getTabAreaProperties().getShapedPanelProperties().getOpaque()
         || this.contentPanel != null && !this.properties.getContentPanelProperties().getShapedPanelProperties().getOpaque()) {
         BaseContainerUtil.setForcedOpaque(this.componentsPanel, false);
         this.setOpaque(false);
      } else {
         BaseContainerUtil.setForcedOpaque(this.componentsPanel, true);
         this.setOpaque(true);
      }

   }

   private class HoverablePanel extends HoverableShapedPanel {
      public HoverablePanel(LayoutManager var2, HoverListener var3) {
         super(var2, var3, TabbedPanel.this);
      }

      protected void processMouseEvent(MouseEvent var1) {
         super.processMouseEvent(var1);
         TabbedPanel.this.doProcessMouseEvent(var1);
      }

      protected void processMouseMotionEvent(MouseEvent var1) {
         super.processMouseMotionEvent(var1);
         TabbedPanel.this.doProcessMouseMotionEvent(var1);
      }

      public boolean acceptHover(ArrayList var1) {
         return this.getHoverListener() == null
            ? false
            : TabbedHoverUtil.acceptTabbedPanelHover(TabbedPanel.this.properties.getHoverPolicy(), var1, TabbedPanel.this, this);
      }
   }

   private class ShadowPanel extends TabbedPanel.HoverablePanel {
      ShadowPanel() {
         super(new BorderLayout(), TabbedPanel.this.properties.getHoverListener());
         this.setCursor(null);
      }

      public boolean contains(int var1, int var2) {
         return TabbedPanel.this.properties.getShadowEnabled() ? this.doContains(var1, var2) : super.contains(var1, var2);
      }

      public boolean inside(int var1, int var2) {
         return TabbedPanel.this.properties.getShadowEnabled() ? this.doContains(var1, var2) : super.inside(var1, var2);
      }

      private boolean doContains(int var1, int var2) {
         Dimension var3 = DimensionUtil.getInnerDimension(this.getSize(), this.getInsets());
         return var1 >= 0 && var2 >= 0 && (double)var1 < var3.getWidth() && (double)var2 < var3.getHeight();
      }

      public void paint(Graphics var1) {
         super.paint(var1);
         if (TabbedPanel.this.contentPanel != null && TabbedPanel.this.properties.getShadowEnabled()) {
            new ShadowPainter(
                  this,
                  TabbedPanel.this.componentsPanel,
                  TabbedPanel.this.highlightedTab,
                  TabbedPanel.this.contentPanel,
                  TabbedPanel.this.tabAreaComponentsPanel,
                  TabbedPanel.this.tabAreaContainer,
                  TabbedPanel.this.draggableComponentBox,
                  TabbedPanel.this.properties.getTabAreaOrientation(),
                  TabbedPanel.this.properties.getPaintTabAreaShadow(),
                  TabbedPanel.this.shadowSize,
                  TabbedPanel.this.properties.getShadowBlendAreaSize(),
                  TabbedPanel.this.properties.getShadowColor(),
                  TabbedPanel.this.properties.getShadowStrength(),
                  TabbedPanel.this.getTabIndex(TabbedPanel.this.getHighlightedTab()) == TabbedPanel.this.getTabCount() - 1
               )
               .paint(var1);
         }
      }
   }
}
