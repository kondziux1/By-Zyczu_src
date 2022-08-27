package net.infonode.docking.properties;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import net.infonode.docking.DefaultButtonFactories;
import net.infonode.docking.action.CloseWithAbortWindowAction;
import net.infonode.docking.action.DockWithAbortWindowAction;
import net.infonode.docking.action.MaximizeWithAbortWindowAction;
import net.infonode.docking.action.MinimizeWithAbortWindowAction;
import net.infonode.docking.action.RestoreViewWithAbortTitleBarAction;
import net.infonode.docking.action.RestoreWithAbortWindowAction;
import net.infonode.docking.action.UndockWithAbortWindowAction;
import net.infonode.docking.drop.AcceptAllDropFilter;
import net.infonode.gui.DynamicUIManager;
import net.infonode.gui.UIManagerUtil;
import net.infonode.gui.colorprovider.FixedColorProvider;
import net.infonode.gui.componentpainter.ComponentPainter;
import net.infonode.gui.componentpainter.GradientComponentPainter;
import net.infonode.gui.componentpainter.SolidColorComponentPainter;
import net.infonode.properties.base.Property;
import net.infonode.properties.gui.util.ComponentProperties;
import net.infonode.properties.gui.util.ShapedPanelProperties;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapContainer;
import net.infonode.properties.propertymap.PropertyMapFactory;
import net.infonode.properties.propertymap.PropertyMapGroup;
import net.infonode.properties.propertymap.PropertyMapProperty;
import net.infonode.properties.propertymap.PropertyMapValueHandler;
import net.infonode.properties.types.BooleanProperty;
import net.infonode.properties.types.IntegerProperty;
import net.infonode.tabbedpanel.TabAreaVisiblePolicy;
import net.infonode.tabbedpanel.TabDropDownListVisiblePolicy;
import net.infonode.tabbedpanel.TabSelectTrigger;
import net.infonode.tabbedpanel.TabbedPanelButtonProperties;
import net.infonode.tabbedpanel.TabbedPanelProperties;
import net.infonode.tabbedpanel.TabbedUIDefaults;
import net.infonode.tabbedpanel.border.TabAreaLineBorder;
import net.infonode.tabbedpanel.titledtab.TitledTabProperties;
import net.infonode.tabbedpanel.titledtab.TitledTabSizePolicy;
import net.infonode.util.Direction;

