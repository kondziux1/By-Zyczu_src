package net.infonode.docking.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import net.infonode.docking.View;
import net.infonode.docking.internal.WriteContext;

public interface ViewWriter {
   void writeWindowItem(WindowItem var1, ObjectOutputStream var2, WriteContext var3) throws IOException;

   void writeView(View var1, ObjectOutputStream var2, WriteContext var3) throws IOException;
}
