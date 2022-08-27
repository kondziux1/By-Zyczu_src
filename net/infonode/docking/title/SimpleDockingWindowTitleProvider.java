package net.infonode.docking.title;

import java.io.ObjectStreamException;
import java.io.Serializable;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.View;

public class SimpleDockingWindowTitleProvider implements DockingWindowTitleProvider, Serializable {
   private static final long serialVersionUID = 1L;
   public static final SimpleDockingWindowTitleProvider INSTANCE = new SimpleDockingWindowTitleProvider();

   private SimpleDockingWindowTitleProvider() {
      super();
   }

   public String getTitle(DockingWindow var1) {
      StringBuffer var2 = new StringBuffer(40);
      this.getTitle(var1, var2);
      return var2.toString();
   }

   private void getTitle(DockingWindow var1, StringBuffer var2) {
      if (var1 != null) {
         if (var1 instanceof View) {
            if (var2.length() > 0) {
               var2.append(", ");
            }

            var2.append(((View)var1).getViewProperties().getTitle());
         } else {
            for(int var3 = 0; var3 < var1.getChildWindowCount(); ++var3) {
               this.getTitle(var1.getChildWindow(var3), var2);
            }
         }

      }
   }

   protected Object readResolve() throws ObjectStreamException {
      return INSTANCE;
   }
}
