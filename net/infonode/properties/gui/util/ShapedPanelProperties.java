package net.infonode.properties.gui.util;

import net.infonode.gui.componentpainter.ComponentPainter;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapContainer;
import net.infonode.properties.propertymap.PropertyMapFactory;
import net.infonode.properties.propertymap.PropertyMapGroup;
import net.infonode.properties.propertymap.PropertyMapValueHandler;
import net.infonode.properties.types.BooleanProperty;
import net.infonode.properties.types.ComponentPainterProperty;
import net.infonode.properties.types.DirectionProperty;
import net.infonode.util.Direction;

public class ShapedPanelProperties extends PropertyMapContainer {
   public static final PropertyMapGroup PROPERTIES = new PropertyMapGroup("Shaped Panel Properties", "");
   public static final BooleanProperty OPAQUE = new BooleanProperty(
      PROPERTIES, "Opaque", "If true the shaped panel is opaque. If false the shaped panel is transparent", PropertyMapValueHandler.INSTANCE
   );
   public static final BooleanProperty HORIZONTAL_FLIP = new BooleanProperty(
      PROPERTIES,
      "Horizontal Flip",
      "If true the shaped panel is flipped horizontally. Used by ComponentPainter's, ShapedBorder's etc.",
      PropertyMapValueHandler.INSTANCE
   );
   public static final BooleanProperty VERTICAL_FLIP = new BooleanProperty(
      PROPERTIES,
      "Vertical Flip",
      "If true the shaped panel is flipped vertically. Used by ComponentPainter's, ShapedBorder's etc.",
      PropertyMapValueHandler.INSTANCE
   );
   public static final BooleanProperty CLIP_CHILDREN = new BooleanProperty(
      PROPERTIES, "Clip Children", "If true the child components of the shaped panel are clipped with the border shape.", PropertyMapValueHandler.INSTANCE
   );
   public static final ComponentPainterProperty COMPONENT_PAINTER = new ComponentPainterProperty(
      PROPERTIES, "Component Painter", "The component painter that paints the shaped panel background.", PropertyMapValueHandler.INSTANCE
   );
   public static final DirectionProperty DIRECTION = new DirectionProperty(
      PROPERTIES, "Direction", "The direction of the shaped panel. Used by ComponentPainter's, ShapedBorder's etc.", PropertyMapValueHandler.INSTANCE
   );

   public ShapedPanelProperties() {
      super(PROPERTIES);
   }

   public ShapedPanelProperties(PropertyMap var1) {
      super(var1);
   }

   public ShapedPanelProperties(ShapedPanelProperties var1) {
      super(PropertyMapFactory.create(var1.getMap()));
   }

   public ShapedPanelProperties addSuperObject(ShapedPanelProperties var1) {
      this.getMap().addSuperMap(var1.getMap());
      return this;
   }

   public ShapedPanelProperties removeSuperObject() {
      this.getMap().removeSuperMap();
      return this;
   }

   public ShapedPanelProperties removeSuperObject(ShapedPanelProperties var1) {
      this.getMap().removeSuperMap(var1.getMap());
      return this;
   }

   public ShapedPanelProperties setOpaque(boolean var1) {
      OPAQUE.set(this.getMap(), var1);
      return this;
   }

   public boolean getOpaque() {
      return OPAQUE.get(this.getMap());
   }

   public ShapedPanelProperties setHorizontalFlip(boolean var1) {
      HORIZONTAL_FLIP.set(this.getMap(), var1);
      return this;
   }

   public boolean getHorizontalFlip() {
      return HORIZONTAL_FLIP.get(this.getMap());
   }

   public ShapedPanelProperties setVerticalFlip(boolean var1) {
      VERTICAL_FLIP.set(this.getMap(), var1);
      return this;
   }

   public boolean getVerticalFlip() {
      return VERTICAL_FLIP.get(this.getMap());
   }

   public ShapedPanelProperties setClipChildren(boolean var1) {
      CLIP_CHILDREN.set(this.getMap(), var1);
      return this;
   }

   public boolean getClipChildren() {
      return CLIP_CHILDREN.get(this.getMap());
   }

   public ShapedPanelProperties setComponentPainter(ComponentPainter var1) {
      COMPONENT_PAINTER.set(this.getMap(), var1);
      return this;
   }

   public ComponentPainter getComponentPainter() {
      return COMPONENT_PAINTER.get(this.getMap());
   }

   public ShapedPanelProperties setDirection(Direction var1) {
      DIRECTION.set(this.getMap(), var1);
      return this;
   }

   public Direction getDirection() {
      return DIRECTION.get(this.getMap());
   }

   static {
      ShapedPanelProperties var0 = new ShapedPanelProperties(PROPERTIES.getDefaultMap());
      var0.setHorizontalFlip(false).setVerticalFlip(false).setComponentPainter(null).setDirection(Direction.RIGHT);
   }
}
