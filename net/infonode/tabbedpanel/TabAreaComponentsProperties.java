package net.infonode.tabbedpanel;

import net.infonode.gui.hover.HoverListener;
import net.infonode.properties.gui.util.ComponentProperties;
import net.infonode.properties.gui.util.ShapedPanelProperties;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapContainer;
import net.infonode.properties.propertymap.PropertyMapFactory;
import net.infonode.properties.propertymap.PropertyMapGroup;
import net.infonode.properties.propertymap.PropertyMapProperty;
import net.infonode.properties.propertymap.PropertyMapValueHandler;
import net.infonode.properties.types.BooleanProperty;
import net.infonode.properties.types.HoverListenerProperty;

public class TabAreaComponentsProperties extends PropertyMapContainer {
   public static final PropertyMapGroup PROPERTIES = new PropertyMapGroup("Tab Area Properties", "Properties for the TabbedPanel class.");
   public static final BooleanProperty STRETCH_ENABLED = new BooleanProperty(
      PROPERTIES, "Stretch Enabled", "Stretch components to be as high as tabs if tabs are higher than components.", PropertyMapValueHandler.INSTANCE
   );
   public static final PropertyMapProperty COMPONENT_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Component Properties", "Properties for tab area components area.", ComponentProperties.PROPERTIES
   );
   public static final PropertyMapProperty SHAPED_PANEL_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Shaped Panel Properties", "Properties for shaped tab area components area.", ShapedPanelProperties.PROPERTIES
   );
   public static final HoverListenerProperty HOVER_LISTENER = new HoverListenerProperty(
      PROPERTIES,
      "Hover Listener",
      "Hover Listener to be used for tracking mouse hovering over the tab area components area.",
      PropertyMapValueHandler.INSTANCE
   );

   public TabAreaComponentsProperties() {
      super(PROPERTIES);
   }

   public TabAreaComponentsProperties(PropertyMap var1) {
      super(var1);
   }

   public TabAreaComponentsProperties(TabAreaComponentsProperties var1) {
      super(PropertyMapFactory.create(var1.getMap()));
   }

   public TabAreaComponentsProperties addSuperObject(TabAreaComponentsProperties var1) {
      this.getMap().addSuperMap(var1.getMap());
      return this;
   }

   public TabAreaComponentsProperties removeSuperObject() {
      this.getMap().removeSuperMap();
      return this;
   }

   public TabAreaComponentsProperties removeSuperObject(TabAreaComponentsProperties var1) {
      this.getMap().removeSuperMap(var1.getMap());
      return this;
   }

   public boolean getStretchEnabled() {
      return STRETCH_ENABLED.get(this.getMap());
   }

   public TabAreaComponentsProperties setStretchEnabled(boolean var1) {
      STRETCH_ENABLED.set(this.getMap(), var1);
      return this;
   }

   public ComponentProperties getComponentProperties() {
      return new ComponentProperties(COMPONENT_PROPERTIES.get(this.getMap()));
   }

   public ShapedPanelProperties getShapedPanelProperties() {
      return new ShapedPanelProperties(SHAPED_PANEL_PROPERTIES.get(this.getMap()));
   }

   public TabAreaComponentsProperties setHoverListener(HoverListener var1) {
      HOVER_LISTENER.set(this.getMap(), var1);
      return this;
   }

   public HoverListener getHoverListener() {
      return HOVER_LISTENER.get(this.getMap());
   }
}
