package net.infonode.docking.theme;

import net.infonode.docking.properties.RootWindowProperties;

public abstract class DockingWindowsTheme {
   public abstract String getName();

   public abstract RootWindowProperties getRootWindowProperties();

   protected DockingWindowsTheme() {
      super();
   }

   public String toString() {
      return this.getName();
   }
}
