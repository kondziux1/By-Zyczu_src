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
import net.infonode.properties.types.HoverListenerProperty;

public class TabbedPanelContentPanelProperties extends PropertyMapContainer {
   public static final PropertyMapGroup PROPERTIES = new PropertyMapGroup("Tab Content Panel Properties", "Properties for the TabContentPanel class.");
   public static final PropertyMapProperty COMPONENT_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Component Properties", "Properties for the content area component.", ComponentProperties.PROPERTIES
   );
   public static final PropertyMapProperty SHAPED_PANEL_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Shaped Panel Properties", "Properties for shaped tab area components area.", ShapedPanelProperties.PROPERTIES
   );
   public static final HoverListenerProperty HOVER_LISTENER = new HoverListenerProperty(
      PROPERTIES, "Hover Listener", "Hover Listener to be used for tracking mouse hovering over the content area.", PropertyMapValueHandler.INSTANCE
   );

   public TabbedPanelContentPanelProperties() {
      super(PropertyMapFactory.create(PROPERTIES));
   }

   public TabbedPanelContentPanelProperties(PropertyMap var1) {
      super(var1);
   }

   public TabbedPanelContentPanelProperties(TabbedPanelContentPanelProperties var1) {
      super(PropertyMapFactory.create(var1.getMap()));
   }

   public TabbedPanelContentPanelProperties addSuperObject(TabbedPanelContentPanelProperties var1) {
      this.getMap().addSuperMap(var1.getMap());
      return this;
   }

   public TabbedPanelContentPanelProperties removeSuperObject() {
      this.getMap().removeSuperMap();
      return this;
   }

   public TabbedPanelContentPanelProperties removeSuperObject(TabbedPanelContentPanelProperties var1) {
      this.getMap().removeSuperMap(var1.getMap());
      return this;
   }

   public ComponentProperties getComponentProperties() {
      return new ComponentProperties(COMPONENT_PROPERTIES.get(this.getMap()));
   }

   public ShapedPanelProperties getShapedPanelProperties() {
      return new ShapedPanelProperties(SHAPED_PANEL_PROPERTIES.get(this.getMap()));
   }

   public TabbedPanelContentPanelProperties setHoverListener(HoverListener var1) {
      HOVER_LISTENER.set(this.getMap(), var1);
      return this;
   }

   public HoverListener getHoverListener() {
      return HOVER_LISTENER.get(this.getMap());
   }
}
