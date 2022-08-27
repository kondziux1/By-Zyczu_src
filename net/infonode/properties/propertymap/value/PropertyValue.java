package net.infonode.properties.propertymap.value;

import java.io.IOException;
import java.io.ObjectOutputStream;
import net.infonode.properties.propertymap.PropertyMapImpl;
import net.infonode.util.Printer;

public interface PropertyValue {
   Object get(PropertyMapImpl var1);

   Object getWithDefault(PropertyMapImpl var1);

   PropertyValue getSubValue(PropertyMapImpl var1);

   void unset();

   PropertyValue getParent();

   void dump(Printer var1);

   void write(ObjectOutputStream var1) throws IOException;

   void updateListener(boolean var1);

   boolean isSerializable();

   PropertyValue copyTo(PropertyMapImpl var1);
}
