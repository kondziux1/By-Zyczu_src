package net.infonode.docking;

import net.infonode.util.Direction;

class DockingWindow$4 implements Runnable {
   DockingWindow$4(DockingWindow var1, SplitWindow var2, Direction var3, DockingWindow var4, float var5) {
      super();
      this.this$0 = var1;
      this.val$w = var2;
      this.val$direction = var3;
      this.val$splitWithWindow = var4;
      this.val$dividerLocation = var5;
   }

   public void run() {
      this.this$0.getWindowParent().replaceChildWindow(this.this$0, this.val$w);
      this.val$w
         .setWindows(
            this.val$direction != Direction.DOWN && this.val$direction != Direction.RIGHT ? this.val$splitWithWindow : this.this$0,
            this.val$direction != Direction.UP && this.val$direction != Direction.LEFT ? this.val$splitWithWindow : this.this$0
         );
      this.val$w.setDividerLocation(this.val$dividerLocation);
      this.val$w.getWindowParent().optimizeWindowLayout();
   }
}
