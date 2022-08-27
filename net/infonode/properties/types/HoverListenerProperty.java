package net.infonode.properties.types;

import net.infonode.gui.hover.HoverListener;
import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.util.PropertyValueHandler;
import net.infonode.properties.util.ValueHandlerProperty;

public class HoverListenerProperty extends ValueHandlerProperty {
   public HoverListenerProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4) {
      super(
         var1,
         var2,
         class$net$infonode$gui$hover$HoverListener == null
            ? (class$net$infonode$gui$hover$HoverListener = class$("net.infonode.gui.hover.HoverListener"))
            : class$net$infonode$gui$hover$HoverListener,
         var3,
         var4
      );
   }

   public HoverListener get(Object var1) {
      return (HoverListener)this.getValue(var1);
   }

   public void set(Object var1, HoverListener var2) {
      this.setValue(var1, var2);
   }
}
