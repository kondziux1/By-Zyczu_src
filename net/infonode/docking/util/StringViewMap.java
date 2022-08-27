package net.infonode.docking.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import net.infonode.docking.View;

public class StringViewMap extends AbstractViewMap {
   public StringViewMap() {
      super();
   }

   public StringViewMap(View[] var1) {
      super();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         this.addView(var1[var2]);
      }

   }

   public void addView(View var1) {
      this.addView(var1.getTitle(), var1);
   }

   public void addView(String var1, View var2) {
      this.addView(var1, var2);
   }

   public void removeView(String var1) {
      this.removeView(var1);
   }

   public View getView(String var1) {
      return this.getView(var1);
   }

   protected void writeViewId(Object var1, ObjectOutputStream var2) throws IOException {
      var2.writeUTF((String)var1);
   }

   protected Object readViewId(ObjectInputStream var1) throws IOException {
      return var1.readUTF();
   }
}
