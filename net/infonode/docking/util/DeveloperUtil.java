package net.infonode.docking.util;

import java.awt.Dimension;
import java.awt.Point;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.FloatingWindow;
import net.infonode.docking.RootWindow;
import net.infonode.docking.SplitWindow;
import net.infonode.docking.TabWindow;
import net.infonode.docking.View;
import net.infonode.docking.WindowBar;

public class DeveloperUtil {
   private static String INDENT_STRING = "    ";

   public DeveloperUtil() {
      super();
   }

   public static String getWindowLayoutAsString(DockingWindow var0) {
      return getDockingWindowLayout(var0, 0);
   }

   public static JFrame createWindowLayoutFrame(String var0, DockingWindow var1) {
      JFrame var2 = new JFrame(var0);
      var2.setDefaultCloseOperation(2);
      JTextArea var3 = new JTextArea(getWindowLayoutAsString(var1));
      JButton var4 = new JButton("Get Current Layout");
      var4.addActionListener(new DeveloperUtil$1(var3, var1));
      Box var5 = new Box(0);
      var5.add(var4);
      var2.getContentPane().add(new JScrollPane(var3), "Center");
      var2.getContentPane().add(var5, "North");
      var2.pack();
      return var2;
   }

   private static String getDockingWindowLayout(DockingWindow var0, int var1) {
      if (var0 instanceof RootWindow) {
         return getRootWindowLayout((RootWindow)var0, var1);
      } else {
         String var2 = var1 > 0 ? "\n" : "";

         for(int var3 = 0; var3 < var1; ++var3) {
            var2 = var2 + INDENT_STRING;
         }

         if (var0 instanceof TabWindow) {
            var2 = var2 + getTabWindowLayout((TabWindow)var0, var1 + 1);
         } else if (var0 instanceof SplitWindow) {
            var2 = var2 + getSplitWindowLayout((SplitWindow)var0, var1 + 1);
         } else {
            var2 = var2 + getViewLayout((View)var0, var1 + 1);
         }

         return var2;
      }
   }

   private static String getRootWindowLayout(RootWindow var0, int var1) {
      String var2 = "";
      if (var0.getWindow() != null) {
         var2 = var2 + "<rootWindow>.setWindow(" + getDockingWindowLayout(var0.getWindow(), var1) + ");\n\n";
      }

      for(int var3 = 0; var3 < var0.getChildWindowCount(); ++var3) {
         DockingWindow var4 = var0.getChildWindow(var3);
         if (var4 != var0.getWindow()) {
            if (!(var4 instanceof WindowBar)) {
               if (var4 instanceof FloatingWindow) {
                  FloatingWindow var10 = (FloatingWindow)var4;
                  Point var11 = var10.getTopLevelAncestor().getLocation();
                  Dimension var7 = var10.getRootPane().getSize();
                  var2 = var2
                     + "<rootWindow>.createFloatingWindow(new Point("
                     + var11.x
                     + ", "
                     + var11.y
                     + "), new Dimension("
                     + var7.width
                     + ", "
                     + var7.height
                     + "), ";
                  var2 = var2 + getDockingWindowLayout(var10.getChildWindow(0), var1);
                  var2 = var2 + ");\n\n";
               }
            } else {
               WindowBar var5 = (WindowBar)var4;
               if (var5.getChildWindowCount() > 0) {
                  for(int var6 = 0; var6 < var5.getChildWindowCount(); ++var6) {
                     var2 = var2
                        + "<rootWindow>.getWindowBar(Direction."
                        + var5.getDirection().toString().toUpperCase()
                        + ").addTab("
                        + getDockingWindowLayout(var5.getChildWindow(var6), var1)
                        + ");\n";
                  }

                  var2 = var2 + "\n";
               }
            }
         }
      }

      return var2;
   }

   private static String getTabWindowLayout(TabWindow var0, int var1) {
      if (var0.getChildWindowCount() == 1 && var0.getChildWindow(0) instanceof View) {
         return getViewLayout((View)var0.getChildWindow(0), var1);
      } else {
         String var2 = "new TabWindow(new DockingWindow[]{";

         for(int var3 = 0; var3 < var0.getChildWindowCount(); ++var3) {
            var2 = var2 + getDockingWindowLayout(var0.getChildWindow(var3), var1);
            if (var3 < var0.getChildWindowCount() - 1) {
               var2 = var2 + ", ";
            }
         }

         return var2 + "})";
      }
   }

   private static String getSplitWindowLayout(SplitWindow var0, int var1) {
      String var2 = "new SplitWindow(" + var0.isHorizontal() + ", " + var0.getDividerLocation() + "f, ";
      var2 = var2 + getDockingWindowLayout(var0.getLeftWindow(), var1) + ", ";
      var2 = var2 + getDockingWindowLayout(var0.getRightWindow(), var1);
      return var2 + ")";
   }

   private static String getViewLayout(View var0, int var1) {
      return "View: \"" + var0.getTitle() + "\" - " + var0.getClass();
   }
}
