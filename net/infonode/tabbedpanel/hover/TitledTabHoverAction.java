package net.infonode.tabbedpanel.hover;

import net.infonode.gui.hover.HoverEvent;
import net.infonode.gui.hover.HoverListener;
import net.infonode.tabbedpanel.titledtab.TitledTab;
import net.infonode.tabbedpanel.titledtab.TitledTabProperties;

public class TitledTabHoverAction implements HoverListener {
   private TitledTabProperties props;

   public TitledTabHoverAction() {
      this(new TitledTabProperties());
   }

   public TitledTabHoverAction(TitledTabProperties var1) {
      super();
      this.props = var1;
   }

   public TitledTabProperties getTitledTabProperties() {
      return this.props;
   }

   public void mouseEntered(HoverEvent var1) {
      ((TitledTab)var1.getSource()).getProperties().addSuperObject(this.props);
   }

   public void mouseExited(HoverEvent var1) {
      ((TitledTab)var1.getSource()).getProperties().removeSuperObject(this.props);
   }
}
