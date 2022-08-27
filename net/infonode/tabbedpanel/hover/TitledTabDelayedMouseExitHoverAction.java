package net.infonode.tabbedpanel.hover;

import javax.swing.SwingUtilities;
import net.infonode.gui.hover.HoverEvent;
import net.infonode.gui.hover.HoverListener;
import net.infonode.gui.hover.action.DelayedHoverExitAction;
import net.infonode.tabbedpanel.TabbedPanel;
import net.infonode.tabbedpanel.titledtab.TitledTab;
import net.infonode.tabbedpanel.titledtab.TitledTabProperties;

public class TitledTabDelayedMouseExitHoverAction implements HoverListener {
   private DelayedHoverExitAction delayedAction;
   private HoverListener hoverListener;

   public TitledTabDelayedMouseExitHoverAction(int var1, HoverListener var2) {
      super();
      this.hoverListener = var2;
      this.delayedAction = new DelayedHoverExitAction(new TitledTabDelayedMouseExitHoverAction$1(this), var1);
   }

   public HoverListener getHoverListener() {
      return this.hoverListener;
   }

   public TitledTabProperties getTitledTabProperties() {
      return this.getHoverListener() instanceof TitledTabHoverAction ? ((TitledTabHoverAction)this.getHoverListener()).getTitledTabProperties() : null;
   }

   public void mouseEntered(HoverEvent var1) {
      this.delayedAction.mouseEntered(var1);
   }

   public void mouseExited(HoverEvent var1) {
      TitledTab var2 = (TitledTab)var1.getSource();
      TabbedPanel var3 = var2.getTabbedPanel();
      this.delayedAction.mouseExited(var1);
      SwingUtilities.invokeLater(new TitledTabDelayedMouseExitHoverAction$2(this, var2, var3));
   }
}
