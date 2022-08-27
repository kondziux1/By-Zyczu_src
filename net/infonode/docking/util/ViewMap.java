package net.infonode.docking.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import net.infonode.docking.View;

public class ViewMap extends AbstractViewMap {
   public ViewMap() {
      super();
   }

   public ViewMap(View[] var1) {
      super();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         this.addView(var2, var1[var2]);
      }

   }

   public void addView(int var1, View var2) {
      this.addView(new Integer(var1), var2);
   }

   public void removeView(int var1) {
      this.removeView(new Integer(var1));
   }

   public View getView(int var1) {
      return this.getView(new Integer(var1));
   }

   protected void writeViewId(Object var1, ObjectOutputStream var2) throws IOException {
      var2.writeInt((Integer)var1);
   }

   protected Object readViewId(ObjectInputStream var1) throws IOException {
      return new Integer(var1.readInt());
   }
}
