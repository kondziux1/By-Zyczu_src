package net.infonode.docking;

import java.io.IOException;
import java.io.ObjectInputStream;
import net.infonode.docking.internal.ReadContext;
import net.infonode.docking.model.SplitWindowItem;
import net.infonode.docking.model.TabWindowItem;
import net.infonode.docking.model.ViewItem;
import net.infonode.docking.model.ViewReader;
import net.infonode.docking.model.WindowItem;

class RootWindow$5 implements ViewReader {
   RootWindow$5(RootWindow var1, View[] var2) throws IOException {
      super();
      this.this$0 = var1;
      this.val$views = var2;
   }

   public ViewItem readViewItem(ObjectInputStream var1, ReadContext var2) throws IOException {
      View var3 = this.readView(var1, var2);
      return var3 == null ? new ViewItem() : (ViewItem)var3.getWindowItem();
   }

   public WindowItem readWindowItem(ObjectInputStream var1, ReadContext var2) throws IOException {
      if (!var1.readBoolean()) {
         return null;
      } else {
         WindowItem var4 = this.this$0.getWindowItem();

         int var3;
         while((var3 = var1.readInt()) != -1) {
            var4 = var4.getWindow(var3);
         }

         return var4;
      }
   }

   public TabWindow createTabWindow(DockingWindow[] var1, TabWindowItem var2) {
      TabWindow var3 = new TabWindow(var1, var2);
      var3.updateSelectedTab();
      return var3;
   }

   public SplitWindow createSplitWindow(DockingWindow var1, DockingWindow var2, SplitWindowItem var3) {
      return new SplitWindow(var3.isHorizontal(), var3.getDividerLocation(), var1, var2, var3);
   }

   public View readView(ObjectInputStream var1, ReadContext var2) throws IOException {
      int var3 = var1.readInt();
      return var3 == -1 ? null : this.val$views[var3];
   }
}
