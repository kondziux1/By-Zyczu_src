package net.infonode.docking.properties;

import javax.swing.Icon;
import net.infonode.properties.gui.util.ComponentProperties;
import net.infonode.properties.gui.util.ShapedPanelProperties;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapContainer;
import net.infonode.properties.propertymap.PropertyMapFactory;
import net.infonode.properties.propertymap.PropertyMapGroup;
import net.infonode.properties.propertymap.PropertyMapProperty;
import net.infonode.properties.propertymap.PropertyMapValueHandler;
import net.infonode.properties.types.AlignmentProperty;
import net.infonode.properties.types.BooleanProperty;
import net.infonode.properties.types.IconProperty;
import net.infonode.properties.types.IntegerProperty;
import net.infonode.properties.types.StringProperty;
import net.infonode.util.Alignment;

public class ViewTitleBarStateProperties extends PropertyMapContainer {
   public static final PropertyMapGroup PROPERTIES = new PropertyMapGroup("View Title Bar State Properties", "");
   public static final PropertyMapProperty COMPONENT_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Component Properties", "Properties for title bar.", ComponentProperties.PROPERTIES
   );
   public static final PropertyMapProperty SHAPED_PANEL_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Shaped Panel Properties", "Properties for shaped title bar.", ShapedPanelProperties.PROPERTIES
   );
   public static final PropertyMapProperty MINIMIZE_BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Minimize Button Properties", "The minimize button property values.", WindowTabButtonProperties.PROPERTIES
   );
   public static final PropertyMapProperty MAXIMIZE_BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Maximize Button Properties", "The maximizee button property values.", WindowTabButtonProperties.PROPERTIES
   );
   public static final PropertyMapProperty RESTORE_BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Restore Button Properties", "The restore button property values.", WindowTabButtonProperties.PROPERTIES
   );
   public static final PropertyMapProperty CLOSE_BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Close Button Properties", "The close button property values.", WindowTabButtonProperties.PROPERTIES
   );
   public static final PropertyMapProperty UNDOCK_BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Undock Button Properties", "The undock button property values.", WindowTabButtonProperties.PROPERTIES
   );
   public static final PropertyMapProperty DOCK_BUTTON_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Dock Button Properties", "The dockbutton property values.", WindowTabButtonProperties.PROPERTIES
   );
   public static final StringProperty TITLE = new StringProperty(PROPERTIES, "Title", "The title bar title.", PropertyMapValueHandler.INSTANCE);
   public static final BooleanProperty TITLE_VISIBLE = new BooleanProperty(
      PROPERTIES, "Title Visible", "Controls if the title should be visible or not.", PropertyMapValueHandler.INSTANCE
   );
   public static final IconProperty ICON = new IconProperty(PROPERTIES, "Icon", "The title bar icon.", PropertyMapValueHandler.INSTANCE);
   public static final BooleanProperty ICON_VISIBLE = new BooleanProperty(
      PROPERTIES, "Icon Visible", "Controls if the icon should be visible or not.", PropertyMapValueHandler.INSTANCE
   );
   public static final IntegerProperty ICON_TEXT_GAP = IntegerProperty.createPositive(
      PROPERTIES, "Icon Text Gap", "Gap in pixels between the icon and the title", 2, PropertyMapValueHandler.INSTANCE
   );
   public static final AlignmentProperty ICON_TEXT_HORIZONTAL_ALIGNMENT = new AlignmentProperty(
      PROPERTIES,
      "Icon Text Horizontal Alignment",
      "Horizontal alignment for the icon and title text.",
      PropertyMapValueHandler.INSTANCE,
      Alignment.getHorizontalAlignments()
   );
   public static final IntegerProperty BUTTON_SPACING = IntegerProperty.createPositive(
      PROPERTIES, "Button Spacing", "Spacing in pixels between the buttons on the title bar", 2, PropertyMapValueHandler.INSTANCE
   );

   public ViewTitleBarStateProperties() {
      super(PropertyMapFactory.create(PROPERTIES));
   }

   public ViewTitleBarStateProperties(PropertyMap var1) {
      super(var1);
   }

   public ViewTitleBarStateProperties(ViewTitleBarStateProperties var1) {
      super(PropertyMapFactory.create(var1.getMap()));
   }

   public ViewTitleBarStateProperties addSuperObject(ViewTitleBarStateProperties var1) {
      this.getMap().addSuperMap(var1.getMap());
      return this;
   }

   public ViewTitleBarStateProperties removeSuperObject(ViewTitleBarStateProperties var1) {
      this.getMap().removeSuperMap(var1.getMap());
      return this;
   }

   public ComponentProperties getComponentProperties() {
      return new ComponentProperties(COMPONENT_PROPERTIES.get(this.getMap()));
   }

   public ShapedPanelProperties getShapedPanelProperties() {
      return new ShapedPanelProperties(SHAPED_PANEL_PROPERTIES.get(this.getMap()));
   }

   public WindowTabButtonProperties getMinimizeButtonProperties() {
      return new WindowTabButtonProperties(MINIMIZE_BUTTON_PROPERTIES.get(this.getMap()));
   }

   public WindowTabButtonProperties getMaximizeButtonProperties() {
      return new WindowTabButtonProperties(MAXIMIZE_BUTTON_PROPERTIES.get(this.getMap()));
   }

   public WindowTabButtonProperties getRestoreButtonProperties() {
      return new WindowTabButtonProperties(RESTORE_BUTTON_PROPERTIES.get(this.getMap()));
   }

   public WindowTabButtonProperties getCloseButtonProperties() {
      return new WindowTabButtonProperties(CLOSE_BUTTON_PROPERTIES.get(this.getMap()));
   }

   public WindowTabButtonProperties getUndockButtonProperties() {
      return new WindowTabButtonProperties(UNDOCK_BUTTON_PROPERTIES.get(this.getMap()));
   }

   public WindowTabButtonProperties getDockButtonProperties() {
      return new WindowTabButtonProperties(DOCK_BUTTON_PROPERTIES.get(this.getMap()));
   }

   public ViewTitleBarStateProperties setButtonSpacing(int var1) {
      BUTTON_SPACING.set(this.getMap(), var1);
      return this;
   }

   public int getButtonSpacing() {
      return BUTTON_SPACING.get(this.getMap());
   }

   public ViewTitleBarStateProperties setTitle(String var1) {
      TITLE.set(this.getMap(), var1);
      return this;
   }

   public String getTitle() {
      return TITLE.get(this.getMap());
   }

   public ViewTitleBarStateProperties setTitleVisible(boolean var1) {
      TITLE_VISIBLE.set(this.getMap(), var1);
      return this;
   }

   public boolean getTitleVisible() {
      return TITLE_VISIBLE.get(this.getMap());
   }

   public ViewTitleBarStateProperties setIcon(Icon var1) {
      ICON.set(this.getMap(), var1);
      return this;
   }

   public Icon getIcon() {
      return ICON.get(this.getMap());
   }

   public ViewTitleBarStateProperties setIconVisible(boolean var1) {
      ICON_VISIBLE.set(this.getMap(), var1);
      return this;
   }

   public boolean getIconVisible() {
      return ICON_VISIBLE.get(this.getMap());
   }

   public ViewTitleBarStateProperties setIconTextGap(int var1) {
      ICON_TEXT_GAP.set(this.getMap(), var1);
      return this;
   }

   public int getIconTextGap() {
      return ICON_TEXT_GAP.get(this.getMap());
   }

   public ViewTitleBarStateProperties setIconTextHorizontalAlignment(Alignment var1) {
      ICON_TEXT_HORIZONTAL_ALIGNMENT.set(this.getMap(), var1);
      return this;
   }

   public Alignment getIconTextHorizontalAlignment() {
      return ICON_TEXT_HORIZONTAL_ALIGNMENT.get(this.getMap());
   }
}
