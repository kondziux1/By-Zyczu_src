package net.infonode.gui.laf.value;

import java.awt.Color;
import javax.swing.plaf.ColorUIResource;

public class ColorValue {
   private ColorUIResource color;
   private ColorUIResource defaultColor;

   public ColorValue() {
      this(Color.BLACK);
   }

   public ColorValue(int var1, int var2, int var3) {
      this(new ColorUIResource(var1, var2, var3));
   }

   public ColorValue(Color var1) {
      this(new ColorUIResource(var1));
   }

   public ColorValue(ColorUIResource var1) {
      super();
      this.defaultColor = var1;
   }

   public ColorUIResource getColor() {
      return this.color == null ? this.defaultColor : this.color;
   }

   public void setColor(Color var1) {
      this.setColor(new ColorUIResource(var1));
   }

   public void setColor(ColorUIResource var1) {
      this.color = var1;
   }

   public void setDefaultColor(Color var1) {
      this.setDefaultColor(new ColorUIResource(var1));
   }

   public void setDefaultColor(ColorUIResource var1) {
      this.defaultColor = var1;
   }

   public void setDefaultColor(ColorValue var1) {
      this.setDefaultColor(var1.getColor());
   }
}
