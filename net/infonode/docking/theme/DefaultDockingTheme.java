package net.infonode.docking.theme;

import net.infonode.docking.properties.RootWindowProperties;

public class DefaultDockingTheme extends DockingWindowsTheme {
   private RootWindowProperties rootWindowProperties = new RootWindowProperties();

   public DefaultDockingTheme() {
      super();
   }

   public String getName() {
      return "Default Theme";
   }

   public RootWindowProperties getRootWindowProperties() {
      return this.rootWindowProperties;
   }
}
