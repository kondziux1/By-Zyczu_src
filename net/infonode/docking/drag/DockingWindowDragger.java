package net.infonode.docking.drag;

import java.awt.event.MouseEvent;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.RootWindow;

public interface DockingWindowDragger {
   RootWindow getDropTarget();

   DockingWindow getDragWindow();

   void dragWindow(MouseEvent var1);

   void abortDrag();

   void dropWindow(MouseEvent var1);
}
