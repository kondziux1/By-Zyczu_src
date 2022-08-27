package net.infonode.properties.propertymap;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Map;
import java.util.WeakHashMap;
import net.infonode.properties.base.Property;
import net.infonode.properties.util.PropertyChangeListener;

public class PropertyMapWeakListenerManager {
   private static WeakHashMap listenerMap = new WeakHashMap();
   private static WeakHashMap propertyChangeListenerMap = new WeakHashMap();
   private static WeakHashMap treeListenerMap = new WeakHashMap();
   private static ReferenceQueue refQueue = new ReferenceQueue();
   private static PropertyMapWeakListenerManager.ListenerRef ref;
   private static Runnable refRemover = new PropertyMapWeakListenerManager$1();

   private PropertyMapWeakListenerManager() {
      super();
   }

   private static void addToMap(WeakHashMap var0, Object var1, Object var2) {
      ArrayList var3 = (ArrayList)var0.get(var1);
      if (var3 == null) {
         var3 = new ArrayList(2);
         var0.put(var1, var3);
      }

      var3.add(var2);
   }

   private static void removeFromMap(WeakHashMap var0, Object var1, PropertyMap var2) {
      ArrayList var3 = (ArrayList)var0.get(var1);
      if (var3 != null) {
         for(int var4 = 0; var4 < var3.size(); ++var4) {
            PropertyMapWeakListenerManager.ListenerRef var5 = (PropertyMapWeakListenerManager.ListenerRef)var3.get(var4);
            if (var5.getMap() == var2) {
               var5.removeFromMap();
               var3.remove(var4);
               if (var3.size() == 0) {
                  var0.remove(var1);
               }

               return;
            }
         }
      }

   }

   private static void removeFromMap(WeakHashMap var0, Object var1, PropertyMap var2, Property var3) {
      ArrayList var4 = (ArrayList)var0.get(var1);
      if (var4 != null) {
         for(int var5 = 0; var5 < var4.size(); ++var5) {
            PropertyMapWeakListenerManager.PropertyChangeListenerRef var6 = (PropertyMapWeakListenerManager.PropertyChangeListenerRef)var4.get(var5);
            if (var6.getMap() == var2 && var6.getProperty() == var3) {
               var6.removeFromMap();
               var4.remove(var5);
               if (var4.size() == 0) {
                  var0.remove(var1);
               }

               return;
            }
         }
      }

   }

   public static void addWeakListener(PropertyMap var0, PropertyMapListener var1) {
      PropertyMapWeakListenerManager.MapListenerRef var2 = new PropertyMapWeakListenerManager.MapListenerRef(var1, refQueue, var0);
      addToMap(listenerMap, var1, var2);
   }

   public static void addWeakPropertyChangeListener(PropertyMap var0, Property var1, PropertyChangeListener var2) {
      PropertyMapWeakListenerManager.PropertyChangeListenerRef var3 = new PropertyMapWeakListenerManager.PropertyChangeListenerRef(var2, refQueue, var0, var1);
      addToMap(propertyChangeListenerMap, var2, var3);
   }

   public static void addWeakTreeListener(PropertyMap var0, PropertyMapTreeListener var1) {
      PropertyMapWeakListenerManager.TreeListenerRef var2 = new PropertyMapWeakListenerManager.TreeListenerRef(var1, refQueue, var0);
      addToMap(treeListenerMap, var1, var2);
   }

   public static void removeWeakListener(PropertyMap var0, PropertyMapListener var1) {
      removeFromMap(listenerMap, var1, var0);
   }

   public static void removeWeakPropertyChangeListener(PropertyMap var0, Property var1, PropertyChangeListener var2) {
      removeFromMap(propertyChangeListenerMap, var2, var0, var1);
   }

   public static void removeWeakTreeListener(PropertyMap var0, PropertyMapTreeListener var1) {
      removeFromMap(treeListenerMap, var1, var0);
   }

   static {
      Thread var0 = new Thread(new PropertyMapWeakListenerManager$2());
      var0.setDaemon(true);
      var0.start();
   }

   private static class ListenerRef extends WeakReference {
      private PropertyMap map;

      protected ListenerRef(Object var1, ReferenceQueue var2, PropertyMap var3) {
         super(var1, var2);
         this.map = var3;
      }

      public PropertyMap getMap() {
         return this.map;
      }

      public void removeFromMap() {
         this.map = null;
      }
   }

   private static class MapListenerRef extends PropertyMapWeakListenerManager.ListenerRef implements PropertyMapListener {
      MapListenerRef(PropertyMapListener var1, ReferenceQueue var2, PropertyMap var3) {
         super(var1, var2, var3);
         var3.addListener(this);
      }

      public void removeFromMap() {
         this.getMap().removeListener(this);
         super.removeFromMap();
      }

      public void propertyValuesChanged(PropertyMap var1, Map var2) {
         PropertyMapListener var3 = (PropertyMapListener)this.get();
         if (var3 != null) {
            var3.propertyValuesChanged(var1, var2);
         }

      }
   }

   private static class PropertyChangeListenerRef extends PropertyMapWeakListenerManager.ListenerRef implements PropertyChangeListener {
      private Property property;

      PropertyChangeListenerRef(PropertyChangeListener var1, ReferenceQueue var2, PropertyMap var3, Property var4) {
         super(var1, var2, var3);
         this.property = var4;
         var3.addPropertyChangeListener(var4, this);
      }

      public Property getProperty() {
         return this.property;
      }

      public void removeFromMap() {
         this.getMap().removePropertyChangeListener(this.property, this);
         super.removeFromMap();
      }

      public void propertyChanged(Property var1, Object var2, Object var3, Object var4) {
         PropertyChangeListener var5 = (PropertyChangeListener)this.get();
         if (var5 != null) {
            var5.propertyChanged(var1, var2, var3, var4);
         }

      }
   }

   private static class TreeListenerRef extends PropertyMapWeakListenerManager.ListenerRef implements PropertyMapTreeListener {
      TreeListenerRef(PropertyMapTreeListener var1, ReferenceQueue var2, PropertyMap var3) {
         super(var1, var2, var3);
         var3.addTreeListener(this);
      }

      public void removeFromMap() {
         this.getMap().removeTreeListener(this);
         super.removeFromMap();
      }

      public void propertyValuesChanged(Map var1) {
         PropertyMapTreeListener var2 = (PropertyMapTreeListener)this.get();
         if (var2 != null) {
            var2.propertyValuesChanged(var1);
         }

      }
   }
}
