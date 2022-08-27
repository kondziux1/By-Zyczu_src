package net.infonode.properties.types;

import java.awt.Insets;
import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.util.PropertyValueHandler;
import net.infonode.properties.util.ValueHandlerProperty;

public class InsetsProperty extends ValueHandlerProperty {
   public InsetsProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4) {
      super(var1, var2, class$java$awt$Insets == null ? (class$java$awt$Insets = class$("java.awt.Insets")) : class$java$awt$Insets, var3, var4);
   }

   public Insets get(Object var1) {
      return (Insets)this.getValue(var1);
   }

   public void set(Object var1, Insets var2) {
      this.setValue(var1, var2);
   }
}
