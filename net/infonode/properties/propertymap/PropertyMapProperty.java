package net.infonode.properties.propertymap;

import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.types.PropertyGroupProperty;

public class PropertyMapProperty extends PropertyGroupProperty {
   public PropertyMapProperty(PropertyGroup var1, String var2, String var3, PropertyMapGroup var4) {
      super(
         var1,
         var2,
         class$net$infonode$properties$propertymap$PropertyMap == null
            ? (class$net$infonode$properties$propertymap$PropertyMap = class$("net.infonode.properties.propertymap.PropertyMap"))
            : class$net$infonode$properties$propertymap$PropertyMap,
         var3,
         PropertyMapValueHandler.INSTANCE,
         var4
      );
   }

   public PropertyMapGroup getPropertyMapGroup() {
      return (PropertyMapGroup)this.getPropertyGroup();
   }

   public boolean isMutable() {
      return false;
   }

   public Object getValue(Object var1) {
      return ((PropertyMapImpl)var1).getChildMapImpl(this);
   }

   public PropertyMap get(Object var1) {
      return (PropertyMap)this.getValue(var1);
   }
}
