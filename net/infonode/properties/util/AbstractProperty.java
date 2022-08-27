package net.infonode.properties.util;

import net.infonode.properties.base.Property;
import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.base.exception.InvalidPropertyValueException;

public abstract class AbstractProperty implements Property {
   private PropertyGroup group;
   private String name;
   private Class type;
   private String description;

   protected AbstractProperty(PropertyGroup var1, String var2, Class var3, String var4) {
      super();
      this.group = var1;
      this.name = var2;
      this.type = var3;
      this.description = var4;
      if (var1 != null) {
         var1.addProperty(this);
      }

   }

   public PropertyGroup getGroup() {
      return this.group;
   }

   public String getName() {
      return this.name;
   }

   public Class getType() {
      return this.type;
   }

   public String getDescription() {
      return this.description;
   }

   public boolean isMutable() {
      return true;
   }

   public void setValue(Object var1, Object var2) {
      if (!this.canBeAssiged(var2)) {
         throw new InvalidPropertyValueException(this, var2);
      }
   }

   public String toString() {
      return this.getName();
   }

   public boolean canBeAssiged(Object var1) {
      return this.isMutable() && (var1 == null || this.getType().isAssignableFrom(var1.getClass()));
   }
}
