package net.infonode.tabbedpanel.hover;

import net.infonode.gui.hover.HoverEvent;
import net.infonode.gui.hover.HoverListener;
import net.infonode.tabbedpanel.TabbedPanel;
import net.infonode.tabbedpanel.TabbedPanelProperties;

public class TabbedPanelHoverAction implements HoverListener {
   private TabbedPanelProperties props;

   public TabbedPanelHoverAction() {
      this(new TabbedPanelProperties());
   }

   public TabbedPanelHoverAction(TabbedPanelProperties var1) {
      super();
      this.props = var1;
   }

   public TabbedPanelProperties getTabbedPanelProperties() {
      return this.props;
   }

   public void mouseEntered(HoverEvent var1) {
      ((TabbedPanel)var1.getSource()).getProperties().addSuperObject(this.props);
   }

   public void mouseExited(HoverEvent var1) {
      ((TabbedPanel)var1.getSource()).getProperties().removeSuperObject(this.props);
   }
}
