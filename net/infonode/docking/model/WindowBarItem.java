package net.infonode.docking.model;

import java.util.ArrayList;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.properties.WindowBarProperties;
import net.infonode.properties.propertymap.PropertyMap;

public class WindowBarItem extends AbstractTabWindowItem {
   private WindowBarProperties windowBarProperties;

   public WindowBarItem() {
      super();
   }

   public WindowBarItem(WindowBarItem var1) {
      super(var1);
   }

   protected DockingWindow createWindow(ViewReader var1, ArrayList var2) {
      return null;
   }

   public WindowItem copy() {
      return new WindowBarItem(this);
   }

   public WindowBarProperties getWindowBarProperties() {
      return this.windowBarProperties;
   }

   protected PropertyMap getPropertyObject() {
      return this.windowBarProperties.getMap();
   }

   public void setWindowBarProperties(WindowBarProperties var1) {
      this.windowBarProperties = var1;
   }

   public boolean isRestoreWindow() {
      return false;
   }

   public String toString() {
      return "WindowBar:\n" + super.toString();
   }
}
