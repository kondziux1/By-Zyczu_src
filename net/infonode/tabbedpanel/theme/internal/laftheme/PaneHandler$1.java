package net.infonode.tabbedpanel.theme.internal.laftheme;

import net.infonode.gui.DynamicUIManagerListener;

class PaneHandler$1 implements DynamicUIManagerListener {
   PaneHandler$1(PaneHandler var1) {
      super();
      this.this$0 = var1;
   }

   public void lookAndFeelChanged() {
      PaneHandler.access$000(this.this$0);
   }

   public void propertiesChanging() {
      PaneHandler.access$100(this.this$0).updating();
   }

   public void propertiesChanged() {
      PaneHandler.access$000(this.this$0);
   }

   public void lookAndFeelChanging() {
      PaneHandler.access$100(this.this$0).updating();
   }
}
