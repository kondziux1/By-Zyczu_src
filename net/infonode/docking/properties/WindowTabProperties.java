package net.infonode.docking.properties;

import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapContainer;
import net.infonode.properties.propertymap.PropertyMapFactory;
import net.infonode.properties.propertymap.PropertyMapGroup;
import net.infonode.properties.propertymap.PropertyMapProperty;
import net.infonode.tabbedpanel.titledtab.TitledTabProperties;
import net.infonode.tabbedpanel.titledtab.TitledTabStateProperties;

public class WindowTabProperties extends PropertyMapContainer {
   public static final PropertyMapGroup PROPERTIES = new PropertyMapGroup("Tab Properties", "");
   public static final PropertyMapProperty TITLED_TAB_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Titled Tab Properties", "Property values for the TitledTab used in the window tab.", TitledTabProperties.PROPERTIES
   );
   public static final PropertyMapProperty FOCUSED_PROPERTIES = new PropertyMapProperty(
      PROPERTIES,
      "Focused Properties",
      "Property values for the TitledTab when the window is focused or a component in the tab's content component has focus.\nThe "
         + TITLED_TAB_PROPERTIES
         + '.'
         + TitledTabProperties.HIGHLIGHTED_PROPERTIES
         + " property values are inherited from.",
      TitledTabStateProperties.PROPERTIES
   );
   public static final PropertyMapProperty NORMAL_BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Normal Button Properties", "Property values for the tab buttons when the tab is in the normal state.", WindowTabStateProperties.PROPERTIES
   );
   public static final PropertyMapProperty HIGHLIGHTED_BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Highlighted Button Properties", "Property values for the tab buttons when the tab is highlighted.", WindowTabStateProperties.PROPERTIES
   );
   public static final PropertyMapProperty FOCUSED_BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES,
      "Focused Button Properties",
      "Property values for the tab buttons when the tab is focused or a component in the tab's content component has focus.",
      WindowTabStateProperties.PROPERTIES
   );

   public WindowTabProperties() {
      super(PropertyMapFactory.create(PROPERTIES));
   }

   public WindowTabProperties(PropertyMap var1) {
      super(var1);
   }

   public WindowTabProperties(WindowTabProperties var1) {
      super(PropertyMapFactory.create(var1.getMap()));
   }

   public WindowTabProperties addSuperObject(WindowTabProperties var1) {
      this.getMap().addSuperMap(var1.getMap());
      return this;
   }

   /** @deprecated */
   public WindowTabProperties removeSuperObject() {
      this.getMap().removeSuperMap();
      return this;
   }

   public WindowTabProperties removeSuperObject(WindowTabProperties var1) {
      this.getMap().removeSuperMap(var1.getMap());
      return this;
   }

   public TitledTabProperties getTitledTabProperties() {
      return new TitledTabProperties(TITLED_TAB_PROPERTIES.get(this.getMap()));
   }

   public TitledTabStateProperties getFocusedProperties() {
      return new TitledTabStateProperties(FOCUSED_PROPERTIES.get(this.getMap()));
   }

   public WindowTabStateProperties getNormalButtonProperties() {
      return new WindowTabStateProperties(NORMAL_BUTTON_PROPERTIES.get(this.getMap()));
   }

   public WindowTabStateProperties getHighlightedButtonProperties() {
      return new WindowTabStateProperties(HIGHLIGHTED_BUTTON_PROPERTIES.get(this.getMap()));
   }

   public WindowTabStateProperties getFocusedButtonProperties() {
      return new WindowTabStateProperties(FOCUSED_BUTTON_PROPERTIES.get(this.getMap()));
   }
}
