package net.infonode.gui.componentpainter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import net.infonode.util.Direction;

public abstract class AbstractComponentPainterWrapper extends AbstractComponentPainter {
   private static final long serialVersionUID = 1L;
   private ComponentPainter painter;

   protected AbstractComponentPainterWrapper(ComponentPainter var1) {
      super();
      this.painter = var1;
   }

   public ComponentPainter getPainter() {
      return this.painter;
   }

   public Color getColor(Component var1) {
      return this.painter.getColor(var1);
   }

   public boolean isOpaque(Component var1) {
      return this.painter.isOpaque(var1);
   }

   public void paint(Component var1, Graphics var2, int var3, int var4, int var5, int var6, Direction var7, boolean var8, boolean var9) {
      this.painter.paint(var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }
}
