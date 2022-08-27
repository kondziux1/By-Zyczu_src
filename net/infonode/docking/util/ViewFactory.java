package net.infonode.docking.util;

import javax.swing.Icon;
import net.infonode.docking.View;

public interface ViewFactory {
   Icon getIcon();

   String getTitle();

   View createView();
}
