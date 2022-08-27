package net.infonode.tabbedpanel.titledtab;

import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.types.EnumProperty;
import net.infonode.properties.util.PropertyValueHandler;

public class TitledTabSizePolicyProperty extends EnumProperty {
   public TitledTabSizePolicyProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4) {
      super(
         var1,
         var2,
         class$net$infonode$tabbedpanel$titledtab$TitledTabSizePolicy == null
            ? (class$net$infonode$tabbedpanel$titledtab$TitledTabSizePolicy = class$("net.infonode.tabbedpanel.titledtab.TitledTabSizePolicy"))
            : class$net$infonode$tabbedpanel$titledtab$TitledTabSizePolicy,
         var3,
         var4,
         TitledTabSizePolicy.getSizePolicies()
      );
   }

   public TitledTabSizePolicy get(Object var1) {
      return (TitledTabSizePolicy)this.getValue(var1);
   }

   public void set(Object var1, TitledTabSizePolicy var2) {
      this.setValue(var1, var2);
   }
}
