package net.infonode.docking.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.internal.WriteContext;
import net.infonode.docking.properties.TabWindowProperties;
import net.infonode.properties.propertymap.PropertyMap;

public class TabWindowItem extends AbstractTabWindowItem {
   public static final TabWindowProperties emptyProperties = new TabWindowProperties();
   private TabWindowProperties tabWindowProperties;
   private TabWindowProperties parentProperties = emptyProperties;

   public TabWindowItem() {
      super();
      this.tabWindowProperties = new TabWindowProperties(emptyProperties);
   }

   public TabWindowItem(TabWindowItem var1) {
      super(var1);
      this.tabWindowProperties = new TabWindowProperties(var1.getTabWindowProperties().getMap().copy(true, true));
      this.tabWindowProperties.getMap().replaceSuperMap(var1.getParentTabWindowProperties().getMap(), emptyProperties.getMap());
   }

   protected DockingWindow createWindow(ViewReader var1, ArrayList var2) {
      return var2.size() == 0 ? null : var1.createTabWindow((DockingWindow[])var2.toArray(new DockingWindow[var2.size()]), this);
   }

   public TabWindowProperties getTabWindowProperties() {
      return this.tabWindowProperties;
   }

   public void setTabWindowProperties(TabWindowProperties var1) {
      this.tabWindowProperties = var1;
   }

   public TabWindowProperties getParentTabWindowProperties() {
      return this.parentProperties;
   }

   public void setParentTabWindowProperties(TabWindowProperties var1) {
      this.tabWindowProperties.getMap().replaceSuperMap(this.parentProperties.getMap(), var1.getMap());
      this.parentProperties = var1;
   }

   public WindowItem copy() {
      return new TabWindowItem(this);
   }

   public void write(ObjectOutputStream var1, WriteContext var2, ViewWriter var3) throws IOException {
      var1.writeInt(1);
      super.write(var1, var2, var3);
   }

   protected PropertyMap getPropertyObject() {
      return this.getTabWindowProperties().getMap();
   }

   public void clearWindows() {
   }

   public String toString() {
      return "TabWindow: " + super.toString();
   }
}
