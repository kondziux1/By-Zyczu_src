package net.infonode.docking.theme;

class LookAndFeelDockingTheme$2 implements Runnable {
   LookAndFeelDockingTheme$2(LookAndFeelDockingTheme var1) {
      super();
      this.this$0 = var1;
   }

   public void run() {
      LookAndFeelDockingTheme.access$200()
         .getTabWindowProperties()
         .getTabbedPanelProperties()
         .removeSuperObject(LookAndFeelDockingTheme.access$100().getTabbedPanelProperties());
      LookAndFeelDockingTheme.access$200()
         .getTabWindowProperties()
         .getTabProperties()
         .getTitledTabProperties()
         .removeSuperObject(LookAndFeelDockingTheme.access$100().getTitledTabProperties());
      LookAndFeelDockingTheme.access$200().getMap().clear(true);
   }
}
