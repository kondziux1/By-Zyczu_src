package net.infonode.properties.types;

import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.util.PropertyValueHandler;
import net.infonode.properties.util.ValueHandlerProperty;

public class NumberProperty extends ValueHandlerProperty {
   private long minValue;
   private long maxValue;
   private int preferredDigitCount;

   public NumberProperty(PropertyGroup var1, String var2, Class var3, String var4, long var5, long var7, int var9, PropertyValueHandler var10) {
      super(var1, var2, var3, var4, var10);
      this.minValue = var5;
      this.maxValue = var7;
      this.preferredDigitCount = var9;
   }

   public int getPreferredDigitCount() {
      return this.preferredDigitCount;
   }

   public long getMinValue() {
      return this.minValue;
   }

   public long getMaxValue() {
      return this.maxValue;
   }

   public long getLongValue(Object var1) {
      Object var2 = this.getValue(var1);
      return var2 == null ? Math.max(0L, this.minValue) : ((Number)this.getValue(var1)).longValue();
   }

   public boolean canBeAssiged(Object var1) {
      if (!super.canBeAssiged(var1)) {
         return false;
      } else {
         long var2 = ((Number)var1).longValue();
         return this.minValue <= var2 && this.maxValue >= var2;
      }
   }
}
