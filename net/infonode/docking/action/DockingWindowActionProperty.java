package net.infonode.docking.action;

import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.util.PropertyValueHandler;
import net.infonode.properties.util.ValueHandlerProperty;

public class DockingWindowActionProperty extends ValueHandlerProperty {
   public DockingWindowActionProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4) {
      super(
         var1,
         var2,
         class$net$infonode$docking$action$DockingWindowAction == null
            ? (class$net$infonode$docking$action$DockingWindowAction = class$("net.infonode.docking.action.DockingWindowAction"))
            : class$net$infonode$docking$action$DockingWindowAction,
         var3,
         var4
      );
   }

   public DockingWindowAction get(Object var1) {
      return (DockingWindowAction)this.getValue(var1);
   }

   public void set(Object var1, DockingWindowAction var2) {
      this.setValue(var1, var2);
   }
}
