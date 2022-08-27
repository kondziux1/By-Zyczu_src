package net.infonode.docking;

import net.infonode.util.ChangeNotifyList;

class AbstractTabWindow$9 extends ChangeNotifyList {
   AbstractTabWindow$9(AbstractTabWindow var1) {
      super();
      this.this$0 = var1;
   }

   protected void changed() {
      this.this$0.updateTabAreaComponents();
   }
}
