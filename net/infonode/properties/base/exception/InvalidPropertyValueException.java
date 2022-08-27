package net.infonode.properties.base.exception;

import net.infonode.properties.base.Property;

public class InvalidPropertyValueException extends PropertyException {
   private Object value;

   public InvalidPropertyValueException(Property var1, Object var2) {
      super(var1, "Property '" + var1 + "' can't be assigned the value '" + var2 + "'!");
      this.value = var2;
   }

   public Object getValue() {
      return this.value;
   }
}
