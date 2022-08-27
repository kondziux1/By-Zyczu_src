package net.infonode.properties.base.exception;

import net.infonode.properties.base.Property;

public class PropertyException extends RuntimeException {
   private Property property;

   public PropertyException(Property var1, String var2) {
      super(var2);
      this.property = var1;
   }

   public Property getProperty() {
      return this.property;
   }
}
