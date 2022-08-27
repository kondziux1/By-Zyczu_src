package net.infonode.properties.gui.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import net.infonode.gui.InsetsUtil;
import net.infonode.gui.panel.BaseContainer;
import net.infonode.gui.panel.BaseContainerUtil;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapContainer;
import net.infonode.properties.propertymap.PropertyMapFactory;
import net.infonode.properties.propertymap.PropertyMapGroup;
import net.infonode.properties.propertymap.PropertyMapValueHandler;
import net.infonode.properties.types.BorderProperty;
import net.infonode.properties.types.ColorProperty;
import net.infonode.properties.types.FontProperty;
import net.infonode.properties.types.InsetsProperty;
import net.infonode.util.Direction;

public class ComponentProperties extends PropertyMapContainer {
   public static final PropertyMapGroup PROPERTIES = new PropertyMapGroup("Component Properties", "");
   public static final BorderProperty BORDER = new BorderProperty(PROPERTIES, "Border", "Component border.", PropertyMapValueHandler.INSTANCE);
   public static final InsetsProperty INSETS = new InsetsProperty(
      PROPERTIES, "Insets", "Component insets inside the border.", PropertyMapValueHandler.INSTANCE
   );
   public static final ColorProperty FOREGROUND_COLOR = new ColorProperty(
      PROPERTIES, "Foreground Color", "Component foreground color.", PropertyMapValueHandler.INSTANCE
   );
   public static final FontProperty FONT = new FontProperty(PROPERTIES, "Font", "Component text font.", PropertyMapValueHandler.INSTANCE);
   public static final ColorProperty BACKGROUND_COLOR = new ColorProperty(
      PROPERTIES, "Background Color", "Component background color. A null value means that no background will be painted.", PropertyMapValueHandler.INSTANCE
   );

   public ComponentProperties() {
      super(PROPERTIES);
   }

   public ComponentProperties(PropertyMap var1) {
      super(var1);
   }

   public ComponentProperties(ComponentProperties var1) {
      super(PropertyMapFactory.create(var1.getMap()));
   }

   public ComponentProperties addSuperObject(ComponentProperties var1) {
      this.getMap().addSuperMap(var1.getMap());
      return this;
   }

   public ComponentProperties removeSuperObject() {
      this.getMap().removeSuperMap();
      return this;
   }

   public ComponentProperties removeSuperObject(ComponentProperties var1) {
      this.getMap().removeSuperMap(var1.getMap());
      return this;
   }

   public ComponentProperties setBorder(Border var1) {
      BORDER.set(this.getMap(), var1);
      return this;
   }

   public ComponentProperties setInsets(Insets var1) {
      INSETS.set(this.getMap(), var1);
      return this;
   }

   public ComponentProperties setBackgroundColor(Color var1) {
      BACKGROUND_COLOR.set(this.getMap(), var1);
      return this;
   }

   public Insets getInsets() {
      return INSETS.get(this.getMap());
   }

   public Border getBorder() {
      return BORDER.get(this.getMap());
   }

   public Color getBackgroundColor() {
      return BACKGROUND_COLOR.get(this.getMap());
   }

   public Font getFont() {
      return FONT.get(this.getMap());
   }

   public Color getForegroundColor() {
      return FOREGROUND_COLOR.get(this.getMap());
   }

   public ComponentProperties setForegroundColor(Color var1) {
      FOREGROUND_COLOR.set(this.getMap(), var1);
      return this;
   }

   public ComponentProperties setFont(Font var1) {
      FONT.set(this.getMap(), var1);
      return this;
   }

   public void applyTo(JComponent var1) {
      this.applyTo(var1, Direction.RIGHT);
   }

   public void applyTo(JComponent var1, Direction var2) {
      Insets var3 = this.getInsets() == null ? null : InsetsUtil.rotate(var2, this.getInsets());
      EmptyBorder var4 = var3 == null ? null : new EmptyBorder(var3);
      var1.setBorder((Border)(this.getBorder() == null ? var4 : (var4 == null ? this.getBorder() : new CompoundBorder(this.getBorder(), var4))));
      if (var1 instanceof BaseContainer) {
         BaseContainer var5 = (BaseContainer)var1;
         BaseContainerUtil.setOverridedBackground(var5, this.getBackgroundColor());
         BaseContainerUtil.setOverridedForeground(var5, this.getForegroundColor());
         BaseContainerUtil.setOverridedFont(var5, this.getFont());
      } else {
         var1.setBackground(this.getBackgroundColor());
         var1.setFont(this.getFont());
         var1.setForeground(this.getForegroundColor());
      }

   }

   static {
      ComponentProperties var0 = new ComponentProperties(PROPERTIES.getDefaultMap());
      var0.setBackgroundColor(null).setBorder(null).setInsets(null);
   }
}
