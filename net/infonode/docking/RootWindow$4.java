package net.infonode.docking;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import net.infonode.docking.internal.WriteContext;
import net.infonode.docking.model.ViewWriter;
import net.infonode.docking.model.WindowItem;

class RootWindow$4 implements ViewWriter {
   RootWindow$4(RootWindow var1, ArrayList var2) throws IOException {
      super();
      this.this$0 = var1;
      this.val$v = var2;
   }

   public void writeWindowItem(WindowItem var1, ObjectOutputStream var2, WriteContext var3) throws IOException {
      if (var1.getRootItem() == this.this$0.getWindowItem()) {
         var2.writeBoolean(true);
         RootWindow.access$400(this.this$0, var1, var2);
         var2.writeInt(-1);
      } else {
         var2.writeBoolean(false);
         var1.writeSettings(var2, var3);
      }

   }

   public void writeView(View var1, ObjectOutputStream var2, WriteContext var3) throws IOException {
      for(int var4 = 0; var4 < this.val$v.size(); ++var4) {
         if (this.val$v.get(var4) == var1) {
            var2.writeInt(var4);
            return;
         }
      }

      var2.writeInt(-1);
   }
}
