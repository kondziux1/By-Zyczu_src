package net.infonode.util.collection.map.base;

import net.infonode.util.collection.ConstCollection;

public interface ConstMap extends ConstCollection {
   Object get(Object var1);

   boolean containsKey(Object var1);

   boolean containsValue(Object var1);

   ConstMapIterator constIterator();
}
