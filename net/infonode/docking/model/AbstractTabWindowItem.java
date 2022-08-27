package net.infonode.docking.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import net.infonode.docking.internal.ReadContext;
import net.infonode.docking.internal.WriteContext;

public abstract class AbstractTabWindowItem extends WindowItem {
   private WindowItem selectedItem;

   protected AbstractTabWindowItem() {
      super();
   }

   protected AbstractTabWindowItem(WindowItem var1) {
      super(var1);
   }

   public WindowItem getSelectedItem() {
      return this.selectedItem;
   }

   public void setSelectedItem(WindowItem var1) {
      this.selectedItem = var1;
   }

   public void writeSettings(ObjectOutputStream var1, WriteContext var2) throws IOException {
      super.writeSettings(var1, var2);
      var1.writeInt(this.getWindowIndex(this.selectedItem));
   }

   public void readSettings(ObjectInputStream var1, ReadContext var2) throws IOException {
      super.readSettings(var1, var2);
      if (var2.getVersion() >= 3) {
         int var3 = var1.readInt();
         this.selectedItem = var3 == -1 ? null : this.getWindow(var3);
      }

   }
}
