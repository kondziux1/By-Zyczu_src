package net.infonode.util.collection.map;

import java.util.ArrayList;
import net.infonode.util.collection.map.base.ConstMap;
import net.infonode.util.collection.map.base.ConstMapIterator;

public class ConstVectorMap implements ConstMap {
   private ArrayList maps = new ArrayList(2);

   public ConstVectorMap() {
      super();
   }

   private Object getValue(Object var1, int var2, int var3) {
      for(int var4 = var2; var4 < var3; ++var4) {
         Object var5 = this.getMap(var4).get(var1);
         if (var5 != null) {
            return var5;
         }
      }

      return null;
   }

   public void addMap(ConstMap var1) {
      this.addMap(this.maps.size(), var1);
   }

   public void addMap(int var1, ConstMap var2) {
      this.maps.add(var1, var2);
   }

   public int getMapCount() {
      return this.maps.size();
   }

   public ConstMap removeMap(int var1) {
      return (ConstMap)this.maps.remove(var1);
   }

   public Object get(Object var1) {
      for(int var2 = 0; var2 < this.maps.size(); ++var2) {
         Object var3 = this.getMap(var2).get(var1);
         if (var3 != null) {
            return var3;
         }
      }

      return null;
   }

   public boolean containsKey(Object var1) {
      for(int var2 = 0; var2 < this.maps.size(); ++var2) {
         if (this.getMap(var2).containsKey(var1)) {
            return true;
         }
      }

      return false;
   }

   public boolean containsValue(Object var1) {
      for(int var2 = 0; var2 < this.maps.size(); ++var2) {
         if (this.getMap(var2).containsValue(var1)) {
            return true;
         }
      }

      return false;
   }

   public boolean isEmpty() {
      for(int var1 = 0; var1 < this.maps.size(); ++var1) {
         if (!this.getMap(var1).isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public ConstMap getMap(int var1) {
      return (ConstMap)this.maps.get(var1);
   }

   public int getMapIndex(ConstMap var1) {
      return this.maps.indexOf(var1);
   }

   public ConstMapIterator constIterator() {
      return new ConstVectorMap.ConstIterator();
   }

   private class ConstIterator implements ConstMapIterator {
      private int index = 1;
      private ConstMapIterator iterator;

      ConstIterator() {
         super();
         if (ConstVectorMap.this.maps.size() > 0) {
            this.iterator = ConstVectorMap.this.getMap(0).constIterator();
            this.advance();
         } else {
            this.iterator = EmptyIterator.INSTANCE;
         }

      }

      public Object getKey() {
         return this.iterator.getKey();
      }

      public Object getValue() {
         return this.iterator.getValue();
      }

      public void next() {
         this.iterator.next();
         this.advance();
      }

      public boolean atEntry() {
         return this.iterator.atEntry();
      }

      private void advance() {
         while(true) {
            if (!this.iterator.atEntry()) {
               if (this.index == ConstVectorMap.this.maps.size()) {
                  return;
               }

               this.iterator = ConstVectorMap.this.getMap(this.index++).constIterator();
            } else {
               if (ConstVectorMap.this.getValue(this.iterator.getKey(), 0, this.index - 1) == null) {
                  return;
               }

               this.iterator.next();
            }
         }
      }
   }
}
