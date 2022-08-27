package net.infonode.tabbedpanel;

import java.awt.Color;
import java.util.HashMap;
import net.infonode.gui.DynamicUIManager;
import net.infonode.gui.hover.HoverListener;
import net.infonode.gui.icon.button.ArrowIcon;
import net.infonode.gui.icon.button.BorderIcon;
import net.infonode.gui.icon.button.DropDownIcon;
import net.infonode.properties.base.Property;
import net.infonode.properties.gui.util.ButtonProperties;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapContainer;
import net.infonode.properties.propertymap.PropertyMapFactory;
import net.infonode.properties.propertymap.PropertyMapGroup;
import net.infonode.properties.propertymap.PropertyMapManager;
import net.infonode.properties.propertymap.PropertyMapProperty;
import net.infonode.properties.propertymap.PropertyMapValueHandler;
import net.infonode.properties.types.BooleanProperty;
import net.infonode.properties.types.ColorProperty;
import net.infonode.properties.types.DirectionProperty;
import net.infonode.properties.types.FloatProperty;
import net.infonode.properties.types.HoverListenerProperty;
import net.infonode.properties.types.IntegerProperty;
import net.infonode.util.ArrayUtil;
import net.infonode.util.Direction;

public class TabbedPanelProperties extends PropertyMapContainer {
   public static final PropertyMapGroup PROPERTIES = new PropertyMapGroup("Tabbed Panel Properties", "Properties for the TabbedPanel class.");
   public static final BooleanProperty TAB_REORDER_ENABLED = new BooleanProperty(
      PROPERTIES, "Tab Reorder Enabled", "Tab reorder enabled or disabled", PropertyMapValueHandler.INSTANCE
   );
   public static final IntegerProperty ABORT_DRAG_KEY = IntegerProperty.createPositive(
      PROPERTIES, "Abort Drag Key Code", "Key code for aborting drag", 3, PropertyMapValueHandler.INSTANCE
   );
   public static final TabLayoutPolicyProperty TAB_LAYOUT_POLICY = new TabLayoutPolicyProperty(
      PROPERTIES, "Layout Policy", "Tab layout in tab area", PropertyMapValueHandler.INSTANCE
   );
   public static final TabDropDownListVisiblePolicyProperty TAB_DROP_DOWN_LIST_VISIBLE_POLICY = new TabDropDownListVisiblePolicyProperty(
      PROPERTIES,
      "Tab Drop Down List Visible Policy",
      "Determins when a drop down list with tabs should be visible in the tab area",
      PropertyMapValueHandler.INSTANCE
   );
   public static final TabSelectTriggerProperty TAB_SELECT_TRIGGER = new TabSelectTriggerProperty(
      PROPERTIES, "Tab Select Trigger", "Determins when a tab should be selected", PropertyMapValueHandler.INSTANCE
   );
   public static final IntegerProperty TAB_SCROLLING_OFFSET = IntegerProperty.createPositive(
      PROPERTIES, "Scroll Offset", "Number of pixels to be shown for the last scrolled tab", 3, PropertyMapValueHandler.INSTANCE
   );
   public static final BooleanProperty ENSURE_SELECTED_VISIBLE = new BooleanProperty(
      PROPERTIES, "Ensure Selected Visible", "Upon select, the selected tab will be scrolled into the visible area.", PropertyMapValueHandler.INSTANCE
   );
   public static final DirectionProperty TAB_AREA_ORIENTATION = new DirectionProperty(
      PROPERTIES, "Tab Area Orientation", "Tab area's orientation relative to the content area.", PropertyMapValueHandler.INSTANCE
   );
   public static final IntegerProperty TAB_SPACING = new IntegerProperty(
      PROPERTIES,
      "Tab Spacing",
      "Number of pixels between tabs in tab area. A negative value will result in tab overlapping.",
      Integer.MIN_VALUE,
      Integer.MAX_VALUE,
      3,
      PropertyMapValueHandler.INSTANCE
   );
   public static final TabDepthOrderPolicyProperty TAB_DEPTH_ORDER = new TabDepthOrderPolicyProperty(
      PROPERTIES,
      "Tab Depth Order",
      "Tabs will overlap when tab spacing is negative. Depth order tells if first tab should be the top most and the other tabs in descending order or if the first tab should be bottom most and the other tabs in ascending order.",
      PropertyMapValueHandler.INSTANCE
   );
   public static final BooleanProperty AUTO_SELECT_TAB = new BooleanProperty(
      PROPERTIES,
      "Auto Select Tab",
      "When enabled the first tab that i added will be selected automatically. If the selected tab is removed then the tab next to the removed tab will be selected automatically.",
      PropertyMapValueHandler.INSTANCE
   );
   public static final BooleanProperty HIGHLIGHT_PRESSED_TAB = new BooleanProperty(
      PROPERTIES,
      "Highlight Pressed Tab",
      "If true the tab pressed with the mouse will be highlighted, otherwise it remains unchanged.",
      PropertyMapValueHandler.INSTANCE
   );
   public static final BooleanProperty TAB_DESELECTABLE = new BooleanProperty(
      PROPERTIES, "Tab Deselectable", "When enabled the selected tab can be deselected by clicking on it.", PropertyMapValueHandler.INSTANCE
   );
   public static final PropertyMapProperty CONTENT_PANEL_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Content Panel Properties", "Content panel properties.", TabbedPanelContentPanelProperties.PROPERTIES
   );
   public static final PropertyMapProperty TAB_AREA_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Tab Area Properties", "Tab area properties.", TabAreaProperties.PROPERTIES
   );
   public static final PropertyMapProperty TAB_AREA_COMPONENTS_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Tab Area Components Properties", "Tab area components properties.", TabAreaComponentsProperties.PROPERTIES
   );
   public static final PropertyMapProperty BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Tabbed Panel Button Properties", "Tabbed panel button properties.", TabbedPanelButtonProperties.PROPERTIES
   );
   public static final BooleanProperty SHADOW_ENABLED = new BooleanProperty(
      PROPERTIES,
      "Shadow Enabled",
      "Indicates that a shadow is painted for the selected tab and the content panel.\nThe shadow is partially painted using alpha transparency which can be slow on some systems.",
      PropertyMapValueHandler.INSTANCE
   );
   public static final HoverListenerProperty HOVER_LISTENER = new HoverListenerProperty(
      PROPERTIES, "Hover Listener", "Hover Listener to be used for tracking mouse hovering over the tabbed panel.", PropertyMapValueHandler.INSTANCE
   );
   public static final TabbedPanelHoverPolicyProperty HOVER_POLICY = new TabbedPanelHoverPolicyProperty(
      PROPERTIES, "Hover Policy", "Policy for when the Tabbed Panel is considerd hovered by the mouse.", PropertyMapValueHandler.INSTANCE
   );
   public static final BooleanProperty PAINT_TAB_AREA_SHADOW = new BooleanProperty(
      PROPERTIES,
      "Paint Tab Area Shadow",
      "Paint a shadow for the tab area. If this property is set to false a shadow is painted for the highlighted tab and the tab area components panel.",
      PropertyMapValueHandler.INSTANCE
   );
   public static final IntegerProperty SHADOW_SIZE = IntegerProperty.createPositive(
      PROPERTIES, "Shadow Size", "The size of the tab shadow.", 2, PropertyMapValueHandler.INSTANCE
   );
   public static final IntegerProperty SHADOW_BLEND_AREA_SIZE = IntegerProperty.createPositive(
      PROPERTIES, "Shadow Blend Size", "The size of the tab shadow blend area.", 2, PropertyMapValueHandler.INSTANCE
   );
   public static final ColorProperty SHADOW_COLOR = new ColorProperty(PROPERTIES, "Shadow Color", "The color of the shadow.", PropertyMapValueHandler.INSTANCE);
   public static final FloatProperty SHADOW_STRENGTH = new FloatProperty(
      PROPERTIES,
      "Shadow Strength",
      "The strength of the shadow. 0 means the shadow color is the same as the backgound color, 1 means the shadow color is '" + SHADOW_COLOR + "'.",
      PropertyMapValueHandler.INSTANCE,
      0.0F,
      1.0F
   );
   public static final Property[] FUNCTIONAL_PROPERTIES = new Property[]{
      TAB_REORDER_ENABLED, ABORT_DRAG_KEY, TAB_LAYOUT_POLICY, ENSURE_SELECTED_VISIBLE, AUTO_SELECT_TAB, TAB_DESELECTABLE, TAB_SELECT_TRIGGER, HOVER_POLICY
   };
   public static final Property[] SHADOW_PROPERTIES = new Property[]{SHADOW_ENABLED, SHADOW_SIZE, SHADOW_BLEND_AREA_SIZE, SHADOW_COLOR, SHADOW_STRENGTH};
   public static final Property[] TABS_VISUAL_PROPERTIES = new Property[]{
      TAB_SCROLLING_OFFSET,
      TAB_SPACING,
      TAB_AREA_ORIENTATION,
      TAB_AREA_PROPERTIES,
      TAB_AREA_COMPONENTS_PROPERTIES,
      TAB_LAYOUT_POLICY,
      CONTENT_PANEL_PROPERTIES,
      TAB_DROP_DOWN_LIST_VISIBLE_POLICY
   };
   public static final Property[] VISUAL_PROPERTIES = (Property[])ArrayUtil.append(
      TABS_VISUAL_PROPERTIES, SHADOW_PROPERTIES, new Property[TABS_VISUAL_PROPERTIES.length + SHADOW_PROPERTIES.length]
   );
   private static final TabbedPanelProperties DEFAULT_PROPERTIES = new TabbedPanelProperties(PROPERTIES.getDefaultMap());

   public static TabbedPanelProperties getDefaultProperties() {
      return new TabbedPanelProperties(DEFAULT_PROPERTIES);
   }

   private static void updateVisualProperties() {
      PropertyMapManager.runBatch(new TabbedPanelProperties$2());
   }

   private static void updateFunctionalProperties() {
      DEFAULT_PROPERTIES.setTabReorderEnabled(false)
         .setAbortDragKey(27)
         .setTabLayoutPolicy(TabLayoutPolicy.SCROLLING)
         .setTabDropDownListVisiblePolicy(TabDropDownListVisiblePolicy.NEVER)
         .setTabSelectTrigger(TabSelectTrigger.MOUSE_PRESS)
         .setTabScrollingOffset(10)
         .setTabSpacing(-1)
         .setTabDepthOrderPolicy(TabDepthOrderPolicy.DESCENDING)
         .setEnsureSelectedTabVisible(false)
         .setTabAreaOrientation(Direction.UP)
         .setAutoSelectTab(true)
         .setHighlightPressedTab(true)
         .setHoverPolicy(TabbedPanelHoverPolicy.NO_HOVERED_CHILD)
         .setShadowEnabled(false)
         .setShadowSize(3)
         .setShadowBlendAreaSize(2)
         .setShadowColor(Color.BLACK)
         .setShadowStrength(0.4F);
      DEFAULT_PROPERTIES.getTabAreaProperties().setTabAreaVisiblePolicy(TabAreaVisiblePolicy.ALWAYS);
      HashMap var0 = new HashMap();
      var0.put(Direction.DOWN, DEFAULT_PROPERTIES.getButtonProperties().getScrollDownButtonProperties());
      var0.put(Direction.UP, DEFAULT_PROPERTIES.getButtonProperties().getScrollUpButtonProperties());
      var0.put(Direction.RIGHT, DEFAULT_PROPERTIES.getButtonProperties().getScrollRightButtonProperties());
      var0.put(Direction.LEFT, DEFAULT_PROPERTIES.getButtonProperties().getScrollLeftButtonProperties());
      int var1 = TabbedUIDefaults.getButtonIconSize();
      Direction[] var2 = Direction.getDirections();

      for(int var3 = 0; var3 < var2.length; ++var3) {
         ArrowIcon var4 = new ArrowIcon(var1 - 2, var2[var3], false);
         var4.setShadowEnabled(false);
         ((ButtonProperties)var0.get(var2[var3]))
            .setFactory(TabbedPanelDefaultButtonFactories.getScrollDownButtonFactory())
            .setIcon(new ArrowIcon(var1, var2[var3]))
            .setDisabledIcon(new BorderIcon(var4, 1));
      }

      DEFAULT_PROPERTIES.getButtonProperties()
         .getTabDropDownListButtonProperties()
         .setFactory(TabbedPanelDefaultButtonFactories.getScrollDownButtonFactory())
         .setIcon(new DropDownIcon(Color.black, TabbedUIDefaults.getButtonIconSize(), Direction.DOWN))
         .setDisabledIcon(null);
   }

   public TabbedPanelProperties() {
      super(PROPERTIES);
   }

   public TabbedPanelProperties(PropertyMap var1) {
      super(var1);
   }

   public TabbedPanelProperties(TabbedPanelProperties var1) {
      super(PropertyMapFactory.create(var1.getMap()));
   }

   public TabbedPanelProperties addSuperObject(TabbedPanelProperties var1) {
      this.getMap().addSuperMap(var1.getMap());
      return this;
   }

   public TabbedPanelProperties removeSuperObject() {
      this.getMap().removeSuperMap();
      return this;
   }

   public TabbedPanelProperties removeSuperObject(TabbedPanelProperties var1) {
      this.getMap().removeSuperMap(var1.getMap());
      return this;
   }

   public TabbedPanelProperties replaceSuperObject(TabbedPanelProperties var1, TabbedPanelProperties var2) {
      this.getMap().replaceSuperMap(var1.getMap(), var2.getMap());
      return this;
   }

   public TabbedPanelProperties setShadowStrength(float var1) {
      SHADOW_STRENGTH.set(this.getMap(), var1);
      return this;
   }

   public TabbedPanelProperties setShadowBlendAreaSize(int var1) {
      SHADOW_BLEND_AREA_SIZE.set(this.getMap(), var1);
      return this;
   }

   public TabbedPanelProperties setShadowSize(int var1) {
      SHADOW_SIZE.set(this.getMap(), var1);
      return this;
   }

   public TabbedPanelProperties setShadowColor(Color var1) {
      SHADOW_COLOR.set(this.getMap(), var1);
      return this;
   }

   public TabbedPanelProperties setShadowEnabled(boolean var1) {
      SHADOW_ENABLED.set(this.getMap(), var1);
      return this;
   }

   public TabbedPanelProperties setAutoSelectTab(boolean var1) {
      AUTO_SELECT_TAB.set(this.getMap(), var1);
      return this;
   }

   public TabbedPanelProperties setTabDeselectable(boolean var1) {
      TAB_DESELECTABLE.set(this.getMap(), var1);
      return this;
   }

   public TabbedPanelProperties setEnsureSelectedTabVisible(boolean var1) {
      ENSURE_SELECTED_VISIBLE.set(this.getMap(), var1);
      return this;
   }

   public TabbedPanelProperties setTabScrollingOffset(int var1) {
      TAB_SCROLLING_OFFSET.set(this.getMap(), var1);
      return this;
   }

   public TabbedPanelProperties setTabReorderEnabled(boolean var1) {
      TAB_REORDER_ENABLED.set(this.getMap(), var1);
      return this;
   }

   public TabbedPanelProperties setHighlightPressedTab(boolean var1) {
      HIGHLIGHT_PRESSED_TAB.set(this.getMap(), var1);
      return this;
   }

   public TabbedPanelProperties setAbortDragKey(int var1) {
      ABORT_DRAG_KEY.set(this.getMap(), var1);
      return this;
   }

   public TabbedPanelProperties setTabLayoutPolicy(TabLayoutPolicy var1) {
      TAB_LAYOUT_POLICY.set(this.getMap(), var1);
      return this;
   }

   public TabbedPanelProperties setTabDropDownListVisiblePolicy(TabDropDownListVisiblePolicy var1) {
      TAB_DROP_DOWN_LIST_VISIBLE_POLICY.set(this.getMap(), var1);
      return this;
   }

   public TabbedPanelProperties setTabSelectTrigger(TabSelectTrigger var1) {
      TAB_SELECT_TRIGGER.set(this.getMap(), var1);
      return this;
   }

   public TabbedPanelProperties setTabAreaOrientation(Direction var1) {
      TAB_AREA_ORIENTATION.set(this.getMap(), var1);
      return this;
   }

   public TabbedPanelProperties setTabSpacing(int var1) {
      TAB_SPACING.set(this.getMap(), var1);
      return this;
   }

   public TabbedPanelProperties setTabDepthOrderPolicy(TabDepthOrderPolicy var1) {
      TAB_DEPTH_ORDER.set(this.getMap(), var1);
      return this;
   }

   public float getShadowStrength() {
      return SHADOW_STRENGTH.get(this.getMap());
   }

   public int getShadowBlendAreaSize() {
      return SHADOW_BLEND_AREA_SIZE.get(this.getMap());
   }

   public int getShadowSize() {
      return SHADOW_SIZE.get(this.getMap());
   }

   public Color getShadowColor() {
      return SHADOW_COLOR.get(this.getMap());
   }

   public boolean getShadowEnabled() {
      return SHADOW_ENABLED.get(this.getMap());
   }

   public boolean getAutoSelectTab() {
      return AUTO_SELECT_TAB.get(this.getMap());
   }

   public boolean getHighlightPressedTab() {
      return HIGHLIGHT_PRESSED_TAB.get(this.getMap());
   }

   public boolean getTabDeselectable() {
      return TAB_DESELECTABLE.get(this.getMap());
   }

   public boolean getEnsureSelectedTabVisible() {
      return ENSURE_SELECTED_VISIBLE.get(this.getMap());
   }

   public boolean getPaintTabAreaShadow() {
      return PAINT_TAB_AREA_SHADOW.get(this.getMap());
   }

   public TabbedPanelProperties setPaintTabAreaShadow(boolean var1) {
      PAINT_TAB_AREA_SHADOW.set(this.getMap(), var1);
      return this;
   }

   public int getTabScrollingOffset() {
      return TAB_SCROLLING_OFFSET.get(this.getMap());
   }

   public boolean getTabReorderEnabled() {
      return TAB_REORDER_ENABLED.get(this.getMap());
   }

   public int getAbortDragKey() {
      return ABORT_DRAG_KEY.get(this.getMap());
   }

   public TabLayoutPolicy getTabLayoutPolicy() {
      return TAB_LAYOUT_POLICY.get(this.getMap());
   }

   public TabDropDownListVisiblePolicy getTabDropDownListVisiblePolicy() {
      return TAB_DROP_DOWN_LIST_VISIBLE_POLICY.get(this.getMap());
   }

   public TabSelectTrigger getTabSelectTrigger() {
      return TAB_SELECT_TRIGGER.get(this.getMap());
   }

   public Direction getTabAreaOrientation() {
      return TAB_AREA_ORIENTATION.get(this.getMap());
   }

   public int getTabSpacing() {
      return TAB_SPACING.get(this.getMap());
   }

   public TabDepthOrderPolicy getTabDepthOrderPolicy() {
      return TAB_DEPTH_ORDER.get(this.getMap());
   }

   public TabbedPanelProperties setHoverListener(HoverListener var1) {
      HOVER_LISTENER.set(this.getMap(), var1);
      return this;
   }

   public HoverListener getHoverListener() {
      return HOVER_LISTENER.get(this.getMap());
   }

   public TabbedPanelProperties setHoverPolicy(TabbedPanelHoverPolicy var1) {
      HOVER_POLICY.set(this.getMap(), var1);
      return this;
   }

   public TabbedPanelHoverPolicy getHoverPolicy() {
      return HOVER_POLICY.get(this.getMap());
   }

   public TabbedPanelContentPanelProperties getContentPanelProperties() {
      return new TabbedPanelContentPanelProperties(CONTENT_PANEL_PROPERTIES.get(this.getMap()));
   }

   public TabAreaProperties getTabAreaProperties() {
      return new TabAreaProperties(TAB_AREA_PROPERTIES.get(this.getMap()));
   }

   public TabAreaComponentsProperties getTabAreaComponentsProperties() {
      return new TabAreaComponentsProperties(TAB_AREA_COMPONENTS_PROPERTIES.get(this.getMap()));
   }

   public TabbedPanelButtonProperties getButtonProperties() {
      return new TabbedPanelButtonProperties(BUTTON_PROPERTIES.get(this.getMap()));
   }

   static {
      DynamicUIManager.getInstance().addListener(new TabbedPanelProperties$1());
      updateVisualProperties();
      updateFunctionalProperties();
   }
}
