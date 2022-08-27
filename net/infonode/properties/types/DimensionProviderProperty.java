package net.infonode.properties.types;

import net.infonode.gui.DimensionProvider;
import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.util.PropertyValueHandler;
import net.infonode.properties.util.ValueHandlerProperty;

public class DimensionProviderProperty extends ValueHandlerProperty {
   public DimensionProviderProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4) {
      super(
         var1,
         var2,
         class$net$infonode$gui$DimensionProvider == null
            ? (class$net$infonode$gui$DimensionProvider = class$("net.infonode.gui.DimensionProvider"))
            : class$net$infonode$gui$DimensionProvider,
         var3,
         var4
      );
   }

   public DimensionProvider get(Object var1) {
      return (DimensionProvider)this.getValue(var1);
   }

   public void set(Object var1, DimensionProvider var2) {
      this.setValue(var1, var2);
   }
}
