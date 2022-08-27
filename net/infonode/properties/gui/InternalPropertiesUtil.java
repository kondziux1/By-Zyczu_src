package net.infonode.properties.gui;

import net.infonode.gui.panel.BaseContainerUtil;
import net.infonode.gui.shaped.panel.ShapedPanel;
import net.infonode.properties.gui.util.ShapedPanelProperties;
import net.infonode.util.Direction;

public class InternalPropertiesUtil {
   private InternalPropertiesUtil() {
      super();
   }

   public static final void applyTo(ShapedPanelProperties var0, ShapedPanel var1) {
      applyTo(var0, var1, null);
   }

   public static final void applyTo(ShapedPanelProperties var0, ShapedPanel var1, Direction var2) {
      var1.setHorizontalFlip(var0.getHorizontalFlip());
      var1.setVerticalFlip(var0.getVerticalFlip());
      var1.setComponentPainter(var0.getComponentPainter());
      var1.setDirection(var2 == null ? var0.getDirection() : var2);
      var1.setClipChildren(var0.getClipChildren());
      BaseContainerUtil.setForcedOpaque(var1, var0.getOpaque());
   }
}
