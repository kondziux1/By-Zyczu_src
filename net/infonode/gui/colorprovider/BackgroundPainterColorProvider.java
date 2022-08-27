package net.infonode.gui.colorprovider;

import java.awt.Color;
import java.awt.Component;
import java.io.ObjectStreamException;
import javax.swing.UIManager;
import net.infonode.gui.ComponentUtil;

public class BackgroundPainterColorProvider extends AbstractColorProvider {
   private static final long serialVersionUID = 1L;
   public static final BackgroundPainterColorProvider INSTANCE = new BackgroundPainterColorProvider();

   private BackgroundPainterColorProvider() {
      super();
   }

   public Color getColor(Component var1) {
      Color var2 = ComponentUtil.getBackgroundColor(var1);
      return var2 == null ? UIManager.getColor("control") : var2;
   }

   protected Object readResolve() throws ObjectStreamException {
      return INSTANCE;
   }
}
