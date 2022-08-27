package net.infonode.docking;

import net.infonode.util.ChangeNotifyList;

class View$5 extends ChangeNotifyList {
   View$5(View var1) {
      super();
      this.this$0 = var1;
   }

   protected void changed() {
      if (View.access$300(this.this$0) != null) {
         View.access$300(this.this$0).updateCustomBarComponents(this);
      }

   }
}
