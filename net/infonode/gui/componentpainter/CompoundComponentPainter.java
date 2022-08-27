package net.infonode.gui.componentpainter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import net.infonode.util.Direction;

public class CompoundComponentPainter extends AbstractComponentPainter {
   private static final long serialVersionUID = 1L;
   private ComponentPainter bottomPainter;
   private ComponentPainter topPainter;

   public CompoundComponentPainter(ComponentPainter var1, ComponentPainter var2) {
      super();
      this.bottomPainter = var1;
      this.topPainter = var2;
   }

   public void paint(Component var1, Graphics var2, int var3, int var4, int var5, int var6, Direction var7, boolean var8, boolean var9) {
      this.bottomPainter.paint(var1, var2, var3, var4, var5, var6, var7, var8, var9);
      this.topPainter.paint(var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   public boolean isOpaque(Component var1) {
      return this.bottomPainter.isOpaque(var1) || this.topPainter.isOpaque(var1);
   }

   public Color getColor(Component var1) {
      return this.topPainter.getColor(var1);
   }
}
