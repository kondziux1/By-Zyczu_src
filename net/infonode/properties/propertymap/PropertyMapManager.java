package net.infonode.properties.propertymap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import net.infonode.properties.propertymap.value.PropertyValue;
import net.infonode.util.Utils;
import net.infonode.util.ValueChange;
import net.infonode.util.collection.map.base.ConstMap;
import net.infonode.util.collection.map.base.ConstMapIterator;

public class PropertyMapManager {
   private static final PropertyMapManager INSTANCE = new PropertyMapManager();
   private HashMap changes;
   private int batchCounter;

   public PropertyMapManager() {
      super();
   }

   public static PropertyMapManager getInstance() {
      return INSTANCE;
   }

   void addMapChanges(PropertyMapImpl var1, ConstMap var2) {
      HashMap var3 = (HashMap)this.changes.get(var1);
      if (var3 == null) {
         var3 = new HashMap();
         this.changes.put(var1, var3);
      }

      for(ConstMapIterator var4 = var2.constIterator(); var4.atEntry(); var4.next()) {
         ValueChange var5 = (ValueChange)var4.getValue();
         Object var6 = var4.getKey();
         Object var7 = var5.getNewValue() == null ? null : ((PropertyValue)var5.getNewValue()).getWithDefault(var1);
         Object var8 = var3.get(var6);
         Object var9 = var8 == null
            ? (var5.getOldValue() == null ? null : ((PropertyValue)var5.getOldValue()).getWithDefault(var1))
            : ((ValueChange)var8).getOldValue();
         if (!Utils.equals(var9, var7)) {
            var3.put(var4.getKey(), new ValueChange(var9, var7));
         } else if (var8 != null) {
            var3.remove(var6);
         }
      }

   }

   public static void runBatch(Runnable var0) {
      getInstance().beginBatch();

      try {
         var0.run();
      } finally {
         getInstance().endBatch();
      }

   }

   public void beginBatch() {
      if (this.batchCounter++ == 0) {
         this.changes = new HashMap();
      }

   }

   private void addTreeChanges(PropertyMapImpl var1, PropertyMapImpl var2, HashMap var3, HashMap var4) {
      HashMap var5 = (HashMap)var4.get(var1);
      if (var5 == null) {
         var5 = new HashMap();
         var4.put(var1, var5);
      }

      var5.put(var2, var3);
      if (var1.getParent() != null) {
         this.addTreeChanges(var1.getParent(), var2, var3, var4);
      }

   }

   public void endBatch() {
      if (--this.batchCounter == 0) {
         HashMap var1 = new HashMap();
         HashMap var2 = this.changes;
         this.changes = null;

         for(Entry var4 : var2.entrySet()) {
            PropertyMapImpl var5 = (PropertyMapImpl)var4.getKey();
            HashMap var6 = (HashMap)var4.getValue();
            if (!var6.isEmpty()) {
               var5.firePropertyValuesChanged(Collections.unmodifiableMap(var6));
               this.addTreeChanges(var5, var5, var6, var1);
            }
         }

         for(Entry var8 : var1.entrySet()) {
            PropertyMapImpl var9 = (PropertyMapImpl)var8.getKey();
            HashMap var10 = (HashMap)var8.getValue();
            if (!var10.isEmpty()) {
               var9.firePropertyTreeValuesChanged(Collections.unmodifiableMap(var10));
            }
         }
      }

   }
}
