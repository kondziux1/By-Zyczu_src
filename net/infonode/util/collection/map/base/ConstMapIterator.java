package net.infonode.util.collection.map.base;

public interface ConstMapIterator {
   Object getKey();

   Object getValue();

   void next();

   boolean atEntry();
}
