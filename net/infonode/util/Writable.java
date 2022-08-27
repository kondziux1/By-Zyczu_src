package net.infonode.util;

import java.io.IOException;
import java.io.ObjectOutputStream;

public interface Writable {
   void write(ObjectOutputStream var1) throws IOException;
}
