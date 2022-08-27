package net.infonode.tabbedpanel;

import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.types.EnumProperty;
import net.infonode.properties.util.PropertyValueHandler;

public class TabDepthOrderPolicyProperty extends EnumProperty {
   public TabDepthOrderPolicyProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4) {
      super(
         var1,
         var2,
         class$net$infonode$tabbedpanel$TabDepthOrderPolicy == null
            ? (class$net$infonode$tabbedpanel$TabDepthOrderPolicy = class$("net.infonode.tabbedpanel.TabDepthOrderPolicy"))
            : class$net$infonode$tabbedpanel$TabDepthOrderPolicy,
         var3,
         var4,
         TabDepthOrderPolicy.getDepthOrderPolicies()
      );
   }

   public TabDepthOrderPolicy get(Object var1) {
      return (TabDepthOrderPolicy)this.getValue(var1);
   }

   public void set(Object var1, TabDepthOrderPolicy var2) {
      this.setValue(var1, var2);
   }
}
