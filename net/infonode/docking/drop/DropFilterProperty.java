package net.infonode.docking.drop;

import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.util.PropertyValueHandler;
import net.infonode.properties.util.ValueHandlerProperty;

public class DropFilterProperty extends ValueHandlerProperty {
   public DropFilterProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4) {
      super(
         var1,
         var2,
         class$net$infonode$docking$drop$DropFilter == null
            ? (class$net$infonode$docking$drop$DropFilter = class$("net.infonode.docking.drop.DropFilter"))
            : class$net$infonode$docking$drop$DropFilter,
         var3,
         var4
      );
   }

   public DropFilter get(Object var1) {
      return (DropFilter)this.getValue(var1);
   }

   public void set(Object var1, DropFilter var2) {
      this.setValue(var1, var2);
   }
}
