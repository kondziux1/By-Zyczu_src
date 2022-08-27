package net.infonode.properties.types;

import net.infonode.gui.componentpainter.ComponentPainter;
import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.util.PropertyValueHandler;
import net.infonode.properties.util.ValueHandlerProperty;

public class ComponentPainterProperty extends ValueHandlerProperty {
   public ComponentPainterProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4) {
      super(
         var1,
         var2,
         class$net$infonode$gui$componentpainter$ComponentPainter == null
            ? (class$net$infonode$gui$componentpainter$ComponentPainter = class$("net.infonode.gui.componentpainter.ComponentPainter"))
            : class$net$infonode$gui$componentpainter$ComponentPainter,
         var3,
         var4
      );
   }

   public ComponentPainter get(Object var1) {
      return (ComponentPainter)this.getValue(var1);
   }

   public void set(Object var1, ComponentPainter var2) {
      this.setValue(var1, var2);
   }
}
