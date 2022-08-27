package net.infonode.util.collection.map.base;

import net.infonode.util.collection.Collection;

public interface Map extends ConstMap, Collection {
   Object put(Object var1, Object var2);

   Object remove(Object var1);

   MapIterator iterator();
}
