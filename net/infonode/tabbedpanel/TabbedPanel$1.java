package net.infonode.tabbedpanel;

import java.awt.Container;
import java.awt.GridBagLayout;

class TabbedPanel$1 extends GridBagLayout {
   TabbedPanel$1(TabbedPanel var1) {
      super();
      this.this$0 = var1;
   }

   public void layoutContainer(Container var1) {
      TabbedPanel.access$100(this.this$0);
      super.layoutContainer(var1);
      if (TabbedPanel.access$200(this.this$0).isVisible()) {
         if (TabbedPanel.access$300(this.this$0).isHorizontal()) {
            if ((double)TabbedPanel.access$400(this.this$0).getHeight() < TabbedPanel.access$200(this.this$0).getPreferredSize().getHeight()) {
               TabbedPanel.access$500(this.this$0).setSize(TabbedPanel.access$500(this.this$0).getWidth(), 0);
               TabbedPanel.access$200(this.this$0).setSize(TabbedPanel.access$200(this.this$0).getWidth(), TabbedPanel.access$400(this.this$0).getHeight());
            }
         } else if ((double)TabbedPanel.access$400(this.this$0).getWidth() < TabbedPanel.access$200(this.this$0).getPreferredSize().getWidth()) {
            TabbedPanel.access$500(this.this$0).setSize(0, TabbedPanel.access$500(this.this$0).getHeight());
            TabbedPanel.access$200(this.this$0).setSize(TabbedPanel.access$400(this.this$0).getWidth(), TabbedPanel.access$200(this.this$0).getHeight());
         }
      }

      TabbedPanel.access$600(this.this$0);
   }
}
