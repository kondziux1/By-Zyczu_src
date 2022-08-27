package net.infonode.util.collection.map;

import net.infonode.util.Utils;
import net.infonode.util.collection.map.base.ConstMap;
import net.infonode.util.collection.map.base.ConstMapIterator;

public class SingleValueMap implements ConstMap {
   private Object key;
   private Object value;

   public SingleValueMap(Object var1, Object var2) {
      super();
      this.key = var1;
      this.value = var2;
   }

   public Object get(Object var1) {
      return Utils.equals(var1, this.key) ? this.value : null;
   }

   public boolean containsKey(Object var1) {
      return Utils.equals(var1, this.key);
   }

   public boolean containsValue(Object var1) {
      return Utils.equals(var1, this.value);
   }

   public ConstMapIterator constIterator() {
      return new SingleValueMap$1(this);
   }

   public boolean isEmpty() {
      return false;
   }
}
