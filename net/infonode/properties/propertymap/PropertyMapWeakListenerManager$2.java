package net.infonode.properties.propertymap;

import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;

class PropertyMapWeakListenerManager$2 implements Runnable {
   PropertyMapWeakListenerManager$2() {
      super();
   }

   public void run() {
      try {
         while(true) {
            PropertyMapWeakListenerManager.access$002((PropertyMapWeakListenerManager.ListenerRef)PropertyMapWeakListenerManager.access$100().remove());
            SwingUtilities.invokeAndWait(PropertyMapWeakListenerManager.access$200());
         }
      } catch (InterruptedException var2) {
      } catch (InvocationTargetException var3) {
      }

   }
}
