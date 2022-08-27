package net.infonode.docking;

import net.infonode.docking.properties.ViewTitleBarProperties;
import net.infonode.gui.hover.HoverEvent;
import net.infonode.gui.hover.HoverListener;
import net.infonode.tabbedpanel.TabListener;
import net.infonode.tabbedpanel.TabbedPanel;
import net.infonode.tabbedpanel.TabbedPanelProperties;
import net.infonode.tabbedpanel.hover.TabbedPanelTitledTabHoverAction;
import net.infonode.tabbedpanel.hover.TitledTabTabbedPanelHoverAction;
import net.infonode.tabbedpanel.titledtab.TitledTab;
import net.infonode.tabbedpanel.titledtab.TitledTabProperties;

public class TabWindowHoverAction implements HoverListener {
   private TabbedPanelProperties tabbedPanelProperties;
   private TitledTabProperties titledTabProperties;
   private ViewTitleBarProperties viewTitleBarProperties;
   private TabbedPanelTitledTabHoverAction tpTabAction;
   private TitledTabTabbedPanelHoverAction tabTpAction;
   private boolean titleBarPropsAdded = false;
   private TabListener tabListener = new TabWindowHoverAction$1(this);

   public TabWindowHoverAction() {
      this(new TabbedPanelProperties(), new TitledTabProperties(), new ViewTitleBarProperties());
   }

   public TabWindowHoverAction(TabbedPanelProperties var1, TitledTabProperties var2, ViewTitleBarProperties var3) {
      super();
      this.tabbedPanelProperties = var1;
      this.titledTabProperties = var2;
      this.viewTitleBarProperties = var3;
      this.tpTabAction = new TabbedPanelTitledTabHoverAction(var1, var2);
      this.tabTpAction = new TitledTabTabbedPanelHoverAction(var2, var1);
   }

   public TabbedPanelProperties getTabbedPanelProperties() {
      return this.tabbedPanelProperties;
   }

   public TitledTabProperties getTitledTabProperties() {
      return this.titledTabProperties;
   }

   public ViewTitleBarProperties getViewTitleBarProperties() {
      return this.viewTitleBarProperties;
   }

   public void mouseEntered(HoverEvent var1) {
      if (var1.getSource() instanceof TabbedPanel) {
         TabbedPanel var2 = (TabbedPanel)var1.getSource();
         var2.addTabListener(this.tabListener);
         if (var2.getSelectedTab() != null) {
            DockingWindow var3 = ((WindowTab)var2.getSelectedTab()).getWindow();
            if (var3 instanceof View) {
               this.addViewTitleBarProperties((View)var3);
            }
         }

         this.tpTabAction.mouseEntered(var1);
      } else if (var1.getSource() instanceof TitledTab) {
         WindowTab var4 = (WindowTab)var1.getSource();
         var4.addTabListener(this.tabListener);
         if (var4.isSelected() && var4.getWindow() instanceof View) {
            this.addViewTitleBarProperties((View)var4.getWindow());
         }

         this.tabTpAction.mouseEntered(var1);
      }

   }

   public void mouseExited(HoverEvent var1) {
      if (var1.getSource() instanceof TabbedPanel) {
         TabbedPanel var2 = (TabbedPanel)var1.getSource();
         var2.removeTabListener(this.tabListener);
         if (this.titleBarPropsAdded && var2.getSelectedTab() != null) {
            DockingWindow var3 = ((WindowTab)var2.getSelectedTab()).getWindow();
            if (var3 instanceof View) {
               this.removeViewTitleBarProperties((View)var3);
            }
         }

         this.tpTabAction.mouseExited(var1);
      } else if (var1.getSource() instanceof TitledTab) {
         WindowTab var4 = (WindowTab)var1.getSource();
         var4.removeTabListener(this.tabListener);
         if (this.titleBarPropsAdded && var4.getWindow() instanceof View) {
            this.removeViewTitleBarProperties((View)var4.getWindow());
         }

         this.tabTpAction.mouseExited(var1);
      }

   }

   private void addViewTitleBarProperties(View var1) {
      var1.getViewProperties().getViewTitleBarProperties().addSuperObject(this.viewTitleBarProperties);
      this.titleBarPropsAdded = true;
   }

   private void removeViewTitleBarProperties(View var1) {
      var1.getViewProperties().getViewTitleBarProperties().removeSuperObject(this.viewTitleBarProperties);
      this.titleBarPropsAdded = false;
   }
}
