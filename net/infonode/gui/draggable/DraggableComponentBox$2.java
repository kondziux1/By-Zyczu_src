package net.infonode.gui.draggable;

import java.awt.Container;
import java.awt.Dimension;
import net.infonode.gui.layout.DirectionLayout;
import net.infonode.util.Direction;

class DraggableComponentBox$2 extends DirectionLayout {
   DraggableComponentBox$2(DraggableComponentBox var1, Direction var2) {
      super(var2);
      this.this$0 = var1;
   }

   public Dimension minimumLayoutSize(Container var1) {
      Dimension var2 = super.minimumLayoutSize(var1);
      Dimension var3 = super.preferredLayoutSize(var1);
      return DraggableComponentBox.access$800(this.this$0).isHorizontal() ? new Dimension(var3.width, var2.height) : new Dimension(var2.width, var3.height);
   }

   public void layoutContainer(Container var1) {
      if (this.this$0 != null) {
         DraggableComponentBox.access$900(this.this$0);
         super.layoutContainer(var1);
      }

   }

   public Dimension preferredLayoutSize(Container var1) {
      DraggableComponentBox.access$900(this.this$0);
      return super.preferredLayoutSize(var1);
   }
}
