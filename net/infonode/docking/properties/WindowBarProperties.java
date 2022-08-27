package net.infonode.docking.properties;

import java.awt.Color;
import net.infonode.properties.gui.util.ComponentProperties;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapContainer;
import net.infonode.properties.propertymap.PropertyMapFactory;
import net.infonode.properties.propertymap.PropertyMapGroup;
import net.infonode.properties.propertymap.PropertyMapProperty;
import net.infonode.properties.propertymap.PropertyMapValueHandler;
import net.infonode.properties.types.BooleanProperty;
import net.infonode.properties.types.ColorProperty;
import net.infonode.properties.types.IntegerProperty;
import net.infonode.util.Direction;

public class WindowBarProperties extends PropertyMapContainer {
   public static final PropertyMapGroup PROPERTIES = new PropertyMapGroup("Window Bar Properties", "");
   public static final PropertyMapProperty COMPONENT_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Component Properties", "The WindowBar component properties.", ComponentProperties.PROPERTIES
   );
   public static final IntegerProperty CONTENT_PANEL_EDGE_RESIZE_DISTANCE = IntegerProperty.createPositive(
      PROPERTIES,
      "Content Panel Edge Resize Distance",
      "Inside this distance from the content panel edge the user can resize the content panel.",
      2,
      PropertyMapValueHandler.INSTANCE
   );
   public static final IntegerProperty MINIMUM_WIDTH = IntegerProperty.createPositive(
      PROPERTIES,
      "Minimum Width",
      "The minimum width of the window bar. If greater than 0, the window bar will always be visible and the user can drag windows to it.",
      2,
      PropertyMapValueHandler.INSTANCE
   );
   public static final BooleanProperty CONTINUOUS_LAYOUT_ENABLED = new BooleanProperty(
      PROPERTIES,
      "Continuous Layout Enabled",
      "When enabled causes the selected tab's content to change size continuously while resizing it.",
      PropertyMapValueHandler.INSTANCE
   );
   public static final ColorProperty DRAG_INDICATOR_COLOR = new ColorProperty(
      PROPERTIES,
      "Drag Indicator Color",
      "The color for the resizer's drag indicator that is shown when continuous layout is disabled.",
      PropertyMapValueHandler.INSTANCE
   );
   public static final PropertyMapProperty TAB_WINDOW_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Tab Window Properties", "", TabWindowProperties.PROPERTIES
   );
   private static WindowBarProperties[] DEFAULT_VALUES = new WindowBarProperties[4];

   public static WindowBarProperties createDefault(Direction var0) {
      return new WindowBarProperties(DEFAULT_VALUES[var0.getValue()]);
   }

   public WindowBarProperties() {
      super(PropertyMapFactory.create(PROPERTIES));
   }

   public WindowBarProperties(PropertyMap var1) {
      super(var1);
   }

   public WindowBarProperties(WindowBarProperties var1) {
      super(PropertyMapFactory.create(var1.getMap()));
   }

   public WindowBarProperties addSuperObject(WindowBarProperties var1) {
      this.getMap().addSuperMap(var1.getMap());
      return this;
   }

   /** @deprecated */
   public WindowBarProperties removeSuperObject() {
      this.getMap().removeSuperMap();
      return this;
   }

   public WindowBarProperties removeSuperObject(WindowBarProperties var1) {
      this.getMap().removeSuperMap(var1.getMap());
      return this;
   }

   public int getContentPanelEdgeResizeDistance() {
      return CONTENT_PANEL_EDGE_RESIZE_DISTANCE.get(this.getMap());
   }

   public WindowBarProperties setContentPanelEdgeResizeEdgeDistance(int var1) {
      CONTENT_PANEL_EDGE_RESIZE_DISTANCE.set(this.getMap(), var1);
      return this;
   }

   public int getMinimumWidth() {
      return MINIMUM_WIDTH.get(this.getMap());
   }

   public WindowBarProperties setMinimumWidth(int var1) {
      MINIMUM_WIDTH.set(this.getMap(), var1);
      return this;
   }

   public TabWindowProperties getTabWindowProperties() {
      return new TabWindowProperties(TAB_WINDOW_PROPERTIES.get(this.getMap()));
   }

   public ComponentProperties getComponentProperties() {
      return new ComponentProperties(COMPONENT_PROPERTIES.get(this.getMap()));
   }

   public WindowBarProperties setDragIndicatorColor(Color var1) {
      DRAG_INDICATOR_COLOR.set(this.getMap(), var1);
      return this;
   }

   public Color getDragIndicatorColor() {
      return DRAG_INDICATOR_COLOR.get(this.getMap());
   }

   public boolean getContinuousLayoutEnabled() {
      return CONTINUOUS_LAYOUT_ENABLED.get(this.getMap());
   }

   public WindowBarProperties setContinuousLayoutEnabled(boolean var1) {
      CONTINUOUS_LAYOUT_ENABLED.set(this.getMap(), var1);
      return this;
   }

   static {
      Direction[] var0 = Direction.getDirections();

      for(int var1 = 0; var1 < var0.length; ++var1) {
         Direction var2 = var0[var1];
         WindowBarProperties var3 = new WindowBarProperties();
         var3.getTabWindowProperties().getTabbedPanelProperties().setTabAreaOrientation(var2);
         var3.getTabWindowProperties()
            .getTabProperties()
            .getTitledTabProperties()
            .getNormalProperties()
            .setDirection(var2.isHorizontal() ? Direction.DOWN : Direction.RIGHT);
         DEFAULT_VALUES[var2.getValue()] = var3;
      }

   }
}
