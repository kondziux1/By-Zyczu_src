package net.infonode.gui.colorprovider;

import java.awt.Color;
import java.awt.Component;
import java.io.Serializable;

public interface ColorProvider extends Serializable {
   Color getColor();

   Color getColor(Component var1);
}
