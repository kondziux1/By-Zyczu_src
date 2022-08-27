package net.infonode.gui;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

public class EventUtil {
   private EventUtil() {
      super();
   }

   public static MouseEvent convert(MouseEvent var0, Component var1) {
      return convert(var0, var1, SwingUtilities.convertPoint((Component)var0.getSource(), var0.getPoint(), var1));
   }

   public static MouseEvent convert(MouseEvent var0, Component var1, Point var2) {
      return new MouseEvent(
         var1, var0.getID(), var0.getWhen(), var0.getModifiers(), var2.x, var2.y, var0.getClickCount(), var0.isPopupTrigger(), var0.getButton()
      );
   }
}
