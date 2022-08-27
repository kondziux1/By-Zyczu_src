package net.infonode.gui;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Window;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;
import net.infonode.gui.componentpainter.ComponentPainter;
import net.infonode.util.Direction;

public class ComponentUtil {
   private ComponentUtil() {
      super();
   }

   public static final Component getChildAt(Container var0, Point var1) {
      Component var2 = var0.getComponentAt(var1);
      return var2 != null && var2.getParent() == var0 ? var2 : null;
   }

   public static final Component getVisibleChildAt(Container var0, Point var1) {
      for(int var2 = 0; var2 < var0.getComponentCount(); ++var2) {
         Component var3 = var0.getComponent(var2);
         if (var3.isVisible() && var3.contains(var1.x - var3.getX(), var1.y - var3.getY())) {
            return var3;
         }
      }

      return null;
   }

   public static final Component getChildAtLine(Container var0, Point var1, boolean var2) {
      if (var2) {
         for(int var3 = 0; var3 < var0.getComponentCount(); ++var3) {
            Component var4 = var0.getComponent(var3);
            if (var1.x >= var4.getX() && var1.x < var4.getX() + var4.getWidth()) {
               return var4;
            }
         }
      } else {
         for(int var5 = 0; var5 < var0.getComponentCount(); ++var5) {
            Component var6 = var0.getComponent(var5);
            if (var1.y >= var6.getY() && var1.y < var6.getY() + var6.getHeight()) {
               return var6;
            }
         }
      }

      return null;
   }

   public static void getComponentTreePosition(Component var0, ArrayList var1) {
      if (var0.getParent() != null) {
         getComponentTreePosition(var0.getParent(), var1);
         var1.add(new Integer(var0.getParent().getComponentCount() - getComponentIndex(var0)));
      }
   }

   public static Component findComponentUnderGlassPaneAt(Point var0, Component var1) {
      Component var2 = null;
      if (var1.isShowing()) {
         if (var1 instanceof RootPaneContainer) {
            var2 = ((RootPaneContainer)var1)
               .getLayeredPane()
               .findComponentAt(SwingUtilities.convertPoint(var1, var0, ((RootPaneContainer)var1).getLayeredPane()));
         } else {
            var2 = ((Container)var1).findComponentAt(var0);
         }
      }

      return var2;
   }

   public static final int getComponentIndex(Component var0) {
      if (var0 != null && var0.getParent() != null) {
         Container var1 = var0.getParent();

         for(int var2 = 0; var2 < var1.getComponentCount(); ++var2) {
            if (var1.getComponent(var2) == var0) {
               return var2;
            }
         }
      }

      return -1;
   }

   public static final String getBorderLayoutOrientation(Direction var0) {
      return var0 == Direction.UP ? "North" : (var0 == Direction.LEFT ? "West" : (var0 == Direction.DOWN ? "South" : "East"));
   }

   public static Color getBackgroundColor(Component var0) {
      if (var0 == null) {
         return null;
      } else {
         if (var0 instanceof BackgroundPainter) {
            ComponentPainter var1 = ((BackgroundPainter)var0).getComponentPainter();
            if (var1 != null) {
               Color var2 = var1.getColor(var0);
               if (var2 != null) {
                  return var2;
               }
            }
         }

         return var0.isOpaque() ? var0.getBackground() : getBackgroundColor(var0.getParent());
      }
   }

   public static int countComponents(Container var0) {
      int var1 = 1;

      for(int var2 = 0; var2 < var0.getComponentCount(); ++var2) {
         Component var3 = var0.getComponent(var2);
         if (var3 instanceof Container) {
            var1 += countComponents((Container)var3);
         } else {
            ++var1;
         }
      }

      return var1;
   }

   public static int getVisibleChildrenCount(Component var0) {
      if (var0 != null && var0 instanceof Container) {
         int var1 = 0;
         Container var2 = (Container)var0;

         for(int var3 = 0; var3 < var2.getComponentCount(); ++var3) {
            if (var2.getComponent(var3).isVisible()) {
               ++var1;
            }
         }

         return var1;
      } else {
         return 0;
      }
   }

