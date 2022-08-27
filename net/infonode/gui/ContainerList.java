package net.infonode.gui;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;
import net.infonode.util.ChangeNotifyList;

public class ContainerList extends ChangeNotifyList {
   private Container container;

   public ContainerList(Container var1) {
      super(new ArrayList());
      this.container = var1;
   }

   protected void changed() {
      this.container.removeAll();
      List var1 = this.getList();

      for(int var2 = 0; var2 < var1.size(); ++var2) {
         this.container.add((Component)var1.get(var2));
      }

   }
}
