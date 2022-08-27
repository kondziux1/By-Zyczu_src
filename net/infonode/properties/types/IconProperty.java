package net.infonode.properties.types;

import javax.swing.Icon;
import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.util.PropertyValueHandler;
import net.infonode.properties.util.ValueHandlerProperty;

public class IconProperty extends ValueHandlerProperty {
   public IconProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4) {
      super(var1, var2, class$javax$swing$Icon == null ? (class$javax$swing$Icon = class$("javax.swing.Icon")) : class$javax$swing$Icon, var3, var4);
   }

   public void set(Object var1, Icon var2) {
      this.setValue(var1, var2);
   }

   public Icon get(Object var1) {
      return (Icon)this.getValue(var1);
   }
}
