package net.infonode.gui.componentpainter;

import java.awt.Component;
import java.awt.Graphics;
import net.infonode.util.Direction;

public class FixedTransformComponentPainter extends AbstractComponentPainterWrapper {
   private static final long serialVersionUID = 1L;
   private Direction direction;
   private boolean horizontalFlip;
   private boolean verticalFlip;

   public FixedTransformComponentPainter(ComponentPainter var1) {
      this(var1, Direction.RIGHT);
   }

   public FixedTransformComponentPainter(ComponentPainter var1, Direction var2) {
      this(var1, var2, false, false);
   }

   public FixedTransformComponentPainter(ComponentPainter var1, Direction var2, boolean var3, boolean var4) {
      super(var1);
      this.direction = var2;
      this.horizontalFlip = var3;
      this.verticalFlip = var4;
   }

   public void paint(Component var1, Graphics var2, int var3, int var4, int var5, int var6) {
      super.paint(var1, var2, var3, var4, var5, var6, this.direction, this.horizontalFlip, this.verticalFlip);
   }

   public void paint(Component var1, Graphics var2, int var3, int var4, int var5, int var6, Direction var7, boolean var8, boolean var9) {
      this.paint(var1, var2, var3, var4, var5, var6);
   }
}
