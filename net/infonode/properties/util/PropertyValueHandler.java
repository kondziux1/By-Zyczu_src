package net.infonode.properties.util;

import net.infonode.properties.base.Property;

public interface PropertyValueHandler {
   Object getValue(Property var1, Object var2);

   void setValue(Property var1, Object var2, Object var3);

   void removeValue(Property var1, Object var2);

   boolean getValueIsSet(Property var1, Object var2);

   boolean getValueIsRemovable(Property var1, Object var2);
}