   public static Component getTopLevelAncestor(Component var0) {
      while(var0 != null && !(var0 instanceof Window) && !(var0 instanceof Applet)) {
         var0 = ((Component)var0).getParent();
      }

      return (Component)var0;
   }

   public static boolean hasVisibleChildren(Component var0) {
      return getVisibleChildrenCount(var0) > 0;
   }

   public static boolean isOnlyVisibleComponent(Component var0) {
      return var0 != null && var0.isVisible() && getVisibleChildrenCount(var0.getParent()) == 1;
   }

   public static boolean isOnlyVisibleComponents(Component[] var0) {
      if (var0 != null && var0.length > 0) {
         boolean var1 = getVisibleChildrenCount(var0[0].getParent()) == var0.length;
         if (var1) {
            for(int var2 = 0; var2 < var0.length; ++var2) {
               var1 = var1 && var0[var2].isVisible();
            }
         }

         return var1;
      } else {
         return false;
      }
   }

   public static Component findFirstComponentOfType(Component var0, Class var1) {
      if (var1.isInstance(var0)) {
         return var0;
      } else {
         if (var0 instanceof Container) {
            Container var2 = (Container)var0;

            for(int var3 = 0; var3 < var2.getComponentCount(); ++var3) {
               Component var4 = findFirstComponentOfType(var2.getComponent(var3), var1);
               if (var4 != null) {
                  return var4;
               }
            }
         }

         return null;
      }
   }

   public static boolean isFocusable(Component var0) {
      return var0.isFocusable() && var0.isDisplayable() && var0.isVisible() && var0.isEnabled();
   }

   public static boolean requestFocus(Component var0) {
      return KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == var0 || var0.requestFocusInWindow();
   }

   public static Component smartRequestFocus(Component var0) {
      if (requestFocus(var0)) {
         return var0;
      } else {
         if (var0 instanceof JComponent) {
            FocusTraversalPolicy var1 = ((JComponent)var0).getFocusTraversalPolicy();
            if (var1 != null) {
               Component var2 = var1.getDefaultComponent((Container)var0);
               if (var2 != null && requestFocus(var2)) {
                  return var2;
               }
            }
         }

         if (var0 instanceof Container) {
            Component[] var4 = ((Container)var0).getComponents();

            for(int var5 = 0; var5 < var4.length; ++var5) {
               var0 = smartRequestFocus(var4[var5]);
               if (var0 != null) {
                  return var0;
               }
            }
         }

         return null;
      }
   }

   public static int getPreferredMaxHeight(Component[] var0) {
      int var1 = 0;

      for(int var2 = 0; var2 < var0.length; ++var2) {
         int var3 = (int)var0[var2].getPreferredSize().getHeight();
         if (var3 > var1) {
            var1 = var3;
         }
      }

      return var1;
   }

   public static int getPreferredMaxWidth(Component[] var0) {
      int var1 = 0;

      for(int var2 = 0; var2 < var0.length; ++var2) {
         int var3 = (int)var0[var2].getPreferredSize().getWidth();
         if (var3 > var1) {
            var1 = var3;
         }
      }

      return var1;
   }

   public static void setAllOpaque(Container var0, boolean var1) {
      if (var0 instanceof JComponent) {
         ((JComponent)var0).setOpaque(var1);

         for(int var2 = 0; var2 < var0.getComponentCount(); ++var2) {
            Component var3 = var0.getComponent(var2);
            if (var3 instanceof Container) {
               setAllOpaque((Container)var3, var1);
            }
         }
      }

   }

   public static void validate(JComponent var0) {
      var0.revalidate();
   }

   public static void validate(Component var0) {
      if (var0 instanceof JComponent) {
         ((JComponent)var0).revalidate();
      } else {
         var0.validate();
      }

   }
}
