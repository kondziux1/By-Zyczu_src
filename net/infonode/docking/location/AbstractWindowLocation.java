package net.infonode.docking.location;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.RootWindow;
import net.infonode.docking.internalutil.InternalDockingUtil;
import net.infonode.util.IntList;

public abstract class AbstractWindowLocation implements WindowLocation {
   private WindowLocation parentLocation;
   private WeakReference window;

   protected abstract boolean set(DockingWindow var1, DockingWindow var2);

   protected AbstractWindowLocation(DockingWindow var1, WindowLocation var2) {
      super();
      this.window = new WeakReference(var1);
      this.parentLocation = var2;
   }

   protected AbstractWindowLocation() {
      super();
   }

   public boolean set(DockingWindow var1) {
      DockingWindow var2 = this.getWindow();
      if (var2 != null) {
         this.set(var2, var1);
      } else if (this.parentLocation != null) {
         this.parentLocation.set(var1);
      }

      return true;
   }

   private DockingWindow getWindow() {
      if (this.window == null) {
         return null;
      } else {
         DockingWindow var1 = (DockingWindow)this.window.get();
         return var1 != null && var1.getRootWindow() != null && !var1.isMinimized() ? var1 : null;
      }
   }

   public void write(ObjectOutputStream var1) throws IOException {
      var1.writeBoolean(this.parentLocation != null);
      if (this.parentLocation != null) {
         this.parentLocation.write(var1);
      }

      DockingWindow var2 = this.getWindow();
      var1.writeBoolean(var2 != null);
      if (var2 != null) {
         InternalDockingUtil.getWindowPath(var2).write(var1);
      }

   }

   protected void read(ObjectInputStream var1, RootWindow var2) throws IOException {
      this.parentLocation = var1.readBoolean() ? LocationDecoder.decode(var1, var2) : null;
      this.window = var1.readBoolean() ? new WeakReference(InternalDockingUtil.getWindow(var2, IntList.decode(var1))) : null;
   }
}
