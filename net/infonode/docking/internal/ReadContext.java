package net.infonode.docking.internal;

import net.infonode.docking.RootWindow;
import net.infonode.docking.ViewSerializer;

public class ReadContext {
   private RootWindow rootWindow;
   private int version;
   private boolean propertyValuesAvailable;
   private boolean readPropertiesEnabled;

   public ReadContext(RootWindow var1, int var2, boolean var3, boolean var4) {
      super();
      this.rootWindow = var1;
      this.version = var2;
      this.propertyValuesAvailable = var3;
      this.readPropertiesEnabled = var4;
   }

   public RootWindow getRootWindow() {
      return this.rootWindow;
   }

   public ViewSerializer getViewSerializer() {
      return this.rootWindow.getViewSerializer();
   }

   public boolean isPropertyValuesAvailable() {
      return this.propertyValuesAvailable;
   }

   public boolean getReadPropertiesEnabled() {
      return this.readPropertiesEnabled;
   }

   public int getVersion() {
      return this.version;
   }
}
