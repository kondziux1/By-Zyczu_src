package net.infonode.util.collection.map;

import net.infonode.util.collection.map.base.ConstMapIterator;

class SingleValueMap$1 implements ConstMapIterator {
   private boolean done;

   SingleValueMap$1(SingleValueMap var1) {
      super();
      this.this$0 = var1;
   }

   public Object getKey() {
      return SingleValueMap.access$000(this.this$0);
   }

   public Object getValue() {
      return SingleValueMap.access$100(this.this$0);
   }

   public void next() {
      this.done = true;
   }

   public boolean atEntry() {
      return !this.done;
   }
}
