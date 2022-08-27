package net.infonode.docking.properties;

import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapContainer;
import net.infonode.properties.propertymap.PropertyMapFactory;
import net.infonode.properties.propertymap.PropertyMapGroup;
import net.infonode.properties.propertymap.PropertyMapProperty;

public class WindowTabStateProperties extends PropertyMapContainer {
   public static final PropertyMapGroup PROPERTIES = new PropertyMapGroup("State Properties", "");
   public static final PropertyMapProperty MINIMIZE_BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Minimize Button Properties", "The minimize button property values.", WindowTabButtonProperties.PROPERTIES
   );
   public static final PropertyMapProperty RESTORE_BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Restore Button Properties", "The restore button property values.", WindowTabButtonProperties.PROPERTIES
   );
   public static final PropertyMapProperty CLOSE_BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Close Button Properties", "The close button property values.", WindowTabButtonProperties.PROPERTIES
   );
   public static final PropertyMapProperty UNDOCK_BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Undock Button Properties", "The undock button property values.", WindowTabButtonProperties.PROPERTIES
   );
   public static final PropertyMapProperty DOCK_BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Dock Button Properties", "The dock button property values.", WindowTabButtonProperties.PROPERTIES
   );

   public WindowTabStateProperties() {
      super(PROPERTIES);
   }

   public WindowTabStateProperties(PropertyMap var1) {
      super(var1);
   }

   public WindowTabStateProperties(WindowTabStateProperties var1) {
      super(PropertyMapFactory.create(var1.getMap()));
   }

   public WindowTabStateProperties addSuperObject(WindowTabStateProperties var1) {
      this.getMap().addSuperMap(var1.getMap());
      return this;
   }

   /** @deprecated */
   public WindowTabStateProperties removeSuperObject() {
      this.getMap().removeSuperMap();
      return this;
   }

   public WindowTabStateProperties removeSuperObject(WindowTabStateProperties var1) {
      this.getMap().removeSuperMap(var1.getMap());
      return this;
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

   public WindowTabButtonProperties getUndockButtonProperties() {
      return new WindowTabButtonProperties(UNDOCK_BUTTON_PROPERTIES.get(this.getMap()));
   }

   public WindowTabButtonProperties getDockButtonProperties() {
      return new WindowTabButtonProperties(DOCK_BUTTON_PROPERTIES.get(this.getMap()));
   }
}
