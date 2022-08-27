package net.infonode.properties.propertymap;

import net.infonode.properties.base.Property;
import net.infonode.properties.base.exception.CantRemoveValueException;
import net.infonode.properties.propertymap.value.PropertyValue;
import net.infonode.properties.propertymap.value.SimplePropertyValue;
import net.infonode.properties.types.PropertyGroupProperty;
import net.infonode.properties.util.PropertyValueHandler;

public class PropertyMapValueHandler implements PropertyValueHandler {
   public static final PropertyMapValueHandler INSTANCE = new PropertyMapValueHandler();

   private PropertyMapValueHandler() {
      super();
   }

   public Object getValue(Property var1, Object var2) {
      PropertyMapImpl var3 = (PropertyMapImpl)var2;
      PropertyValue var4 = var3.getValueWithDefault(var1);
      return var4 == null ? null : var4.getWithDefault(var3);
   }

   public void setValue(Property var1, Object var2, Object var3) {
      ((PropertyMapImpl)var2).setValue(var1, new SimplePropertyValue(var3));
   }

   public boolean getValueIsRemovable(Property var1, Object var2) {
      return !(var1 instanceof PropertyGroupProperty);
   }

   public void removeValue(Property var1, Object var2) {
      if (var1 instanceof PropertyGroupProperty) {
         throw new CantRemoveValueException(var1);
      } else {
         ((PropertyMapImpl)var2).removeValue(var1);
      }
   }

   public boolean getValueIsSet(Property var1, Object var2) {
      return var1 instanceof PropertyGroupProperty || ((PropertyMapImpl)var2).valueIsSet(var1);
   }
}
