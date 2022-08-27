package net.infonode.docking.theme.internal.laftheme;

import net.infonode.gui.DynamicUIManagerListener;

class TitleBarUI$1 implements DynamicUIManagerListener {
   TitleBarUI$1(TitleBarUI var1) {
      super();
      this.this$0 = var1;
   }

   public void lookAndFeelChanged() {
      TitleBarUI.access$000(this.this$0);
   }

   public void propertiesChanging() {
      TitleBarUI.access$100(this.this$0).updating();
   }

   public void propertiesChanged() {
      TitleBarUI.access$000(this.this$0);
   }

   public void lookAndFeelChanging() {
      TitleBarUI.access$100(this.this$0).updating();
   }
}
