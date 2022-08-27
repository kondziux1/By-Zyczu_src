package net.infonode.properties.base;

import net.infonode.properties.base.exception.CantRemoveValueException;
import net.infonode.properties.base.exception.ImmutablePropertyException;
import net.infonode.properties.base.exception.InvalidPropertyException;
import net.infonode.properties.base.exception.InvalidPropertyValueException;

public interface Property {
   String getName();

   String getDescription();

   Class getType();

   PropertyGroup getGroup();

   Object getValue(Object var1) throws InvalidPropertyException;

   void setValue(Object var1, Object var2) throws ImmutablePropertyException, InvalidPropertyException, InvalidPropertyValueException;

   boolean canBeAssiged(Object var1);

   boolean isMutable();

   boolean valueIsRemovable(Object var1);

   boolean valueIsSet(Object var1);

   void removeValue(Object var1) throws ImmutablePropertyException, CantRemoveValueException;
}
