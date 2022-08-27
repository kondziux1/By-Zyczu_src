package net.infonode.properties.base.exception;

import net.infonode.properties.base.Property;

public class InvalidPropertyTypeException extends PropertyException {
   private Property invalidProperty;

   public InvalidPropertyTypeException(Property var1, Property var2, String var3) {
      super(var1, var3);
      this.invalidProperty = var2;
   }

   public Property getInvalidProperty() {
      return this.invalidProperty;
   }
}
