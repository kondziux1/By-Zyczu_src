package net.infonode.docking.model;

import java.util.ArrayList;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.properties.FloatingWindowProperties;
import net.infonode.properties.propertymap.PropertyMap;

public class FloatingWindowItem extends WindowItem {
   private FloatingWindowProperties properties = new FloatingWindowProperties();

   public FloatingWindowItem() {
      super();
   }

   protected DockingWindow createWindow(ViewReader var1, ArrayList var2) {
      return null;
   }

   public WindowItem copy() {
      return null;
   }

   public boolean isRestoreWindow() {
      return false;
   }

   public FloatingWindowProperties getFloatingWindowProperties() {
      return this.properties;
   }

   protected PropertyMap getPropertyObject() {
      return this.properties.getMap();
   }
}
