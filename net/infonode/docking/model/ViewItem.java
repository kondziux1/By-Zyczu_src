package net.infonode.docking.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.View;
import net.infonode.docking.internal.ReadContext;
import net.infonode.docking.internal.WriteContext;
import net.infonode.docking.properties.ViewProperties;
import net.infonode.properties.propertymap.PropertyMap;

public class ViewItem extends WindowItem {
   private ViewProperties viewProperties = new ViewProperties();

   public ViewItem() {
      super();
   }

   public ViewItem(ViewItem var1) {
      super(var1);
   }

   protected PropertyMap getPropertyObject() {
      return this.viewProperties.getMap();
   }

   protected DockingWindow createWindow(ViewReader var1, ArrayList var2) {
      return null;
   }

   public ViewProperties getViewProperties() {
      return this.viewProperties;
   }

   public void write(ObjectOutputStream var1, WriteContext var2, ViewWriter var3) throws IOException {
      var1.writeInt(2);
      DockingWindow var4 = this.getConnectedWindow();
      var3.writeView((View)this.getConnectedWindow(), var1, var2);
      var1.writeBoolean(var4 != null && !var4.isMinimized() && !var4.isUndocked() && var4.getRootWindow() != null);
   }

   public DockingWindow read(ObjectInputStream var1, ReadContext var2, ViewReader var3) throws IOException {
      return var1.readBoolean() ? this.getConnectedWindow() : null;
   }

   public WindowItem copy() {
      return new ViewItem(this);
   }
}
