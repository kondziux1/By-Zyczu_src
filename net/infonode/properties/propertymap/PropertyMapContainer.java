package net.infonode.properties.propertymap;

public class PropertyMapContainer {
   private PropertyMap map;

   public PropertyMapContainer(PropertyMapGroup var1) {
      super();
      this.map = PropertyMapFactory.create(var1);
   }

   public PropertyMapContainer(PropertyMap var1) {
      super();
      this.map = var1;
   }

   public PropertyMap getMap() {
      return this.map;
   }
}
