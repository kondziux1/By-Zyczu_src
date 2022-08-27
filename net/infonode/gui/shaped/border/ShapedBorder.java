package net.infonode.gui.shaped.border;

import java.awt.Component;
import java.awt.Shape;
import javax.swing.border.Border;

public interface ShapedBorder extends Border {
   Shape getShape(Component var1, int var2, int var3, int var4, int var5);
}
