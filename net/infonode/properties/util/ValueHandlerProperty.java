package net.infonode.properties.util;

import net.infonode.properties.base.PropertyGroup;

public class ValueHandlerProperty extends AbstractProperty {
   private PropertyValueHandler valueHandler;

   public ValueHandlerProperty(PropertyGroup var1, String var2, Class var3, String var4, PropertyValueHandler var5) {
      super(var1, var2, var3, var4);
      this.valueHandler = var5;
   }

   public void setValue(Object var1, Object var2) {
      super.setValue(var1, var2);
      this.valueHandler.setValue(this, var1, var2);
   }

   public Object getValue(Object var1) {
      return this.valueHandler.getValue(this, var1);
   }

   public boolean valueIsRemovable(Object var1) {
      return this.valueHandler.getValueIsRemovable(this, var1);
   }

   public void removeValue(Object var1) {
      this.valueHandler.removeValue(this, var1);
   }

   public boolean valueIsSet(Object var1) {
      return this.valueHandler.getValueIsSet(this, var1);
   }
}
