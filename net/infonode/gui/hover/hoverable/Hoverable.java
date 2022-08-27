package net.infonode.gui.hover.hoverable;

import java.util.ArrayList;

public interface Hoverable {
   boolean acceptHover(ArrayList var1);

   void hoverEnter();

   void hoverExit();
}
