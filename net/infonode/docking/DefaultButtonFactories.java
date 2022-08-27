package net.infonode.docking;

import net.infonode.gui.button.ButtonFactory;
import net.infonode.gui.button.FlatButtonFactory;

public class DefaultButtonFactories {
   private static final FlatButtonFactory BUTTON_FACTORY = new FlatButtonFactory();

   private DefaultButtonFactories() {
      super();
   }

   public static ButtonFactory getCloseButtonFactory() {
      return BUTTON_FACTORY;
   }

   public static ButtonFactory getMinimizeButtonFactory() {
      return BUTTON_FACTORY;
   }

   public static ButtonFactory getMaximizeButtonFactory() {
      return BUTTON_FACTORY;
   }

   public static ButtonFactory getRestoreButtonFactory() {
      return BUTTON_FACTORY;
   }

   public static ButtonFactory getUndockButtonFactory() {
      return BUTTON_FACTORY;
   }

   public static ButtonFactory getDockButtonFactory() {
      return BUTTON_FACTORY;
   }
}
