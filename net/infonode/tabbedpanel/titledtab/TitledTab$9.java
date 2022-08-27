package net.infonode.tabbedpanel.titledtab;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class TitledTab$9 implements MouseListener {
   TitledTab$9(TitledTab var1) {
      super();
      this.this$0 = var1;
   }

   public void mouseClicked(MouseEvent var1) {
      if (TitledTab.access$800(this.this$0) != null) {
         MouseEvent var2 = TitledTab.access$900(this.this$0, var1);
         Object[] var3 = TitledTab.access$800(this.this$0).toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((MouseListener)var3[var4]).mouseClicked(var2);
         }
      }

   }

   public void mousePressed(MouseEvent var1) {
      if (TitledTab.access$800(this.this$0) != null) {
         MouseEvent var2 = TitledTab.access$900(this.this$0, var1);
         Object[] var3 = TitledTab.access$800(this.this$0).toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((MouseListener)var3[var4]).mousePressed(var2);
         }
      }

   }

   public void mouseReleased(MouseEvent var1) {
      if (TitledTab.access$800(this.this$0) != null) {
         MouseEvent var2 = TitledTab.access$900(this.this$0, var1);
         Object[] var3 = TitledTab.access$800(this.this$0).toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((MouseListener)var3[var4]).mouseReleased(var2);
         }
      }

   }

   public void mouseEntered(MouseEvent var1) {
      if (TitledTab.access$800(this.this$0) != null) {
         MouseEvent var2 = TitledTab.access$900(this.this$0, var1);
         Object[] var3 = TitledTab.access$800(this.this$0).toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((MouseListener)var3[var4]).mouseEntered(var2);
         }
      }

   }

   public void mouseExited(MouseEvent var1) {
      if (TitledTab.access$800(this.this$0) != null) {
         MouseEvent var2 = TitledTab.access$900(this.this$0, var1);
         Object[] var3 = TitledTab.access$800(this.this$0).toArray();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            ((MouseListener)var3[var4]).mouseExited(var2);
         }
      }

   }
}
