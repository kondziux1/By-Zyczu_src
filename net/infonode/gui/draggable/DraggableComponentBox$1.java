package net.infonode.gui.draggable;

class DraggableComponentBox$1 implements DraggableComponentListener {
   DraggableComponentBox$1(DraggableComponentBox var1) {
      super();
      this.this$0 = var1;
   }

   public void changed(DraggableComponentEvent var1) {
      if (var1.getType() == 0) {
         DraggableComponentBox.access$100(this.this$0, !DraggableComponentBox.access$000(this.this$0));
      }

      DraggableComponentBox.access$200(this.this$0, var1);
   }

   public void selected(DraggableComponentEvent var1) {
      DraggableComponentBox.access$300(this.this$0, var1.getSource());
   }

   public void dragged(DraggableComponentEvent var1) {
      DraggableComponentBox.access$400(this.this$0, var1);
   }

   public void dropped(DraggableComponentEvent var1) {
      DraggableComponentBox.access$500(this.this$0);
      DraggableComponentBox.access$600(this.this$0, var1);
   }

   public void dragAborted(DraggableComponentEvent var1) {
      DraggableComponentBox.access$500(this.this$0);
      DraggableComponentBox.access$700(this.this$0, var1);
   }
}
