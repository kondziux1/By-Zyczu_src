package net.infonode.properties.propertymap;

public class PropertyMapFactory {
   private PropertyMapFactory() {
      super();
   }

   public static PropertyMap create(PropertyMapGroup var0) {
      return new PropertyMapImpl(var0);
   }

   public static PropertyMap create(PropertyMap var0) {
      return new PropertyMapImpl((PropertyMapImpl)var0);
   }
}
