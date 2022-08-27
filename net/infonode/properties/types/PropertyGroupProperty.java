package net.infonode.properties.types;

import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.util.PropertyValueHandler;
import net.infonode.properties.util.ValueHandlerProperty;

public class PropertyGroupProperty extends ValueHandlerProperty {
   private PropertyGroup propertyGroup;

   public PropertyGroupProperty(PropertyGroup var1, String var2, Class var3, String var4, PropertyValueHandler var5, PropertyGroup var6) {
      super(var1, var2, var3, var4, var5);
      this.propertyGroup = var6;
   }

   public PropertyGroup getPropertyGroup() {
      return this.propertyGroup;
   }
}
