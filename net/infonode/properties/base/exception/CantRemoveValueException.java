package net.infonode.properties.base.exception;

import net.infonode.properties.base.Property;

public class CantRemoveValueException extends PropertyException {
   public CantRemoveValueException(Property var1) {
      super(var1, "Can't remove property value for '" + var1.getName() + "'!");
   }
}
