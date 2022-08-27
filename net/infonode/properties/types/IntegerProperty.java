package net.infonode.properties.types;

import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.util.PropertyValueHandler;

public class IntegerProperty extends NumberProperty {
   public IntegerProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4) {
      this(var1, var2, var3, Integer.MIN_VALUE, Integer.MAX_VALUE, -1, var4);
   }

   public IntegerProperty(PropertyGroup var1, String var2, String var3, int var4, int var5, int var6, PropertyValueHandler var7) {
      super(
         var1,
         var2,
         class$java$lang$Integer == null ? (class$java$lang$Integer = class$("java.lang.Integer")) : class$java$lang$Integer,
         var3,
         (long)var4,
         (long)var5,
         var6,
         var7
      );
   }

   public static IntegerProperty createPositive(PropertyGroup var0, String var1, String var2, int var3, PropertyValueHandler var4) {
      return new IntegerProperty(var0, var1, var2, 0, Integer.MAX_VALUE, var3, var4);
   }

   public int get(Object var1) {
      return (int)this.getLongValue(var1);
   }

   public void set(Object var1, int var2) {
      this.setValue(var1, new Integer(var2));
   }
}
