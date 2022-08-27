package net.infonode.tabbedpanel;

import net.infonode.gui.button.ButtonFactory;
import net.infonode.gui.button.FlatButtonFactory;

public class TabbedPanelDefaultButtonFactories {
   private static final FlatButtonFactory BUTTON_FACTORY = new FlatButtonFactory();

   public TabbedPanelDefaultButtonFactories() {
      super();
   }

   public static ButtonFactory getScrollUpButtonFactory() {
      return BUTTON_FACTORY;
   }

   public static ButtonFactory getScrollDownButtonFactory() {
      return BUTTON_FACTORY;
   }

   public static ButtonFactory getScrollLeftButtonFactory() {
      return BUTTON_FACTORY;
   }

   public static ButtonFactory getScrollRightButtonFactory() {
      return BUTTON_FACTORY;
   }

   public static ButtonFactory getTabDropDownListButtonFactory() {
      return BUTTON_FACTORY;
   }
}
