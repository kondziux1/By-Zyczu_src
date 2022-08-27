package net.infonode.docking.properties;

import net.infonode.properties.gui.util.ComponentProperties;
import net.infonode.properties.gui.util.ShapedPanelProperties;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapContainer;
import net.infonode.properties.propertymap.PropertyMapFactory;
import net.infonode.properties.propertymap.PropertyMapGroup;
import net.infonode.properties.propertymap.PropertyMapProperty;
import net.infonode.properties.propertymap.PropertyMapValueHandler;
import net.infonode.properties.types.BooleanProperty;

public class FloatingWindowProperties extends PropertyMapContainer {
   public static final PropertyMapGroup PROPERTIES = new PropertyMapGroup("Floating Window Properties", "");
   public static final PropertyMapProperty COMPONENT_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Component Properties", "Component properties for floating window.", ComponentProperties.PROPERTIES
   );
   public static final PropertyMapProperty SHAPED_PANEL_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Shaped Panel Properties", "Properties for floating window internal shape.", ShapedPanelProperties.PROPERTIES
   );
   public static final BooleanProperty AUTO_CLOSE_ENABLED = new BooleanProperty(
      PROPERTIES,
      "Auto Close Enabled",
      "Enables/disables if the floating window should be automatically closed when it doesn't contain any child window.",
      PropertyMapValueHandler.INSTANCE
   );
   public static final BooleanProperty USE_FRAME = new BooleanProperty(
      PROPERTIES,
      "Use Frame",
      "If true the floating window will be created as a JFrame, otherwise a JDialog will be created.",
      PropertyMapValueHandler.INSTANCE
   );

   public FloatingWindowProperties() {
      super(PropertyMapFactory.create(PROPERTIES));
   }

   public FloatingWindowProperties(PropertyMap var1) {
      super(var1);
   }

   public FloatingWindowProperties(FloatingWindowProperties var1) {
      super(PropertyMapFactory.create(var1.getMap()));
   }

   public FloatingWindowProperties addSuperObject(FloatingWindowProperties var1) {
      this.getMap().addSuperMap(var1.getMap());
      return this;
   }

   public FloatingWindowProperties removeSuperObject(FloatingWindowProperties var1) {
      this.getMap().removeSuperMap(var1.getMap());
      return this;
   }

   public ComponentProperties getComponentProperties() {
      return new ComponentProperties(COMPONENT_PROPERTIES.get(this.getMap()));
   }

   public ShapedPanelProperties getShapedPanelProperties() {
      return new ShapedPanelProperties(SHAPED_PANEL_PROPERTIES.get(this.getMap()));
   }

   public boolean getAutoCloseEnabled() {
      return AUTO_CLOSE_ENABLED.get(this.getMap());
   }

   public FloatingWindowProperties setAutoCloseEnabled(boolean var1) {
      AUTO_CLOSE_ENABLED.set(this.getMap(), var1);
      return this;
   }

   public boolean getUseFrame() {
      return USE_FRAME.get(this.getMap());
   }

   public FloatingWindowProperties setUseFrame(boolean var1) {
      USE_FRAME.set(this.getMap(), var1);
      return this;
   }
}
