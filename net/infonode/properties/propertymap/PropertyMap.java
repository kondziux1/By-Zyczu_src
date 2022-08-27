package net.infonode.properties.propertymap;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import net.infonode.properties.base.Property;
import net.infonode.properties.base.exception.InvalidPropertyException;
import net.infonode.properties.base.exception.InvalidPropertyTypeException;
import net.infonode.properties.util.PropertyChangeListener;
import net.infonode.util.ReadWritable;

public interface PropertyMap extends ReadWritable {
   void addListener(PropertyMapListener var1);

   void removeListener(PropertyMapListener var1);

   void addTreeListener(PropertyMapTreeListener var1);

   void removeTreeListener(PropertyMapTreeListener var1);

   void addPropertyChangeListener(Property var1, PropertyChangeListener var2);

   void removePropertyChangeListener(Property var1, PropertyChangeListener var2);

   void addSuperMap(PropertyMap var1);

   PropertyMap removeSuperMap();

   boolean removeSuperMap(PropertyMap var1);

   boolean replaceSuperMap(PropertyMap var1, PropertyMap var2);

   PropertyMap getSuperMap();

   Object createRelativeRef(Property var1, PropertyMap var2, Property var3) throws InvalidPropertyTypeException;

   Object removeValue(Property var1) throws InvalidPropertyException;

   boolean isEmpty(boolean var1);

   void clear(boolean var1);

   boolean valuesEqualTo(PropertyMap var1, boolean var2);

   void write(ObjectOutputStream var1, boolean var2) throws IOException;

   void write(ObjectOutputStream var1) throws IOException;

   void read(ObjectInputStream var1) throws IOException;

   PropertyMap copy(boolean var1, boolean var2);
}
