package net.infonode.properties.base;

import java.util.ArrayList;

public class PropertyGroup {
   private PropertyGroup superGroup;
   private String name;
   private String description;
   private ArrayList properties = new ArrayList(10);

   public PropertyGroup(String var1, String var2) {
      super();
      this.name = var1;
      this.description = var2;
   }

   public PropertyGroup(PropertyGroup var1, String var2, String var3) {
      this(var2, var3);
      this.superGroup = var1;
   }

   public PropertyGroup getSuperGroup() {
      return this.superGroup;
   }

   public String getDescription() {
      return this.description;
   }

   public String getName() {
      return this.name;
   }

   public void addProperty(Property var1) {
      this.properties.add(var1);
   }

   public int getPropertyCount() {
      return this.properties.size();
   }

   public boolean hasProperty(Property var1) {
      return this.isA(var1.getGroup());
   }

   public Property getProperty(int var1) {
      return (Property)this.properties.get(var1);
   }

   public Property[] getProperties() {
      return (Property[])this.properties.toArray(new Property[this.properties.size()]);
   }

   public String toString() {
      return this.getName();
   }

   public Property getProperty(String var1) {
      for(int var2 = 0; var2 < this.getPropertyCount(); ++var2) {
         if (this.getProperty(var2).getName().equals(var1)) {
            return this.getProperty(var2);
         }
      }

      return this.superGroup == null ? null : this.superGroup.getProperty(var1);
   }

   private boolean isA(PropertyGroup var1) {
      return var1 == this || this.getSuperGroup() != null && this.getSuperGroup().isA(var1);
   }
}
