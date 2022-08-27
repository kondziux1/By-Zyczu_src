package net.infonode.docking.internal;

import net.infonode.docking.ViewSerializer;

public class WriteContext {
   private boolean writePropertiesEnabled;
   private ViewSerializer viewSerializer;

   public WriteContext(boolean var1, ViewSerializer var2) {
      super();
      this.writePropertiesEnabled = var1;
      this.viewSerializer = var2;
   }

   public boolean getWritePropertiesEnabled() {
      return this.writePropertiesEnabled;
   }

   public ViewSerializer getViewSerializer() {
      return this.viewSerializer;
   }

   public void setViewSerializer(ViewSerializer var1) {
      this.viewSerializer = var1;
   }
}
