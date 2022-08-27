package net.infonode.tabbedpanel.titledtab;

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
import net.infonode.properties.types.DirectionProperty;
import net.infonode.properties.types.IconProperty;
import net.infonode.properties.types.IntegerProperty;
import net.infonode.properties.types.StringProperty;
import net.infonode.util.Alignment;
import net.infonode.util.Direction;

public class TitledTabStateProperties extends PropertyMapContainer {
   public static final PropertyMapGroup PROPERTIES = new PropertyMapGroup("State Properties", "");
   public static final IconProperty ICON = new IconProperty(PROPERTIES, "Icon", "Icon", PropertyMapValueHandler.INSTANCE);
   public static final StringProperty TEXT = new StringProperty(PROPERTIES, "Text", "Text", PropertyMapValueHandler.INSTANCE);
   public static final IntegerProperty ICON_TEXT_GAP = IntegerProperty.createPositive(
      PROPERTIES, "Icon Text Gap", "Number of pixels between icon and text.", 2, PropertyMapValueHandler.INSTANCE
   );
   public static final StringProperty TOOL_TIP_TEXT = new StringProperty(PROPERTIES, "Tool Tip Text", "Tool tip text", PropertyMapValueHandler.INSTANCE);
   public static final BooleanProperty TOOL_TIP_ENABLED = new BooleanProperty(
      PROPERTIES, "Tool Tip Enabled", "Tool tip enabled or disabled", PropertyMapValueHandler.INSTANCE
   );
   public static final BooleanProperty ICON_VISIBLE = new BooleanProperty(
      PROPERTIES, "Icon Visible", "Icon visible or not visible", PropertyMapValueHandler.INSTANCE
   );
   public static final BooleanProperty TEXT_VISIBLE = new BooleanProperty(
      PROPERTIES, "Text Visible", "Text visible or not visible", PropertyMapValueHandler.INSTANCE
   );
   public static final BooleanProperty TITLE_COMPONENT_VISIBLE = new BooleanProperty(
      PROPERTIES, "Title Component Visible", "Title component visible or not visible", PropertyMapValueHandler.INSTANCE
   );
   public static final AlignmentProperty HORIZONTAL_ALIGNMENT = new AlignmentProperty(
      PROPERTIES, "Horizontal Alignment", "Horizontal alignment for the icon and text.", PropertyMapValueHandler.INSTANCE, Alignment.getHorizontalAlignments()
   );
   public static final AlignmentProperty VERTICAL_ALIGNMENT = new AlignmentProperty(
      PROPERTIES, "Vertical Alignment", "Vertical alignment for the icon and text.", PropertyMapValueHandler.INSTANCE, Alignment.getVerticalAlignments()
   );
   public static final AlignmentProperty ICON_TEXT_RELATIVE_ALIGNMENT = new AlignmentProperty(
      PROPERTIES,
      "Icon Text Relative Alignment",
      "Icon horizontal alignment relative to text.",
      PropertyMapValueHandler.INSTANCE,
      new Alignment[]{Alignment.LEFT, Alignment.RIGHT}
   );
   public static final IntegerProperty TEXT_TITLE_COMPONENT_GAP = IntegerProperty.createPositive(
      PROPERTIES, "Text Title Component Gap", "Number of pixels between text and title component.", 2, PropertyMapValueHandler.INSTANCE
   );
   public static final AlignmentProperty TITLE_COMPONENT_TEXT_RELATIVE_ALIGNMENT = new AlignmentProperty(
      PROPERTIES,
      "Title Component Text Relative Alignment",
      "Title component horizontal alignment relative to text and icon.",
      PropertyMapValueHandler.INSTANCE,
      new Alignment[]{Alignment.LEFT, Alignment.RIGHT}
   );
   public static final DirectionProperty DIRECTION = new DirectionProperty(
      PROPERTIES, "Direction", "Direction for tab contents", PropertyMapValueHandler.INSTANCE
   );
   public static final PropertyMapProperty COMPONENT_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Component Properties", "Tab component properties.", ComponentProperties.PROPERTIES
   );
   public static final PropertyMapProperty SHAPED_PANEL_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Shaped Panel Properties", "Tab shaped panel properties.", ShapedPanelProperties.PROPERTIES
   );

   public TitledTabStateProperties() {
      super(PROPERTIES);
   }

   public TitledTabStateProperties(PropertyMap var1) {
      super(var1);
   }

   public TitledTabStateProperties(TitledTabStateProperties var1) {
      super(PropertyMapFactory.create(var1.getMap()));
   }

   public TitledTabStateProperties addSuperObject(TitledTabStateProperties var1) {
      this.getMap().addSuperMap(var1.getMap());
      return this;
   }

   public TitledTabStateProperties removeSuperObject() {
      this.getMap().removeSuperMap();
      return this;
   }

   public TitledTabStateProperties removeSuperObject(TitledTabStateProperties var1) {
      this.getMap().removeSuperMap(var1.getMap());
      return this;
   }

   public TitledTabStateProperties setIcon(Icon var1) {
      ICON.set(this.getMap(), var1);
      return this;
   }

   public Icon getIcon() {
      return ICON.get(this.getMap());
   }

   public TitledTabStateProperties setText(String var1) {
      TEXT.set(this.getMap(), var1);
      return this;
   }

   public String getText() {
      return TEXT.get(this.getMap());
   }

   public TitledTabStateProperties setIconTextGap(int var1) {
      ICON_TEXT_GAP.set(this.getMap(), var1);
      return this;
   }

   public int getIconTextGap() {
      return ICON_TEXT_GAP.get(this.getMap());
   }

   public TitledTabStateProperties setToolTipText(String var1) {
      TOOL_TIP_TEXT.set(this.getMap(), var1);
      return this;
   }

   public String getToolTipText() {
      return TOOL_TIP_TEXT.get(this.getMap());
   }

   public TitledTabStateProperties setToolTipEnabled(boolean var1) {
      TOOL_TIP_ENABLED.set(this.getMap(), var1);
      return this;
   }

   public boolean getToolTipEnabled() {
      return TOOL_TIP_ENABLED.get(this.getMap());
   }

   public TitledTabStateProperties setIconVisible(boolean var1) {
      ICON_VISIBLE.set(this.getMap(), var1);
      return this;
   }

   public boolean getIconVisible() {
      return ICON_VISIBLE.get(this.getMap());
   }

   public TitledTabStateProperties setTextVisible(boolean var1) {
      TEXT_VISIBLE.set(this.getMap(), var1);
      return this;
   }

   public boolean getTextVisible() {
      return TEXT_VISIBLE.get(this.getMap());
   }

   public TitledTabStateProperties setTitleComponentVisible(boolean var1) {
      TITLE_COMPONENT_VISIBLE.set(this.getMap(), var1);
      return this;
   }

   public boolean getTitleComponentVisible() {
      return TITLE_COMPONENT_VISIBLE.get(this.getMap());
   }

   public TitledTabStateProperties setHorizontalAlignment(Alignment var1) {
      HORIZONTAL_ALIGNMENT.set(this.getMap(), var1);
      return this;
   }

   public Alignment getHorizontalAlignment() {
      return HORIZONTAL_ALIGNMENT.get(this.getMap());
   }

   public TitledTabStateProperties setVerticalAlignment(Alignment var1) {
      VERTICAL_ALIGNMENT.set(this.getMap(), var1);
      return this;
   }

   public Alignment getVerticalAlignment() {
      return VERTICAL_ALIGNMENT.get(this.getMap());
   }

   public TitledTabStateProperties setIconTextRelativeAlignment(Alignment var1) {
      ICON_TEXT_RELATIVE_ALIGNMENT.set(this.getMap(), var1);
      return this;
   }

   public Alignment getIconTextRelativeAlignment() {
      return ICON_TEXT_RELATIVE_ALIGNMENT.get(this.getMap());
   }

   public TitledTabStateProperties setTextTitleComponentGap(int var1) {
      TEXT_TITLE_COMPONENT_GAP.set(this.getMap(), var1);
      return this;
   }

   public int getTextTitleComponentGap() {
      return TEXT_TITLE_COMPONENT_GAP.get(this.getMap());
   }

   public TitledTabStateProperties setTitleComponentTextRelativeAlignment(Alignment var1) {
      TITLE_COMPONENT_TEXT_RELATIVE_ALIGNMENT.set(this.getMap(), var1);
      return this;
   }

   public Alignment getTitleComponentTextRelativeAlignment() {
      return TITLE_COMPONENT_TEXT_RELATIVE_ALIGNMENT.get(this.getMap());
   }

   public TitledTabStateProperties setDirection(Direction var1) {
      DIRECTION.set(this.getMap(), var1);
      return this;
   }

   public Direction getDirection() {
      return DIRECTION.get(this.getMap());
   }

   public ComponentProperties getComponentProperties() {
      return new ComponentProperties(COMPONENT_PROPERTIES.get(this.getMap()));
   }

   public ShapedPanelProperties getShapedPanelProperties() {
      return new ShapedPanelProperties(SHAPED_PANEL_PROPERTIES.get(this.getMap()));
   }
}
