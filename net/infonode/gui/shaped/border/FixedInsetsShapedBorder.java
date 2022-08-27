package net.infonode.gui.shaped.border;

import java.awt.Component;
import java.awt.Insets;
import net.infonode.gui.shaped.ShapedUtil;

public class FixedInsetsShapedBorder extends AbstractShapedBorderWrapper {
   private static final long serialVersionUID = 1L;
   private Insets insets;

   public FixedInsetsShapedBorder(Insets var1, ShapedBorder var2) {
      super(var2);
      this.insets = var1;
   }

   public Insets getBorderInsets(Component var1) {
      return ShapedUtil.transformInsets(var1, this.insets);
   }
}
