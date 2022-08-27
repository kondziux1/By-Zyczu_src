package net.infonode.gui.colorprovider;

import java.awt.Color;
import java.io.ObjectStreamException;

public class FixedColorProvider extends AbstractColorProvider {
   private static final long serialVersionUID = 1L;
   public static final FixedColorProvider BLACK = new FixedColorProvider(Color.BLACK);
   public static final FixedColorProvider WHITE = new FixedColorProvider(Color.WHITE);
   private final Color color;

   public FixedColorProvider(Color var1) {
      super();
      this.color = var1;
   }

   public Color getColor() {
      return this.color;
   }

   protected Object readResolve() throws ObjectStreamException {
      return this.color.equals(Color.BLACK) ? BLACK : this;
   }
}
