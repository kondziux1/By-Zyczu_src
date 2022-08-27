package net.infonode.properties.types;

import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.util.PropertyValueHandler;
import net.infonode.properties.util.ValueHandlerProperty;

public class BooleanProperty extends ValueHandlerProperty {
   public BooleanProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4) {
      super(var1, var2, class$java$lang$Boolean == null ? (class$java$lang$Boolean = class$("java.lang.Boolean")) : class$java$lang$Boolean, var3, var4);
   }

   public boolean get(Object var1) {
      Object var2 = this.getValue(var1);
      return var2 == null ? false : (Boolean)var2;
   }

   public void set(Object var1, boolean var2) {
      this.setValue(var1, Boolean.valueOf(var2));
   }
}
