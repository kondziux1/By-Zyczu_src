package net.infonode.properties.types;

import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.util.PropertyValueHandler;
import net.infonode.properties.util.ValueHandlerProperty;

public class FloatProperty extends ValueHandlerProperty {
   private float minValue;
   private float maxValue;
   private int preferredDigitCount;
   private float preferredDelta;

   public FloatProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4) {
      this(var1, var2, var3, var4, Float.MIN_VALUE, Float.MAX_VALUE);
   }

   public FloatProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4, float var5, float var6) {
      this(var1, var2, var3, var4, var5, var6, 6, 0.1F);
   }

   public FloatProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4, float var5, float var6, int var7, float var8) {
      super(var1, var2, class$java$lang$Float == null ? (class$java$lang$Float = class$("java.lang.Float")) : class$java$lang$Float, var3, var4);
      this.minValue = var5;
      this.maxValue = var6;
      this.preferredDigitCount = var7;
      this.preferredDelta = var8;
   }

   public float getPreferredDelta() {
      return this.preferredDelta;
   }

   public float getMinValue() {
      return this.minValue;
   }

   public float getMaxValue() {
      return this.maxValue;
   }

   public int getPreferredDigitCount() {
      return this.preferredDigitCount;
   }

   public float get(Object var1) {
      Object var2 = this.getValue(var1);
      return var2 == null ? 0.0F : ((Number)this.getValue(var1)).floatValue();
   }

   public void set(Object var1, float var2) {
      this.setValue(var1, new Float(var2));
   }

   public boolean canBeAssiged(Object var1) {
      if (!super.canBeAssiged(var1)) {
         return false;
      } else {
         float var2 = ((Number)var1).floatValue();
         return var2 >= this.minValue && var2 <= this.maxValue;
      }
   }
}
