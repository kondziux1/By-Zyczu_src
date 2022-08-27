package net.infonode.docking.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import net.infonode.docking.View;
import net.infonode.docking.ViewSerializer;

public abstract class AbstractViewMap implements ViewFactoryManager, ViewSerializer {
   private HashMap viewMap = new HashMap();
   private ArrayList views = new ArrayList(20);

   public AbstractViewMap() {
      super();
   }

   protected abstract void writeViewId(Object var1, ObjectOutputStream var2) throws IOException;

   protected abstract Object readViewId(ObjectInputStream var1) throws IOException;

   public int getViewCount() {
      return this.viewMap.size();
   }

   public View getViewAtIndex(int var1) {
      return (View)this.views.get(var1);
   }

   public ViewFactory[] getViewFactories() {
      ArrayList var1 = new ArrayList();

      for(int var2 = 0; var2 < this.views.size(); ++var2) {
         View var3 = (View)this.views.get(var2);
         if (var3.getRootWindow() == null) {
            var1.add(new AbstractViewMap$1(this, var3));
         }
      }

      return (ViewFactory[])var1.toArray(new ViewFactory[var1.size()]);
   }

   public boolean contains(View var1) {
      return this.views.contains(var1);
   }

   public void writeView(View var1, ObjectOutputStream var2) throws IOException {
      for(Entry var4 : this.viewMap.entrySet()) {
         if (var4.getValue() == var1) {
            this.writeViewId(var4.getKey(), var2);
            return;
         }
      }

      throw new IOException("Serialization of unknown view!");
   }

   public View readView(ObjectInputStream var1) throws IOException {
      return (View)this.viewMap.get(this.readViewId(var1));
   }

   protected void addView(Object var1, View var2) {
      Object var3 = this.viewMap.put(var1, var2);
      if (var3 != null) {
         this.views.remove(var3);
      }

      this.views.add(var2);
   }

   protected void removeView(Object var1) {
      Object var2 = this.viewMap.remove(var1);
      if (var2 != null) {
         this.views.remove(var2);
      }

   }

   protected View getView(Object var1) {
      return (View)this.viewMap.get(var1);
   }
}
