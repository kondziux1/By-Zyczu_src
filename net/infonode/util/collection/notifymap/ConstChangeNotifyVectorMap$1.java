package net.infonode.util.collection.notifymap;

import net.infonode.util.ValueChange;
import net.infonode.util.collection.map.MapAdapter;
import net.infonode.util.collection.map.base.ConstMap;
import net.infonode.util.collection.map.base.ConstMapIterator;
import net.infonode.util.signal.Signal;
import net.infonode.util.signal.SignalListener;

class ConstChangeNotifyVectorMap$1 implements SignalListener {
   ConstChangeNotifyVectorMap$1(ConstChangeNotifyVectorMap var1, ConstChangeNotifyMap var2) {
      super();
      this.this$0 = var1;
      this.val$map = var2;
   }

   public void signalEmitted(Signal var1, Object var2) {
      ConstMap var3 = (ConstMap)var2;
      MapAdapter var4 = new MapAdapter();
      int var5 = this.this$0.getMapIndex(this.val$map);

      for(ConstMapIterator var6 = var3.constIterator(); var6.atEntry(); var6.next()) {
         Object var7 = ConstChangeNotifyVectorMap.access$000(this.this$0, var6.getKey(), 0, var5);
         if (var7 == null) {
            ValueChange var8 = (ValueChange)var6.getValue();
            var4.put(
               var6.getKey(),
               var8.getOldValue() == null
                  ? new ValueChange(ConstChangeNotifyVectorMap.access$000(this.this$0, var6.getKey(), var5 + 1, this.this$0.getMapCount()), var8.getNewValue())
                  : (
                     var8.getNewValue() == null
                        ? new ValueChange(
                           var8.getOldValue(), ConstChangeNotifyVectorMap.access$000(this.this$0, var6.getKey(), var5 + 1, this.this$0.getMapCount())
                        )
                        : var8
                  )
            );
         }
      }

      this.this$0.fireEntriesChanged(var4);
   }
}
