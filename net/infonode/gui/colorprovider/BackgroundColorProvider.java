package net.infonode.gui.colorprovider;

import java.awt.Color;
import java.awt.Component;
import java.io.ObjectStreamException;
import javax.swing.UIManager;

public class BackgroundColorProvider extends AbstractColorProvider {
   private static final long serialVersionUID = 1L;
   public static final BackgroundColorProvider INSTANCE = new BackgroundColorProvider();

   private BackgroundColorProvider() {
      super();
   }

   public Color getColor(Component var1) {
      Color var2 = var1.getBackground();
      return var2 == null ? UIManager.getColor("control") : var2;
   }

   protected Object readResolve() throws ObjectStreamException {
      return INSTANCE;
   }
}
