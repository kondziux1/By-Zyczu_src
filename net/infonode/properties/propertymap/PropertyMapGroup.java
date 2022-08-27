package net.infonode.properties.propertymap;

import net.infonode.properties.base.PropertyGroup;

public class PropertyMapGroup extends PropertyGroup {
   private PropertyMapImpl defaultMap;

   public PropertyMapGroup(String var1, String var2) {
      super(var1, var2);
   }

   public PropertyMapGroup(PropertyMapGroup var1, String var2, String var3) {
      super(var1, var2, var3);
   }

   public PropertyMap getDefaultMap() {
      if (this.defaultMap == null) {
         this.defaultMap = new PropertyMapImpl(this);
      }

      return this.defaultMap;
   }
}
