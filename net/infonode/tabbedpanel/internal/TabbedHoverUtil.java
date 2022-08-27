package net.infonode.tabbedpanel.internal;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import net.infonode.tabbedpanel.TabbedPanel;
import net.infonode.tabbedpanel.TabbedPanelContentPanel;
import net.infonode.tabbedpanel.TabbedPanelHoverPolicy;

public class TabbedHoverUtil {
   public TabbedHoverUtil() {
      super();
   }

   public static boolean isDeepestHoverableTabbedPanel(ArrayList var0, TabbedPanel var1) {
      for(Object var2 = (Component)var0.get(0); var2 != null; var2 = ((Component)var2).getParent()) {
         TabbedPanel var3 = null;
         if (var2 instanceof TabbedPanel) {
            var3 = (TabbedPanel)var2;
         }

         if (var2 instanceof TabbedPanelContentPanel) {
            var3 = ((TabbedPanelContentPanel)var2).getTabbedPanel();
         }

         if (var3 != null) {
            if (var3 == var1) {
               return true;
            }

            if (var3.getProperties().getHoverPolicy() != TabbedPanelHoverPolicy.ALWAYS_AND_EXCLUDE) {
               return false;
            }
         }
      }

      return true;
   }

   public static boolean hasVisibleTabbedPanelChild(Component var0) {
      if (var0 instanceof TabbedPanel && ((TabbedPanel)var0).getProperties().getHoverPolicy() != TabbedPanelHoverPolicy.ALWAYS_AND_EXCLUDE) {
         return true;
      } else {
         if (var0 instanceof Container) {
            Container var1 = (Container)var0;

            for(int var2 = 0; var2 < var1.getComponentCount(); ++var2) {
               if (var1.getComponent(var2).isVisible() && hasVisibleTabbedPanelChild(var1.getComponent(var2))) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public static boolean acceptTabbedPanelHover(TabbedPanelHoverPolicy var0, ArrayList var1, TabbedPanel var2, Component var3) {
      if (var0 == TabbedPanelHoverPolicy.NO_HOVERED_CHILD) {
         return isDeepestHoverableTabbedPanel(var1, var2);
      } else if (var0 == TabbedPanelHoverPolicy.NEVER) {
         return false;
      } else if (var0 == TabbedPanelHoverPolicy.ALWAYS || var0 == TabbedPanelHoverPolicy.ALWAYS_AND_EXCLUDE) {
         return true;
      } else if (var0 == TabbedPanelHoverPolicy.ONLY_WHEN_DEEPEST && var3 != null) {
         return !hasVisibleTabbedPanelChild(var3);
      } else {
         return false;
      }
   }
}
