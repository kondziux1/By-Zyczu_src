package net.infonode.tabbedpanel;

import java.awt.Component;
import net.infonode.gui.hover.hoverable.HoverManager;

public class TabbedUtils {
   private TabbedUtils() {
      super();
   }

   public static Tab getParentTab(Component var0) {
      while(var0 != null) {
         if (var0 instanceof Tab) {
            return (Tab)var0;
         }

         var0 = ((Component)var0).getParent();
      }

      return null;
   }

   public static TabbedPanel getParentTabbedPanel(Component var0) {
      while(var0 != null) {
         if (var0 instanceof TabbedPanel) {
            return (TabbedPanel)var0;
         }

         var0 = ((Component)var0).getParent();
      }

      return null;
   }

   public static TabbedPanelContentPanel getParentTabbedPanelContentPanel(Component var0) {
      while(var0 != null) {
         if (var0 instanceof TabbedPanelContentPanel) {
            return (TabbedPanelContentPanel)var0;
         }

         var0 = ((Component)var0).getParent();
      }

      return null;
   }

   public static boolean isHoverEnabled() {
      return HoverManager.getInstance().isEventListeningActive();
   }
}
