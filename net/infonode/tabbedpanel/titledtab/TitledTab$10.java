package net.infonode.tabbedpanel.titledtab;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

class TitledTab$10 implements MouseMotionListener {
   TitledTab$10(TitledTab var1) {
      super();
      this.this$0 = var1;
   }

   public void mouseDragged(MouseEvent var1) {
      if (TitledTab.access$1000(this.this$0) != null) {
         MouseEvent var2 = TitledTab.access$900(this.this$0, var1);
         Object[] var3 = TitledTab.access$1000(this.this$0).toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((MouseMotionListener)var3[var4]).mouseDragged(var2);
         }
      }

   }

   public void mouseMoved(MouseEvent var1) {
      if (TitledTab.access$1000(this.this$0) != null) {
         MouseEvent var2 = TitledTab.access$900(this.this$0, var1);
         Object[] var3 = TitledTab.access$1000(this.this$0).toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((MouseMotionListener)var3[var4]).mouseMoved(var2);
         }
      }

   }
}
