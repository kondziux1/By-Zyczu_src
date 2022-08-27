package net.infonode.docking.properties;

import net.infonode.docking.title.DockingWindowTitleProvider;
import net.infonode.docking.title.DockingWindowTitleProviderProperty;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapContainer;
import net.infonode.properties.propertymap.PropertyMapFactory;
import net.infonode.properties.propertymap.PropertyMapGroup;
import net.infonode.properties.propertymap.PropertyMapProperty;
import net.infonode.properties.propertymap.PropertyMapValueHandler;
import net.infonode.properties.types.BooleanProperty;

public class DockingWindowProperties extends PropertyMapContainer {
   public static final PropertyMapGroup PROPERTIES = new PropertyMapGroup("Docking Window Properties", "");
   public static final PropertyMapProperty TAB_PROPERTIES = new PropertyMapProperty(
      PROPERTIES,
      "Tab Properties",
      "Property values for the window tab when the window is located in a TabWindow or a WindowBar.",
      WindowTabProperties.PROPERTIES
   );
   public static final PropertyMapProperty DROP_FILTER_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Drop Filter Properties", "Property values for drop filters.", DockingWindowDropFilterProperties.PROPERTIES
   );
   public static final BooleanProperty DRAG_ENABLED = new BooleanProperty(
      PROPERTIES, "Drag Enabled", "Enables/disables window drag by the user.", PropertyMapValueHandler.INSTANCE
   );
   public static final BooleanProperty UNDOCK_ENABLED = new BooleanProperty(
      PROPERTIES, "Undock Enabled", "Enables/disables if a window can be undocked to a floating window.", PropertyMapValueHandler.INSTANCE
   );
   public static final BooleanProperty UNDOCK_ON_DROP = new BooleanProperty(
      PROPERTIES,
      "Undock when Dropped",
      "Enables/disables window undock to floating window when a drag and drop is performed outside the root window.",
      PropertyMapValueHandler.INSTANCE
   );
   public static final BooleanProperty DOCK_ENABLED = new BooleanProperty(
      PROPERTIES, "Dock Enabled", "Enables/disables if a window can be docked to the root window from a floating window.", PropertyMapValueHandler.INSTANCE
   );
   public static final BooleanProperty MINIMIZE_ENABLED = new BooleanProperty(
      PROPERTIES, "Minimize Enabled", "Enables/disables window minimize by the user.", PropertyMapValueHandler.INSTANCE
   );
   public static final BooleanProperty CLOSE_ENABLED = new BooleanProperty(
      PROPERTIES, "Close Enabled", "Enables/disables window close by the user.", PropertyMapValueHandler.INSTANCE
   );
   public static final BooleanProperty RESTORE_ENABLED = new BooleanProperty(
      PROPERTIES, "Restore Enabled", "Enables/disables window restore by the user.", PropertyMapValueHandler.INSTANCE
   );
   public static final BooleanProperty MAXIMIZE_ENABLED = new BooleanProperty(
      PROPERTIES, "Maximize Enabled", "Enables/disables window maximize by the user.", PropertyMapValueHandler.INSTANCE
   );
   public static final DockingWindowTitleProviderProperty TITLE_PROVIDER = new DockingWindowTitleProviderProperty(
      PROPERTIES, "Title Provider", "Provides a title for a window.", PropertyMapValueHandler.INSTANCE
   );

   public DockingWindowProperties() {
      super(PropertyMapFactory.create(PROPERTIES));
   }

   public DockingWindowProperties(PropertyMap var1) {
      super(var1);
   }

   public DockingWindowProperties(DockingWindowProperties var1) {
      super(PropertyMapFactory.create(var1.getMap()));
   }

   public DockingWindowProperties addSuperObject(DockingWindowProperties var1) {
      this.getMap().addSuperMap(var1.getMap());
      return this;
   }

   /** @deprecated */
   public DockingWindowProperties removeSuperObject() {
      this.getMap().removeSuperMap();
      return this;
   }

   public DockingWindowProperties removeSuperObject(DockingWindowProperties var1) {
      this.getMap().removeSuperMap(var1.getMap());
      return this;
   }

   public WindowTabProperties getTabProperties() {
      return new WindowTabProperties(TAB_PROPERTIES.get(this.getMap()));
   }

   public DockingWindowDropFilterProperties getDropFilterProperties() {
      return new DockingWindowDropFilterProperties(DROP_FILTER_PROPERTIES.get(this.getMap()));
   }

   public boolean getDragEnabled() {
      return DRAG_ENABLED.get(this.getMap());
   }

   public DockingWindowProperties setDragEnabled(boolean var1) {
      DRAG_ENABLED.set(this.getMap(), var1);
      return this;
   }

   public boolean getUndockEnabled() {
      return UNDOCK_ENABLED.get(this.getMap());
   }

   public DockingWindowProperties setUndockEnabled(boolean var1) {
      UNDOCK_ENABLED.set(this.getMap(), var1);
      return this;
   }

   public boolean getUndockOnDropEnabled() {
      return UNDOCK_ON_DROP.get(this.getMap());
   }

   public DockingWindowProperties setUndockOnDropEnabled(boolean var1) {
      UNDOCK_ON_DROP.set(this.getMap(), var1);
      return this;
   }

   public boolean getDockEnabled() {
      return DOCK_ENABLED.get(this.getMap());
   }

   public DockingWindowProperties setDockEnabled(boolean var1) {
      DOCK_ENABLED.set(this.getMap(), var1);
      return this;
   }

   public boolean getMinimizeEnabled() {
      return MINIMIZE_ENABLED.get(this.getMap());
   }

   public DockingWindowProperties setMinimizeEnabled(boolean var1) {
      MINIMIZE_ENABLED.set(this.getMap(), var1);
      return this;
   }

   public boolean getMaximizeEnabled() {
      return MAXIMIZE_ENABLED.get(this.getMap());
   }

   public DockingWindowProperties setMaximizeEnabled(boolean var1) {
      MAXIMIZE_ENABLED.set(this.getMap(), var1);
      return this;
   }

   public boolean getCloseEnabled() {
      return CLOSE_ENABLED.get(this.getMap());
   }

   public DockingWindowProperties setCloseEnabled(boolean var1) {
      CLOSE_ENABLED.set(this.getMap(), var1);
      return this;
   }

   public boolean getRestoreEnabled() {
      return RESTORE_ENABLED.get(this.getMap());
   }

   public DockingWindowProperties setRestoreEnabled(boolean var1) {
      RESTORE_ENABLED.set(this.getMap(), var1);
      return this;
   }

   public DockingWindowTitleProvider getTitleProvider() {
      return TITLE_PROVIDER.get(this.getMap());
   }

   public DockingWindowProperties setTitleProvider(DockingWindowTitleProvider var1) {
      TITLE_PROVIDER.set(this.getMap(), var1);
      return this;
   }
}
