package net.infonode.docking;

import java.io.IOException;
import java.io.ObjectInputStream;
import net.infonode.docking.internal.ReadContext;
import net.infonode.docking.model.SplitWindowItem;
import net.infonode.docking.model.TabWindowItem;
import net.infonode.docking.model.ViewReader;
import net.infonode.docking.model.WindowItem;

class WindowDecoder {
   private WindowDecoder() {
      super();
   }

   static DockingWindow decodeWindow(ObjectInputStream var0, ReadContext var1) throws IOException {
      int var2 = var0.readInt();
      switch(var2) {
         case 1:
            return View.read(var0, var1);
         case 2:
            SplitWindow var4 = new SplitWindow(true);
            return var4.oldRead(var0, var1);
         case 3:
            TabWindow var3 = new TabWindow();
            return var3.oldRead(var0, var1);
         default:
            throw new IOException("Invalid window ID: " + var2 + '!');
      }
   }

   static DockingWindow decodeWindow(ObjectInputStream var0, ReadContext var1, ViewReader var2) throws IOException {
      int var3 = var0.readInt();
      if (var3 == 1) {
         return var2.readView(var0, var1);
      } else {
         WindowItem var4 = var2.readWindowItem(var0, var1);
         switch(var3) {
            case 2:
               SplitWindowItem var7 = (SplitWindowItem)var4;
               if (var7 == null) {
                  var7 = new SplitWindowItem();
                  var7.readSettings(var0, var1);
               }

               SplitWindow var8 = new SplitWindow(var7.isHorizontal(), var7.getDividerLocation(), null, null, var7);
               return var8.newRead(var0, var1, var2);
            case 3:
               TabWindowItem var5 = (TabWindowItem)var4;
               if (var5 == null) {
                  var5 = new TabWindowItem();
                  var5.readSettings(var0, var1);
               }

               TabWindow var6 = new TabWindow(null, var5);
               return var6.newRead(var0, var1, var2);
            default:
               throw new IOException("Invalid window ID: " + var3 + '!');
         }
      }
   }
}
