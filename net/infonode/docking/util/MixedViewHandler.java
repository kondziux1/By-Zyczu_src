package net.infonode.docking.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import net.infonode.docking.View;
import net.infonode.docking.ViewSerializer;

public class MixedViewHandler implements ViewFactoryManager, ViewSerializer {
   private AbstractViewMap viewMap;
   private ViewSerializer viewSerializer;

   public MixedViewHandler(AbstractViewMap var1, ViewSerializer var2) {
      super();
      this.viewMap = var1;
      this.viewSerializer = var2;
   }

   public ViewFactory[] getViewFactories() {
      return new ViewFactory[0];
   }

   public void writeView(View var1, ObjectOutputStream var2) throws IOException {
      if (this.viewMap.contains(var1)) {
         var2.writeBoolean(true);
         this.viewMap.writeView(var1, var2);
      } else {
         var2.writeBoolean(false);
         this.viewSerializer.writeView(var1, var2);
      }

   }

   public View readView(ObjectInputStream var1) throws IOException {
      return var1.readBoolean() ? this.viewMap.readView(var1) : this.viewSerializer.readView(var1);
   }
}
