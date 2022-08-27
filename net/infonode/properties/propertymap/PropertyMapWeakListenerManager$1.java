package net.infonode.properties.propertymap;

class PropertyMapWeakListenerManager$1 implements Runnable {
   PropertyMapWeakListenerManager$1() {
      super();
   }

   public void run() {
      while(PropertyMapWeakListenerManager.access$000() != null) {
         PropertyMapWeakListenerManager.access$000().removeFromMap();
         PropertyMapWeakListenerManager.access$002((PropertyMapWeakListenerManager.ListenerRef)PropertyMapWeakListenerManager.access$100().poll());
      }

   }
}
