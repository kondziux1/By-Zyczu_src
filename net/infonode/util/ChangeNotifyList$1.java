package net.infonode.util;

import java.util.List;

class ChangeNotifyList$1 extends ChangeNotifyList {
   ChangeNotifyList$1(ChangeNotifyList var1, List var2) {
      super(var2);
      this.this$0 = var1;
   }

   protected void changed() {
      this.this$0.changed();
   }
}
