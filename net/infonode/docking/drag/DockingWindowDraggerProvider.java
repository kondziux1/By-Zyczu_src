package net.infonode.docking.drag;

import java.awt.event.MouseEvent;

public interface DockingWindowDraggerProvider {
   DockingWindowDragger getDragger(MouseEvent var1);
}
