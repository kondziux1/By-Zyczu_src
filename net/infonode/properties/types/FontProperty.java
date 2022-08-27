package net.infonode.properties.types;

import java.awt.Font;
import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.util.PropertyValueHandler;
import net.infonode.properties.util.ValueHandlerProperty;

public class FontProperty extends ValueHandlerProperty {
   public FontProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4) {
      super(var1, var2, class$java$awt$Font == null ? (class$java$awt$Font = class$("java.awt.Font")) : class$java$awt$Font, var3, var4);
   }

   public Font get(Object var1) {
      return (Font)this.getValue(var1);
   }

   public void set(Object var1, Font var2) {
      this.setValue(var1, var2);
   }
}
