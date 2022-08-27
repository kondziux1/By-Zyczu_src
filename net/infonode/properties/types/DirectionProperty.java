package net.infonode.properties.types;

import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.util.PropertyValueHandler;
import net.infonode.util.Direction;

public class DirectionProperty extends EnumProperty {
   public DirectionProperty(PropertyGroup var1, String var2, String var3, PropertyValueHandler var4) {
      super(
         var1,
         var2,
         class$net$infonode$util$Direction == null
            ? (class$net$infonode$util$Direction = class$("net.infonode.util.Direction"))
            : class$net$infonode$util$Direction,
         var3,
         var4,
         Direction.getDirections()
      );
   }

   public Direction get(Object var1) {
      return (Direction)this.getValue(var1);
   }

   public void set(Object var1, Direction var2) {
      this.setValue(var1, var2);
   }
}
