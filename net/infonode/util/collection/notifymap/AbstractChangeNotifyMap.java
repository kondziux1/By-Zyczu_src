package net.infonode.util.collection.notifymap;

import net.infonode.util.collection.map.base.ConstMapIterator;

public abstract class AbstractChangeNotifyMap extends AbstractConstChangeNotifyMap implements ChangeNotifyMap {
   public AbstractChangeNotifyMap() {
      super();
   }

   public ConstMapIterator constIterator() {
      return this.iterator();
   }
}
