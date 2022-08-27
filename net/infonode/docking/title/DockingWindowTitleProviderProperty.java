package net.infonode.docking.title;

import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.util.PropertyValueHandler;
import net.infonode.properties.util.ValueHandlerProperty;

public class DockingWindowTitleProviderProperty extends ValueHandlerProperty {
   public DockingWindowTitleProviderProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4) {
      super(
         var1,
         var2,
         class$net$infonode$docking$title$DockingWindowTitleProvider == null
            ? (class$net$infonode$docking$title$DockingWindowTitleProvider = class$("net.infonode.docking.title.DockingWindowTitleProvider"))
            : class$net$infonode$docking$title$DockingWindowTitleProvider,
         var3,
         var4
      );
   }

   public DockingWindowTitleProvider get(Object var1) {
      return (DockingWindowTitleProvider)this.getValue(var1);
   }

   public void set(Object var1, DockingWindowTitleProvider var2) {
      this.setValue(var1, var2);
   }
}
