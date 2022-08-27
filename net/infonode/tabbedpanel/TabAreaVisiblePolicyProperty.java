package net.infonode.tabbedpanel;

import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.types.EnumProperty;
import net.infonode.properties.util.PropertyValueHandler;

public class TabAreaVisiblePolicyProperty extends EnumProperty {
   public TabAreaVisiblePolicyProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4) {
      super(
         var1,
         var2,
         class$net$infonode$tabbedpanel$TabAreaVisiblePolicy == null
            ? (class$net$infonode$tabbedpanel$TabAreaVisiblePolicy = class$("net.infonode.tabbedpanel.TabAreaVisiblePolicy"))
            : class$net$infonode$tabbedpanel$TabAreaVisiblePolicy,
         var3,
         var4,
         TabAreaVisiblePolicy.getVisiblePolicies()
      );
   }

   public TabAreaVisiblePolicy get(Object var1) {
      return (TabAreaVisiblePolicy)this.getValue(var1);
   }

   public void set(Object var1, TabAreaVisiblePolicy var2) {
      this.setValue(var1, var2);
   }
}
