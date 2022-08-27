package net.infonode.gui.componentpainter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import net.infonode.util.Direction;

public interface ComponentPainter {
   void paint(Component var1, Graphics var2, int var3, int var4, int var5, int var6);

   void paint(Component var1, Graphics var2, int var3, int var4, int var5, int var6, Direction var7, boolean var8, boolean var9);

   boolean isOpaque(Component var1);

   Color getColor(Component var1);
}