public class RootWindowProperties extends PropertyMapContainer {
   public static final int DEFAULT_WINDOW_TAB_BUTTON_ICON_SIZE = 10;
   public static final PropertyMapGroup PROPERTIES = new PropertyMapGroup("Root Window Properties", "");
   public static final PropertyMapProperty COMPONENT_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Component Properties", "The root window component property values.", ComponentProperties.PROPERTIES
   );
   public static final PropertyMapProperty SHAPED_PANEL_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Shaped Panel Properties", "The root window shaped panel property values.", ShapedPanelProperties.PROPERTIES
   );
   public static final PropertyMapProperty WINDOW_AREA_PROPERTIES = new PropertyMapProperty(
      PROPERTIES,
      "Window Area Properties",
      "The window area component property values. The window area is the area inside the WindowBars.",
      ComponentProperties.PROPERTIES
   );
   public static final PropertyMapProperty WINDOW_AREA_SHAPED_PANEL_PROPERTIES = new PropertyMapProperty(
      PROPERTIES,
      "Window Area Shaped Panel Properties",
      "The window area shaped panel property values. The window area is the area inside the WindowBars.",
      ShapedPanelProperties.PROPERTIES
   );
   public static final PropertyMapProperty DRAG_RECTANGLE_SHAPED_PANEL_PROPERTIES = new PropertyMapProperty(
      PROPERTIES,
      "Drag Rectangle Shaped Panel Properties",
      "Shaped panel properties for the drag rectangle. Setting a painter disables the default drag rectangle.",
      ShapedPanelProperties.PROPERTIES
   );
   public static final IntegerProperty DRAG_RECTANGLE_BORDER_WIDTH = IntegerProperty.createPositive(
      PROPERTIES,
      "Drag Rectangle Border Width",
      "The width of the drag rectangle border. The drag rectangle will only be painted if the painter of the '"
         + DRAG_RECTANGLE_SHAPED_PANEL_PROPERTIES.getName()
         + "' property is not set.",
      2,
      PropertyMapValueHandler.INSTANCE
   );
   public static final PropertyMapProperty DRAG_LABEL_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Drag Label Properties", "The window drag label property values.", ComponentProperties.PROPERTIES
   );
   public static final PropertyMapProperty DOCKING_WINDOW_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Docking Window Properties", "Default property values for DockingWindows inside this RootWindow.", DockingWindowProperties.PROPERTIES
   );
   public static final PropertyMapProperty TAB_WINDOW_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Tab Window Properties", "Default property values for TabWindows inside this RootWindow.", TabWindowProperties.PROPERTIES
   );
   public static final PropertyMapProperty SPLIT_WINDOW_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Split Window Properties", "Default property values for SplitWindows inside this RootWindow.", SplitWindowProperties.PROPERTIES
   );
   public static final PropertyMapProperty FLOATING_WINDOW_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Floating Window Properties", "Default property values for FloatingWindows inside this RootWindow.", FloatingWindowProperties.PROPERTIES
   );
   public static final PropertyMapProperty VIEW_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "View Properties", "Default property values for Views inside this RootWindow.", ViewProperties.PROPERTIES
   );
   public static final BooleanProperty DOUBLE_CLICK_RESTORES_WINDOW = new BooleanProperty(
      PROPERTIES, "Double Click Restores Window", "Double clicking on a minimized window in a window bar restores it.", PropertyMapValueHandler.INSTANCE
   );
   public static final BooleanProperty RECURSIVE_TABS_ENABLED = new BooleanProperty(
      PROPERTIES,
      "Recursive Tabs Enabled",
      "If true, makes it possible for the user to create tab windows inside other tab windows by dragging windows. If false, only one level of tab windows is allowed. Changing the value of this property does not alter the window tree.",
      PropertyMapValueHandler.INSTANCE
   );
   public static final IntegerProperty EDGE_SPLIT_DISTANCE = IntegerProperty.createPositive(
      PROPERTIES,
      "Edge Split Distance",
      "Inside this distance from the window edge a mouse drag will trigger a window split.",
      3,
      PropertyMapValueHandler.INSTANCE
   );
   public static final IntegerProperty ABORT_DRAG_KEY = IntegerProperty.createPositive(
      PROPERTIES, "Abort Drag Key Code", "Key code for the key that aborts a drag.", 3, PropertyMapValueHandler.INSTANCE
   );
   public static final PropertyMapProperty WINDOW_BAR_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Window Bar Properties", "Default property values for WindowBars inside this RootWindow.", WindowBarProperties.PROPERTIES
   );
   private static RootWindowProperties DEFAULT_VALUES = new RootWindowProperties(PROPERTIES.getDefaultMap());

   private static void setTabProperties() {
      WindowTabProperties var0 = DEFAULT_VALUES.getTabWindowProperties().getTabProperties();
      PropertyMapProperty[] var1 = new PropertyMapProperty[]{
         WindowTabStateProperties.CLOSE_BUTTON_PROPERTIES,
         WindowTabStateProperties.MINIMIZE_BUTTON_PROPERTIES,
         WindowTabStateProperties.RESTORE_BUTTON_PROPERTIES,
         WindowTabStateProperties.UNDOCK_BUTTON_PROPERTIES,
         WindowTabStateProperties.DOCK_BUTTON_PROPERTIES
      };

      for(int var2 = 0; var2 < var1.length; ++var2) {
         for(int var3 = 0; var3 < WindowTabButtonProperties.PROPERTIES.getPropertyCount(); ++var3) {
            Property var4 = WindowTabButtonProperties.PROPERTIES.getProperty(var3);
            var1[var2]
               .get(var0.getHighlightedButtonProperties().getMap())
               .createRelativeRef(var4, var1[var2].get(var0.getNormalButtonProperties().getMap()), var4);
            var1[var2]
               .get(var0.getFocusedButtonProperties().getMap())
               .createRelativeRef(var4, var1[var2].get(var0.getHighlightedButtonProperties().getMap()), var4);
         }
      }

      var0.getTitledTabProperties().getNormalProperties().setToolTipEnabled(true).getComponentProperties().setInsets(new Insets(0, 3, 0, 2));
      var0.getTitledTabProperties().setSizePolicy(TitledTabSizePolicy.INDIVIDUAL_SIZE);
      var0.getNormalButtonProperties()
         .getCloseButtonProperties()
         .setFactory(DefaultButtonFactories.getCloseButtonFactory())
         .setTo(CloseWithAbortWindowAction.INSTANCE);
      var0.getNormalButtonProperties()
         .getUndockButtonProperties()
         .setFactory(DefaultButtonFactories.getUndockButtonFactory())
         .setVisible(false)
         .setTo(UndockWithAbortWindowAction.INSTANCE);
      var0.getNormalButtonProperties()
         .getDockButtonProperties()
         .setFactory(DefaultButtonFactories.getDockButtonFactory())
         .setVisible(false)
         .setTo(DockWithAbortWindowAction.INSTANCE);
      var0.getNormalButtonProperties()
         .getRestoreButtonProperties()
         .setFactory(DefaultButtonFactories.getRestoreButtonFactory())
         .setVisible(false)
         .setTo(RestoreWithAbortWindowAction.INSTANCE);
      var0.getNormalButtonProperties()
         .getMinimizeButtonProperties()
         .setFactory(DefaultButtonFactories.getMinimizeButtonFactory())
         .setVisible(false)
         .setTo(MinimizeWithAbortWindowAction.INSTANCE);
      var0.getTitledTabProperties().setFocusable(false);
      var0.getHighlightedButtonProperties().getCloseButtonProperties().setVisible(true);
      var0.getHighlightedButtonProperties().getMinimizeButtonProperties().setVisible(true);
      var0.getHighlightedButtonProperties().getRestoreButtonProperties().setVisible(true);
      var0.getHighlightedButtonProperties().getUndockButtonProperties().setVisible(true);
      var0.getHighlightedButtonProperties().getDockButtonProperties().setVisible(true);
   }

   private static void setTabbedPanelProperties() {
      TabWindowProperties var0 = DEFAULT_VALUES.getTabWindowProperties();
      var0.getTabbedPanelProperties()
         .setTabDropDownListVisiblePolicy(TabDropDownListVisiblePolicy.TABS_NOT_VISIBLE)
         .setTabSelectTrigger(TabSelectTrigger.MOUSE_RELEASE)
         .setEnsureSelectedTabVisible(true)
         .setTabReorderEnabled(false)
         .setHighlightPressedTab(false)
         .setShadowEnabled(true);
      var0.getTabbedPanelProperties().getTabAreaComponentsProperties().getComponentProperties().setInsets(new Insets(1, 3, 1, 3));
      var0.getCloseButtonProperties().setFactory(DefaultButtonFactories.getCloseButtonFactory()).setVisible(true).setTo(CloseWithAbortWindowAction.INSTANCE);
      var0.getRestoreButtonProperties()
         .setFactory(DefaultButtonFactories.getRestoreButtonFactory())
         .setVisible(true)
         .setTo(RestoreWithAbortWindowAction.INSTANCE);
      var0.getMinimizeButtonProperties()
         .setFactory(DefaultButtonFactories.getMinimizeButtonFactory())
         .setVisible(true)
         .setTo(MinimizeWithAbortWindowAction.INSTANCE);
      var0.getMaximizeButtonProperties()
         .setFactory(DefaultButtonFactories.getMaximizeButtonFactory())
         .setVisible(true)
         .setTo(MaximizeWithAbortWindowAction.INSTANCE);
      var0.getUndockButtonProperties()
         .setFactory(DefaultButtonFactories.getUndockButtonFactory())
         .setVisible(true)
         .setTo(UndockWithAbortWindowAction.INSTANCE);
      var0.getDockButtonProperties().setFactory(DefaultButtonFactories.getDockButtonFactory()).setVisible(true).setTo(DockWithAbortWindowAction.INSTANCE);
      TabbedPanelButtonProperties var1 = var0.getTabbedPanelProperties().getButtonProperties();
      var1.getTabDropDownListButtonProperties().setToolTipText("Tab List");
      var1.getScrollLeftButtonProperties().setToolTipText("Scroll Left");
      var1.getScrollRightButtonProperties().setToolTipText("Scroll Right");
      var1.getScrollUpButtonProperties().setToolTipText("Scroll Up");
      var1.getScrollDownButtonProperties().setToolTipText("Scroll Down");
   }

   private static void setWindowBarProperties() {
      WindowBarProperties var0 = DEFAULT_VALUES.getWindowBarProperties();
      var0.setMinimumWidth(4);
      var0.setContentPanelEdgeResizeEdgeDistance(6);
      var0.setContinuousLayoutEnabled(true);
      var0.setDragIndicatorColor(Color.DARK_GRAY);
      var0.getTabWindowProperties()
         .getTabbedPanelProperties()
         .setTabDeselectable(true)
         .setAutoSelectTab(false)
         .getTabAreaComponentsProperties()
         .setStretchEnabled(true)
         .getComponentProperties()
         .setBorder(new TabAreaLineBorder());
      var0.getTabWindowProperties().getTabbedPanelProperties().getContentPanelProperties().getComponentProperties().setInsets(new Insets(4, 4, 4, 4));
      var0.getTabWindowProperties().getTabbedPanelProperties().getContentPanelProperties().getShapedPanelProperties().setOpaque(true);
      var0.getTabWindowProperties().getTabbedPanelProperties().getTabAreaProperties().setTabAreaVisiblePolicy(TabAreaVisiblePolicy.ALWAYS);
      WindowTabProperties var1 = DEFAULT_VALUES.getWindowBarProperties().getTabWindowProperties().getTabProperties();
      var1.getTitledTabProperties().setSizePolicy(TitledTabSizePolicy.EQUAL_SIZE).setHighlightedRaised(0);
      var1.getTitledTabProperties().getNormalProperties().getComponentProperties().setInsets(new Insets(1, 4, 1, 4));
      var1.getNormalButtonProperties().getCloseButtonProperties().setVisible(true);
      var1.getNormalButtonProperties().getRestoreButtonProperties().setVisible(true);
      var1.getNormalButtonProperties().getUndockButtonProperties().setVisible(true);
      var1.getNormalButtonProperties().getDockButtonProperties().setVisible(true);
   }

   private static void setFloatingWindowProperties() {
      for(int var0 = 0; var0 < ComponentProperties.PROPERTIES.getPropertyCount(); ++var0) {
         Property var1 = ComponentProperties.PROPERTIES.getProperty(var0);
         FloatingWindowProperties.COMPONENT_PROPERTIES
            .get(DEFAULT_VALUES.getFloatingWindowProperties().getMap())
            .createRelativeRef(var1, WINDOW_AREA_PROPERTIES.get(DEFAULT_VALUES.getMap()), var1);
      }

      for(int var2 = 0; var2 < ShapedPanelProperties.PROPERTIES.getPropertyCount(); ++var2) {
         Property var3 = ShapedPanelProperties.PROPERTIES.getProperty(var2);
         FloatingWindowProperties.SHAPED_PANEL_PROPERTIES
            .get(DEFAULT_VALUES.getFloatingWindowProperties().getMap())
            .createRelativeRef(var3, WINDOW_AREA_SHAPED_PANEL_PROPERTIES.get(DEFAULT_VALUES.getMap()), var3);
      }

      DEFAULT_VALUES.getFloatingWindowProperties().setAutoCloseEnabled(true);
      DEFAULT_VALUES.getFloatingWindowProperties().setUseFrame(false);
   }

   private static void setViewTitleBarProperties() {
      ViewTitleBarProperties var0 = DEFAULT_VALUES.getViewProperties().getViewTitleBarProperties();
      var0.setOrientation(Direction.UP).setDirection(Direction.RIGHT).getNormalProperties().setTitleVisible(true).setIconVisible(true);
      ViewTitleBarStateProperties var1 = var0.getNormalProperties();
      var1.setButtonSpacing(0);
      var1.getUndockButtonProperties()
         .setFactory(DefaultButtonFactories.getUndockButtonFactory())
         .setVisible(true)
         .setTo(UndockWithAbortWindowAction.INSTANCE);
      var1.getDockButtonProperties().setFactory(DefaultButtonFactories.getDockButtonFactory()).setVisible(true).setTo(DockWithAbortWindowAction.INSTANCE);
      var1.getCloseButtonProperties().setFactory(DefaultButtonFactories.getCloseButtonFactory()).setVisible(true).setTo(CloseWithAbortWindowAction.INSTANCE);
      var1.getRestoreButtonProperties()
         .setFactory(DefaultButtonFactories.getRestoreButtonFactory())
         .setVisible(true)
         .setTo(RestoreViewWithAbortTitleBarAction.INSTANCE);
      var1.getMinimizeButtonProperties()
         .setFactory(DefaultButtonFactories.getMinimizeButtonFactory())
         .setVisible(true)
         .setTo(MinimizeWithAbortWindowAction.INSTANCE);
      var1.getMaximizeButtonProperties()
         .setFactory(DefaultButtonFactories.getMaximizeButtonFactory())
         .setVisible(true)
         .setTo(MaximizeWithAbortWindowAction.INSTANCE);
      var1.getMap().createRelativeRef(ViewTitleBarStateProperties.TITLE, DEFAULT_VALUES.getViewProperties().getMap(), ViewProperties.TITLE);
      var1.getMap().createRelativeRef(ViewTitleBarStateProperties.ICON, DEFAULT_VALUES.getViewProperties().getMap(), ViewProperties.ICON);
   }

   private static void updateVisualProperties() {
      DEFAULT_VALUES.getWindowBarProperties()
         .getTabWindowProperties()
         .getTabProperties()
         .getTitledTabProperties()
         .getNormalProperties()
         .getComponentProperties()
         .setBackgroundColor(TabbedUIDefaults.getHighlightedStateBackground());
      Color var0 = UIManagerUtil.getColor("controlDkShadow", Color.BLACK);
      DEFAULT_VALUES.getWindowAreaProperties().setBorder(new LineBorder(var0)).setBackgroundColor(UIManagerUtil.getColor("Desktop.background", "control"));
      DEFAULT_VALUES.getWindowAreaShapedPanelProperties().setOpaque(true);
      DEFAULT_VALUES.getDragLabelProperties()
         .setBorder(new LineBorder(var0))
         .setFont(UIManagerUtil.getFont("ToolTip.font"))
         .setForegroundColor(UIManagerUtil.getColor("ToolTip.foreground", "controlText"))
         .setBackgroundColor(UIManagerUtil.getColor("ToolTip.background", "control"));
      DEFAULT_VALUES.setRecursiveTabsEnabled(true);
   }

   private static void updateFont() {
      Font var0 = TitledTabProperties.getDefaultProperties().getHighlightedProperties().getComponentProperties().getFont();
      if (var0 != null) {
         var0 = var0.deriveFont(1);
      }

      DEFAULT_VALUES.getTabWindowProperties().getTabProperties().getTitledTabProperties().getHighlightedProperties().getComponentProperties().setFont(var0);
   }

   private static void updateViewTitleBarProperties() {
      ViewTitleBarProperties var0 = DEFAULT_VALUES.getViewProperties().getViewTitleBarProperties();
      Font var1 = TabbedUIDefaults.getFont();
      if (var1 != null) {
         var1 = var1.deriveFont(1);
      }

      var0.getNormalProperties()
         .getComponentProperties()
         .setFont(var1)
         .setForegroundColor(UIManager.getColor("InternalFrame.inactiveTitleForeground"))
         .setBackgroundColor(UIManager.getColor("InternalFrame.inactiveTitleBackground"))
         .setInsets(new Insets(2, 2, 2, 2));
      var0.getFocusedProperties()
         .getComponentProperties()
         .setForegroundColor(UIManager.getColor("InternalFrame.activeTitleForeground"))
         .setBackgroundColor(UIManager.getColor("InternalFrame.activeTitleBackground"));
      Color var2 = UIManager.getColor("InternalFrame.inactiveTitleBackground");
      Color var3 = UIManager.getColor("InternalFrame.inactiveTitleGradient");
      Object var4;
      if (var2 == null) {
         var4 = null;
      } else if (!var2.equals(var3) && var3 != null) {
         var4 = new GradientComponentPainter(var3, var3, var2, var2);
      } else {
         var4 = new SolidColorComponentPainter(new FixedColorProvider(var2));
      }

      var0.getNormalProperties().getShapedPanelProperties().setComponentPainter((ComponentPainter)var4).setOpaque(true);
      Color var5 = UIManager.getColor("InternalFrame.activeTitleBackground");
      Color var6 = UIManager.getColor("InternalFrame.activeTitleGradient");
      Object var7;
      if (var5 == null) {
         var7 = null;
      } else if (!var5.equals(var6) && var6 != null) {
         var7 = new GradientComponentPainter(var6, var6, var5, var5);
      } else {
         var7 = new SolidColorComponentPainter(new FixedColorProvider(var5));
      }

      var0.getFocusedProperties().getShapedPanelProperties().setComponentPainter((ComponentPainter)var7);
      var0.getFocusedProperties().getComponentProperties().setForegroundColor(UIManager.getColor("InternalFrame.activeTitleForeground"));
      var0.setContentTitleBarGap(0).getNormalProperties().setIconTextGap(TabbedUIDefaults.getIconTextGap());
   }

   public static RootWindowProperties createDefault() {
      return new RootWindowProperties(DEFAULT_VALUES);
   }

   public RootWindowProperties() {
      super(PropertyMapFactory.create(PROPERTIES));
   }

   public RootWindowProperties(PropertyMap var1) {
      super(var1);
   }

   public RootWindowProperties(RootWindowProperties var1) {
      super(PropertyMapFactory.create(var1.getMap()));
   }

   public RootWindowProperties addSuperObject(RootWindowProperties var1) {
      this.getMap().addSuperMap(var1.getMap());
      return this;
   }

   /** @deprecated */
   public RootWindowProperties removeSuperObject() {
      this.getMap().removeSuperMap();
      return this;
   }

   public RootWindowProperties removeSuperObject(RootWindowProperties var1) {
      this.getMap().removeSuperMap(var1.getMap());
      return this;
   }

   public RootWindowProperties replaceSuperObject(RootWindowProperties var1, RootWindowProperties var2) {
      this.getMap().replaceSuperMap(var1.getMap(), var2.getMap());
      return this;
   }

   public TabWindowProperties getTabWindowProperties() {
      return new TabWindowProperties(TAB_WINDOW_PROPERTIES.get(this.getMap()));
   }

   public SplitWindowProperties getSplitWindowProperties() {
      return new SplitWindowProperties(SPLIT_WINDOW_PROPERTIES.get(this.getMap()));
   }

   public FloatingWindowProperties getFloatingWindowProperties() {
      return new FloatingWindowProperties(FLOATING_WINDOW_PROPERTIES.get(this.getMap()));
   }

   public ViewProperties getViewProperties() {
      return new ViewProperties(VIEW_PROPERTIES.get(this.getMap()));
   }

   public DockingWindowProperties getDockingWindowProperties() {
      return new DockingWindowProperties(DOCKING_WINDOW_PROPERTIES.get(this.getMap()));
   }

   public RootWindowProperties setDragRectangleBorderWidth(int var1) {
      DRAG_RECTANGLE_BORDER_WIDTH.set(this.getMap(), var1);
      return this;
   }

   public int getDragRectangleBorderWidth() {
      return DRAG_RECTANGLE_BORDER_WIDTH.get(this.getMap());
   }

   public boolean getRecursiveTabsEnabled() {
      return RECURSIVE_TABS_ENABLED.get(this.getMap());
   }

   public boolean getDoubleClickRestoresWindow() {
      return DOUBLE_CLICK_RESTORES_WINDOW.get(this.getMap());
   }

   public RootWindowProperties setDoubleClickRestoresWindow(boolean var1) {
      DOUBLE_CLICK_RESTORES_WINDOW.set(this.getMap(), var1);
      return this;
   }

   public RootWindowProperties setRecursiveTabsEnabled(boolean var1) {
      RECURSIVE_TABS_ENABLED.set(this.getMap(), var1);
      return this;
   }

   public ComponentProperties getDragLabelProperties() {
      return new ComponentProperties(DRAG_LABEL_PROPERTIES.get(this.getMap()));
   }

   public ComponentProperties getComponentProperties() {
      return new ComponentProperties(COMPONENT_PROPERTIES.get(this.getMap()));
   }

   public ShapedPanelProperties getShapedPanelProperties() {
      return new ShapedPanelProperties(SHAPED_PANEL_PROPERTIES.get(this.getMap()));
   }

   public ComponentProperties getWindowAreaProperties() {
      return new ComponentProperties(WINDOW_AREA_PROPERTIES.get(this.getMap()));
   }

   public ShapedPanelProperties getWindowAreaShapedPanelProperties() {
      return new ShapedPanelProperties(WINDOW_AREA_SHAPED_PANEL_PROPERTIES.get(this.getMap()));
   }

   public RootWindowProperties setEdgeSplitDistance(int var1) {
      EDGE_SPLIT_DISTANCE.set(this.getMap(), var1);
      return this;
   }

   public int getEdgeSplitDistance() {
      return EDGE_SPLIT_DISTANCE.get(this.getMap());
   }

   public int getAbortDragKey() {
      return ABORT_DRAG_KEY.get(this.getMap());
   }

   public RootWindowProperties setAbortDragKey(int var1) {
      ABORT_DRAG_KEY.set(this.getMap(), var1);
      return this;
   }

   public WindowBarProperties getWindowBarProperties() {
      return new WindowBarProperties(WINDOW_BAR_PROPERTIES.get(this.getMap()));
   }

   public ShapedPanelProperties getDragRectangleShapedPanelProperties() {
      return new ShapedPanelProperties(DRAG_RECTANGLE_SHAPED_PANEL_PROPERTIES.get(this.getMap()));
   }

   static {
      DEFAULT_VALUES.setAbortDragKey(TabbedPanelProperties.getDefaultProperties().getAbortDragKey()).setEdgeSplitDistance(6).setDragRectangleBorderWidth(5);
      DEFAULT_VALUES.getShapedPanelProperties().setOpaque(true);
      DEFAULT_VALUES.getDockingWindowProperties()
         .setMaximizeEnabled(true)
         .setMinimizeEnabled(true)
         .setCloseEnabled(true)
         .setRestoreEnabled(true)
         .setDragEnabled(true)
         .setUndockEnabled(true)
         .setUndockOnDropEnabled(true)
         .setDockEnabled(true);
      DEFAULT_VALUES.getDockingWindowProperties()
         .getDropFilterProperties()
         .setChildDropFilter(AcceptAllDropFilter.INSTANCE)
         .setInsertTabDropFilter(AcceptAllDropFilter.INSTANCE)
         .setInteriorDropFilter(AcceptAllDropFilter.INSTANCE)
         .setSplitDropFilter(AcceptAllDropFilter.INSTANCE);
      DEFAULT_VALUES.getWindowAreaProperties().setInsets(new Insets(6, 6, 2, 2));
      DEFAULT_VALUES.getDragLabelProperties().setInsets(new Insets(4, 6, 4, 6));
      DEFAULT_VALUES.getDragRectangleShapedPanelProperties().setOpaque(false);
      DEFAULT_VALUES.getSplitWindowProperties()
         .setContinuousLayoutEnabled(true)
         .setDividerSize(4)
         .setDividerLocationDragEnabled(true)
         .setDragIndicatorColor(Color.DARK_GRAY);
      DEFAULT_VALUES.getViewProperties().setAlwaysShowTitle(true);
      setTabbedPanelProperties();
      setTabProperties();
      setWindowBarProperties();
      setViewTitleBarProperties();
      setFloatingWindowProperties();
      updateVisualProperties();
      updateViewTitleBarProperties();
      updateFont();
      TitledTabProperties.getDefaultProperties().getHighlightedProperties().getComponentProperties().getMap().addListener(new RootWindowProperties$1());
      DynamicUIManager.getInstance().addListener(new RootWindowProperties$2());
   }
}
