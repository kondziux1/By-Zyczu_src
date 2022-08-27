package net.infonode.tabbedpanel;

import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.types.EnumProperty;
import net.infonode.properties.util.PropertyValueHandler;

public class TabLayoutPolicyProperty extends EnumProperty {
   public TabLayoutPolicyProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4) {
      super(
         var1,
         var2,
         class$net$infonode$tabbedpanel$TabLayoutPolicy == null
            ? (class$net$infonode$tabbedpanel$TabLayoutPolicy = class$("net.infonode.tabbedpanel.TabLayoutPolicy"))
            : class$net$infonode$tabbedpanel$TabLayoutPolicy,
         var3,
         var4,
         TabLayoutPolicy.getLayoutPolicies()
      );
   }

   public TabLayoutPolicy get(Object var1) {
      return (TabLayoutPolicy)this.getValue(var1);
   }

   public void set(Object var1, TabLayoutPolicy var2) {
      this.setValue(var1, var2);
   }
}
