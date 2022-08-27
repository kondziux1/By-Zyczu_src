package net.infonode.docking.properties;

import javax.swing.Icon;
import net.infonode.docking.action.DockingWindowAction;
import net.infonode.docking.action.DockingWindowActionProperty;
import net.infonode.gui.button.ButtonFactory;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapContainer;
import net.infonode.properties.propertymap.PropertyMapFactory;
import net.infonode.properties.propertymap.PropertyMapGroup;
import net.infonode.properties.propertymap.PropertyMapValueHandler;
import net.infonode.properties.types.BooleanProperty;
import net.infonode.properties.types.ButtonFactoryProperty;
import net.infonode.properties.types.IconProperty;
import net.infonode.properties.types.StringProperty;

public class WindowTabButtonProperties extends PropertyMapContainer {
   public static final PropertyMapGroup PROPERTIES = new PropertyMapGroup("Window Tab Button Properties", "");
   public static final BooleanProperty VISIBLE = new BooleanProperty(PROPERTIES, "Visible", "True if the button is visible.", PropertyMapValueHandler.INSTANCE);
   public static final IconProperty ICON = new IconProperty(PROPERTIES, "Icon", "The button icon.", PropertyMapValueHandler.INSTANCE);
   public static final StringProperty TOOL_TIP_TEXT = new StringProperty(
      PROPERTIES, "Tool Tip Text", "The button tool tip text.", PropertyMapValueHandler.INSTANCE
   );
   public static final DockingWindowActionProperty ACTION = new DockingWindowActionProperty(
      PROPERTIES, "Action", "The action that is performed when the button is clicked.", PropertyMapValueHandler.INSTANCE
   );
   public static final ButtonFactoryProperty FACTORY = new ButtonFactoryProperty(
      PROPERTIES,
      "Factory",
      "The button factory. This factory is used to create the button when it's first needed. Modifying this property will NOT cause already created buttons to be replaced. The created button will be set to non-focusable and will be assigned the icon from the '"
         + ICON.getName()
         + "' property and the tool tip from the '"
         + TOOL_TIP_TEXT.getName()
         + "' "
         + "property. An action listener that performs the action in set in the '"
         + ACTION.getName()
         + "' property is added to the button.",
      PropertyMapValueHandler.INSTANCE
   );

   public WindowTabButtonProperties() {
      super(PROPERTIES);
   }

   public WindowTabButtonProperties(PropertyMap var1) {
      super(var1);
   }

   public WindowTabButtonProperties(WindowTabButtonProperties var1) {
      super(PropertyMapFactory.create(var1.getMap()));
   }

   public WindowTabButtonProperties addSuperObject(WindowTabButtonProperties var1) {
      this.getMap().addSuperMap(var1.getMap());
      return this;
   }

   /** @deprecated */
   public WindowTabButtonProperties removeSuperObject() {
      this.getMap().removeSuperMap();
      return this;
   }

   public WindowTabButtonProperties removeSuperObject(WindowTabButtonProperties var1) {
      this.getMap().removeSuperMap(var1.getMap());
      return this;
   }

   public WindowTabButtonProperties setVisible(boolean var1) {
      VISIBLE.set(this.getMap(), var1);
      return this;
   }

   public boolean isVisible() {
      return VISIBLE.get(this.getMap());
   }

   public WindowTabButtonProperties setIcon(Icon var1) {
      ICON.set(this.getMap(), var1);
      return this;
   }

   public Icon getIcon() {
      return ICON.get(this.getMap());
   }

   public String getToolTipText() {
      return TOOL_TIP_TEXT.get(this.getMap());
   }

   public WindowTabButtonProperties setToolTipText(String var1) {
      TOOL_TIP_TEXT.set(this.getMap(), var1);
      return this;
   }

   public ButtonFactory getFactory() {
      return FACTORY.get(this.getMap());
   }

   public WindowTabButtonProperties setFactory(ButtonFactory var1) {
      FACTORY.set(this.getMap(), var1);
      return this;
   }

   public DockingWindowAction getAction() {
      return ACTION.get(this.getMap());
   }

   public WindowTabButtonProperties setAction(DockingWindowAction var1) {
      ACTION.set(this.getMap(), var1);
      return this;
   }

   public WindowTabButtonProperties setTo(DockingWindowAction var1) {
      this.setAction(var1);
      this.setIcon(var1.getIcon());
      this.setToolTipText(var1.getName());
      return this;
   }
}
