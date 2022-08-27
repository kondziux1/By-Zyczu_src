package net.infonode.properties.types;

import java.awt.Color;
import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.util.PropertyValueHandler;
import net.infonode.properties.util.ValueHandlerProperty;

public class ColorProperty extends ValueHandlerProperty {
   public ColorProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4) {
      super(var1, var2, class$java$awt$Color == null ? (class$java$awt$Color = class$("java.awt.Color")) : class$java$awt$Color, var3, var4);
   }

   public Color get(Object var1) {
      return (Color)this.getValue(var1);
   }

   public void set(Object var1, Color var2) {
      this.setValue(var1, var2);
   }
}
