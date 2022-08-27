package net.infonode.docking.location;

import java.io.IOException;
import java.io.ObjectOutputStream;
import net.infonode.docking.DockingWindow;

public interface WindowLocation {
   boolean set(DockingWindow var1);

   void write(ObjectOutputStream var1) throws IOException;
}
