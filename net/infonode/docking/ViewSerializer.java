package net.infonode.docking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface ViewSerializer {
   void writeView(View var1, ObjectOutputStream var2) throws IOException;

   View readView(ObjectInputStream var1) throws IOException;
}
