package net.infonode.docking.properties;

import net.infonode.gui.DynamicUIManagerListener;
import net.infonode.properties.propertymap.PropertyMapManager;

class RootWindowProperties$2 implements DynamicUIManagerListener {
   RootWindowProperties$2() {
      super();
   }

   public void lookAndFeelChanged() {
      PropertyMapManager.runBatch(new RootWindowProperties$3(this));
   }

   public void propertiesChanged() {
      PropertyMapManager.runBatch(new RootWindowProperties$4(this));
   }

   public void propertiesChanging() {
   }

   public void lookAndFeelChanging() {
   }
}
