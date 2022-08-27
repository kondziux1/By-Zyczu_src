package net.infonode.properties.base.exception;

import net.infonode.properties.base.Property;

public class ImmutablePropertyException extends PropertyException {
   public ImmutablePropertyException(Property var1) {
      super(var1, "Can't modify immutable property '" + var1.getName() + "'!");
   }
}
