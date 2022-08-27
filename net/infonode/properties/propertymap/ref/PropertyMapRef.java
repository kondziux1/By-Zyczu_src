package net.infonode.properties.propertymap.ref;

import java.io.IOException;
import java.io.ObjectOutputStream;
import net.infonode.properties.propertymap.PropertyMapImpl;

public interface PropertyMapRef {
   PropertyMapImpl getMap(PropertyMapImpl var1);

   void write(ObjectOutputStream var1) throws IOException;
}
