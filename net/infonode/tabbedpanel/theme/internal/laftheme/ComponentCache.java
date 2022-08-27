package net.infonode.tabbedpanel.theme.internal.laftheme;

import java.awt.Component;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import javax.swing.JComponent;

public class ComponentCache {
   private ArrayList cache = new ArrayList(10);
   private int index = 0;

   ComponentCache() {
      super();

      for(int var1 = 0; var1 < 1; ++var1) {
         this.cache.add(new SoftReference(this.createComponent()));
      }

   }

   void reset() {
      this.index = 0;
   }

   Component getComponent() {
      Object var1 = null;
      JComponent var2;
      if (this.index == this.cache.size()) {
         var2 = this.createComponent();
         this.cache.add(new SoftReference(var2));
      } else {
         var2 = (JComponent)((SoftReference)this.cache.get(this.index)).get();
         if (var2 == null) {
            this.cache.remove(this.index);
            var2 = this.createComponent();
            this.cache.add(new SoftReference(var2));
         }
      }

      ++this.index;
      var2.setOpaque(false);
      return var2;
   }

   private JComponent createComponent() {
      return new ComponentCache$1(this);
   }
}
