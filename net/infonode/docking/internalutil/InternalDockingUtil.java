package net.infonode.docking.internalutil;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.JPopupMenu;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.RootWindow;
import net.infonode.docking.TabWindow;
import net.infonode.docking.View;
import net.infonode.docking.action.DockingWindowAction;
import net.infonode.docking.properties.WindowTabButtonProperties;
import net.infonode.docking.util.DockingUtil;
import net.infonode.docking.util.ViewMap;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.util.IntList;
import net.infonode.util.Printer;

public class InternalDockingUtil {
   public static final int DEFAULT_BUTTON_ICON_SIZE = 10;

   private InternalDockingUtil() {
      super();
   }

   public static void getViews(DockingWindow var0, ArrayList var1) {
      if (var0 != null) {
         if (var0 instanceof View) {
            var1.add(var0);
         } else {
            for(int var2 = 0; var2 < var0.getChildWindowCount(); ++var2) {
               getViews(var0.getChildWindow(var2), var1);
            }
         }

      }
   }

   public static IntList getWindowPath(DockingWindow var0) {
      return getWindowPath(var0, IntList.EMPTY_LIST);
   }

   public static DockingWindow getWindow(DockingWindow var0, IntList var1) {
      return var1.isEmpty() ? var0 : (var1.getValue() >= var0.getChildWindowCount() ? null : getWindow(var0.getChildWindow(var1.getValue()), var1.getNext()));
   }

   private static IntList getWindowPath(DockingWindow var0, IntList var1) {
      DockingWindow var2 = var0.getWindowParent();
      return var2 == null ? var1 : getWindowPath(var2, new IntList(var2.getChildWindowIndex(var0), var1));
   }

   public static void addDebugMenuItems(JPopupMenu var0, DockingWindow var1) {
      var0.add("Dump Tree").addActionListener(new InternalDockingUtil$1(var1));
   }

   public static void dump(DockingWindow var0, Printer var1) {
      DockingWindow var2 = var0.getWindowParent();
      String var3 = var0.getClass().getName();
      var1.println(
         var3.substring(var3.lastIndexOf(46) + 1)
            + ", "
            + System.identityHashCode(var0)
            + " ("
            + (var2 == null ? "null" : String.valueOf(System.identityHashCode(var2)))
            + "), '"
            + var0.getTitle()
            + "', "
            + (var0.isVisible() ? "visible" : "not visible")
            + ", "
            + (var0.isMaximized() ? "maximized" : "not maximized")
            + ", "
            + (var0.getChildWindowCount() > 0 ? ":" : "")
      );
      if (var0.getChildWindowCount() > 0) {
         var1.beginSection();

         for(int var4 = 0; var4 < var0.getChildWindowCount(); ++var4) {
            if (var0.getChildWindow(var4) == null) {
               var1.println("null");
            } else {
               dump(var0.getChildWindow(var4), var1);
            }
         }

         var1.endSection();
      }

   }

   public static RootWindow createInnerRootWindow(View[] var0) {
      RootWindow var1 = DockingUtil.createRootWindow(new ViewMap(var0), true);
      var1.getRootWindowProperties().getWindowAreaProperties().setBackgroundColor(null);
      var1.getRootWindowProperties().getWindowAreaShapedPanelProperties().setComponentPainter(null);
      var1.getRootWindowProperties().getComponentProperties().setBackgroundColor(null);
      var1.getRootWindowProperties().getComponentProperties().setBorder(null);
      return var1;
   }

   public static boolean updateButtons(ButtonInfo[] var0, AbstractButton[] var1, Container var2, DockingWindow var3, PropertyMap var4, Map var5) {
      boolean var6 = false;

      for(int var7 = 0; var7 < var0.length; ++var7) {
         WindowTabButtonProperties var8 = new WindowTabButtonProperties(var0[var7].getProperty().get(var4));
         DockingWindowAction var9 = var8.getAction();
         Map var10 = var5 == null ? null : (Map)var5.get(var8.getMap());
         boolean var11 = var8.isVisible() && var9 != null && var9.getAction(var3).isEnabled();
         if ((var1[var7] == null || var10 != null && var10.containsKey(WindowTabButtonProperties.FACTORY)) && var8.getFactory() != null && var9 != null) {
            var1[var7] = var8.getFactory().createButton(var3);
            var1[var7].setFocusable(false);
            var1[var7].addActionListener(var9.getAction(var3).toSwingAction());
            var6 = true;
         }

         if (var1[var7] != null) {
            var1[var7].setToolTipText(var8.getToolTipText());
            var1[var7].setIcon(var8.getIcon());
            var1[var7].setVisible(var11);
         }
      }

      if (var6 && var2 != null) {
         var2.removeAll();

         for(int var12 = 0; var12 < var0.length; ++var12) {
            if (var1[var12] != null) {
               var2.add(var1[var12]);
            }
         }
      }

      return var6;
   }

   public static void addToRootWindow(DockingWindow var0, RootWindow var1) {
      if (var1 != null) {
         DockingWindow var2 = var1.getWindow();
         if (var2 == null) {
            var1.setWindow(var0);
         } else if (var2 instanceof TabWindow) {
            ((TabWindow)var2).addTab(var0);
         } else {
            var1.setWindow(new TabWindow(new DockingWindow[]{var2, var0}));
         }

      }
   }
}
