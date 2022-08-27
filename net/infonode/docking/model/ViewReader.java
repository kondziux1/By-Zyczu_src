package net.infonode.docking.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.SplitWindow;
import net.infonode.docking.TabWindow;
import net.infonode.docking.View;
import net.infonode.docking.internal.ReadContext;

public interface ViewReader {
   ViewItem readViewItem(ObjectInputStream var1, ReadContext var2) throws IOException;

   View readView(ObjectInputStream var1, ReadContext var2) throws IOException;

   TabWindow createTabWindow(DockingWindow[] var1, TabWindowItem var2);

   SplitWindow createSplitWindow(DockingWindow var1, DockingWindow var2, SplitWindowItem var3);

   WindowItem readWindowItem(ObjectInputStream var1, ReadContext var2) throws IOException;
}
