package net.infonode.docking.properties;

import net.infonode.gui.DimensionProvider;
import net.infonode.gui.hover.HoverListener;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapContainer;
import net.infonode.properties.propertymap.PropertyMapFactory;
import net.infonode.properties.propertymap.PropertyMapGroup;
import net.infonode.properties.propertymap.PropertyMapProperty;
import net.infonode.properties.propertymap.PropertyMapValueHandler;
import net.infonode.properties.types.BooleanProperty;
import net.infonode.properties.types.DimensionProviderProperty;
import net.infonode.properties.types.DirectionProperty;
import net.infonode.properties.types.HoverListenerProperty;
import net.infonode.properties.types.IntegerProperty;
import net.infonode.util.Direction;

public class ViewTitleBarProperties extends PropertyMapContainer {
   public static final PropertyMapGroup PROPERTIES = new PropertyMapGroup("View Title Bar Properties", "");
   public static final PropertyMapProperty NORMAL_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Normal Properties", "Properties for title bar.", ViewTitleBarStateProperties.PROPERTIES
   );
   public static final PropertyMapProperty FOCUSED_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "Focused Properties", "Properties for title bar.", ViewTitleBarStateProperties.PROPERTIES
   );
   public static final BooleanProperty VISIBLE = new BooleanProperty(
      PROPERTIES, "Visible", "Controls if the view title bar should be visible or not", PropertyMapValueHandler.INSTANCE
   );
   public static final DimensionProviderProperty MINIMUM_SIZE_PROVIDER = new DimensionProviderProperty(
      PROPERTIES, "Minimum Size", "Title bar minimum size.", PropertyMapValueHandler.INSTANCE
   );
   public static final IntegerProperty CONTENT_TITLE_BAR_GAP = IntegerProperty.createPositive(
      PROPERTIES, "Content Title Bar Gap", "Gap in pixels between the view content and the title bar", 2, PropertyMapValueHandler.INSTANCE
   );
   public static final DirectionProperty ORIENTATION = new DirectionProperty(
      PROPERTIES, "Orientation", "Title bar's orientation relative to the view's content area.", PropertyMapValueHandler.INSTANCE
   );
   public static final DirectionProperty DIRECTION = new DirectionProperty(
      PROPERTIES, "Direction", "Title bar's layout direction.", PropertyMapValueHandler.INSTANCE
   );
   public static final HoverListenerProperty HOVER_LISTENER = new HoverListenerProperty(
      PROPERTIES, "Hover Listener", "Hover Listener to be used for tracking mouse hovering over the title bar.", PropertyMapValueHandler.INSTANCE
   );

   public ViewTitleBarProperties() {
      super(PropertyMapFactory.create(PROPERTIES));
   }

   public ViewTitleBarProperties(PropertyMap var1) {
      super(var1);
   }

   public ViewTitleBarProperties(ViewTitleBarProperties var1) {
      super(PropertyMapFactory.create(var1.getMap()));
   }

   public ViewTitleBarProperties addSuperObject(ViewTitleBarProperties var1) {
      this.getMap().addSuperMap(var1.getMap());
      return this;
   }

   public ViewTitleBarProperties removeSuperObject(ViewTitleBarProperties var1) {
      this.getMap().removeSuperMap(var1.getMap());
      return this;
   }

   public ViewTitleBarStateProperties getNormalProperties() {
      return new ViewTitleBarStateProperties(NORMAL_PROPERTIES.get(this.getMap()));
   }

   public ViewTitleBarStateProperties getFocusedProperties() {
      return new ViewTitleBarStateProperties(FOCUSED_PROPERTIES.get(this.getMap()));
   }

   public ViewTitleBarProperties setVisible(boolean var1) {
      VISIBLE.set(this.getMap(), var1);
      return this;
   }

   public boolean getVisible() {
      return VISIBLE.get(this.getMap());
   }

   public ViewTitleBarProperties setMinimumSizeProvider(DimensionProvider var1) {
      MINIMUM_SIZE_PROVIDER.set(this.getMap(), var1);
      return this;
   }

   public DimensionProvider getMinimumSizeProvider() {
      return MINIMUM_SIZE_PROVIDER.get(this.getMap());
   }

   public ViewTitleBarProperties setContentTitleBarGap(int var1) {
      CONTENT_TITLE_BAR_GAP.set(this.getMap(), var1);
      return this;
   }

   public int getContentTitleBarGap() {
      return CONTENT_TITLE_BAR_GAP.get(this.getMap());
   }

   public ViewTitleBarProperties setOrientation(Direction var1) {
      ORIENTATION.set(this.getMap(), var1);
      return this;
   }

   public Direction getOrientation() {
      return ORIENTATION.get(this.getMap());
   }

   public ViewTitleBarProperties setDirection(Direction var1) {
      DIRECTION.set(this.getMap(), var1);
      return this;
   }

   public Direction getDirection() {
      return DIRECTION.get(this.getMap());
   }

   public ViewTitleBarProperties setHoverListener(HoverListener var1) {
      HOVER_LISTENER.set(this.getMap(), var1);
      return this;
   }

   public HoverListener getHoverListener() {
      return HOVER_LISTENER.get(this.getMap());
   }
}
