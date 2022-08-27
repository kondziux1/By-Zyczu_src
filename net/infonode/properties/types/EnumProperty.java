package net.infonode.properties.types;

import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.util.PropertyValueHandler;
import net.infonode.properties.util.ValueHandlerProperty;
import net.infonode.util.ArrayUtil;

public class EnumProperty extends ValueHandlerProperty {
   private Object[] validValues;

   public EnumProperty(PropertyGroup var1, String var2, Class var3, String var4, PropertyValueHandler var5, Object[] var6) {
      super(var1, var2, var3, var4, var5);
      this.validValues = var6.clone();
   }

   public void setValue(Object var1, Object var2) {
      if (!ArrayUtil.contains(this.validValues, var2)) {
         throw new IllegalArgumentException("Invalid enum value!");
      } else {
         super.setValue(var1, var2);
      }
   }

   public Object[] getValidValues() {
      return this.validValues.clone();
   }

   public Object getValue(Object var1) {
      Object var2 = super.getValue(var1);
      return var2 == null ? this.validValues[0] : var2;
   }
}
