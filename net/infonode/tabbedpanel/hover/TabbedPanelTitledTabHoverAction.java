package net.infonode.tabbedpanel.hover;

import net.infonode.gui.hover.HoverEvent;
import net.infonode.gui.hover.HoverListener;
import net.infonode.tabbedpanel.Tab;
import net.infonode.tabbedpanel.TabAdapter;
import net.infonode.tabbedpanel.TabbedPanel;
import net.infonode.tabbedpanel.TabbedPanelProperties;
import net.infonode.tabbedpanel.titledtab.TitledTab;
import net.infonode.tabbedpanel.titledtab.TitledTabProperties;

public class TabbedPanelTitledTabHoverAction implements HoverListener {
   private TabbedPanelProperties tabbedPanelProperties;
   private TitledTabProperties titledTabProperties;
   private boolean onlyHighlighted;
   private TabAdapter tabListener = new TabbedPanelTitledTabHoverAction$1(this);

   public TabbedPanelTitledTabHoverAction() {
      this(false);
   }

   public TabbedPanelTitledTabHoverAction(boolean var1) {
      this(new TabbedPanelProperties(), new TitledTabProperties(), var1);
   }

   public TabbedPanelTitledTabHoverAction(TabbedPanelProperties var1, TitledTabProperties var2) {
      this(var1, var2, false);
   }

   public TabbedPanelTitledTabHoverAction(TabbedPanelProperties var1, TitledTabProperties var2, boolean var3) {
      super();
      this.tabbedPanelProperties = var1;
      this.titledTabProperties = var2;
      this.onlyHighlighted = !var3;
   }

   public TitledTabProperties getTitledTabProperties() {
      return this.titledTabProperties;
   }

   public TabbedPanelProperties getTabbedPanelProperties() {
      return this.tabbedPanelProperties;
   }

   public void mouseEntered(HoverEvent var1) {
      TabbedPanel var2 = (TabbedPanel)var1.getSource();
      var2.getProperties().addSuperObject(this.tabbedPanelProperties);
      var2.addTabListener(this.tabListener);
      if (var2.getHighlightedTab() != null && var2.getHighlightedTab() instanceof TitledTab) {
         this.applyTitledTabProperties(var2, (TitledTab)var2.getHighlightedTab());
      } else {
         this.applyTitledTabProperties(var2, null);
      }

   }

   public void mouseExited(HoverEvent var1) {
      TabbedPanel var2 = (TabbedPanel)var1.getSource();
      var2.getProperties().removeSuperObject(this.tabbedPanelProperties);
      var2.removeTabListener(this.tabListener);
      if (var2.getHighlightedTab() != null && var2.getHighlightedTab() instanceof TitledTab) {
         this.removeTitledTabProperties(var2, (TitledTab)var2.getHighlightedTab());
      } else {
         this.removeTitledTabProperties(var2, null);
      }

   }

   private void applyTitledTabProperties(TabbedPanel var1, TitledTab var2) {
      if (this.onlyHighlighted) {
         if (var2 != null) {
            var2.getProperties().addSuperObject(this.titledTabProperties);
         }
      } else {
         for(int var3 = 0; var3 < var1.getTabCount(); ++var3) {
            Tab var4 = var1.getTabAt(var3);
            if (var4 instanceof TitledTab) {
               ((TitledTab)var4).getProperties().addSuperObject(this.titledTabProperties);
            }
         }
      }

   }

   private void removeTitledTabProperties(TabbedPanel var1, TitledTab var2) {
      if (this.onlyHighlighted) {
         if (var2 != null) {
            var2.getProperties().removeSuperObject(this.titledTabProperties);
         }
      } else {
         for(int var3 = 0; var3 < var1.getTabCount(); ++var3) {
            Tab var4 = var1.getTabAt(var3);
            if (var4 instanceof TitledTab) {
               ((TitledTab)var4).getProperties().removeSuperObject(this.titledTabProperties);
            }
         }
      }

   }
}
