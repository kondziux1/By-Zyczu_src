package net.infonode.tabbedpanel.theme.internal.laftheme;

class PaneUI$1 implements PaneHandlerListener {
   PaneUI$1(PaneUI var1) {
      super();
      this.this$0 = var1;
   }

   public void updating() {
      this.this$0.setEnabled(false);
      PaneUI.access$000(this.this$0).updating();
   }

   public void updated() {
      PaneUI.access$100(this.this$0);
      this.this$0.setEnabled(true);
      PaneUI.access$000(this.this$0).updated();
   }
}
