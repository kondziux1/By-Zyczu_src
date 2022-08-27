package net.infonode.gui.colorprovider;

import java.awt.Color;
import java.awt.Component;

public abstract class AbstractColorProvider implements ColorProvider {
   public AbstractColorProvider() {
      super();
   }

   public Color getColor() {
      return Color.BLACK;
   }

   public Color getColor(Component var1) {
      return this.getColor();
   }
}
