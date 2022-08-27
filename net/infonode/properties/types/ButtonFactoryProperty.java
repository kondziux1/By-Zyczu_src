package net.infonode.properties.types;

import net.infonode.gui.button.ButtonFactory;
import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.util.PropertyValueHandler;
import net.infonode.properties.util.ValueHandlerProperty;

public class ButtonFactoryProperty extends ValueHandlerProperty {
   public ButtonFactoryProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4) {
      super(
         var1,
         var2,
         class$net$infonode$gui$button$ButtonFactory == null
            ? (class$net$infonode$gui$button$ButtonFactory = class$("net.infonode.gui.button.ButtonFactory"))
            : class$net$infonode$gui$button$ButtonFactory,
         var3,
         var4
      );
   }

   public ButtonFactory get(Object var1) {
      return (ButtonFactory)this.getValue(var1);
   }

   public void set(Object var1, ButtonFactory var2) {
      this.setValue(var1, var2);
   }
}
