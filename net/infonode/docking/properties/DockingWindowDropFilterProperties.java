package net.infonode.docking.properties;

import net.infonode.docking.drop.DropFilter;
import net.infonode.docking.drop.DropFilterProperty;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapContainer;
import net.infonode.properties.propertymap.PropertyMapFactory;
import net.infonode.properties.propertymap.PropertyMapGroup;
import net.infonode.properties.propertymap.PropertyMapValueHandler;

public class DockingWindowDropFilterProperties extends PropertyMapContainer {
   public static final PropertyMapGroup PROPERTIES = new PropertyMapGroup("Docking Window Drop Filter Properties", "");
   public static final DropFilterProperty SPLIT_DROP_FILTER = new DropFilterProperty(
      PROPERTIES, "Split Drop Filter", "The drop filter that is called when a split drop is in progress.", PropertyMapValueHandler.INSTANCE
   );
   public static final DropFilterProperty CHILD_DROP_FILTER = new DropFilterProperty(
      PROPERTIES, "Child Drop Filter", "The drop filter that is called when a child window will be asked for accept drop.", PropertyMapValueHandler.INSTANCE
   );
   public static final DropFilterProperty INTERIOR_DROP_FILTER = new DropFilterProperty(
      PROPERTIES, "Interior Drop Filter", "The drop filter that is called when an interior drop is in progress.", PropertyMapValueHandler.INSTANCE
   );
   public static final DropFilterProperty INSERT_TAB_DROP_FILTER = new DropFilterProperty(
      PROPERTIES, "Insert Tab Drop Filter", "The drop filter that is called when an insert tab drop is in progress.", PropertyMapValueHandler.INSTANCE
   );

   public DockingWindowDropFilterProperties() {
      super(PropertyMapFactory.create(PROPERTIES));
   }

   public DockingWindowDropFilterProperties(PropertyMap var1) {
      super(var1);
   }

   public DockingWindowDropFilterProperties(DockingWindowDropFilterProperties var1) {
      super(PropertyMapFactory.create(var1.getMap()));
   }

   public DockingWindowDropFilterProperties addSuperObject(DockingWindowDropFilterProperties var1) {
      this.getMap().addSuperMap(var1.getMap());
      return this;
   }

   public DockingWindowDropFilterProperties removeSuperObject(DockingWindowDropFilterProperties var1) {
      this.getMap().removeSuperMap(var1.getMap());
      return this;
   }

   public DockingWindowDropFilterProperties setSplitDropFilter(DropFilter var1) {
      SPLIT_DROP_FILTER.set(this.getMap(), var1);
      return this;
   }

   public DropFilter getSplitDropFilter() {
      return SPLIT_DROP_FILTER.get(this.getMap());
   }

   public DockingWindowDropFilterProperties setChildDropFilter(DropFilter var1) {
      CHILD_DROP_FILTER.set(this.getMap(), var1);
      return this;
   }

   public DropFilter getChildDropFilter() {
      return CHILD_DROP_FILTER.get(this.getMap());
   }

   public DockingWindowDropFilterProperties setInteriorDropFilter(DropFilter var1) {
      INTERIOR_DROP_FILTER.set(this.getMap(), var1);
      return this;
   }

   public DropFilter getInteriorDropFilter() {
      return INTERIOR_DROP_FILTER.get(this.getMap());
   }

   public DockingWindowDropFilterProperties setInsertTabDropFilter(DropFilter var1) {
      INSERT_TAB_DROP_FILTER.set(this.getMap(), var1);
      return this;
   }

   public DropFilter getInsertTabDropFilter() {
      return INSERT_TAB_DROP_FILTER.get(this.getMap());
   }
}
