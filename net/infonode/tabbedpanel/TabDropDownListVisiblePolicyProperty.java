package net.infonode.tabbedpanel;

import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.types.EnumProperty;
import net.infonode.properties.util.PropertyValueHandler;

public class TabDropDownListVisiblePolicyProperty extends EnumProperty {
   public TabDropDownListVisiblePolicyProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4) {
      super(
         var1,
         var2,
         class$net$infonode$tabbedpanel$TabDropDownListVisiblePolicy == null
            ? (class$net$infonode$tabbedpanel$TabDropDownListVisiblePolicy = class$("net.infonode.tabbedpanel.TabDropDownListVisiblePolicy"))
            : class$net$infonode$tabbedpanel$TabDropDownListVisiblePolicy,
         var3,
         var4,
         TabDropDownListVisiblePolicy.getDropDownListVisiblePolicies()
      );
   }

   public TabDropDownListVisiblePolicy get(Object var1) {
      return (TabDropDownListVisiblePolicy)this.getValue(var1);
   }

   public void set(Object var1, TabDropDownListVisiblePolicy var2) {
      this.setValue(var1, var2);
   }
}
