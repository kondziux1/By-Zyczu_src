package net.infonode.docking.util;

import net.infonode.docking.DockingWindow;
import net.infonode.docking.FloatingWindow;
import net.infonode.docking.RootWindow;
import net.infonode.docking.TabWindow;
import net.infonode.docking.ViewSerializer;
import net.infonode.docking.internalutil.InternalDockingUtil;

public final class DockingUtil {
   private DockingUtil() {
      super();
   }

   public static RootWindow createRootWindow(AbstractViewMap var0, boolean var1) {
      return createRootWindow(var0, var0, var1);
   }

   public static RootWindow createHeavyweightSupportedRootWindow(AbstractViewMap var0, boolean var1) {
      return createRootWindow(true, var0, var0, var1);
   }

   public static RootWindow createRootWindow(AbstractViewMap var0, ViewSerializer var1, boolean var2) {
      return createRootWindow(false, var0, var1, var2);
   }

   public static RootWindow createHeavyweightSupportedRootWindow(AbstractViewMap var0, ViewSerializer var1, boolean var2) {
      return createRootWindow(true, var0, var1, var2);
   }

   private static RootWindow createRootWindow(boolean var0, AbstractViewMap var1, ViewSerializer var2, boolean var3) {
      TabWindow var4 = new TabWindow();

      for(int var5 = 0; var5 < var1.getViewCount(); ++var5) {
         var4.addTab(var1.getViewAtIndex(var5));
      }

      var4.setSelectedTab(0);
      RootWindow var6 = new RootWindow(var0, var2, var4);
      if (var3) {
         var6.setPopupMenuFactory(WindowMenuUtil.createWindowMenuFactory(var1, true));
      }

      return var6;
   }

   public static boolean isAncestor(DockingWindow var0, DockingWindow var1) {
      return var1 != null && (var0 == var1 || isAncestor(var0, var1.getWindowParent()));
   }

   public static void addWindow(DockingWindow var0, RootWindow var1) {
      if (var1 != null && var0.getRootWindow() != var1) {
         if (var0.getRootWindow() == null) {
            var0.restore();
            if (var0.getRootWindow() == var1) {
               return;
            }
         }

         InternalDockingUtil.addToRootWindow(var0, var1);
      }
   }

   public static TabWindow getTabWindowFor(DockingWindow var0) {
      return var0 instanceof TabWindow
         ? (TabWindow)var0
         : (var0.getWindowParent() != null && var0.getWindowParent() instanceof TabWindow ? (TabWindow)var0.getWindowParent() : null);
   }

   public static FloatingWindow getFloatingWindowFor(DockingWindow var0) {
      if (var0 == null) {
         return null;
      } else if (!var0.isUndocked()) {
         return null;
      } else {
         while(var0 != null && !(var0 instanceof FloatingWindow)) {
            var0 = var0.getWindowParent();
         }

         return (FloatingWindow)var0;
      }
   }
}
