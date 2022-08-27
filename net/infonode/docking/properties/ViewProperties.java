package net.infonode.docking.properties;

import javax.swing.Icon;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.properties.propertymap.PropertyMapContainer;
import net.infonode.properties.propertymap.PropertyMapFactory;
import net.infonode.properties.propertymap.PropertyMapGroup;
import net.infonode.properties.propertymap.PropertyMapProperty;
import net.infonode.properties.propertymap.PropertyMapValueHandler;
import net.infonode.properties.types.BooleanProperty;
import net.infonode.properties.types.IconProperty;
import net.infonode.properties.types.StringProperty;

public class ViewProperties extends PropertyMapContainer {
   public static final PropertyMapGroup PROPERTIES = new PropertyMapGroup("View Properties", "");
   public static final PropertyMapProperty VIEW_TITLE_BAR_PROPERTIES = new PropertyMapProperty(
      PROPERTIES, "View Title Bar Properties", "Properties for view's title bar.", ViewTitleBarProperties.PROPERTIES
   );
   public static final BooleanProperty ALWAYS_SHOW_TITLE = new BooleanProperty(
      PROPERTIES, "Always Show Title", "If true the view will always be placed in a TabWindow so that it's title is shown.", PropertyMapValueHandler.INSTANCE
   );
   public static final StringProperty TITLE = new StringProperty(PROPERTIES, "Title", "The view title.", PropertyMapValueHandler.INSTANCE);
   public static final IconProperty ICON = new IconProperty(PROPERTIES, "Icon", "The view icon.", PropertyMapValueHandler.INSTANCE);

   public ViewProperties() {
      super(PropertyMapFactory.create(PROPERTIES));
   }

   public ViewProperties(PropertyMap var1) {
      super(var1);
   }

   public ViewProperties(ViewProperties var1) {
      super(PropertyMapFactory.create(var1.getMap()));
   }

   public ViewProperties addSuperObject(ViewProperties var1) {
      this.getMap().addSuperMap(var1.getMap());
      return this;
   }

   /** @deprecated */
   public ViewProperties removeSuperObject() {
      this.getMap().removeSuperMap();
      return this;
   }

   public ViewProperties removeSuperObject(ViewProperties var1) {
      this.getMap().removeSuperMap(var1.getMap());
      return this;
   }

   public ViewTitleBarProperties getViewTitleBarProperties() {
      return new ViewTitleBarProperties(VIEW_TITLE_BAR_PROPERTIES.get(this.getMap()));
   }

   public boolean getAlwaysShowTitle() {
      return ALWAYS_SHOW_TITLE.get(this.getMap());
   }

   public ViewProperties setAlwaysShowTitle(boolean var1) {
      ALWAYS_SHOW_TITLE.set(this.getMap(), var1);
      return this;
   }

   public ViewProperties setTitle(String var1) {
      TITLE.set(this.getMap(), var1);
      return this;
   }

   public ViewProperties setIcon(Icon var1) {
      ICON.set(this.getMap(), var1);
      return this;
   }

   public String getTitle() {
      return TITLE.get(this.getMap());
   }

   public Icon getIcon() {
      return ICON.get(this.getMap());
   }

   static {
      new ViewProperties(PROPERTIES.getDefaultMap()).setAlwaysShowTitle(true);
   }
}
