package net.infonode.tabbedpanel.hover;

import net.infonode.gui.hover.HoverEvent;
import net.infonode.gui.hover.HoverListener;
import net.infonode.tabbedpanel.TabAdapter;
import net.infonode.tabbedpanel.TabbedPanel;
import net.infonode.tabbedpanel.TabbedPanelProperties;
import net.infonode.tabbedpanel.titledtab.TitledTab;
import net.infonode.tabbedpanel.titledtab.TitledTabProperties;

public class TitledTabTabbedPanelHoverAction implements HoverListener {
   private TabbedPanelProperties tabbedPanelProperties;
   private TitledTabProperties titledTabProperties;
   private boolean applied = false;
   private boolean onlyHighlighted;
   private TabAdapter tabListener = new TitledTabTabbedPanelHoverAction$1(this);

   public TitledTabTabbedPanelHoverAction() {
      this(new TitledTabProperties(), new TabbedPanelProperties());
   }

   public TitledTabTabbedPanelHoverAction(boolean var1) {
      this(new TitledTabProperties(), new TabbedPanelProperties(), var1);
   }

   public TitledTabTabbedPanelHoverAction(TitledTabProperties var1, TabbedPanelProperties var2) {
      this(var1, var2, false);
   }

   public TitledTabTabbedPanelHoverAction(TitledTabProperties var1, TabbedPanelProperties var2, boolean var3) {
      super();
      this.titledTabProperties = var1;
      this.tabbedPanelProperties = var2;
      this.onlyHighlighted = !var3;
   }

   public TitledTabProperties getTitledTabProperties() {
      return this.titledTabProperties;
   }

   public TabbedPanelProperties getTabbedPanelProperties() {
      return this.tabbedPanelProperties;
   }

   public void mouseEntered(HoverEvent var1) {
      TitledTab var2 = (TitledTab)var1.getSource();
      if (this.onlyHighlighted) {
         var2.addTabListener(this.tabListener);
         if (var2.isHighlighted()) {
            this.applyTabbedPanel(var2.getTabbedPanel());
         }
      } else {
         this.applyTabbedPanel(var2.getTabbedPanel());
      }

      var2.getProperties().addSuperObject(this.titledTabProperties);
   }

   public void mouseExited(HoverEvent var1) {
      TitledTab var2 = (TitledTab)var1.getSource();
      this.removeTabbedPanel(var2.getTabbedPanel());
      if (this.onlyHighlighted) {
         var2.removeTabListener(this.tabListener);
      }

      var2.getProperties().removeSuperObject(this.titledTabProperties);
   }

   private void applyTabbedPanel(TabbedPanel var1) {
      if (!this.applied) {
         var1.getProperties().addSuperObject(this.tabbedPanelProperties);
         this.applied = true;
      }

   }

   private void removeTabbedPanel(TabbedPanel var1) {
      if (this.applied) {
         var1.getProperties().removeSuperObject(this.tabbedPanelProperties);
         this.applied = false;
      }

   }
}
