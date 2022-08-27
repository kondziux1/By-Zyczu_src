package net.infonode.tabbedpanel;

import net.infonode.properties.gui.util.ButtonProperties;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapContainer;
import net.infonode.properties.propertymap.PropertyMapFactory;
import net.infonode.properties.propertymap.PropertyMapGroup;
import net.infonode.properties.propertymap.PropertyMapProperty;

public class TabbedPanelButtonProperties extends PropertyMapContainer {
   public static final PropertyMapGroup PROPERTIES = new PropertyMapGroup("Tabbed Panel Button Properties", "Properties for a Tabbed Panel's buttons.");
   public static final PropertyMapProperty SCROLL_UP_BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Scroll Up Button Properties", "Properties for scroll up button.", ButtonProperties.PROPERTIES
   );
   public static final PropertyMapProperty SCROLL_LEFT_BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Scroll Left Button Properties", "Properties for scroll left button.", ButtonProperties.PROPERTIES
   );
   public static final PropertyMapProperty SCROLL_DOWN_BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Scroll Down Button Properties", "Properties for scroll down button.", ButtonProperties.PROPERTIES
   );
   public static final PropertyMapProperty SCROLL_RIGHT_BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Scroll Right Button Properties", "Properties for scroll right button.", ButtonProperties.PROPERTIES
   );
   public static final PropertyMapProperty TAB_DROP_DOWN_LIST_BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Tab Drop Down List Button Properties", "Properties for the tab drop down list button.", ButtonProperties.PROPERTIES
   );

   public TabbedPanelButtonProperties() {
      super(PROPERTIES);
   }

   public TabbedPanelButtonProperties(PropertyMap var1) {
      super(var1);
   }

   public TabbedPanelButtonProperties(TabbedPanelButtonProperties var1) {
      super(PropertyMapFactory.create(var1.getMap()));
   }

   public TabbedPanelButtonProperties addSuperObject(TabbedPanelButtonProperties var1) {
      this.getMap().addSuperMap(var1.getMap());
      return this;
   }

   public TabbedPanelButtonProperties removeSuperObject() {
      this.getMap().removeSuperMap();
      return this;
   }

   public TabbedPanelButtonProperties removeSuperObject(TabbedPanelButtonProperties var1) {
      this.getMap().removeSuperMap(var1.getMap());
      return this;
   }

   public ButtonProperties getScrollUpButtonProperties() {
      return new ButtonProperties(SCROLL_UP_BUTTON_PROPERTIES.get(this.getMap()));
   }

   public ButtonProperties getScrollDownButtonProperties() {
      return new ButtonProperties(SCROLL_DOWN_BUTTON_PROPERTIES.get(this.getMap()));
   }

   public ButtonProperties getScrollLeftButtonProperties() {
      return new ButtonProperties(SCROLL_LEFT_BUTTON_PROPERTIES.get(this.getMap()));
   }

   public ButtonProperties getScrollRightButtonProperties() {
      return new ButtonProperties(SCROLL_RIGHT_BUTTON_PROPERTIES.get(this.getMap()));
   }

   public ButtonProperties getTabDropDownListButtonProperties() {
      return new ButtonProperties(TAB_DROP_DOWN_LIST_BUTTON_PROPERTIES.get(this.getMap()));
   }
}
