package net.infonode.docking.title;

import java.io.Serializable;
import java.util.ArrayList;
import net.infonode.docking.AbstractTabWindow;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.View;

public class LengthLimitedDockingWindowTitleProvider implements DockingWindowTitleProvider, Serializable {
   private static final long serialVersionUID = 1L;
   private int maxLength;

   public LengthLimitedDockingWindowTitleProvider(int var1) {
      super();
      this.maxLength = var1;
   }

   public String getTitle(DockingWindow var1) {
      ArrayList var2 = new ArrayList();
      ArrayList var3 = new ArrayList();
      this.getViews(var1, var2, var3, true);
      int var4 = 0;

      for(int var5 = 0; var5 < var2.size(); ++var5) {
         if (var3.get(var5)) {
            var4 += ((String)var2.get(var5)).length();
         }
      }

      StringBuffer var9 = new StringBuffer(40);
      int var6 = 0;

      for(int var7 = 0; var7 < var2.size() && var9.length() < this.maxLength; ++var7) {
         boolean var8 = var3.get(var7);
         if (var8 || var4 < this.maxLength) {
            if (var9.length() > 0) {
               var9.append(", ");
            }

            var9.append((String)var2.get(var7));
            ++var6;
            if (!var8) {
               var4 += ((String)var2.get(var7)).length();
            }
         }
      }

      if (var6 < var2.size()) {
         var9.append(", ...");
      }

      return var9.toString();
   }

   private void getViews(DockingWindow var1, ArrayList var2, ArrayList var3, boolean var4) {
      if (var1 != null) {
         if (var1 instanceof View) {
            var2.add(((View)var1).getViewProperties().getTitle());
            var3.add(var4);
         } else if (var1 instanceof AbstractTabWindow) {
            DockingWindow var5 = ((AbstractTabWindow)var1).getSelectedWindow();

            for(int var6 = 0; var6 < var1.getChildWindowCount(); ++var6) {
               this.getViews(var1.getChildWindow(var6), var2, var3, var5 == var1.getChildWindow(var6) && var4);
            }
         } else {
            for(int var7 = 0; var7 < var1.getChildWindowCount(); ++var7) {
               this.getViews(var1.getChildWindow(var7), var2, var3, var4);
            }
         }

      }
   }
}
