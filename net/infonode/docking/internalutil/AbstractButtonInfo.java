package net.infonode.docking.internalutil;

import net.infonode.properties.propertymap.PropertyMapProperty;

public abstract class AbstractButtonInfo implements ButtonInfo {
   private PropertyMapProperty property;

   protected AbstractButtonInfo(PropertyMapProperty var1) {
      super();
      this.property = var1;
   }

   public PropertyMapProperty getProperty() {
      return this.property;
   }
}
