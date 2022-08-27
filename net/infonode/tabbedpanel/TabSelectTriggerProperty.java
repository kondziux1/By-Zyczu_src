package net.infonode.tabbedpanel;

import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.types.EnumProperty;
import net.infonode.properties.util.PropertyValueHandler;

public class TabSelectTriggerProperty extends EnumProperty {
   public TabSelectTriggerProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4) {
      super(
         var1,
         var2,
         class$net$infonode$tabbedpanel$TabSelectTrigger == null
            ? (class$net$infonode$tabbedpanel$TabSelectTrigger = class$("net.infonode.tabbedpanel.TabSelectTrigger"))
            : class$net$infonode$tabbedpanel$TabSelectTrigger,
         var3,
         var4,
         TabSelectTrigger.getSelectTriggers()
      );
   }

   public TabSelectTrigger get(Object var1) {
      return (TabSelectTrigger)this.getValue(var1);
   }

   public void set(Object var1, TabSelectTrigger var2) {
      this.setValue(var1, var2);
   }
}
