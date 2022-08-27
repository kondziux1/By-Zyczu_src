package net.infonode.tabbedpanel.titledtab;

import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.types.EnumProperty;
import net.infonode.properties.util.PropertyValueHandler;

public class TitledTabBorderSizePolicyProperty extends EnumProperty {
   public TitledTabBorderSizePolicyProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4) {
      super(
         var1,
         var2,
         class$net$infonode$tabbedpanel$titledtab$TitledTabBorderSizePolicy == null
            ? (class$net$infonode$tabbedpanel$titledtab$TitledTabBorderSizePolicy = class$("net.infonode.tabbedpanel.titledtab.TitledTabBorderSizePolicy"))
            : class$net$infonode$tabbedpanel$titledtab$TitledTabBorderSizePolicy,
         var3,
         var4,
         TitledTabBorderSizePolicy.getSizePolicies()
      );
   }

   public TitledTabBorderSizePolicy get(Object var1) {
      return (TitledTabBorderSizePolicy)this.getValue(var1);
   }

   public void set(Object var1, TitledTabBorderSizePolicy var2) {
      this.setValue(var1, var2);
   }
}
