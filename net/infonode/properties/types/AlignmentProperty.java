package net.infonode.properties.types;

import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.util.PropertyValueHandler;
import net.infonode.util.Alignment;

public class AlignmentProperty extends EnumProperty {
   public AlignmentProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4, Alignment[] var5) {
      super(
         var1,
         var2,
         class$net$infonode$util$Alignment == null
            ? (class$net$infonode$util$Alignment = class$("net.infonode.util.Alignment"))
            : class$net$infonode$util$Alignment,
         var3,
         var4,
         var5
      );
   }

   public Alignment get(Object var1) {
      return (Alignment)this.getValue(var1);
   }

   public void set(Object var1, Alignment var2) {
      this.setValue(var1, var2);
   }
}
