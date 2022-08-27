package net.infonode.properties.types;

import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.util.PropertyValueHandler;
import net.infonode.properties.util.ValueHandlerProperty;

public class StringProperty extends ValueHandlerProperty {
   public StringProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4) {
      super(var1, var2, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String, var3, var4);
   }

   public String get(Object var1) {
      Object var2 = this.getValue(var1);
      return var2 == null ? null : var2.toString();
   }

   public void set(Object var1, String var2) {
      this.setValue(var1, var2);
   }
}
