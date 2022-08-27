package net.infonode.docking.properties;

import java.awt.Color;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapContainer;
import net.infonode.properties.propertymap.PropertyMapFactory;
import net.infonode.properties.propertymap.PropertyMapGroup;
import net.infonode.properties.propertymap.PropertyMapValueHandler;
import net.infonode.properties.types.BooleanProperty;
import net.infonode.properties.types.ColorProperty;
import net.infonode.properties.types.IntegerProperty;

public class SplitWindowProperties extends PropertyMapContainer {
   public static final PropertyMapGroup PROPERTIES = new PropertyMapGroup("Split Window Properties", "");
   public static final BooleanProperty CONTINUOUS_LAYOUT_ENABLED = new BooleanProperty(
      PROPERTIES,
      "Continuous Layout Enabled",
      "When enabled causes the windows to change size continuously while dragging the split window divider.",
      PropertyMapValueHandler.INSTANCE
   );
   public static final IntegerProperty DIVIDER_SIZE = IntegerProperty.createPositive(
      PROPERTIES, "Divider Size", "The split pane divider size.", 2, PropertyMapValueHandler.INSTANCE
   );
   public static final BooleanProperty DIVIDER_LOCATION_DRAG_ENABLED = new BooleanProperty(
      PROPERTIES,
      "Divider Location Drag Enabled",
      "When enabled the user can drag the SplitWindow divider to a new location.",
      PropertyMapValueHandler.INSTANCE
   );
   public static final ColorProperty DRAG_INDICATOR_COLOR = new ColorProperty(
      PROPERTIES,
      "Drag Indicator Color",
      "The color for the divider's drag indicator that is shown when continuous layout is disabled.",
      PropertyMapValueHandler.INSTANCE
   );

   public SplitWindowProperties() {
      super(PropertyMapFactory.create(PROPERTIES));
   }

   public SplitWindowProperties(PropertyMap var1) {
      super(var1);
   }

   public SplitWindowProperties(SplitWindowProperties var1) {
      super(PropertyMapFactory.create(var1.getMap()));
   }

   public SplitWindowProperties addSuperObject(SplitWindowProperties var1) {
      this.getMap().addSuperMap(var1.getMap());
      return this;
   }

   /** @deprecated */
   public SplitWindowProperties removeSuperObject() {
      this.getMap().removeSuperMap();
      return this;
   }

   public SplitWindowProperties removeSuperObject(SplitWindowProperties var1) {
      this.getMap().removeSuperMap(var1.getMap());
      return this;
   }

   public SplitWindowProperties setDividerSize(int var1) {
      DIVIDER_SIZE.set(this.getMap(), var1);
      return this;
   }

   public int getDividerSize() {
      return DIVIDER_SIZE.get(this.getMap());
   }

   public SplitWindowProperties setDragIndicatorColor(Color var1) {
      DRAG_INDICATOR_COLOR.set(this.getMap(), var1);
      return this;
   }

   public Color getDragIndicatorColor() {
      return DRAG_INDICATOR_COLOR.get(this.getMap());
   }

   public boolean getContinuousLayoutEnabled() {
      return CONTINUOUS_LAYOUT_ENABLED.get(this.getMap());
   }

   public SplitWindowProperties setContinuousLayoutEnabled(boolean var1) {
      CONTINUOUS_LAYOUT_ENABLED.set(this.getMap(), var1);
      return this;
   }

   public boolean getDividerLocationDragEnabled() {
      return DIVIDER_LOCATION_DRAG_ENABLED.get(this.getMap());
   }

   public SplitWindowProperties setDividerLocationDragEnabled(boolean var1) {
      DIVIDER_LOCATION_DRAG_ENABLED.set(this.getMap(), var1);
      return this;
   }
}
