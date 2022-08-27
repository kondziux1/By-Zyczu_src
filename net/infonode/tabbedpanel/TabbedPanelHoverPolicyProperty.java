package net.infonode.tabbedpanel;

import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.types.EnumProperty;
import net.infonode.properties.util.PropertyValueHandler;

public class TabbedPanelHoverPolicyProperty extends EnumProperty {
   public TabbedPanelHoverPolicyProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4) {
      super(
         var1,
         var2,
         class$net$infonode$tabbedpanel$TabbedPanelHoverPolicy == null
            ? (class$net$infonode$tabbedpanel$TabbedPanelHoverPolicy = class$("net.infonode.tabbedpanel.TabbedPanelHoverPolicy"))
            : class$net$infonode$tabbedpanel$TabbedPanelHoverPolicy,
         var3,
         var4,
         TabbedPanelHoverPolicy.getHoverPolicies()
      );
   }

   public TabbedPanelHoverPolicy get(Object var1) {
      return (TabbedPanelHoverPolicy)this.getValue(var1);
   }

   public void set(Object var1, TabbedPanelHoverPolicy var2) {
      this.setValue(var1, var2);
   }
}
