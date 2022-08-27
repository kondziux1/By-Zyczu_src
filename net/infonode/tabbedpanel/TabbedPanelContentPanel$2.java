package net.infonode.tabbedpanel;

import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import net.infonode.gui.hover.HoverListener;
import net.infonode.gui.hover.panel.HoverableShapedPanel;
import net.infonode.tabbedpanel.internal.TabbedHoverUtil;

class TabbedPanelContentPanel$2 extends HoverableShapedPanel {
   TabbedPanelContentPanel$2(TabbedPanelContentPanel var1, LayoutManager var2, HoverListener var3, Component var4) {
      super(var2, var3, var4);
      this.this$0 = var1;
   }

   public boolean acceptHover(ArrayList var1) {
      return TabbedHoverUtil.acceptTabbedPanelHover(this.this$0.getTabbedPanel().getProperties().getHoverPolicy(), var1, this.this$0.getTabbedPanel(), this);
   }

   protected void processMouseEvent(MouseEvent var1) {
      super.processMouseEvent(var1);
      if (this.this$0.getTabbedPanel().hasContentArea()) {
         this.this$0.getTabbedPanel().doProcessMouseEvent(var1);
      } else {
         TabbedPanelContentPanel.access$300(this.this$0, SwingUtilities.convertMouseEvent(this, var1, this.this$0));
      }

   }

   protected void processMouseMotionEvent(MouseEvent var1) {
      super.processMouseMotionEvent(var1);
      if (this.this$0.getTabbedPanel().hasContentArea()) {
         this.this$0.getTabbedPanel().doProcessMouseMotionEvent(var1);
      } else {
         TabbedPanelContentPanel.access$400(this.this$0, SwingUtilities.convertMouseEvent(this, var1, this.this$0));
      }

   }
}
