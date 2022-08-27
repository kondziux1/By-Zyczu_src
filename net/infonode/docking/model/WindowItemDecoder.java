package net.infonode.docking.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import net.infonode.docking.internal.ReadContext;

public class WindowItemDecoder {
   static final int SPLIT = 0;
   static final int TAB = 1;
   static final int VIEW = 2;

   private WindowItemDecoder() {
      super();
   }

   static WindowItem decodeWindowItem(ObjectInputStream var0, ReadContext var1, ViewReader var2) throws IOException {
      int var3 = var0.readInt();
      switch(var3) {
         case 0:
            return new SplitWindowItem();
         case 1:
            return new TabWindowItem();
         case 2:
            return var2.readViewItem(var0, var1);
         default:
            throw new IOException("Invalid window item id!");
      }
   }
}
