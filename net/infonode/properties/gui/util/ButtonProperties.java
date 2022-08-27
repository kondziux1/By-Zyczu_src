package net.infonode.properties.gui.util;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import net.infonode.gui.button.ButtonFactory;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapContainer;
import net.infonode.properties.propertymap.PropertyMapFactory;
import net.infonode.properties.propertymap.PropertyMapGroup;
import net.infonode.properties.propertymap.PropertyMapValueHandler;
import net.infonode.properties.types.ButtonFactoryProperty;
import net.infonode.properties.types.IconProperty;
import net.infonode.properties.types.StringProperty;

public class ButtonProperties extends PropertyMapContainer {
   public static final PropertyMapGroup PROPERTIES = new PropertyMapGroup("Button Properties", "");
   public static final IconProperty ICON = new IconProperty(PROPERTIES, "Icon", "Icon for the enabled button state.", PropertyMapValueHandler.INSTANCE);
   public static final IconProperty DISABLED_ICON = new IconProperty(
      PROPERTIES, "Disabled Icon", "Icon for the disabled button state.", PropertyMapValueHandler.INSTANCE
   );
   public static final StringProperty TOOL_TIP_TEXT = new StringProperty(
      PROPERTIES, "Tool Tip Text", "The button tool tip text.", PropertyMapValueHandler.INSTANCE
   );
   public static final ButtonFactoryProperty FACTORY = new ButtonFactoryProperty(
      PROPERTIES,
      "Factory",
      "The button factory. This factory is used to create a button. The created button will be assigned the icon from the '"
         + ICON.getName()
         + "' property or the '"
         + DISABLED_ICON.getName()
         + "' property and the tool tip from the '"
         + TOOL_TIP_TEXT.getName()
         + "' "
         + "property. An action listener is also added to the button.",
      PropertyMapValueHandler.INSTANCE
   );

   public ButtonProperties() {
      super(PROPERTIES);
   }

   public ButtonProperties(PropertyMap var1) {
      super(var1);
   }

   public ButtonProperties(ButtonProperties var1) {
      super(PropertyMapFactory.create(var1.getMap()));
   }

   public ButtonProperties addSuperObject(ButtonProperties var1) {
      this.getMap().addSuperMap(var1.getMap());
      return this;
   }

   public ButtonProperties removeSuperObject() {
      this.getMap().removeSuperMap();
      return this;
   }

   public ButtonProperties removeSuperObject(ButtonProperties var1) {
      this.getMap().removeSuperMap(var1.getMap());
      return this;
   }

   public ButtonProperties setIcon(Icon var1) {
      ICON.set(this.getMap(), var1);
      return this;
   }

   public Icon getIcon() {
      return ICON.get(this.getMap());
   }

   public ButtonProperties setDisabledIcon(Icon var1) {
      DISABLED_ICON.set(this.getMap(), var1);
      return this;
   }

   public Icon getDisabledIcon() {
      return DISABLED_ICON.get(this.getMap());
   }

   public String getToolTipText() {
      return TOOL_TIP_TEXT.get(this.getMap());
   }

   public ButtonProperties setToolTipText(String var1) {
      TOOL_TIP_TEXT.set(this.getMap(), var1);
      return this;
   }

   public ButtonFactory getFactory() {
      return FACTORY.get(this.getMap());
   }

   public ButtonProperties setFactory(ButtonFactory var1) {
      FACTORY.set(this.getMap(), var1);
      return this;
   }

   public AbstractButton applyTo(AbstractButton var1) {
      var1.setIcon(this.getIcon());
      var1.setDisabledIcon(this.getDisabledIcon());
      var1.setToolTipText(this.getToolTipText());
      return var1;
   }

   static {
      ButtonProperties var0 = new ButtonProperties(PROPERTIES.getDefaultMap());
      var0.setIcon(null).setDisabledIcon(null).setToolTipText(null);
   }
}
