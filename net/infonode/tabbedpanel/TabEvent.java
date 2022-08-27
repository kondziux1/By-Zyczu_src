package net.infonode.tabbedpanel;

import java.util.EventObject;

public class TabEvent extends EventObject {
   private Tab tab;

   TabEvent(Object var1, Tab var2) {
      super(var1);
      this.tab = var2;
   }

   public Tab getTab() {
      return this.tab;
   }
}
