package net.infonode.tabbedpanel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import net.infonode.util.Direction;

class Tab$1 extends KeyAdapter {
   Tab$1(Tab var1) {
      super();
      this.this$0 = var1;
   }

   public void keyPressed(KeyEvent var1) {
      if (Tab.access$000(this.this$0) != null) {
         Direction var2 = Tab.access$000(this.this$0).getProperties().getTabAreaOrientation();
         int var3 = var2.isHorizontal() ? 40 : 39;
         int var4 = var2.isHorizontal() ? 38 : 37;
         int var5 = Tab.access$000(this.this$0).getTabIndex(this.this$0);

         Tab var6;
         do {
            var5 = (var5 + Tab.access$000(this.this$0).getTabCount() + (var1.getKeyCode() == var3 ? 1 : (var1.getKeyCode() == var4 ? -1 : 0)))
               % Tab.access$000(this.this$0).getTabCount();
            var6 = Tab.access$000(this.this$0).getTabAt(var5);
            if (var6 == this.this$0) {
               return;
            }
         } while(var6.getFocusableComponent() == null);

         var6.setSelected(true);
      }

   }
}
