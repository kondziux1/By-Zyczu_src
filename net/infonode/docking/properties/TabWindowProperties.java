package net.infonode.docking.properties;

import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapContainer;
import net.infonode.properties.propertymap.PropertyMapFactory;
import net.infonode.properties.propertymap.PropertyMapGroup;
import net.infonode.properties.propertymap.PropertyMapProperty;
import net.infonode.properties.propertymap.PropertyMapValueHandler;
import net.infonode.properties.types.BooleanProperty;
import net.infonode.tabbedpanel.TabbedPanelProperties;

public class TabWindowProperties extends PropertyMapContainer {
   public static final PropertyMapGroup PROPERTIES = new PropertyMapGroup("Tab Window Properties", "");
   public static final PropertyMapProperty TABBED_PANEL_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Tabbed Panel Properties", "Property values for the tabbed panel in the TabWindow.", TabbedPanelProperties.PROPERTIES
   );
   public static final PropertyMapProperty TAB_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Tab Properties", "Default property values for the window tabs in the TabWindow.", WindowTabProperties.PROPERTIES
   );
   public static final PropertyMapProperty MINIMIZE_BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Minimize Button Properties", "The minimize button property values.", WindowTabButtonProperties.PROPERTIES
   );
   public static final PropertyMapProperty RESTORE_BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Restore Button Properties", "The restore button property values.", WindowTabButtonProperties.PROPERTIES
   );
   public static final PropertyMapProperty CLOSE_BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Close Button Properties", "The close button property values.", WindowTabButtonProperties.PROPERTIES
   );
   public static final PropertyMapProperty MAXIMIZE_BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Maximize Button Properties", "The maximize button property values.", WindowTabButtonProperties.PROPERTIES
   );
   public static final PropertyMapProperty UNDOCK_BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Undock Button Properties", "The undock button property values.", WindowTabButtonProperties.PROPERTIES
   );
   public static final PropertyMapProperty DOCK_BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Dock Button Properties", "The dock button property values.", WindowTabButtonProperties.PROPERTIES
   );
   public static final BooleanProperty RESPECT_CHILD_WINDOW_MINIMUM_SIZE = new BooleanProperty(
      PROPERTIES,
      "Respect Child Window Minimum Size",
      "When enabled the Tab Window will respect its child windows minimum sizes.",
      PropertyMapValueHandler.INSTANCE
   );

   public TabWindowProperties() {
      super(PropertyMapFactory.create(PROPERTIES));
   }

   public TabWindowProperties(PropertyMap var1) {
      super(var1);
   }

   public TabWindowProperties(TabWindowProperties var1) {
      super(PropertyMapFactory.create(var1.getMap()));
   }

   public TabWindowProperties addSuperObject(TabWindowProperties var1) {
      this.getMap().addSuperMap(var1.getMap());
      return this;
   }

   /** @deprecated */
   public TabWindowProperties removeSuperObject() {
      this.getMap().removeSuperMap();
      return this;
   }

   public TabWindowProperties removeSuperObject(TabWindowProperties var1) {
      this.getMap().removeSuperMap(var1.getMap());
      return this;
   }

   public TabbedPanelProperties getTabbedPanelProperties() {
      return new TabbedPanelProperties(TABBED_PANEL_PROPERTIES.get(this.getMap()));
   }

   public WindowTabProperties getTabProperties() {
      return new WindowTabProperties(TAB_PROPERTIES.get(this.getMap()));
   }

   public WindowTabButtonProperties getMinimizeButtonProperties() {
      return new WindowTabButtonProperties(MINIMIZE_BUTTON_PROPERTIES.get(this.getMap()));
   }

   public WindowTabButtonProperties getRestoreButtonProperties() {
      return new WindowTabButtonProperties(RESTORE_BUTTON_PROPERTIES.get(this.getMap()));
   }

   public WindowTabButtonProperties getCloseButtonProperties() {
      return new WindowTabButtonProperties(CLOSE_BUTTON_PROPERTIES.get(this.getMap()));
   }

   public WindowTabButtonProperties getMaximizeButtonProperties() {
      return new WindowTabButtonProperties(MAXIMIZE_BUTTON_PROPERTIES.get(this.getMap()));
   }

   public WindowTabButtonProperties getUndockButtonProperties() {
      return new WindowTabButtonProperties(UNDOCK_BUTTON_PROPERTIES.get(this.getMap()));
   }

   public WindowTabButtonProperties getDockButtonProperties() {
      return new WindowTabButtonProperties(DOCK_BUTTON_PROPERTIES.get(this.getMap()));
   }

   public boolean getRespectChildWindowMinimumSize() {
      return RESPECT_CHILD_WINDOW_MINIMUM_SIZE.get(this.getMap());
   }

   public TabWindowProperties setRespectChildWindowMinimumSize(boolean var1) {
      RESPECT_CHILD_WINDOW_MINIMUM_SIZE.set(this.getMap(), var1);
      return this;
   }
}
