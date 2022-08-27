package net.infonode.docking.model;

import java.util.ArrayList;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.properties.propertymap.PropertyMap;

public class RootWindowItem extends WindowItem {
   private RootWindowProperties rootWindowProperties = RootWindowProperties.createDefault();

   public RootWindowItem() {
      super();
   }

   public RootWindowItem(RootWindowItem var1) {
      super(var1);
   }

   protected PropertyMap createPropertyObject() {
      return new RootWindowProperties().getMap();
   }

   protected PropertyMap getPropertyObject() {
      return this.rootWindowProperties.getMap();
   }

   public RootWindowProperties getRootWindowProperties() {
      return this.rootWindowProperties;
   }

   public boolean isRestoreWindow() {
      return true;
   }

   protected DockingWindow createWindow(ViewReader var1, ArrayList var2) {
      return var2.size() == 0 ? null : (DockingWindow)var2.get(0);
   }

   public RootWindowItem getRootItem() {
      return this;
   }

   public WindowItem copy() {
      return new RootWindowItem(this);
   }
}
