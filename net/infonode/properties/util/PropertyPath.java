package net.infonode.properties.util;

import net.infonode.properties.base.Property;

public class PropertyPath {
   private Property property;
   private PropertyPath tail;

   public PropertyPath(Property var1) {
      this(var1, null);
   }

   public PropertyPath(Property var1, PropertyPath var2) {
      super();
      this.property = var1;
      this.tail = var2;
   }

   public Property getProperty() {
      return this.property;
   }

   public PropertyPath getTail() {
      return this.tail;
   }

   public PropertyPath copy() {
      return new PropertyPath(this.property, this.getTail() == null ? null : this.getTail().copy());
   }
}
