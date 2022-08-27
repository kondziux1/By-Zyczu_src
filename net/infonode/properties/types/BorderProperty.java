package net.infonode.properties.types;

import javax.swing.border.Border;
import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.util.PropertyValueHandler;
import net.infonode.properties.util.ValueHandlerProperty;

public class BorderProperty extends ValueHandlerProperty {
   public BorderProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4) {
      super(
         var1,
         var2,
         class$javax$swing$border$Border == null ? (class$javax$swing$border$Border = class$("javax.swing.border.Border")) : class$javax$swing$border$Border,
         var3,
         var4
      );
   }

   public Border get(Object var1) {
      return (Border)this.getValue(var1);
   }

   public void set(Object var1, Border var2) {
      this.setValue(var1, var2);
   }
}
